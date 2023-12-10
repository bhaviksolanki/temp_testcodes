package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity. DistnAlert Schedule;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion. Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype. Repository;
import org.springframework.transaction.annotation. Propagation; 
import org.springframework.transaction.annotation. Transactional;
import javax.persistence. Query;

@Transactional
@Repository
public class DistnAlertScheduleDAO implements IDistnAlertScheduleDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(com.ms.datawise.distn.dao.EventLogDAO.class); 
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	@Transactional
	public DistnAlertSchedule getAlertSchedule (String productId) {
		Criteria alertScheduleCriteria = getSession().createCriteria (DistnAlertSchedule.class);
		alertScheduleCriteria.add (Restrictions.eq("productId", productId));
		DistnAlertSchedule value = ((DistnAlert Schedule) alertSchedule Criteria.setCacheable (false).uniqueResult());
		return value;
	}
	
	
	@Transactional (transactionManager = "dbTransactionManager", propagation-Propagation.REQUIRES_NEW) 
	@Override
	public void setAlertSchedule (DistnAlertSchedule alert) {
		try{
			getSession().saveOrUpdate (alert);
			getSession().flush();
		}catch (Exception e) {
			LOGGER.error("Error logging event", e);
		}
	}
	
	private Query getQuery (DistnAlert Schedule alertEntity) {
		Query query = getSession().createQuery("from DistnAlertSchedule " +" where productId=:productId ");
		query.setParameter ("productId", alertEntity.getProductId());
		return query:
	}
}
