package com.ms.datawise.distn.datasourceReference;
import org.apache.commons.collections.MapUtils; 
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceReferenceFactory {
	
	final private Map<String, com.ms.datawise.distn.datasourceReference. IDataSourceReferenceGenerator> factoryMap = 
		new HashMap<String, com.ms.datawise.distn.datasourceReference. IDataSourceReferenceGenerator>();
	
	public DataSourceReference createReference (String appId, String refrence Desc) {	
		com.ms.datawise.distn.datasourceReference.IDataSourceReferenceGenerator factoryFunctor = getDactoryFunctor (appId); 
		if (factoryFunctor == null)
			return null;
		
		return factoryFunctor.generateReference (refrenceDesc);
	}
	
	public com.ms.datawise.distn.datasourceReference.IDataSourceReferenceGenerator getDactoryFunctor (String appId) { 
		if (MapUtils.isNotEmpty (factoryMap)) {
			return factoryMap.get(appId);
		}

		return null;
	}
	
	public void registerFacotryFunctor (String appId, com.ms.datawise.distn.datasourceReference. IDataSourceReferenceGenerator generator) { 
		this.factoryMap.put (appId, generator);
	}
}