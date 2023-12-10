package com.ms.datawise.distn.dao;

import com.ms.datawise.distn.entity.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private final ClientDao client Dao;
	
	public Client Service (ClientDao client Dao) { 
		this.client Dao = client Dao;
	}

	public Client getClientDetails (String id) { 
		return client Dao.client Data (id);
	}
}