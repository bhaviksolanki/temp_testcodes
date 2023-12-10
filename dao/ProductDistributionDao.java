package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.ProDistUniqueID;
import com.ms.datawise.distn.entity.ProductDistribution;
import com.ms.datawise.distn.repository.IProductionDistributionRepository;
import lombok.extern.slf4j.slf4j;
import org.springframework.stereotype.Component;
@slf4j
@Component
public class ProductDistributionDao {
	private final IProductionDistributionRepository productionDistributionRepository;
	
	public Product Distribution Dao (IProductionDistributionRepository productionDistributionRepository) { 
			this.productionDistributionRepository = productionDistributionRepository;
	}
	
	public Product Distribution prodDist Data (ProDistUnique ID uniqueID) {
		// log.info(clientRepository.findByClientId (clientId).toString());
		return productionDistributionRepository.findByProDistUnique ID (uniqueID);
	}
}