
@Component
public class DistributionMessage Publisher {
	private static final Logger logger = ContextLogger.safeLogger();

	@Autowired
	private QueueConnectionFactory factory;

	public DistributionMessage Publisher (QueueConnectionFactory mqConnectionFactory) { 
		this.factory = mgConnectionFactory;
	}
	
	public void writeToQueue (String queueName, String messageText) throws JMSException { 
		Session session = null;
		MessageProducer producer = null;
		try {
			Connection connection = factory.createQueueConnection();
			session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
			TextMessage request = session.createTextMessage (messageText);
			Queue queue = session.createQueue (queueName);
			producer = session.createProducer (queue);
			producer.send (request);
			logger.info("Datawise Request | Sent message --> {}", request.getText());
			logger.info("Datawise Request | Message {} successfully placed on queue {}", messageText, queueName);
		} finally {
			// returns the connection back to connection pool
			if (producer != null) {
				producer.close();
			}
			
			if (session != null) {
				session.close();
			}
		}
	}
}