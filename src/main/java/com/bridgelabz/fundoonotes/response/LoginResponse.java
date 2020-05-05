package com.bridgelabz.fundoonotes.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class LoginResponse {

	@Autowired 
	Utility utility;
	
	public String getToken(UserDetails userdetails)
	{
		return utility.generateToken(userdetails);
	}
}
