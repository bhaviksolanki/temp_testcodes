package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.DistributionDetail; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

@Service
public class DistributionDetailService {
	
	private final DistributionDetailDao distributionDetailDao;
	
	@Autowired
	public DistributionDetailService (Distribution DetailDao distributionDetailDao) { 
		this.distributionDetailDao = distributionDetailDao;
	}
	
	public DistributionDetail getDistributionDetails (Integer id) {
		return distribution DetailDao.distributionDetailData (id);
	}
}