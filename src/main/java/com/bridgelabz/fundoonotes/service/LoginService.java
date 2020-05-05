package com.bridgelabz.fundoonotes.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.bridgelabz.fundoonotes.dto.LoginDTO;

public interface LoginService {

	public boolean checkUser(LoginDTO logindto);
	public UserDetails logger(LoginDTO logindto);
	
}
