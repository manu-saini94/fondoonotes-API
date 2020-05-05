package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDTO {

	@NotNull(message = " Mobile number cannot be empty ")
	private String mobileno;

	@NotNull(message = " First name cannot be empty")
	private String firstname;

	@NotNull(message = " Last name cannot be empty")
	private String lastname;

	@NotNull(message = " email cannot be empty")
	private String email;

	@NotNull
	private boolean emailverified;

	@NotNull(message = "password cannot be empty")
	@Size(min = 8, max = 20)
	private String password;

	@NotNull(message = "confirmation is necessary")
	private String passwordagain;

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailverified() {
		return emailverified;
	}

	public void setEmailverified(boolean emailverified) {
		this.emailverified = emailverified;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordagain() {
		return passwordagain;
	}

	public void setPasswordagain(String passwordagain) {
		this.passwordagain = passwordagain;
	}
}
