package com.ms.datawise.distn.dao;

import com.ms.datawise.distn.entity.ClientDistributionLog; 
import com.ms.datawise.distn.entity.DistType;
import com.ms.datawise.distn.entity. DistributionDetail;
import java.util.List;
public interface IDistributionDAO {
	List<DistributionDetail> getActive DistributionsByProduct (String productId); 
	List<Integer> getDistributionIdsByProduct (String productId);
	DistType lookupDistributionType (String distType);
	List<DistType> getAllDistributionType();
	void logClient DistributionAction (Client DistributionLog logEnt);
}