package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.model.Users;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ForgotPasswordService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Utility utility;

	private String jwt;

	@Override
	public void send(String email) {

		sendMail(email);
	}

	public void sendMail(String email) {
		Users user = userRepository.findByEmail(email);
		String jwt = utility.generateToken(new User(user.getEmailId(), user.getPassword(), new ArrayList<>()));
		String url = "http://localhost:8080/changepass?jwt=" + jwt;
		utility.sendEMail(email, "changing password", url);
	}

	@Override
	public boolean checkmail(String email) {

		return userRepository.findByEmail(email) != null;
	}

	@Override
	public boolean checkJWT(String token) {

		return token.equals(jwt) && utility.validateToken(token);
	}

	@Override
	public void changePassword(String password, String username) {

		userRepository.changepassword(password, username);
	}

}
