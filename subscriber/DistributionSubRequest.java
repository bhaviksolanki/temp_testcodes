
@Data
public class DistributionSubRequest extends DistributionRequestBase {
	public DistributionSubRequest() {
		super();
	}
	
	public DistributionSubRequest (String txn_id, String requestType, Integer 			distribution_id, String action) {
		super();
		this.txn_id = txn_id;
		this.requestType = requestType;
		this.distribution_id = distribution_id;
		this.action action;
	}

	private Integer distribution_id;
	private String action;
	private String fileName;
	private String sourceFolder;
	private String destination Folder;
	private String requestType;
	private String jobId;
	private String applicationId;	
}