package com.ms.datawise.distn.constants;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax. annotation. PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Data
@Component
public class LDAPConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger (LDAPConstants.class);
	
	private Map<String, List<String>> groupMap = new HashMap<String, List<String>>();
	
	@Value ("${datawise.distribution.entitlementGroup}")
	private String entitlementGroup;
	
	@Value ("${macroservices.ldap.environment}")
	private String environment;
	
	@Value ("${datawise.distribution.entitledResource}") private String entitledResource;
	
	public Map<String, List<String>> getGroupMap() {
		return groupMap;
	}
	
	public String getEnvironment () {
		return environment;
	}
	public void setEnvironment (String environment) { 
		this.environment environment;
	}
	
	
	@PostConstruct
	public void initializeMap() {
	
		LOGGER.info("environment ===>"+this.getEnvironment()); 
		this.getGroupMap().put (this.getEntitlement Group (),
			Stream.of (this.getEntitledResource ().split(",")).collect (Collectors.toList()));
		LOGGER.info("map"+this.getGroupMap());
		
	}
}