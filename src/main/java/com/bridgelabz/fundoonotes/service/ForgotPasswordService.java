package com.bridgelabz.fundoonotes.service;

public interface ForgotPasswordService {

	public void send(String email);
	public boolean checkmail(String email);
	
	public boolean checkJWT(String token);
	public void changePassword(String password,String username);
	
}
