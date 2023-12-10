package com.ms.datawise.distn.aop;
import com.ms.datawise.distn.publisher. DistributionMessagePublisher;
import msjava.integrations.spring.retrybatching. BatchMessageListenerContainer; import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation. After Throwing;
import org.aspectj.lang.annotation. Aspect;
import org.aspectj.lang.annotation. Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetrySynchronizationManager;
import javax.jms. JMSException;
import javax.jms.TextMessage;

@Aspect
public class HandleExceptionAdvice {
	private static final Logger logger = LoggerFactory.getLogger (HandleExceptionAdvice.class); 
	
	@Autowired
	private DistributionMessage Publisher publisher;
	
	@Autowired
	private BatchMessageListenerContainer batchMessageListenerContainer;

	@Pointcut ("execution (* com.ms.datawise.distn.listener. DistributionReqeustListener.onMessage(..))")
	public void handle () {
	}

	@After Throwing (pointcut = "handle ()", throwing = "throwable")
	public void process (JoinPoint joinPoint, Throwable throwable) {
		Object[] arguments = joinPoint.getArgs();
		
		TextMessage message = (TextMessage) arguments[0];
		
		RetryContext context = RetrySynchronizationManager.getContext();
		
		if (context.getRetryCount() > 0) {
			try {
				logger.debug("Retry number {} failed for message :", context.getRetryCount()); 
				logger.debug (message.getText());
			} catch (JMSException e) {
			logger.error("Exception Occurred :", e);
		}
	}

	if (context.getRetryCount() > 2) {
		try {
				logger.error("max retry hitted");
				publisher.writeToQueue(batchMessageListenerContainer.getErrorQueueName (), message.getText());
			} catch (JMSException e) {
				logger.error ("Exception Occurred : ", e);
			}
		}
	}
}
