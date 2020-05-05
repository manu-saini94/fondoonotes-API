package com.bridgelabz.fundoonotes.response;

public class Response {

	private int messagecode;
	private String message;
	private  Object object;
	
	public Response(int messagecode, String message, Object object) {
		super();
		this.messagecode = messagecode;
		this.message = message;
		this.object = object;
	}
	
	public int getMessagecode() {
		return messagecode;
	}
	public void setMessagecode(int messagecode) {
		this.messagecode = messagecode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
