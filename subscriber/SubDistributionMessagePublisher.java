
@Component
@NoArgsConstructor
public class SubDistributionMessagePublisher {

	@Value ("${distn.input-queue}")
	private String queueName;
	
	@Resource
	DistributionMessagePublisher publisher;
	
	private static final Logger logger = ContextLogger.safeLogger();
	
	public void publish (String messsageStr) {
		try {
			logger.info("Datawise Request | Publishing message {} on queue {}", messsageStr, queueName);
			publisher.writeToQueue (queueName, messsageStr);
		} catch (JMSException e) {
			logger.info("Datawise Request | error Publishing message {} on queue {}", messsageStr, queueName); 
			throw new RuntimeException(e);
		}
	}
}	
	