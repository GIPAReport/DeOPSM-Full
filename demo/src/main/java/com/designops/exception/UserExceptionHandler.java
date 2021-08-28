package com.designops.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.designops.model.UserErrorResponse;

@ControllerAdvice
public class UserExceptionHandler {
	
	@ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException rne){
		UserErrorResponse errorResponse = new UserErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(rne.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<UserErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception ex){
    	UserErrorResponse errorResponse = new UserErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<UserErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserAlreadyExistsException ex){
    	UserErrorResponse errorResponse = new UserErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<UserErrorResponse>(errorResponse,HttpStatus.CONFLICT);
    }

}
