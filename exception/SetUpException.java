package com.ms.datawise.distn.exception;
import com.ms.datawise.distn.vo.DataValidatorMessage;

public class SetUpException extends RuntimeException {
	DataValidatorMessage validateMessage;
	public SetUpException() {
		super();
	}
	
	public SetUpException (String message, Throwable cause, boolean enable Suppression, boolean writableStackTrace) { 
		super (message, cause, enable Suppression, writableStackTrace);
	}
	
	public SetUpException (String message, Throwable cause) {
		super (message, cause);
	}
	
	public SetUpException (String message) {
		super (message);
	}


	public SetUpException (Throwable cause) {
		super (cause);
	}
	
	public DataValidatorMessage getValidateMessage() {
		return validateMessage;
	}
	
	public void setValidateMessage (DataValidatorMessage validateMessage) {
		this.validateMessage = validateMessage;
	}
}