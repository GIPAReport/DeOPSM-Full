package com.designops.exception;

public class ParameterNotFoundException extends RuntimeException {
	
	public ParameterNotFoundException(String message)
	{
		super(message);
	}
	
	public ParameterNotFoundException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public ParameterNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
