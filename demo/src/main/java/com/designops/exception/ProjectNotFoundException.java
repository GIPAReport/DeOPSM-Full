package com.designops.exception;

public class ProjectNotFoundException extends RuntimeException {
	
	public ProjectNotFoundException(String message)
	{
		super(message);
	}
	
	public ProjectNotFoundException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public ProjectNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
