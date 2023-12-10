package com.ms.datawise.distn.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io. PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public abstract class DatawiseException extends RuntimeException {
	
	private static final Logger LOG = LoggerFactory.getLogger (DatawiseException.class); 
	
	public static String stackTraceAsString (Throwable exception) {
		Writer result = new StringWriter();
		exception.printStackTrace (new PrintWriter (result)); 
		return result.toString();
	}
	
	public static String causeAsString (Throwable exception) { 
		StringBuilder builder = new StringBuilder();
		try {
			builder.append(exception.getMessage());
			Throwable cause exception.getCause();
			while (cause != null) {
				builder.append(" caused by {"cause.getClass() + ": + cause.getMessage() + "}"); 
				cause = cause.getCause();
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}	
		return builder.toString();
	}
	
	protected DatawiseException (Throwable nested) {
		super (nested);
	}
	
	protected DatawiseException (String message, Throwable nested) {
		super (message, nested);
	}
	
	protected DatawiseException (String message) {
		super (message);
	}
	
	protected DatawiseException (Throwable cause, String message, Object... arguments) { 
		super(String.format (message, arguments), cause);
	}

	protected DatawiseException (String message, Object... arguments) {
		super(String.format (message, arguments));
	}
}