package com.ms.datawise.distn.config;

import msjava.cxfutils.auth.feature.SpnegoHTTPClientFeature; 
import msjava.kerberos.auth.MSKerberosConfiguration;
import org.apache.cxf.feature. LoggingFeature;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation. Configuration;
import java.net.URISyntaxException; 
import java.net.UnknownHostException;

@Configuration
public class IngestionConfiguration {
	@Bean (name="IngestionWebClientFactory")
	public JAXRSClientFactoryBean getIngestionFileClientFactory() throws UnknownHostException, URISyntaxException {
		MSKerberosConfiguration.setClientConfiguration();
		JAXRSClientFactoryBean factory = new JAXRSClientFactoryBean();
		factory.getFeatures().add(new SpnegoHTTPClientFeature()); 
		factory.getFeatures().add(new Logging Feature());
		return factory;
	}
	
	@Bean (name="DocRepoClientFactory")
	public JAXRSClientFactoryBean getDocRepoClientFactory() throws UnknownHostException, URISyntaxException {
		MSKerberosConfiguration.setClientConfiguration();
		JAXRSClientFactoryBean factory = new JAXRSClientFactoryBean();
		factory.getFeatures().add(new SpnegoHTTPClientFeature()); 
		factory.getFeatures().add(new LoggingFeature());
		return factory;
	}
}