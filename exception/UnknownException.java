package com.ms.datawise.distn.exception;

public class UnknownException extends DatawiseException { 
	
	public UnknownException (Throwable nested) {
		super (nested);
	}
	
	public UnknownException (String message, Throwable nested) { 
		super (message, nested);
	}
	
	public UnknownException (String message) {
		super (message);
	}
	
	public UnknownException (Throwable cause, String message, Object... arguments) {
		super (cause, message, arguments);
	}
	
	public UnknownException (String message, Object... arguments) {
		super (message, arguments);
	}
}