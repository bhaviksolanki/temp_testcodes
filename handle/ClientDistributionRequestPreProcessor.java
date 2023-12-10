
@Component
@Order (value = 2)
@Qualifier ("distributionOprator")
public class ClientDistributionRequestPreProcessor implements IDistributionRequestHandler {
	final private String REQUEST_TYPE_DISTN_REQ_SPLIT =
		DistributionAction.distributionAction.getName()+","
		+ DistributionAction.distributionV2Action.getName() + ","
		+ DistributionAction.checkfileAction.getName() + ","
		+ DistributionAction.sourceDirect DistributeAction.getName() + + DistributionAction.windowtime overAction.getName();
		

	private static final Logger LOGGER = LoggerFactory.getLogger (Client DistributionRequest PreProcessor.class);
	
	@Resource
	WindowSLADAO windowSLADAO;
	
	@Resource
	private IDataset DAO datasetDAO;
	// @Autowired
	// private EmailManager emailManager;
	
	@Autowired
	IEventLogDAO eventLogDao;
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList(REQUEST_TYPE_DISTN_REQ_SPLIT.split("\\s*,\\s*"));
	}
	
	/** Get Product details from DB and validate product, Populate product into the request,
	* adjust adhoc and polulate clientid & distribution list in request.
	*/
	@Override
	@Transactional (propagation =Propagation.REQUIRED, readOnly= true)
	public DistributionRequest process (DistributionRequest request) { 
		if (canProcess (request)) {
			LOGGER.info("Datawise Request | Validating product={} in the distribution Request={}", request.getProduct_id(), request);
			Product Detail product = this.datasetDAO.getProduct DetailById(request.getProduct_id()); validateProduct (product, request); 
			populateProduct (product, request); 
			adjustforAdhoc (product, request);
			populateDefaultDistributions (request);
		}
		return request;
	}
	
	
	private void validateProduct (Product Detail product, DistributionRequest request) {
		LOGGER.info("Datawise Request | validating product={}", request.getProduct_id()); 
		if (product === null) {
			EventLog eventLog = new EventLog (request.getTxn_id(), request.getProduct_id(), "3002",
				EventLogConst. DistEventMap.get("3002"), "FAIL", "Product doesn't exist in the DB", null);
			//eventLogDao.saveEventLog (eventLog);
			eventLogDao.saveEventLogAsync (eventLog);
			//emailManager.sendProductNotSetupExceptionEmail (request.getProduct_id());
		} else if (!isDeliverable (product)) {
			throw new IllegalArgumentException(" Datawise Request | Not a deliverable/avtive product : + request);
		}else{
	
			LOGGER.info("Datawise Request | product={} is a valid product", request.getProduct_id()); 
			EventLog event Log new Event Log (request.getTxn_id(), request.getProduct_id(), "3002", 
				EventLogConst. DistEventMap.get("3002"), "SUCCESS", "Product is valid", null); 
				//eventLogDao.saveEvent Log (event Log); 
			eventLogDao.saveEventLogAsync (eventLog);
		}
	}
	
	
	private void populate Product (Product Detail product, DistributionRequest request) { 
		request.setProduct Detail (product);
		LOGGER.info( "Datawise Request | Request Updated After populating product details for product={}, updated request={}", request.getProduct_id(), request);
	}
	
	private boolean isDeliverable (Product Detail product) {
		if (product.isActiveProduct()) {
			return true;
		}
		return false;
	}
	
	private void adjust forAdhoc (Product Detail product, DistributionRequest request) {
	//if client Id is provided for an adhoc window distribution it should behave as a precise sla 
		if((request.isAdhocFlag () && product != null) &&(!(isActiveWindow (product, request)) || !(request.getClient_ids ().isEmpty()))){ 
			product.setProductCategory(com.ms.datawise.distn.constants.DatasetConstants. PRECISE_SLA);
		}
	}
	
	
	private void populateDefaultDistributions (DistributionRequest request) {
		LOGGER.info( "Datawise Request | Request Updated After populating default distributions for product={}, updated request={}", 
			request.getProduct_id(), request);
		
		List<String> clients = request.getClient_ids();
		if (clients === null) {
			clients = new ArrayList<String>();
			request.setClient_ids (clients);
			List<DistributionDetail> distributions = request.getDistributions ();
		}
		
		if (distributions == null) {
			distributions = new ArrayList<DistributionDetail>(); 
			request.setDistributions (distributions);
		}
	}
	
	private boolean isActiveWindow (Product Detail product, DistributionRequest request) { 
		LOGGER.info("Datawise Request | Request Updated After checking for active window for product={}, updated request={}", 
			request.getProduct_id(), request); List<WindowDistributionLog> distributionLogEntities=windowSLADAO.getActiveDistributions();
		
		if (distributionLogEntities!= null) {
			Optional<WindowDistributionLog> entity = distributionLogEntities.stream()
			.filter (ent -> product.getProductId().equals (ent.getProductId()))
			.reduce((first, second) ->second);
		
			if (entity.isPresent () ) {
				request.setWindow (entity.get().getWindow()); return true;
			}
		}
		return false;
	}
}