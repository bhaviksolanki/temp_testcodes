package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.annotation.processor.CopyPropAnnotationProcessor;
import com.ms.datawise.distn.entity.*;
import com.ms.datawise.distn.exception.Datawise CometException;
import com.ms.datawise.distn.exception.SetUpException;
import com.ms.datawise.distn.model.Distribution;
import com.ms.datawise.distn.model.Product ContactModel;
import com.ms.datawise.distn.util.Pair;
import org.apache.commons.collections.CollectionUtils; 
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation; 
import org.springframework.transaction.annotation.Transactional;
import javax.persistence. PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class DatasetDAO implements com.ms.datawise.distn.dao.IDatasetDAO {
	
	private static final String DISTRIBUTION_ID = "distributionId";
	
	private static final String YES = "YES";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private org.slf4j.Logger LOGGER = LoggerFactory.getLogger (Dataset DAO.class);
	
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	
	@Override
	public String addClient (Client clientEntity) {
		Session sess = getSession();
		String message = null;
		Client existingClient = null;
		try {
			Query ifClientExists = sess.createQuery("from Client where clientId = :clientId"); 
			ifClientExists.setParameter("clientId", clientEntity.getClientId());
		
			existingClient = (Client) ifClientExists.uniqueResult();
			
			if (existingClient != null) {
				message="Client with clientId" +clientEntity.getClientId() + " updated successfully."; 
				CopyPropAnnotationProcessor.copyFromSrcToTarget (clientEntity, existingClient); 
				sess.update(existingClient);
				sess.flush();
			} else {
				message="Client with clientId"clientEntity.getClientId() + " inserted successfully."; 
				sess.save(clientEntity);
				sess.flush();
		} catch (StaleStateException e) {
			sess.evict(existingClient);
			sess.save(clientEntity);
			sess.flush();
		} catch (PersistenceException e) {
			LOGGER.error("Exception Occurred ", e);
			//LOGGER.error(Netcool. DB, e.getMessage(), e);
			throw new SetUpException();
		}catch (Exception e) {
			LOGGER.error ("Exception Occurred :", e);
			//LOGGER.error (Netcool. APPLICATION, e.getMessage(), e); 
			throw new SetUpException();
		}
		return message;
	}
	
	
	@Override
	public String setupProduct(Product Detail productDetailEntity) {
		Session sess = getSession();
		String message = null;
		ProductDetail existing Product = null;
		try {
			Query ifProductExists = sess.createQuery("from Product Detail where productId :productId"); 
			ifProductExists.setParameter("productId", productDetailEntity.getProductId());
			existingProduct = (ProductDetail) ifProductExists.uniqueResult();
			
			if (existingProduct != null) {
				message = " Product with productId" + productDetailEntity.getProductId() + " updated successfully."; CopyPropAnnotationProcessor.copyFromSrcToTarget(productDetailEntity, existing Product); 
				sess.update(existing Product); 
				sess.flush();
			} else {
				message= " Product with productId" + productDetailEntity.getProductId() + "inserted successfully.";
				sess.save(productDetailEntity); 
				sess.flush();
			}
		} catch (StaleStateException e) {
			LOGGER.error("Exception Occurred while persisting product in database :",e);
			sess.evict(existingProduct);
			sess.save(product DetailEntity); 
			sess.flush();
		
		} catch (Persistence Exception e) {
			LOGGER.error("Exception Occurred while persisting product in database :", e); 
			//LOGGER.error(Netcool. DB, e.getMessage(), e);
			throw new SetUpException ("Exception Occurred while persisting product in database", e);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred while persisting product in database :", e); 
			//LOGGER.error(Netcool. APPLICATION, e.getMessage(), e);
			throw new SetUpException ("Exception Occurred while persisting product in database", e);
		}
		return message;
	}
	
		
	@Override
	public String setupDistribution(Distribution distribution, String user) {
		String message = null;
		try {
			boolean checkForDistributionId = false;
			if (StringUtils.isNotBlank (distribution.getDistributionId())) {
				checkForDistributionId = checkExistenceWithId (DistributionDetail.class, DISTRIBUTION_ID, distribution.getDistributionId());
				
				if (checkForDistributionId) {
					DistributionDetail exisitingDistribution =(Distribution Detail) fetchEntityWithId( DistributionDetail.class, 	DISTRIBUTION_ID, Integer.parseInt(distribution.getDistributionId())); 
					distribution.setClient_id (exisitingDistribution.getClientId()); 
					ProductDistribution exisitingProduct = getProductDistById(distribution.getDistributionId()); 
					distribution.setProduct_id(exisitingProduct.getProDistUnique ID ().getProduct_id());
				}
			}
			boolean clientExistence InDistribution = checkClientExistence(distribution.getClient_id(),DistributionDetail.class);
			
			boolean productExistence InDistribution = checkForProduct Distribution (distribution.getClient_id(),distribution.getProduct_id());

			if ((clientExistence InDistribution && productExistence InDistribution) || checkForDistributionId) { 
				LOGGER.debug("Distribution Setup called for Update Mode");
				message= updateExisitingDistribution (distribution, user);
			
			}else {
				message = setupNewDistribution (distribution, user);
		} catch (Exception e) {
			//LOGGER.error (Netcool.APPLICATION, e.getMessage(), e); 
			throw new SetUpException(e);
		}
		return message;
	}
	
	
	public Product DistributiongetProductDistById(String distributionID) {
		Session sess = getSession();
		Query query = getSession().createQuery(
			"from Product Distribution as diobject where diobject.proDistUnique ID.distribution_id=:distributionId"); 
			query.setParameter ("distributionId", Integer.parseInt(distributionID));
		if (query.list().size() > 0) {
			return (Product Distribution) query.list().get(0);
		}
		return null;
	}
	
	private String updateExisitingDistribution(Distribution distribution, String user) {
		boolean isProductDistUpdated = updateProdDistribution(distribution, user); 
		boolean isClientDistUpdated = updateClientDistribution(distribution, user);
		if (!isProductDistUpdated && isClientDistUpdated) {
			throw new SetUpException("Error occured while updating the distribution.");
		}
		
		return "Distribution setup for product with productId:" + distribution.getProduct_id() + " and client with clientId: " + distribution.getClient_id() + " got modified successfully" ;
	}
		
	
	private boolean updateProdDistribution (Distribution distribution, String user) {
			Session sess = getSession();
			
			Query query = sess.createQuery( 
				"select d.distributionId from DistributionDetail d, ProductDistribution p where p.proDistUniqueID.distribution_id=d.distributionId and p.proDistUniqueID.product_id=:productID and d.clientId=:clientID"); 
			query.setParameter ("clientID", distribution.getClient_id()); 
			query.setParameter ("productID", distribution.getProduct_id());
			
			List<Integer> distId = query.list();
			if (CollectionUtils.isNotEmpty (distId)) {
				Product Distribution exisitingDistribution = sess.get(Product Distribution.class,
					new ProDistUnique ID (distId.get(0), distribution.getProduct_id()));
			
			String active Flag = distribution.getActive();
			
			if (active Flag== null || active Flag.isEmpty() || active Flag.equals (exisitingDistribution.getActive())) { 
				return true;
			} else if ("Y".equals (active Flag) || "N".equals (active Flag)) {
				exisitingDistribution.setActive (distribution.getActive()); 
				exisitingDistribution.setUpdatedBy (user);
				exisitingDistribution.setUpdatedDateTime (new Date()); 
				sess.update(exisitingDistribution);
				return true;
			}
			return false;
		}
		throw new SetUpException ("Distribution Id doesn't exist for the given client and product pair.");
	}
	
	
	private boolean updateClientDistribution (Distribution distribution, String user) {
	
		Session sess = getSession();
		
		Query query sess.createQuery(
			"select d.distributionId from DistributionDetail d, ProductDistribution p where p.proDistUniqueID.distribution_id=d.distributionId and p.proDistUniqueID.product_id=:productID and d.clientId=:clientID");
		
		query.setParameter ("clientID", distribution.getClient_id()); query.setParameter("productID", distribution.getProduct_id());
		
		List<Integer> distId = query.list();
		
		if (CollectionUtils.isNotEmpty (distId)) {
			DistributionDetail exisitingDistribution = sess.get(DistributionDetail.class, distId.get(0));
			
			CopyPropAnnotation Processor.copyFromSrcToTarget (distribution, exisitingDistribution);
			exisitingDistribution.setUpdatedBy (user);
			exisitingDistribution.setUpdatedDateTime (new Date());
			sess.update (exisitingDistribution);
			return true;
		}
		throw new SetUpException("Distribution Id doesn't exist for the given client and product pair.");
	}
	
	private String setupNewDistribution (Distribution distribution, String user) {
		int distID = generateDistribution (distribution, user);
		mapProductDistribution (distID, distribution, user);
		return "Distribution setup for product with productId:" + distribution.getProduct_id() + " and client with clientId:" + distribution.getClient_id()+ " got created successfully ";
	
	@Override
	public Boolean checkForProductDistribution (String clientId, String distId) { 
		List<Integer> distIds = getDistributionIds (clientId);
		if (CollectionUtils.isEmpty (distIds))
			return false;
		
		Boolean recordExist = checkProduct DistributionRecord (distIds, distId); 
		
		return recordExist;
	}
	
	
	private Boolean checkProductDistributionRecord (final List<Integer> distIds, final String productID) { 
		Session sess = getSession();
		Query query = getSession().createQuery(
			"from ProductDistribution as diobject where diobject.proDistUniqueID.distribution_id in (:IdList) and diobject.proDistUniqueID.product_id =:productID");
		
		query.setParameterList ("IdList", distIds);
		query.setParameter ("productID", productID);
		if (query.list().size() > 0) {
			return true;
		}	
		return false;
	}
	
	private List<Integer> getDistributionIds (String clientID) {
		Session sess = getSession();
		Query distributionIds = sess
			.createQuery("select distributionId from DistributionDetail where clientId = :clientID"); 
		distributionIds.setParameter("clientID", clientID);
		List<Integer> idList = distributionIds.list();
		return idList;
	}
	
	private void mapProductDistribution (final Integer distID, final Distribution distribution, final String user) { 
		Session sess = getSession();
		Product Distribution prodDistribution = new Product Distribution();
		
		prodDistribution.setProDistUniqueID(new ProDistUniqueID(distID, distribution.getProduct_id()));
		if (distribution.getActive () != null) {
			prodDistribution.setActive (distribution.getActive());
		} else {
			prodDistribution.setActive ("Y");
		}
		prodDistribution.setCreatedBy (user);
		prodDistribution.setUpdatedBy (user);
		sess.save(prodDistribution);
		sess.flush();
	}
	
	
	private int generateDistribution (final Distribution distribution, final String user) { 
		Session sess = getSession();
		DistributionDetail distEntity = new DistributionDetail();
		CopyPropAnnotation Processor.copyFromSrcToTarget (distribution, distEntity);
		distEntity.setCreatedBy (user); distEntity.setUpdatedBy (user);
		return (int) sess.save (distEntity);
	}
	
	@Override
	public Boolean checkClientExistence (String clientID, Class entityClass) {
		Session sess = getSession();
		boolean exists = false;
		return exists;
	}
	
	private Boolean isProductExist (String productID) {
		Session sess = getSession();
		Product Detail existing Product = null;
		existing Product = sess.get(ProductDetail.class, productID); 
		return existing Product != null;
	}
	
	@Override
	public String getProductCategory (String categoryId) {
		
		try {
			Session sess = getSession();
			ProductMaster productMaster = (ProductMaster) sess.get (ProductMaster.class, categoryId);
			if (productMaster != null)
				return productMaster.getCategoryName();
		} catch (Exception e) {
			LOGGER.error("Problem occurred in Validating the Product Category. Please try again.", e);
			throw new PersistenceException ("Problem occurred in Validating the Product Category. Please try again.");
		}
		return null;
	}
	
	
	@Override
	public boolean checkExistenceWithId (Class<?> clazz, String property, String value) {
		try {
			Session sess = getSession();
			Query ifEntityExists = sess
				.createQuery("from" + clazz.getName() + "where" + property + " :propertyValue");
			if (clazz.isAssignable From (DistributionDetail.class))
				ifEntityExists.setParameter ("propertyValue", Integer.parseInt(value));
			else
				ifEntityExists.setParameter("propertyValue", value);
		
			if (ifEntityExists.uniqueResult() != null)
				return true;
		} catch (Exception e) {
			LOGGER.error("Problem occurred in Validating the Property. Please try again.", e); 
			throw new SetUpException();
		}
		return false;
	}
	
	@Override
	public <T> T fetchEntityWithId (Class<?> clazz, String property, Object value) {
		try {
			Session sess = getSession();
			Query ifEntityExists = sess
				.createQuery("from" + clazz.getName() + "where" + property + " :propertyValue"); 
			
			if (clazz.isAssignable From (DistributionDetail.class))
				ifEntityExists.setParameter ("propertyValue", value);
			else
				ifEntityExists.setParameter("propertyValue", value);
		
			if (ifEntityExists.uniqueResult() != null)
				return (T) ifEntityExists.uniqueResult();
		} catch (Exception e) {
		
			LOGGER.error("Problem occurred while fetching entity from property. Please try again.", e); 
			throw new SetUpException();
		}
		return null;
	}
	
		
	@Override
	public ProductDetail getProductDetailById(String productID) {
		Session sess = getSession();
		return sess.get(ProductDetail.class, productID);
	}
	
	@Override
	public Client getClient DetailById(String clientId) {
		Session sess = getSession();
		Query ifClientExists = sess.createQuery("from Client where clientId = :clientId"); 
		ifClientExists.setParameter("clientId", clientId);
		return (Client) ifClientExists.uniqueResult();
	}
	
	@Override
	public List<Product Detail> getProductDetails (String productAttribute, String criteria) { 
		Session sess = getSession();
		List<Product Detail> productsDetail = null;
		try {
			Query query = null;
			
			if ("ID".equals (criteria)) {
				productsDetail = new ArrayList<Product Detail>();
				Product Detail productDetail = getProductDetailById (productAttribute);
				if (productDetail != null) {
					productsDetail.add(getProductDetailById(productAttribute));
				}
			} else {
				query = sess.createQuery(" from Product Detail pd where pd.productName=:paraml "); 
				query.setParameter("paraml", productAttribute);
				productsDetail = query.list();
			}
		} catch (Exception e) {
			
			LOGGER.error("Exception Occurred :", e); 
			//LOGGER.error (Netcool. DB, e.getMessage(), e);
			
		}	
		return productsDetail;
	}
	
		
	@Override
	public List<Pair<Product Distribution, DistributionDetail>> getdistributionDetail (String clientID,String productID) {
		Session sess = getSession();
		List<Pair<Product Distribution, DistributionDetail>> entList = new ArrayList<Pair<ProductDistribution, DistributionDetail>>();
		
		try {
			@SuppressWarnings("unchecked")
			Query<Object> qDistribution sess.createQuery(
				" from Product Distribution p join fetch DistributionDetail d on d.distributionId=p.proDistUniqueID.distribution_id where p.proDistUniqueID.product_id = productId and d.clientId = :clientId"); 
			
			qDistribution.setParameter ("productId", productID); 
			qDistribution.setParameter ("clientId", clientID);
			
			List<Object> resList = qDistribution.list();
			for (Object o : resList) {
				if (o instanceof Object[]) {
					Object[] arr = (Object[]) 0;
					ProductDistribution proDuct Distribution = null; 
					DistributionDetail distributionDetail = null;
					for (int i = 0; i < arr.length; i++) {
						if (arr[i] instanceof ProductDistribution) {
							proDuctDistribution = (ProductDistribution) arr[i];
						}
						if (arr[i] instanceof DistributionDetail) {
							distributionDetail = (DistributionDetail) arr[i];
						}
					}
					Pair<ProductDistribution, DistributionDetail> prodObj = new Pair<ProductDistribution, DistributionDetail>( 
						proDuctDistribution, distributionDetail); 
					entList.add(prodObj);
				}
			}
		} catch (Exception e) {
			LOGGER.error ("Exception Occurred :", e);
		}
		return entList;
	}
	

	@Override
	public Boolean checkDistributionWasActivated (Product Distribution distribution) {
		Session sess = getSession();
		Query ifDistributionWasActive = sess.createQuery("from ProductDistributionHist pdh where pdh.proDistUniqueID.distribution_id=:distributionId and pdh.active='Y'"); 
		ifDistributionWasActive.setParameter ("distributionId", distribution.getProDistUnique ID ().getDistribution_id()); 
		List<Product DistributionHist> result = new ArrayList<Product DistributionHist>();
		result ifDistributionWasActive.list();
		if (result!=null && result.size()>0) {
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public Boolean checkProductWasActivated (Product Detail productDetailEntity) {
		Session sess = getSession();
		Query ifProductWasActive = sess.createQuery("from ProductDetailHist ph where ph.productId=:productId and ph.active='Y'"); 
		ifProductWasActive.setParameter ("productId", productDetailEntity.getProductId());
		List<Product DetailHist> result = new ArrayList<Product DetailHist>();
		result = ifProductWasActive.list();
		if (result!=null && result.size()>0){
			return true;
		} else{
			return false;
		}
	}
	
	
	@Override
	public void setupProductContact (ProductContact productContactEntity) {
		Session session = getSession();
		String message = null;
		try{
			message= "Contacts for Product with product Id" + productContactEntity.getProductId() + "inserted successfully."; 
			session.save (productContactEntity);
			session.flush();
		}
		catch (StaleStateException e) {
			session.save (productContactEntity); 
			session.flush();
		} catch (PersistenceException e) {
			LOGGER.error ("Exception Occurred : ", e);
			//LOGGER.error (Netcool. DB, e.getMessage(), e);
			throw new SetUpException();
		} catch (Exception e) {
			LOGGER.error ("Exception Occurred : ", e);
			//LOGGER.error (Netcool. APPLICATION, e.getMessage(), e);
			throw new SetUpException();
		}
	}
	
	
	@Override
	public List<DistributionStatus> fetchStatusForDistribution (String startTime, String endTime) { Session sess = getSession();
		Query distributionStatus = sess.createNativeQuery(
			"Select DISTRIBUTION_ID, CLIENT_ID, CLIENT_NAME, DIST_TYPE, ACTION, PRODUCT_ID, PRODUCT_NAME, ACTION_STATUS, ACTION_TIMESTAMP, FAILURE_REASON" 
			+" FROM ("
			+" Select DISTRIBUTION_ID, CLIENT_ID, CLIENT_NAME, DIST_TYPE, ACTION, PRODUCT_ID, PRODUCT_NAME, ACTION_STATUS, ACTION_TIMESTAMP, FAILURE_REASON, " 
			+" RANK () over (partition by DISTRIBUTION_ID order by ACTION_TIMESTAMP desc) as RANK "
			+" from ( "
				+"SELECT"
				+" cdl.DISTRIBUTION_ID, c.CLIENT_ID,c.CLIENT_NAME, dd.DIST_TYPE, cdl.ACTION, pn.PRODUCT_ID, pn.PRODUCT_NAME, cdl.ACTION_STATUS, cdl.ACTION_TIMESTAMP, cdl.FAILURE_REASON " 
				+" FROM "
					+" res_dw_dstn.CLIENT_DISTRIBUTION_LOG cdl JOIN res_dw_dstn.DISTRIBUTION_DETAIL dd "
					+" ON cdl.DISTRIBUTION_ID = dd.DISTRIBUTION_ID,"
					+" res dw dstn.CLIENT c,"
					+" res_dw_dstn.PRODUCT_DISTRIBUTION pd, "
					+" res_dw_dstn.PRODUCT_DETAIL pn "
				+" WHERE dd.CLIENT_ID = c.CLIENT_ID AND dd.DISTRIBUTION_ID = pd.DISTRIBUTION_ID"
				+" AND pd.PRODUCT_ID = pn.PRODUCT_ID AND cdl.ACTION_TIMESTAMP BETWEEN :startTime AND :endTime" 
				+" )"
			+") tl where RANK=1 ", DistributionStatus.class);
		
		distributionStatus.setParameter ("startTime", startTime); 
		distributionStatus.setParameter ("endTime", endTime);
		List<DistributionStatus> result = new ArrayList<DistributionStatus>();
		result = distributionStatus.getResultList();
		return result;
	}
	
	
	@Override
	public int setupClientContact (ClientContact clientContactEntity) {
		Session session = = getSession();
		String message = null;
		Integer identifier = 0;
		try{
			identifier (Integer) session.save (clientContactEntity); 
			session.flush();
			message="Contacts for Client with client Id" + clientContactEntity.getClientId() + "inserted successfully."; 
			clientContactEntity.setContactId(identifier);
		} catch (StaleStateException e) {
			session.save (clientContactEntity);
			session.flush();
		} catch (PersistenceException e) {
			LOGGER.error ("Exception Occurred : ", e);
			//LOGGER.error (Netcool. DB, e.getMessage(), e);
			throw new SetUpException();
		} catch (Exception e) {
			LOGGER.error ("Exception Occurred : ", e);
			//LOGGER.error (Netcool. APPLICATION, e.getMessage(), e);
			throw new SetUpException();
		}
		return identifier;
	}
	
	@Override
	public void updateClientContact (ClientContact clientContactEntity) {
		Session session= getSession();
		List<Client Contact> clientContactEntities = getClientContact Detail (clientContactEntity); 
		ClientContact fetchedEntity;
		if (clientContactEntities.size() > 0) {
			for (Iterator <ClientContact> iterator = clientContactEntities.iterator(); iterator.hasNext(); ) {
				fetchedEntity = iterator.next();
				CopyPropAnnotation Processor.copyFromSrcToTarget (clientContactEntities, fetchedEntity); 
				LOGGER.debug("final entity to be updated "+fetchedEntity.toString());
				session.update (fetchedEntity);
			}
			session.flush();
		} else {
			LOGGER.warn("No Result found to update");
		}
	}
	
	
	@Override
	public void inactivateProductContact (ProductContactModel productContactModel) throws DatawiseCometException { 
		Session sess = getSession();
		Query query = getSession().createQuery(
		"from ProductContact as prodContact where prodContact.productId=:productId and prodContact.contactType=:contactType"); 
		
		query.setParameter ("productId", Integer.parseInt (productContactModel.getProductId())); 
		query.setParameter ("contactType", Integer.parseInt (product ContactModel.getContactType()));
		if (query.list().size() > 0) {
			List<Product Contact> prodContactList = query.list();
			for (Iterator iterator = prodContactList.iterator(); iterator.hasNext(); ) {
		
				ProductContact product Contact = (ProductContact) iterator.next();
				productContact.setIsActive ("N");
				Object obj = sess.save (productContact);
				if (obj==null) {
					throw new Datawise CometException("Error Updating Old Product Contacts to Inactive");
				}
			}
			sess.flush();
		}
	}
	
	@Override
	public List<Client Contact> getClient Contact Detail (Client Contact clientContactEntity) {
		Session session = getSession();
		Query query = session.createQuery(
				"from ClientContact as clientContact "
				+ "where clientContact.clientId=:clientId"
				+ "and clientContact.edgContactId=: edgContactId");
		query.setParameter ("clientId", clientContactEntity.getClientId()); 
		query.setParameter ("edgContact Id", clientContactEntity.getEdgContactId());
		
		ClientContact fetchedEntity = null;
		List<ClientContact> clientContactEntities= null;
		clientContactEntities = query.list();
		
		return clientContactEntities;
	}
	
	
	@Override
	@Transactional (transactionManager ="dbTransactionManager", propagation-Propagation. REQUIRED, readonly=true) 
	public List<ClientContact> fetchContactDetail (String clientID) {
		Session session = getSession();
	
		Query contact DetailsQuery = session.createQuery(" from Client Contact where clientId=:clientID and isActive='Y'"); 
		contactDetailsQuery.setParameter ("clientID", clientID);
		return contact DetailsQuery.list();
	}
	
	@Override
	public List<DistributionEndpoint> fetchAllDistributionEndPoint Detail() {
		Session session= getSession();
		Query contactEndPoint DetailsQuery = session.createQuery(" from DistributionEndpoint where isActive='Y'");
		return (List<DistributionEndpoint>) contactEndPoint DetailsQuery.getResultList();
	}
	
	@Override
	public int setupDistributionEndPoint (DistributionEndpoint distributionEndPoint) {
		Session session= getSession();
		String message = null;
		Integer identifier = 0;
		try{
			identifier = (Integer) session.save (distributionEndPoint);
			session.flush();
			message= "Endpoints inserted successfully.";
			
			distributionEndPoint.setEndPoint DetailId(identifier);
		} catch (StaleStateException e) {
			session.save (distributionEndPoint); 
			session.flush();
		} catch (PersistenceException e) {
			LOGGER.error ("Exception Occurred : ", e);
			//LOGGER.error (Netcool. DB, e.getMessage(), e);
			throw new SetUpException();
		} catch (Exception e) {
		
			LOGGER.error ("Exception Occurred : ", e);
			//LOGGER.error (Netcool. APPLICATION, e.getMessage(), e); 
			throw new SetUpException();
		}
		return identifier;
	}
	
	
	@Override
	@Transactional (transactionManager = "dbTransactionManager", propagation-Propagation. REQUIRED, readOnly=true)
	public List<client> fetchClient IDs For Product (String productID) {
		Session sess = getSession();
		List<String> clientList = new ArrayList<String>();
		List<client> clients = new ArrayList<Client>();
		try {
			Query clientQuery = sess.createNativeQuery("select dd.client_id from res_dw_dstn.PRODUCT_DISTRIBUTION pd JOIN res_dw_dstn.DISTRIBUTION_DETAIL dd on pd.DISTRIBUTION_ID = dd.distribution_id" 
				+ "where product_id=:productID and pd.is_active='Y'");
			clientQuery.setParameter ("productID", productID);
			clientList=clientQuery.getResultList();
			for (String clientID: clientList) {
				clients.add(getClientDetailById(clientID));
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occurred :", e);
		}
		return clients;
	}
}