package com.ms.datawise.distn.exception;

public class IngestionClientException extends Exception {
	private static final long serialVersionUID = -7783559924301397172L;
	
	public IngestionClientException (Throwable nested) {
		super (nested);
	}
	
	public IngestionClientException (String message, Throwable nested) { 
		super (message, nested);
	}
	
	public IngestionClientException (String message) {
		super (message);
	}
	
	public IngestionClientException (int status) {
		super("Ingestion Service returns status code : " + status);
	}
}