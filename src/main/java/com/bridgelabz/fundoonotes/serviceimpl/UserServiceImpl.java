package com.bridgelabz.fundoonotes.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.RegisterDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.Users;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private Utility utility;

	@Override
	public boolean register(RegisterDTO userdto) {
		Users us = null;
		try {
			Users user = new Users();
			user.setFirstName(userdto.getFirstname());
			user.setLastName(userdto.getLastname());
			user.setMobileNumber(userdto.getMobileno());
			user.setEmailId(userdto.getEmail());
			user.setPassword(userdto.getPassword());
			us = userRepository.save(user);	
			kafkaTemplate.send("test5", us.getEmailId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (us != null)
			return true;
		else
			return false;
	}

	@KafkaListener(topics = "test5", groupId = "group5")
	public void consume(String message) {
		Users user = userRepository.findByEmail(message);
		String token = utility.generateToken(new User(user.getEmailId(),user.getPassword(), new ArrayList<>()));
		String url = "http://localhost:3000/verify/" + token;
		utility.sendEMail(message, "verifying email", url);
		kafkaTemplate.flush();
	}

	@KafkaListener(topics = "test4", groupId = "group5")
	public void consume2(String message) {
		Users user = userRepository.findByEmail(message);
		String jwt = utility.generateToken(new User(user.getEmailId(), user.getPassword(), new ArrayList<>()));
		String url = "http://localhost:3000/reset/" + jwt;
		utility.sendEMail(message, "changing password", url);
		kafkaTemplate.flush();
	}

	@Override
	public String login(LoginDTO logindto) throws LoginException {

		Users user = userRepository.findByEmail(logindto.getEmail());
		if (user == null) {
			throw new LoginException("Register Before Login!");
		}
		boolean x = utility.checkVerified(logindto.getEmail());
		if (x) {
			boolean a = logindto.getEmail().equals(user.getEmailId());
			boolean b = logindto.getPassword().equals(user.getPassword());
			if (a && b) {
				String token = utility
						.generateToken(new User(logindto.getEmail(), logindto.getPassword(), new ArrayList<>()));
				return token;
			} else
				throw new LoginException("Bad Credentials");
		} else
			throw new LoginException("You are not a Verified User!");

	}

	@Override
	public boolean verifyEmail(String jwt) throws JWTTokenException {
		try {
		    utility.validateToken(jwt);
			Integer I = userRepository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
			if (I != 0)
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			throw new JWTTokenException(HttpStatus.FORBIDDEN,"Not a Valid User",LocalDateTime.now());
		}
	}

	@Override
	public String resetPassword(String password, String jwt) throws JWTTokenException {
		if (utility.validateToken(jwt)) {
			userRepository.changepassword(password, utility.getUsernameFromToken(jwt));
			return null;
		} else {
			throw new JWTTokenException(HttpStatus.FORBIDDEN,"Not a Valid User",LocalDateTime.now());
		}
	}

	@Override
	public void forgotPassword(ForgotDTO forgotdto) {
		try {
			Users user = modelMapper.map(forgotdto, Users.class);
			kafkaTemplate.send("test4", user.getEmailId());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		Users user = userRepository.findByEmail(email);
		return new User(user.getEmailId(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByEmail(username);
		return new User(user.getEmailId(), user.getPassword(), new ArrayList<>());
	}

}
