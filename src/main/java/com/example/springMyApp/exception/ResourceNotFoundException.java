package com.example.springMyApp.exception;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class ResourceNotFoundException extends Exception {
	
	private Object obj;
	private String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	


	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(final String message) {
		this.message=message;
	//	super(message);
	}
	
	
	public ResourceNotFoundException(String message,Object obj) {
		this.message=message;
		this.obj=obj;
	}

}
