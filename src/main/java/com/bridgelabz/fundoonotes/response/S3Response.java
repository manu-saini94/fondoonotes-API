package com.bridgelabz.fundoonotes.response;


import lombok.Data;

@Data
public class S3Response {
	private int messagecode;
	private String message;
	private  Object object;
	
	public S3Response() {
	}
	
	public S3Response( int messagecode,String message) {
		super();
		this.message = message;
		this.messagecode = messagecode;
	}
	public S3Response(int messagecode,String message, String data) {
		super();
		this.message = message;
		this.messagecode = messagecode;
		this.object = data;
	}
	
	
	

}