
@Component
public class Client DistributionRequest Subscriber implements ISubscriber, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger (Client DistributionRequestSubscriber.class); 
	
	@Resource
	@Qualifier ("distributionOprator")
	List<IDistributionRequestHandler> distributionOprators;
	
	@Resource (name = "subscriberFactory") 
	SubscriberFactory subscriberFactory;

	@Autowired
	IEventLogDAO eventLogDao;
	
	@Override
	public void processRequest (String request) throws JMSException {
		try {
			LOGGER.info("Datawise Request | Incoming Distribution Request from queue ={}", request); 
			Optional<DistributionRequest> requestWrap = JsonTransformer.transform (request, DistributionRequest.class, new ObjectMapper());
			
			//DistributionRequest distributionRequest = requestWrap.filter (d-> d.isValidRequest()).orElse Throw (()-> new JMSException("Error parsing request : //Optional DistributionRequest> distributionRequest Op = requestWrap.filter (d->d.isValidRequest()); 
			DistributionRequest distributionRequest = null;
			if (requestWrap.isPresent()) {
					distributionRequest = requestWrap.get();
					if (distributionRequest.isValidRequest()) {
					LOGGER.info("Datawise Request | processing distribution request ={}", distributionRequest.toString());
					EventLog event Log = new Event Log (distributionRequest.getTxn_id(), distributionRequest.getProduct_id(),
															"3001", EventLogConst. DistEventMap.get("3001"), "SUCCESS", "", null);
					//eventLogDao.saveEventLog (eventLog);
					eventLogDao.saveEventLogAsync (event Log);
					processRequest (distributionRequest);
					} else {
						String warnMsg = "Invalid Request found inside Client DistributionRequestSubscriber "+request; LOGGER.warn (warnMsg);
						EventLog event Log = new EventLog (distributionRequest.getTxn_id(), distributionRequest.getProduct_id(), 
															"3001", EventLogConst. DistEventMap.get("3001"), "FAIL", warnMsg, null);
					}
					//eventLogDao.saveEventLog (eventLog); 
					eventLogDao.saveEventLogAsync (event Log);
					throw new JMSException (warnMsg);
			}
			catch (Exception e) {
				LOGGER.error("Exception Occurred: ", e); 
				throw new JMSException (e.getMessage());
			}
		}
	}
	

	private void processRequest (DistributionRequest distributionRequest) {
		LOGGER.info("Datawise Request | Request Type ={} | transaction id = {} | product {}", distributionRequest.getRequestType(),
						distributionRequest.getTxn_id(), distributionRequest.getProduct_id());
		for (IDistributionRequestHandler handler distributionOprators) {
			LOGGER.info("calling handler {}", handler); 
			handler.process (distributionRequest);
		}
	}
	
	@Override
	public void afterPropertiesSet () throws Exception {
		subscriberFactory.addSubscriberMap (Modes.DATA_DISTRIBUTION_REQUEST.toString(), this);
	}
}
