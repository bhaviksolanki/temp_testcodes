
@Component
@Order (value=150)
@Qualifier ("distributionOprator")
public class DistributionSucceedLogger implements IDistributionRequestHandler{
	
	final private String REQUEST_TYPE_DISTN_SUCC_LOG = DistributionAction.dist succeedAction.getName(); 
	
	private static final Logger LOGGER = LoggerFactory.getLogger (Distribution SucceedLogger.class);
	
	@Resource
	LoggingUtil loggingUtil;
	
	@Value ("${dw.environment}")
	private String environment;
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList (REQUEST_TYPE_DISTN_SUCC_LOG.split("\\s*, \\s*"));
	}
	
	@Override
	public DistributionRequest process (DistributionRequest request) {
	
		if (canProcess (request)) {
			LOGGER.info("logging {} for product {}", request.getAction(), request.getProduct_id()); 
			ProductDetail product = request.getProduct Detail();

			if (Product Predictes.isWindowSLA ().test (product)) {
				buildLog (request);
				loggingUtil.logWindowSLASuccess (request); 
				String nextAction = DistributionAction.windowcompleteAction.getName();
				request.setAction (nextAction);
			}
		}
		return request;
	}
	
	private void buildLog (DistributionRequest request) {
		HandlerLog log = new HandlerLog ("check_send", "success", "info", ""); request.addLog (log);
	}
}