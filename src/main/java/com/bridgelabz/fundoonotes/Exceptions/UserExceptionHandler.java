package com.bridgelabz.fundoonotes.Exceptions;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.ExceptionResponse;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(JWTTokenException.class)
	public final ResponseEntity<ExceptionResponse> tokenException(JWTTokenException ex) {	
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(),ex.getMessage(),LocalDateTime.now());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}
	
	@ExceptionHandler(NoteException.class)
	public final ResponseEntity<ExceptionResponse> noteException(NoteException ex) {	
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(),ex.getMessage(),LocalDateTime.now());	
		return ResponseEntity.status(exp.getCode()).body(exp);
	}

}