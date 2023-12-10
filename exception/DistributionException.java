package com.ms.datawise.distn.exception;

public class DistributionException extends Exception {

	private static final long serialVersionUID = -3422686630177847657L;
	
	public DistributionException(String msg) {
		super (msg);
	}
	
	public DistributionException (String msg, Throwable t) {
		super (msg, t);
	}
}