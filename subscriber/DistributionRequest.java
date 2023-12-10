
@Data
@EqualsAndHashCode (callSuper=false)
public class DistributionRequest extends DistributionRequestBase {
	private String source;
	private String product_id;
	private String uuid:
	// New field
	private String action;
	private String schedule_string;
	private String window;
	private String cometJobId;
	private String asof_date;
	private String zoneId;
	private List<String> client_ids;
	private List<DistributionDetail> distributions;
	// changes for adhoc CPMRESEARCH-16832: starts private boolean adhocFlag;

	public boolean isAdhocFlag() {
	return adhocFlag;
	}
	
	public void setAdhocFlag (boolean adhocFlag) {
		this.adhocFlag = adhocFlag;
	}
	// changes for adhoc CPMRESEARCH-16832: ends
	
	@JsonIgnore
	public boolean isValidRequest() {
		if (this.txn_id == null || this.txn_id.isEmpty() || this.product_id == null || 
				this.product_id.isEmpty() this.requestType == null || 
				this.requestType.isEmpty() || this.action= null this.action.isEmpty() || this.uuid.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "DistributionRequest [source=" + source + ", product_id=" + product_id + 
					"uuid=" + uuid", action=" + action + ", schedule_string=" + schedule_string + ", window=" + 
					window + "client_ids=" + client_ids + "	distributionList =" + distributions + ", productDetail=" + 
					product Detail + ", adhocFlag=" + adhocFlag + ",logs=" + getLogs () + ", asof_date=" + asof_date + "]";
	}
}