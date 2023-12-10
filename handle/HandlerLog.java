
public class HandlerLog {
	
	private String handlerName;
	private String status; 
	private String logLevel;
	private String message;
	private Properties properties;
	
	public HandlerLog (String handlerName, String status, String logLevel, String message) {
		super();
		this.handlerName = handlerName;
		this.status = status;
		this.logLevel = logLevel;
		this.message message;
		this.properties = new Properties ();
	}
	
	public String getHandlerName() {
		return handlerName;
	}
	
	public void setHandlerName (String handlerName) { 
		this.handlerName = handlerName;
	}
	
	public String getStatus () {
	
		return status;
	}
	
	public void setStatus (String status) {
	
		this.status
		status;
	}
	public String getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel (String logLevel) {
		this.logLevel = logLevel;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage (String message) { 
		this.message = message;
	}
	
	public Properties getProperties () {
		return properties;
	}
}