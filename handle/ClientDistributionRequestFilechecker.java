
@Component
@Order (value = 10)
@Qualifier("distributionOprator")
public class ClientDistributionRequestFilechecker implements IDistributionRequestHandler {
	
	final private String REQUEST_TYPE_DISTN_CHECK_FILE = "distribution, checkfile";
	private static final Logger LOGGER = LoggerFactory.getLogger (ClientDistributionRequestFilechecker.class);
	
	@Resource
	IDistributionDAO distributionDAO;
	
	@Resource
	ProductCheckerFactory productCheckerFactory;
	
	@Autowired
	private FileNotFoundNotificationManager notificationManager;
	
	@Resource
	LoggingUtil loggingUtil;
	
	@Autowired
	private EmailManager emailManager;
	
	@Autowired
	IEventLogDAO eventLogDao;
	
	@Override
	public List<String> getActionType() {
		return Arrays.asList(REQUEST_TYPE_DISTN_CHECK_FILE.split("\\s*,\\s*"));
	}
	
	
	@Override
	public DistributionRequest process (DistributionRequest request) {
		if (canProcess (request)) {
			LOGGER.info("Datawise Request | checking file availability for product={}", request.getProduct_id()); 
			Product Detail product = request.getProduct Detail();
			IProductChecker productCheker = productCheckerFactory.getProductChecker (product);
			
			if (productCheker != null) {
				EventLog eventLogEntity = new EventLog (request.getTxn_id(), request.getProduct_id(), "3006",EventLogConst.DistEventMap.get("3006"), "SUCCESS", "Product checker is "+productCheker.getClass().getCanonicalName(), null); 
				eventLogDao.saveEventLogAsync (eventLogEntity);
			}
			
			CheckProductResult res = productCheker.checkProduct (product, request);
			HandlerLog log = null;
			
			if (!res.isProductExists()) {
				LOGGER.info("Datawise Request | File doesn't exist for the product={}", request.getProduct_id());
				
				if (productCheker instanceof Ingestion ProductChecker) {
					log = new HandlerLog (Client DistributionRequestFilechecker.class.getName(),
						"FAILED", "INFO", "Ingestion Process Failure");
				}else{
					log = new HandlerLog (Client DistributionRequestFilechecker.class.getName(), 
						"FAILED", "INFO", "Internal distribution Failure");
				}
				if ("distribution".equals (request.getAction())) {
					String productName = product.getProductName();
					LOGGER.info("Datawise Request | Sending the alert emails for product={}", productName); 
					emailManager.sendDistributionExceptionEmail (productName, res.getMessage());
				}
				notificationManager.manageNotification (product, res, request);
				String nextAction= DistributionWorkFlow.getNextActionName (request.getAction(),
					DistributionAction.enumDistnActionResult.fail.name());
				request.setAction (nextAction);
				
				request.addLog (log);
				loggingUtil.logIngestionProcess (log, request);
			} else{
				
				log = new HandlerLog (Client DistributionRequestFilechecker.class.getName(),
					"SUCCESS", "INFO", "Datawise Request | File exist for the product="+ request.getProduct_id()); 
				loggingUtil.logIngestionProcess (log, request);
				request.getProduct Detail ().setDistFilePattern (res.getProductChecked()); 
				LOGGER.info("Datawise Request | File exist for the product={}", request.getProduct_id());
			}
		}
		return request;
	}
}