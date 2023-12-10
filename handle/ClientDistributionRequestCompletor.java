
@Component
@Order (value = 100)
@Qualifier ("distributionOprator")
public class ClientDistributionRequestCompletor implements IDistributionRequestHandler {
	final private String REQUEST_TYPE_DISTN_REQ_COMPLET = DistributionAction.distributionAction.getName(); 
	private static final Logger LOGGER = LoggerFactory.getLogger (Client DistributionRequestCompletor.class);
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList(REQUEST_TYPE_DISTN_REQ_COMPLET.split("\\s*,\\s*"));
	}
	
	@Autowired
	IEventLogDAO eventLogDao;
	
	@Override
	public DistributionRequest process (DistributionRequest request) {
		if (canProcess (request)) {
			LOGGER.info("distribution request completed");
			request.setAction (DistributionAction.distsucceedAction.getName());
			EventLog eventLogEntity = new Event Log (request.getTxn_id(), request.getProduct_id(), "3015", EventLogConst. DistEventMap.get("3015"), "SUCCESS", "Distribution request completed", null); 
			eventLogDao.saveEventLogAsync (event LogEntity);
		}
		return request;
	}
}