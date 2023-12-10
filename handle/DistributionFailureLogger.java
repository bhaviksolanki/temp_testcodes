
@Component
@Order (value=50)
@Qualifier ("distributionOprator")
public class DistributionFailureLogger implements IDistributionRequestHandler{
	
	final private String REQUEST_TYPE_DISTN_FAIL_LOG = DistributionAction.enumDistnAction.handle failure.name(); 
	
	private static final Logger LOGGER = LoggerFactory.getLogger (Distribution FailureLogger.class);
	
	@Resource
	LoggingUtil loggingUtil;
	
	@Value ("${dw.environment}") private String environment;
	@Autowired
	IEventLogDAO event LogDao;
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList (REQUEST_TYPE_DISTN_FAIL_LOG.split("\\s*, \\s*"));
	}
	
	@Override
	public DistributionRequest process (DistributionRequest request) {
		if (canProcess (request)) {
			LOGGER.info(" Datawise Request | logging {} for product {}", request.getAction(), request.getProduct_id()); 
			ProductDetail product = request.getProductDetail();
			
			if (Product Predictes.isWindowSLA ().test (product)) {
				loggingUtil.logWindowSLAFailure (request);
				EventLog event LogEntity = new Event Log (request.getTxn_id(), request.getProduct_id(), "3014", EventLogConst. DistEventMap.get("3014"), "FAIL", "Distribution failed", null); 
				eventLogDao.saveEventLogAsync (eventLogEntity);
			}
		}
		return request;
	}
}