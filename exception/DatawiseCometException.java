package com.ms.datawise.distn.exception;

public class DatawiseCometException extends Exception {
	private static final long serialVersionUID = -3422686630177847657L;
	
	public Datawise CometException (String msg) {
		super (msg);
	}
	
	public DatawiseCometException (String msg, Throwable t) {
		super (msg, t);
	}
}