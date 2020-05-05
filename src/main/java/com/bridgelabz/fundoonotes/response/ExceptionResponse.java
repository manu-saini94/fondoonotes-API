package com.bridgelabz.fundoonotes.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private HttpStatus code;
	private String message;
	private LocalDateTime time;

	public ExceptionResponse(HttpStatus code, String message, LocalDateTime time) {
		super();
		this.code = code;
		this.message = message;
		this.time = time;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setMessagecode(HttpStatus code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
