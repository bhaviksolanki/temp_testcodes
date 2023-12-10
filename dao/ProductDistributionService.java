package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.ProDistUniqueID;
import com.ms.datawise.distn.entity.ProductDistribution; 
import org.springframework.stereotype.Service;
@Service
public class ProductDistributionService {
	private final ProductDistributionDao productDistributionDao;
	public Product DistributionService (Product DistributionDao product DistributionDao) { 
		this.product DistributionDao = product DistributionDao;
	}
	public Product Distribution getProdDistDetails (ProDist Unique ID uniqueID) {
		return product Distribution Dao.prodDist Data (uniqueID);
	}
}