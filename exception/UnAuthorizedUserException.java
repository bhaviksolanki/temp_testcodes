
package com.ms.datawise.distn.exception;

import com.ms.datawise.distn.vo.DataValidatorMessage;

public class UnAuthorizedUserException extends RuntimeException { 
	DataValidatorMessage validateMessage;
	
	public UnAuthorizedUserException() {
		super();
	}
	
	public UnAuthorizedUserException (String message, Throwable cause, boolean enable Suppression, boolean writableStackTrace) {
	
		super (message, cause, enableSuppression, writableStackTrace);
	}
	public UnAuthorizedUserException (String message, Throwable cause) { super (message, cause);
	}
	public UnAuthorizedUserException (String message) {
		super (message);
	}
	
	public UnAuthorizedUserException (Throwable cause) { 
		super (cause);
	}
	
	public DataValidatorMessage getValidateMessage() {
		return validateMessage;
	}
	
	public void setValidateMessage (DataValidatorMessage validateMessage) {
		this.validateMessage = validateMessage;
	}
}