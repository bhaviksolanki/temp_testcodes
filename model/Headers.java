
@JsonInclude (JsonInclude. Include.NON_NULL)
@JsonPropertyOrder ({
})

public class Headers {
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
}