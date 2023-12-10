
@Component
public class PilotSubscriber implements ISubscriber, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger (Pilot Subscriber.class);

	@Value ("${datawise.distribution.filecheck.alert.email.from: datawise-distribution-dev}") 
	private String emailFrom;
	
	@Resource (name = "subscriberFactory")
	com.ms.datawise.distn.subscriber.SubscriberFactory subscriberFactory;
	
	@Value ("${dw.environment}")
	private String environment;
	
	@Resource
	EmailUtils emailUtils;
	
	@Resource
	LoggingUtil loggingUtil;
	
	@Autowired
	IEventLogDAO eventLogDao;
	
	@Override
	public void afterPropertiesSet () throws Exception {
		subscriberFactory.addSubscriberMap (Modes. DATA_DISTRIBUTION_PILOT_REQUEST.toString(), this);
	}
	
	@Override
	public void processRequest (String request) throws JMSException {
		LOGGER.info(" Datawise Request | processing pilotRequest: {}", request);
		Optional<CometRequest> requestWrap = JsonTransformer.transform (request, CometRequest.class, new ObjectMapper());
		
		if (requestWrap.isPresent ()) {
			CometRequest cometRequest = requestWrap.get();
			if (cometRequest.isValidRequest()) {
				String srcPath= cometRequest.getSourceFolder(); LOGGER.info(" Datawise Request | processed comet Request: {}", cometRequest.toString());
				String filePathString = srcPath.replaceAll("/$", "") + "/" + cometRequest.getFileName(); String mailTo = cometRequest.getJobId();
				LOGGER.info( "Datawise Request | sending email to the following receipents = {} for the pilot Request: {}", mailTo, request); boolean isSent = emailUtils.sendEmail (emailFrom, mailTo, "["+ environment + "] Snowflake Pilot Distribution Test", "", false, filePathString, null, "5");
				
				logCometJobSuccess (cometRequest, isSent);
			} else {
				String warnMsg = "Invalid Request found inside PilotSubscriber "+request;
				LOGGER.warn (warnMsg);
				EventLog event Log = new Event Log (cometRequest.getTxn_id(), cometRequest.getProduct Detail().getProductId(), "3001",
				EventLogConst.DistEventMap.get("3001"), "FAIL", warnMsg, null);
				eventLogDao.saveEventLog (eventLog);
				throw new JMSException (warnMsg);
			}
		}
	}
	
	private void logCometJobSuccess (Comet Request cometRequest, boolean isSucceed) {
		String requestStr = JsonTransformer.transform (cometRequest);
		requestStr = requestStr.replace(":", "=");
		String jobStatus = isSucceed? "SUCCESS" : "FAIL";
		String logMessage = isSucceed? "Pilot distribution was successfull for request" : "Pilot distribution was failed for request"; LOGGER.info("logging {} for distribution {}", cometRequest.getRequestType(), cometRequest.getDistribution_id());

		try {
			HandlerLog log = new Handler Log (Pilot Subscriber.class.getName(),
			jobStatus, "INFO", "");
			cometRequest.addLog (log);
			LOGGER.info("Datawise Request | sending pilot with request={} status={}", requestStr,jobStatus); loggingUtil.logDataTransfer (cometRequest);
			EventLog event Log = new Event Log (cometRequest.getTxn_id(), cometRequest.getProduct Detail ().getProductId(), "3023", EventLogConst. DistEventMap.get("3023"), jobStatus, logMessage, null);
			eventLogDao.addEventLogAsync (eventLog);
		}catch (Exception e) {
			LOGGER.error("Datawise Request | sending pilot with request={} status={}", requestStr LOGGER.error("Error when logging internal client delivery", e);
			HandlerLog log = new HandlerLog (Pilot Subscriber.class.getName(),
			"FAILURE", "DEBUG", "Error while logging pilot status"+e.getMessage());
			cometRequest.addLog (log);
			"FAILURE");
			EventLog event Log = new Event Log (cometRequest.getTxn_id(), cometRequest.getProduct Detail ().getProductId(), "3023", EventLogConst. DistEventMap.get("3023"), jobStatus, logMessage, null); eventLogDao.addEventLogAsync (eventLog);
			loggingUtil.logDataTransfer (cometRequest);
		}
	}
	
	@Override
	public String toString() {
		return "Pilot Subscriber [subscriberFactory=" + subscriberFactory + "]";
	}	
}