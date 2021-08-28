package com.designops.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.designops.model.PermissionErrorResponse;

@ControllerAdvice
public class ArtifactExceptionHandler {
	
	@ExceptionHandler
    public ResponseEntity<ArtifactErrorResponse> handleException(ArtifactNotFoundException pne){
		ArtifactErrorResponse errorResponse = new ArtifactErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(pne.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ArtifactErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ArtifactErrorResponse> handleException(Exception ex){
    	ArtifactErrorResponse errorResponse = new ArtifactErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ArtifactErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
