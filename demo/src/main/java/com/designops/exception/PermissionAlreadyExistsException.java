
package com.designops.exception;

public class PermissionAlreadyExistsException extends RuntimeException {
	
	public PermissionAlreadyExistsException(String message)
	{
		super(message);
	}
	
	public PermissionAlreadyExistsException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public PermissionAlreadyExistsException(Throwable cause)
	{
		super(cause);
	}

}