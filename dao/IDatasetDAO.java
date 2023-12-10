package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.*;
import com.ms.datawise.distn.exception. DatawiseCometException;
import com.ms.datawise.distn.model.Distribution;
import com.ms.datawise.distn.model. Product ContactModel;
import com.ms.datawise.distn.util.Pair;
import java.util.List;
public interface IDatasetDAO {
	
	public String addClient (Client clientEntity);
	
	public String setupProduct (Product Detail product DetailEntity);
	
	public String setupDistribution (Distribution distribution, String user);
	
	String getProduct Category (String categoryId);
	
	boolean checkExistenceWithId (Class<?> clazz, String id, String value); Boolean checkClientExistence (String clientID, Class entityClass);
	
	Boolean checkForProduct Distribution (String clientId, String distId);
	
	public Product Detail getProduct DetailById(String product_id);
	
	public <T> T fetchEntityWithId (Class<?> clazz, String property, Object value);
	
	public List<Product Detail> getProduct Details (String productAttribute, String criteria);
	
	public List<Pair<Product Distribution, DistributionDetail>> getdistribution Detail (String clientID, String productID);
	
	public Client getClientDetailById(String clientId);
	
	public Product Distribution getProduct DistById(String distributionID);
	
	public Boolean checkDistributionWasActivated (ProductDistribution distribution);
	
	public Boolean checkProductWasActivated (Product Detail productDetailEntity);
	
	void setupProductContact (Product Contact productContactEntity);
	
	public List<DistributionStatus> fetchStatusForDistribution (String startTime, String endTime);
	
	

	/*
	** product contact : not bing used
	* @param product ContactModel
	* @throws DatawiseCometException
	*/
	
	public void inactivate ProductContact (Product ContactModel product ContactModel) throws Datawise CometException; 
	
	/**
	* Client Contact INSERT, FETCH and UPDATE methods
	* @param clientContactEntity
	* @return
	*/
	
	public int setupClientContact (ClientContact clientContactEntity);
	public List<Client Contact> getClientContact Detail (Client Contact clientContactEntity); 
	public void updateClient Contact (Client Contact clientContactEntity);
	public List<Client Contact> fetchContact Detail (String clientID);
	
	/*** Distribution Endpoint INSERT method */
	
	public int setupDistributionEndPoint (DistributionEndpoint distributionEndPoint);
	
	public List<DistributionEndpoint> fetchAllDistributionEndPointDetail ();
	
	public List<Client> fetchClient IDsForProduct (String productID);
}