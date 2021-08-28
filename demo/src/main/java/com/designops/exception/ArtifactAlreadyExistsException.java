
package com.designops.exception;

public class ArtifactAlreadyExistsException extends RuntimeException {
	
	public ArtifactAlreadyExistsException(String message)
	{
		super(message);
	}
	
	public ArtifactAlreadyExistsException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public ArtifactAlreadyExistsException(Throwable cause)
	{
		super(cause);
	}

}
