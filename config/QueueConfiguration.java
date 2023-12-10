
package com.ms.datawise.distn.config;
import com.ms.datawise.distn.aop.Datazone TransferAspect; 
import com.ms.datawise.distn.aop.HandleExceptionAdvice; 
import com.ms.datawise.distn.aop.IngestionRetryAspect;
import com.ms.datawise.distn.listener. DistributionReqeustListener;
import com.ms.datawise.distn.publisher. DistributionMessage Publisher;
import com.ms.datawise.distn.util.EmailUtils;
import msjava.integrations.spring.retrybatching. BatchMessageListenerContainer; 
import msjava.integrations.spring.retrybatching. ExternalRetryManager; 
import msjava.integrations.spring.transaction.MSTransactionManager;
import msjava.mqjms.MQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspect JAutoProxy; 
import org.springframework.core.env.Environment;

@Configuration
@EnableAspect JAutoProxy (proxyTargetClass = true)
//@EnableRetry
// @ComponentScan (base Packages 
public class QueueConfiguration (
//
//
	//@Autowired
	//Bus bus;
	/*
	{ "com.ms.datawise.distn"})
	* @Bean public DistributionMessageListener dataWise () {
	*
	* return new DistributionMessageListener(); }
	*/
	@Bean
	public DistributionReqeustListener dataWise () { 
		return new DistributionReqeustListener();
	}
	
	@Bean
	public HandleExceptionAdvice advice () {
		return new HandleExceptionAdvice();
	}
	
	@Bean
	public IngestionRetryAspect retryAdvice () { 
		return new IngestionRetryAspect();
	}
	
	
	@Bean
	public DatazoneTransferAspect datazoneRetryAdvice () {
		return new DatazoneTransferAspect();
	}
	
	@Autowired
	private Environment env;
	
	@Bean
	public BatchMessageListenerContainer batchMessageListenerContainer() { 
		BatchMessageListenerContainer listener = new BatchMessageListenerContainer();
	
		listener.setInputQueueName(
		env.getRequiredProperty("distn.input-queue"));
		listener.setErrorQueueName(
		env.getRequiredProperty("distn.error-queue"));
		listener.setConnectionFactory (mqConnectionFactory()); 
		listener.setTransactionManager (transactionManager());
		listener.setMessageListener (dataWise ());
		listener.setConcurrentGetters(Integer.parseInt(env.getRequiredProperty("distn.input-queue.concurrentGetters")));
		ExternalRetryManager s = new ExternalRetryManager();
		listener.setExternalRetryManager (s);
		return listener;
	}
	
	@Bean
	public ExternalRetryManager externalRetryManager() {
		ExternalRetryManager erm = new ExternalRetryManager(); 
		return erm;
	}
	
	@Bean
	public MQConnectionFactory mqConnectionFactory() {
		MQConnectionFactory connectionFactory = new MQConnectionFactory(); connectionFactory.setQueueManagerName (env
			.getRequiredProperty("distn.queue-manager"));
		return connectionFactory;
	}
	

	// @Bean (name="queuTransactionManager")
	public MSTransactionManager transactionManager() {
		MSTransactionManager tm = new MSTransactionManager();
		return tm;
	}
	
	@Bean
	public DistributionMessage Publisher publisher() {
	
		DistributionMessage Publisher publisher = new DistributionMessage Publisher (mqConnectionFactory()); 
		return publisher;
	}
	
	//@Bean
	// public HDOMRequestSender getCometRequestSender() {
	// HDOMMessage SenderImpl cometRequestSender =new HDOMMessage SenderImpl() // :
	//SOAPHDOMMessageSender cometRequestSender = new SOAPHDOMMessage Sender();
	// ToDo: Uncomment and resolve bus
	//bus.setProperty("msjava.cxfutils.msUniqueId.soap.out", false);
	//cometRequestSender.setPayloadMode (false); 
	//cometRequestSender.afterPropertiesSet();
	//cometRequestSender.setClientTransport (getClientTransport()); 
	//return cometRequestSender;
	// }
	
	// @Bean
	// public TCPClientTransport getClientTransport () {
	// // }
	
	
	//TCPClientTransport tcpClient = new TCPClientTransport(); tcpClient.setHostPort
	//(env.getRequiredProperty("comet.submission.server"));
	//tcpClient.setKerberos (true);
	//return tcpClient;
	//}

	@Bean
	public EmailUtils getEmailUtils() {
		return new EmailUtils ();
	}
}