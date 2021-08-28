package com.designops.exception;

public class ArtifactNotFoundException extends RuntimeException {
	
	public ArtifactNotFoundException(String message)
	{
		super(message);
	}
	
	public ArtifactNotFoundException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public ArtifactNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
