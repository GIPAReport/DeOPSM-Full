package com.designops.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.designops.model.RoleErrorResponse;

@ControllerAdvice
public class RoleExceptionHandler {
	
	@ExceptionHandler
    public ResponseEntity<RoleErrorResponse> handleException(RoleNotFoundException rne){
		RoleErrorResponse errorResponse = new RoleErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(rne.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<RoleErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RoleErrorResponse> handleException(Exception ex){
    	RoleErrorResponse errorResponse = new RoleErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<RoleErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
