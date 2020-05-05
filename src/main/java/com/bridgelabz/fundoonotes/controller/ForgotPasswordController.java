package com.bridgelabz.fundoonotes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.service.ForgotPasswordService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
public class ForgotPasswordController {

	@Autowired
	private ForgotPasswordService forgotService;
	
	@Autowired
	private Utility utility;
	
	private String email;
	
	@PostMapping("forgotpass")
	public String forgotPassword(@RequestBody ForgotDTO forgotdto)
	{
		
		if(forgotService.checkmail(forgotdto.getEmail()))
		{
			this.email=forgotdto.getEmail();
			forgotService.send(email);
			return "sending";
		}
		else
			{
			return "Email-id not Found!";
			}
		
	}
	
	@RequestMapping("changepass")
	public String changePassword(@RequestParam("jwt") String jwt) 
	{
		if(forgotService.checkJWT(jwt))
		return jwt;
		else
		return "JWT is not Valid! - Unauthorized User!";
	}
	
	@RequestMapping("newpass")
	public String newPassword(@RequestBody PasswordDTO password,HttpServletRequest request)
	{
	if(password.getNewpassword().equals(password.getConfirmnewpassword()))
	{
		String username=utility.getUsernameFromToken(request.getHeader("header"));
		System.out.println(username);
		forgotService.changePassword(password.getNewpassword(),username);
		return "Changed successfully!";
	}
	else
	{
		return "Password not matching!";
	}
	}
}
