
@Data
@NoArgsConstructor
@AllArgsConstructor
@slf4j
public class DistributionRequestBase {
	protected String txn_id;
	protected String requestType;
	protected List<HandlerLog> logs;
	protected Product Detail productDetail;

	public List<HandlerLog> getLogs () {
		return logs;
	}
		
	public void setLogs (List<HandlerLog> logs) {
		this.logs = logs;
	}
	
	public void addLog (HandlerLog log) {
		if (logs== null) {
			logs = new ArrayList<HandlerLog>();
		}
		logs.add(log);
	}
	
	public void addLog (String handlerName, String status, String logLevel, String message) { 
		HandlerLog log = new HandlerLog (handlerName, status, logLevel, message);
		addLog (log);
	}
}