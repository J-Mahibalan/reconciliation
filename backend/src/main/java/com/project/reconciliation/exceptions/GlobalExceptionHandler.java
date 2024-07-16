package com.project.reconciliation.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 @ExceptionHandler(FileNotFoundException.class)
	    public ResponseEntity<String> handleFileNotFoundException() {
	        return new ResponseEntity<String>("File not Found", HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(IOException.class)
	    public ResponseEntity<String> handleIOException() {
	        return new ResponseEntity<String>("IO Exception", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	
	@ExceptionHandler(DiscrepancyNotFoundException.class)
	public ResponseEntity<String> handleDiscrepancyNotFoundException() {
		return new ResponseEntity<String>("Discrepancy not found ",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BillingRecordNotFoundException.class)
	public ResponseEntity<String> handleBillingRecordNotFoundException() {
		return new ResponseEntity<String>("Bill not found ",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SubscriberNotFoundException.class)
	public ResponseEntity<String> handleSubscrriberNotFoundException() {
		return new ResponseEntity<String>("Subscriber not found ",HttpStatus.NOT_FOUND);
	}
}
