
@Data 
@Getter
@Setter
@JsonInclude (JsonInclude. Include.NON_NULL)
//@JsonPropertyOrder ({ "headers", "entityMetaData", "statusCode", "statusCodeValue" }) 
public class ProductEntityMetaData {
	@JsonProperty("headers") 
	private Headers headers;

	@JsonProperty("entity")
	private EntityMetaData entity;

	@JsonProperty("status")
	private Integer status;

	@JsonIgnore
	private Map<String, Object> additional Properties = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditional Properties() {
		return this.additional Properties;
	}
	
	@JsonAnySetter
	public void setAdditional Property (String name, Object value) { 
		this.additional Properties.put (name, value);
	}
	
	@Data
	@Getter
	public static class EntityMetaData {
		@JsonProperty("headers") private Headers headers;
		@JsonProperty("body") private EntityBody body; 
		@JsonProperty("statusCode") private String statusCode;
		@JsonProperty("statusCodeValue")
		private Integer statusCodeValue;
	}
	
	
	@Data
	@Getter
	@JsonInclude (JsonInclude. Include. NON_NULL) 
	public static class EntityBody {
		@JsonProperty("status") 
		private String status;
		
		@JsonProperty("message") 
		private String message;
		
		@JsonProperty("reportsList")
		private List<Report> ReportList = null;
	}
	
	@Data
	@Getter
	@JsonInclude (JsonInclude. Include.NON_NULL) 
	public static class Report {
		@JsonProperty("uuid") 
		private String uuid;
		
		@JsonProperty("stagePath") 
		private String stagePath;
		
		@JsonProperty ("runTimestamp") 
		private String runTimestamp:
		
		@JsonProperty("documentType") 
		private String documentType:
		
		@JsonProperty("fileDownloadLink")
		private String fileDownloadLink;
	}
}