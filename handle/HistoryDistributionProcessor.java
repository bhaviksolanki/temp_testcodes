
@Component @Order (value= 5)
@Qualifier ("distributionOprator")
public class HistoryDistributionProcessor implements IDistributionRequestHandler {
	final static public String ERROR_NO_SPECIFIC_CLIENT = "No specific client found for a history product distribution"; 
	final static public String ERROR_MORE_TAHN_ONE_CLIENT = "More than one client found for a history product distribution"; 
	final static public String ERROR_MORE_TAHN_ONE_DISTRIUBTION = "More than one distribution found for a history product distribution"; 
	final private String REQUEST_TYPE_DISTN_REQ_SPLIT = DistributionAction.distributionAction.getName() +","
		+ DistributionAction.checkfileAction.getName() + ","
		+ DistributionAction.windowtimeoverAction.getName();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryDistribution Processor.class);
	
	@Resource
	
	private IDatasetDAO dataset DAO;
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList(REQUEST_TYPE_DISTN_REQ_SPLIT.split("\\s*, \\s*"));
	}
	
	@Autowired
	IEventLogDAO eventLogDao;
	
	@Override
	public boolean canProcess (DistributionRequest distributionRequest) {
		if (IDistributionRequestHandler.super.canProcess (distributionRequest)
			&& "Y".equalsIgnoreCase (distributionRequest.getProduct Detail().getIsHistory())) { 
			return true;
		}
		return false;
	}
	
	
	@Override
	@Transactional (propagation = Propagation. REQUIRED, readonly= true)
	public DistributionRequest process (DistributionRequest request) {
		if (canProcess (request)) {
			LOGGER.info("Datawise Request | Processing History product ={} in the distribution Request ={}", request.getProduct_id(), request);
			adjustProductCategoryforHistory (request);
			validateRequestClient (request);
			adjustDistributionforHistory (request);
		}
		return request;
	}
	
	private void adjust Product CategoryforHistory( DistributionRequest request) {
	
		ProductDetail product = request.getProduct Detail();
		product.setProduct Category (com.ms.datawise.distn.constants. DatasetConstants. PRECISE_SLA);
	}
	
	private void adjustDistributionfor History( DistributionRequest request) {
	if (request.getCometJobId() != null && ! request.getCometJobId().isEmpty()) {
		List<DistributionDetail> distributions = request.getDistributions (); 
		DistributionDetail distEnt = distributions.get(0);
		
		if (distEnt.getDistType().equals (Modes. DATA_DISTRIBUTION_TARGET_COMET.toString())) {

	
			LOGGER.info("Datawise Request | apply comet job override to history product distribution {}", request.getProduct_id()); 
			distEnt.setDist Handle (request.getCometJobId());
		} else {	
		
			String msg = "No Comet job found for txn "+ request.getTxn_id() +", product "+request.getProduct_id(); 
			LOGGER.warn (msg);
			EventLog eventLogEntity = new EventLog (request.getTxn_id(), request.getProduct_id(), "3005",
				EventLogConst. DistEventMap.get("3005"), "FAIL", msg, null);
			eventLogDao.saveEventLogAsync (eventLogEntity);
		}
	}
	
	
	private boolean validateRequestClient (DistributionRequest request) {
		LOGGER.info("Datawise Request | validating request history product {}", request.getProduct_id()); 
		List<String> requestedClients = request.getClient_ids();
		List<DistributionDetail> distributions = request.getDistributions();
		if (requestedClients == null || requestedClients.isEmpty()) {
			EventLog eventLogEntity = new EventLog (request.getTxn_id(), request.getProduct_id(), "3004", 
				EventLogConst. DistEventMap.get("3004"), "FAIL", ERROR_NO_SPECIFIC_CLIENT, null);
				eventLogDao.saveEventLogAsync (event LogEntity);
				throw new IllegalArgumentException (ERROR_NO_SPECIFIC_CLIENT);
		} else if (requestedClients.size() > 1) {
			EventLog eventLogEntity = new EventLog (request.getTxn_id(), request.getProduct_id(), "3004", 
				EventLogConst. DistEventMap.get("3004"), "FAIL", ERROR_MORE_TAHN_ONE_CLIENT, null); 
				eventLogDao.saveEventLogAsync (eventLogEntity);
				throw new IllegalArgumentException (ERROR_MORE_TAHN_ONE_CLIENT);
		} else if (distributions.size() > 1) {

			EventLog eventLogEntity = new Event Log (request.getTxn_id(), request.getProduct_id(), "3004", 
				EventLogConst. DistEventMap.get("3004"), "FAIL", ERROR_MORE_TAHN_ONE_DISTRIUBTION, null); 
				eventLogDao.saveEventLogAsync (eventLogEntity);
			
			throw new IllegalArgumentException (ERROR_MORE_TAHN_ONE_DISTRIUBTION);
		}
		EventLog eventLogEntity = new EventLog (request.getTxn_id(), request.getProduct_id(), "3004",
			EventLogConst. DistEventMap.get("3004"), "SUCCESS", "", null);
		eventLogDao.saveEventLogAsync (eventLogEntity);
		return true;
	}
}
	
	
	