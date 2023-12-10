
package com.ms.datawise.distn.datasourceReference;

public interface IDataSourceReferenceGenerator {
	public DataSourceReference generateReference (String refrence Desc);
	String retriveDataFromSource (String refrence Desc, String productId, String savePath, String transactionId);
}