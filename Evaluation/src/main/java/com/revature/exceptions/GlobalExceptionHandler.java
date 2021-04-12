package com.revature.exceptions;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler(EvaluationException.class)
	public ResponseEntity<Object> handleEvaluationException(EvaluationException ex, WebRequest request){
		String bodyOfResponse = "Exception was handled.";
		bodyOfResponse = ex.getMessage();
		MDC.clear();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);		
	}

	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<Object> handleNoContentException(NoContentException ex, WebRequest request){
		String bodyOfResponse = "Exception was handled.";
		bodyOfResponse = ex.getMessage();
		MDC.clear();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NO_CONTENT, request);		
	}
}
