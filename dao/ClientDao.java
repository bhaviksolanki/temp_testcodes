
package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.repository. IClientRepository; 
import com.ms.datawise.distn.entity. Client;
import lombok.extern.slf4j.slf4j;
import org.springframework.stereotype.Component;
@slf4j @Component
public class ClientDao {
	private final IClientRepository clientRepository;
	
	public Client Dao (IClientRepository clientRepository) { 
		this.clientRepository = clientRepository;
	}
	
	public Client client Data (String clientId) {
		log.info(clientRepository.findByClientId(clientId).toString()); 
		return clientRepository.findByClientId(clientId);
	}
}