package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.DistributionDetail;
import com.ms.datawise.distn.repository. IDistribution DetailRepository; 
import lombok.extern.slf4j.slf4j;
import org.springframework.stereotype.Component;

@slf4j 
@Component
public class DistributionDetailDao {
	
	private final IDistributionDetailRepository distributionDetailRepository;
	
	public DistributionDetailDao (IDistributionDetailRepository clientRepository) { 
		this.distributionDetailRepository = clientRepository;
	}

	public DistributionDetail distribution DetailData (Integer distributionId) {
		// log.info(distributionDetailRepository.findByDistributionId (distributionId).toString()); 
		return distributionDetailRepository.findByDistributionId (distributionId);
	}
}