package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.annotation.processor.CopyPropAnnotationProcessor; 
import com.ms.datawise.distn.entity.ProductAdditionalDetail;
import com.ms.datawise.distn.entity.Product Detail;
import com.ms.datawise.distn.entity.WindowDistributionLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory; 
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation. Propagation; 
import org.springframework.transaction.annotation. Transactional;
import javax.persistence.criteria. CriteriaBuilder;
import javax.persistence.criteria. CriteriaQuery; 
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@Component
public class WindowSLADAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unused")
	private org.slf4j.Logger logger = LoggerFactory.getLogger (WindowSLADAO.class);
	
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Transactional (readOnly= true)
	public List<WindowDistributionLog> getActiveDistributions () {
		Session sess = getSession();
		@SuppressWarnings("unchecked")
		Query<WindowDistributionLog> query = sess.getNamedQuery("callgetactivewindows"); 
		List<WindowDistributionLog> resList = query.list();
		return resList;
	}
}