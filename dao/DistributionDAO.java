package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.*; 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation; 
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria. CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class DistributionDAO implements IDistributionDAO {
	
	static private final Logger LOGGER = LoggerFactory.getLogger (DistributionDAO.class); 
	
	@Autowired
	private SessionFactory sessionFactory;
	
		
	@Override
	@Transactional (transactionManager = "dbTransactionManager", readOnly= true, propagation-Propagation. REQUIRED) 
	public List<DistributionDetail> getActiveDistributionsByProduct (String productId) {
		Session sess = getSession();
		List<DistributionDetail> entList = new ArrayList<DistributionDetail>();
		try {
			@SuppressWarnings("unchecked")
			Query<Object> qDistribution = sess.createQuery("from Product Distribution p join fetch DistributionDetail d on d.distributionId=p.proDistUniqueID.distribution_id where p.proDistUnique ID.product_id :productId");
			
			qDistribution.setParameter ("productId", productId); 
			List<Object> resList = qDistribution.list();
			for (Object o: resList) {
				if (o instanceof Object[]) {
					Object[] arr = (Object []) 0;
					DistributionDetail distribution = null;
					boolean isActive = false;
					for (int i = 0; i < arr.length; i++) {
						if (arr[i] instanceof Product Distribution) {
							isActive ((Product Distribution) arr[i]).isActive();
						}
						if (arr[i] instanceof Distribution Detail) {
							distribution = (DistributionDetail) arr[i];
						}
					}
					if (isActive) {
						entList.add(distribution);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occurred :", e);
		}
		return entList;
	}
	
	
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", readOnly= true, propagation-Propagation.REQUIRED) 
	public List<Integer> getDistributionIdsByProduct (String productId) {
		Session sess = getSession();
		List<Integer> entList = new ArrayList<Integer>();
		try {
			@SuppressWarnings("unchecked")
			Query<Product Distribution> qDistribution = sess.createQuery("from ProductDistribution where proDistUniqueID.product_id :productId");
			
			qDistribution.setParameter ("productId", productId);
			List<Product Distribution> resList = qDistribution.list();
			entList = resList.stream().map (Product Distribution::getProDistUniqueID)
				.map (ProDistUnique ID::getDistribution_id).collect (Collectors.toList());
		} catch (Exception e) {
		
			LOGGER.error ("Exception Occurred ", e);
		}
		return entList;
	}
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", readOnly= true, propagation-Propagation.REQUIRED) 
	public DistType lookupDistributionType (String distType) {
		if (distType == null || distType.isEmpty()) {
			return null;
		}
		
		Session sess = getSession();
		DistType existingDistType = null;
		existingDistType = sess.get (DistType.class, distType); 
		return existingDistType:
	}
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", readOnly = true, propagation-Propagation. REQUIRED) 
	public List<DistType> getAllDistributionType() {
		Session sess = getSession();
		CriteriaQuery<DistType> criteria = sess.getCriteriaBuilder().createQuery (DistType.class);
		criteria.from (DistType.class);
		List<DistType> allDistTypes = sess.createQuery (criteria).getResultList();
		return allDist Types;
	}
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", propagation=Propagation.REQUIRED) 
	public void logClient DistributionAction (Client DistributionLog logEnt) {
		Session sess = getSession();
		sess.save(logEnt);
		sess.flush();
	}
}