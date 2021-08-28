package com.designops.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.designops.model.PermissionErrorResponse;

@ControllerAdvice
public class PermissionExceptionHandler {
	
	@ExceptionHandler
    public ResponseEntity<PermissionErrorResponse> handleException(PermissionNotFoundException pne){
		PermissionErrorResponse errorResponse = new PermissionErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(pne.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<PermissionErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PermissionErrorResponse> handleException(Exception ex){
    	PermissionErrorResponse errorResponse = new PermissionErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<PermissionErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
