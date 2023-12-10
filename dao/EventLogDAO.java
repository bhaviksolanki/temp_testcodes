package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.EventLog: import com.ms.datawise.distn.util.ContactUtil; 
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor; 
import org.hibernate.Session;
import org.hibernate.SessionFactory; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.scheduling.annotation.Async;
import org.springframework. stereotype. Repository;
import org.springframework.transaction.annotation.Propagation; 
import org.springframework.transaction.annotation.Transactional;
import javax.persistence. Query;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Repository
public class EventLogDAO implements IEventLogDAO {
	
	private static final Logger loggger = LoggerFactory.getLogger (EventLogDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ContactUtil contactUtil;
	
	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", propagation-Propagation. REQUIRES_NEW) 
	public Event Log saveEvent Log (EventLog eventLogEntity) {
		try{
			eventLogEntity.setCreatedBy (contactUtil.getProid()); 
			loggger.debug("eventLog: "+ eventLogEntity.toString());
			Query query = getQuery (event LogEntity);
			List<EventLog> eventList = query.getResultList();
			if (eventList != null && eventList.size() >0){
				EventLog existingEvent eventList.get(0);
				loggger.info("existing event found to update: "+ existingEvent.getLogId()); 
				existingEvent.setErrorCode(eventLogEntity.getErrorCode()); 
				existingEvent.setProcessDesc (eventLogEntity.getProcessDesc());
				getSession().update (existingEvent);
			} else {
				loggger.info("No event found to update. Saving the event."); 
				getSession().save(eventLogEntity);
			}
			getSession().flush();
		} catch (Exception e) {
			loggger.error("Error logging event e);
		}
			return eventLogEntity;
	}
	
	
	@Override
	@Async
	@Transactional (transactionManager = "dbTransactionManager", propagation-Propagation. REQUIRES_NEW) 
	public void saveEventLogAsync (EventLog event LogEntity) {
		loggger.debug("Async eventLog : "+ eventLogEntity.toString());
		try{
			EventLog savedEvent = saveEventLog (eventLogEntity); 
			//return new AsyncResult<EventLog> (savedEvent);
		} catch (Exception e) {
			loggger.error("Error logging Async event ", e);
		}
		//return null;
	}
	
		
	private Query getQuery (EventLog eventLogEntity) {
		Query query= getSession().createQuery("from EventLog "
			+ "where batchId=:batchId"
			+ " and productId=:productId"
			+ " and eventId : eventId ");
		query.setParameter ("batchId", eventLogEntity.getBatchId()); 
		query.setParameter ("productId", eventLogEntity.getProductId()); 
		query.setParameter ("eventId", eventLogEntity.getEventId()); 
		/*loggger.error("batchId "+query.getParameterValue ("batchId")); 
		loggger.error("productId "+query.getParameterValue ("productId")); 
		loggger.error ("eventId "+query.getParameterValue ("eventId"));*/
		return query:
	}
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", propagation-Propagation.REQUIRES_NEW) 
	public Event Log addEventLog (EventLog eventLogEntity) {
		try{
			eventLogEntity.setCreatedBy (contactUtil.getProid());
			loggger.debug("Adding event Log: "+ event LogEntity.toString()); 
			getSession().save (eventLogEntity);
			getSession().flush();
		} catch (Exception e) {
			loggger.error("Error logging event ", e);
		}
		return eventLogEntity;
	}
	
	@Override
	@Async
	@Transactional (transactionManager = "dbTransactionManager", propagation=Propagation. REQUIRES_NEW) 
	public void addEventLogAsync (EventLog eventLogEntity) {
		loggger.debug("Adding async eventLog : "+ eventLogEntity.toString());
		try{
			EventLog addedEvent = addEventLog (eventLogEntity); 
			//return new AsyncResult<EventLog> (savedEvent);
		} catch (Exception e) {
			loggger.error("Error logging Async event ", e);
		}
		//return null;
	}
}