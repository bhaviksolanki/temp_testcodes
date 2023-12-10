
package com.ms.datawise.distn.entity;
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.Id; 
import java.io.Serializable; 
import java.util.Date;
@Entity
public class DistributionStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "DISTRIBUTION_ID")
	private Integer distributionId;
	
	@Column(name = "CLIENT_ID") 
	private String clientId;
	
	@Column(name = "CLIENT_NAME") 
	private String clientName;
	
	@Column(name = "DIST_TYPE") 
	private String distType;
	
	@Column(name = "ACTION") 
	private String action;
	
	@Column(name = "PRODUCT_ID") 
	private String productId;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "ACTION_STATUS") 
	private String actionStatus;
	
	@Column(name = "ACTION_TIMESTAMP") 
	private Date actionTimeStamp;
	
	@Column(name="FAILURE_REASON") 
	private String failureReason;
}