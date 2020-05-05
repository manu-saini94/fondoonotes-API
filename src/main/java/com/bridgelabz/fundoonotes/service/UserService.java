package com.bridgelabz.fundoonotes.service;

import javax.security.auth.login.LoginException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.RegisterDTO;

public interface UserService {

	public boolean register(RegisterDTO userdto);

	public String login(LoginDTO logindto) throws LoginException;

	public boolean verifyEmail(String jwt) throws JWTTokenException;

	public String resetPassword(String password, String jwt) throws JWTTokenException;

	public void forgotPassword(ForgotDTO forgotdto);

	UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

}
