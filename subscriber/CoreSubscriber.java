
@Component
public class CoreSubscriber {
	private static final Logger LOGGER = LoggerFactory.getLogger (CoreSubscriber.class);
	@Resource (name = "subscriberFactory")
	SubscriberFactory subscriberFactory;

	// @Transactional (propagation = Propagation.REQUIRED)
	//@Transactional()
	public void processRequest (String request) throws JMSException {
		JSONObject datawiseJson = null;
		datawiseJson = new JSONObject(request);
		String requestType = datawiseJson.getString (Modes. DATASET_REQUEST_TYPE.toString());
		LOGGER.info("Datawise Request | Datawise Request For product |Incoming Request Type ={}", requestType); 
		
		ISubscriber subscriber subscriberFactory.getSubscriber(requestType);
		
		if (subscriber == null) {
			LOGGER.info(" Datawise Request | Datawise Request For product subscriber is null "); //netcoolUtils.sendNetcool (String.format (message.getAlertKey(),
			// eventId), String.format (message.getAlertDescription(), eventId),
			// NetcoolSeverity. Major);
			
			LOGGER.error("Datawise Request | Datawise Request For product |Message not processed ::" + request);
			throw new JMSException ("Datawise Request | could not find subscriber for the message:\n" + request);
		}
		
		LOGGER.info("Datawise Request | Datawise Request For product |Subscriber Invoked for Incoming Request {}" subscriber.toString());
		subscriber.processRequest (request); 
	}
	
}