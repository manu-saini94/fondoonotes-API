package com.bridgelabz.fundoonotes.Exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
public class JWTTokenException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	HttpStatus code;
	private LocalDateTime time;


	public JWTTokenException(HttpStatus code,String message,LocalDateTime time) {
		//super(message);
		this.message = message;
		this.code=code;
		this.time=time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	
	
}



//public class JWTTokenException extends Exception
//{
//	
//	private static final long serialVersionUID = 1L;
//
//	public JWTTokenException(String message)
//	 {
//		 super(message);
//	 }
//
//	
//	
//	
//	
//}
