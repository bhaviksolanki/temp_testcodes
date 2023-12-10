
@Component
// @EnableAspect JAutoProxy
public class DistributionReqeustListener implements InvocationContextAwareMessageListener<TextMessage> {
	private org.slf4j.Logger logger = LoggerFactory.getLogger (DistributionReqeustListener.class);

	@Resource
	CoreSubscriber subscriber;
	@Override
	public void onMessage (TextMessage message, QueueListener InvocationContext context) throws JMSException { 
		try {
				logger.info(" ***********START ****** " + 
							" Datawise Request | start processing incoming Request Message ={}", message.getText());
				subscriber.processRequest (message.getText()); logger.info("****************END ***************" + 
											" Datawise Request | finished processing income Request Message ={}", message.getText());
		} catch (Exception e) {
			logger.error("***********END**********" + "Datawise Request | Exception Occurred: ", e); 
			throw new JMSException (e.getMessage());
		}
	}
}