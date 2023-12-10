
public interface IDistributionRequestHandler {
	
	List<String> getActionType();
	
	DistributionRequest process (DistributionRequest distributionRequest);
	
	public default boolean canProcess (DistributionRequest distributionRequest) {
		Optional DistributionRequest> requestWrap = Optional. ofNullable (distributionRequest);
		Optional<String> action = requestWrap.map(r->r.getAction());
		List<String> myList = getActionType();
		myList.stream().anyMatch (str -> "B".equals (str.trim()));
		return action. filter (a->myList.stream().anyMatch (str -> str.trim().equalsIgnoreCase (a)) ).isPresent();
	}
}