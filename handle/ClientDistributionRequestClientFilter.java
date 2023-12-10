
@slf4j
@Component
@Order (value = 3)
@Qualifier ("distributionOprator")
public class ClientDistributionRequestClientFilter implements IDistributionRequestHandler{
	final private String REQUEST_TYPE_DISTN_REQ_SPLIT = DistributionAction.distributionAction.getName()+","
		+ DistributionAction.distributionV2Action.getName() + ","
		+ DistributionAction.sourceDirect DistributeAction.getName();
	
	private static final Logger LOGGER = LoggerFactory.getLogger (Client DistributionRequestClientFilter.class);
	
	@Resource
	IDistributionDAO distributionDAO;
	
	@Autowired
	IEventLogDAO event LogDao;
	
	@Override
	public List<String> getActionType() {
		// Split the string at delimiter "," including all pre and post white spaces.
		// Ex: The string "Apple, Orange, Banana" will be split to ["Apple", "Orange", "Banana"]
		return Arrays.asList (REQUEST_TYPE_DISTN_REQ_SPLIT.split("\\s*,\\s*"));
	}
	
		
	/* For the given Distribution request for the given product_id,
	* if the distribution request contain the client id of distribution detail list in DB,
	* then Log the event */
	@Override
	public DistributionRequest process (DistributionRequest request) {
		if (canProcess (request)) {
			LOGGER.info("Datawise Request | processing distribution request ={} for product {}", request,request.getProduct_id());
			
			String productId = request.getProduct_id();
			
			List<DistributionDetail> distributions = request.getDistributions();
			List<DistributionDetail> dstnList = distributionDAO.getActiveDistributionsByProduct (productId); LOGGER.info("Datawise Request | Filtering Distributions based on Client List.");
			
			for (Distribution Detail d: dstnList) {
				if (distInScope (request, d)) {
					/* Add the distribution detail into list if no clients or
					* client ids of the request contain the client id in the distribution detail. */ 
					distributions.add(d);
				}
			}
			
			if (distributions.isEmpty()) {
				String msg = "Distribution request completed | No active Distribution found for product " +productId; 
				LOGGER.info (msg);
				EventLog entity = new Event Log (request.getTxn_id(), request.getProduct_id(), "3003", EventLogConst.DistEventMap.get("3003"), "FAIL", msg, null);
				
				eventLogDao.saveEventLogAsync (entity);
				request.setAction (DistributionAction.dist succeedAction.getName());
			} else {
				EventLog entity = new Event Log (request.getTxn_id(), request.getProduct_id(), "3003", EventLogConst. DistEventMap.get("3003"), "SUCCESS", "", null); 
				eventLogDao.saveEventLogAsync (entity);
			}
			//String requestStr = JsonTransformer.transform (request);
			//LOGGER.info(" Datawise Request | Adding active distributions , updated request is {}", requestStr);
		}
		return request;
	}
	
	
	private boolean distInScope (DistributionRequest request, DistributionDetail distn) {
		List<String> requestedClients = request.getClient_ids();
		// TODO: need to check distribution activity once the flag is added.
		if (requestedClients == null || requestedClients.isEmpty() || requestedClients.contains (distn.getClientId())) { 
			return true;
		}
		return false;
	}
}