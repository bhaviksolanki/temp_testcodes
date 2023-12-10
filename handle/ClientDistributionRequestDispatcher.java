
@Component
@Order (value = 20)
@Qualifier ("distributionOprator")
public class ClientDistributionRequestDispatcher implements IDistributionRequestHandler {
	
	final private String REQUEST_TYPE_DISTN_REQ_DISPATCH = "distribution";
	private static final Logger LOGGER = LoggerFactory.getLogger (ClientDistributionRequestDispatcher.class); 
	private static final String INTERNAL_CLIENTS = "file PushRequest, trigger PushRequest, dual PushRequest"; 
	private static final String DUAL_PUSH = "dualPushRequest";
	private static final String COMMA = ",";
	
	@Resource
	SubDistributionMessage Publisher subPublisher;
	
	@Autowired
	IEventLogDAO event LogDao;
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList (REQUEST_TYPE_DISTN_REQ_DISPATCH.split("\\s*,\\s*"));
	}
	
		
	@Override
	public DistributionRequest process (DistributionRequest request) {
		if (canProcess (request)) {
			LOGGER.info("Datawise Request | Putting Appropriate MQ Requests for each distribution based on Dist Type "); 
			String requestStr = JsonTransformer.transform (request);
			requestStr = requestStr.replace(":", "=");
			
			LOGGER.info ("Datawise Request | inputRequest={}", requestStr);
			
			List<Integer> distributions = request.getDistributions ().stream().map(distribution -> distribution.getDistributionId()).collect (Collectors.toList()); 
			LOGGER.info("Datawise Request | input request received for product={} asofDate={} clients={} distributions={}"
				, request.getProduct_id(), request.getAsof_date(), request.getClient_ids (), distributions);
			
			List<DistributionSubRequest> subRequestList generateMQRequests (request);
			
			List<String> requestStrl = JsonTransformer.transform (subRequestList);
			
			requestStrl.stream().forEach (str -> str.replace(":", "="));
			
			LOGGER.info("Datawise Request | request dispatched for distributions={}", requestStrl);
			
			LOGGER.info("Datawise Request | request dispatched for product = {} distributions={}, requestType={}", request.getProduct_id(), subRequestList.stream().map(distribution -> distribution.getDistribution_id()).collect (Collectors.toList()), subRequestList.stream().map (subRequest -> subRequest.getRequestType()).collect (Collectors.toList())); 
			
			List<String> jsonMsgList = JsonTransformer.transform (subRequestList); 
			
			//jsonMsgList.stream().map(s-> "subRequest : " +
			// s).peek (LOGGER:: info).count();
			long count = jsonMsgList.size();
			
			jsonMsgList.stream().peek (LOGGER: : info).forEach (subPublisher::publish);
			EventLog event LogEntity = new Event Log (request.getTxn_id(), request.getProduct_id(),"3013",
				EventLogConst. DistEventMap.get("3013"), "SUCCESS", "Number of published messages are "+count, null); 
			eventLogDao.saveEventLogAsync (eventLogEntity);
		}
		return request;
	}
	
		
	private List<Distribution SubRequest> generateMQRequests (DistributionRequest request) {
		List<DistributionSubRequest> reqList = new ArrayList<DistributionSubRequest>(); 
		List<Distribution Detail> distributions = request.getDistributions ();
		
		if (distributions == null || distributions.isEmpty()) {
			return reqList;
		}
		
		ProductDetail product = request.getProduct Detail();
		for (Distribution Detail dstn: distributions) {
			String requestType = getDistributionRequestType (dstn.getDistType());
			DistributionSubRequest subRequest = new DistributionSubRequest(request.getTxn_id(), requestType, dstn.getDistributionId(), null);
			
			String[] distHandleArray dstn.getDist Handle().split (COMMA);
			
			if (Arrays.asList(INTERNAL_CLIENTS.split(COMMA)).contains (requestType) && distHandleArray != null && dist HandleArray.length > 0) { 
				subRequest.setDestination Folder (dist HandleArray[0]); 
			}
			
			if (requestType.equals (DUAL_PUSH) && distHandleArray != null && dist HandleArray.length > 1) { 
				subRequest.setJobId (dist HandleArray[1]);
			}else{
				subRequest.setJobId (dstn.getDist Handle());
			}
			
			subRequest.setProduct Detail (product);
			subRequest.setSourceFolder (product.getSourcePath());
			String distFileName = File PatternTranslator.translateFilePattern (product.getDistFilePattern()); 
			subRequest.setFileName (distFileName);
			subRequest.setApplicationId("DWDSTN");
			reqList.add(subRequest);
		}
		return reqList;
	}
	
	
	private String getDistributionRequestType (String distributionType) {
		// TODO: refine this methode. there should be a reference db table for // the mapping
		if (distributionType == null || distributionType.isEmpty()) {
			throw new IllegalArgumentException("empty dsit type");
		}
		return distributionType + "Request";
	}
}