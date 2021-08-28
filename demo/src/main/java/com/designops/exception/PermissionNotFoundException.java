package com.designops.exception;

public class PermissionNotFoundException extends RuntimeException {
	
	public PermissionNotFoundException(String message)
	{
		super(message);
	}
	
	public PermissionNotFoundException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public PermissionNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
