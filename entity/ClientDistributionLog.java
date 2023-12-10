
package com.ms.datawise.distn.entity;
import com.ms.datawise.distn.annotation.CopyProp; 
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp; 
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table (name="CLIENT_DISTRIBUTION_LOG") @DynamicUpdate
public class ClientDistributionLog {
	
	@Id
	@GeneratedValue (strategy-GenerationType.IDENTITY)
	@Column(name="RUN_ID", insertable = false, updatable = false) 
	@CopyProp (targetPropName = "run_id")
	private Integer run_id;
	
	@Column(name="TXN_ID")
	@CopyProp (targetPropName = "txn_id")
	private String txn_id;
	
	@Column(name="DISTRIBUTION_ID")
	@CopyProp (target PropName = "distribution_id") 
	private Integer distributionId;
	
	@Column(name="ACTION")
	@CopyProp (targetPropName = "action")
	private String action;
	
	@Column(name = "ACTION_TIMESTAMP")
	@CreationTimestamp
	@CopyProp (target PropName = "actionTimeStamp")
	private Date actionTimeStamp;
	
	@Column(name = "ACTION_STATUS")
	@CopyProp (targetPropName = "actionStatus")
	private String actionStatus;
	
	@Column(name="FILE_NAME")
	@CopyProp (targetPropName = "fileName")
	private String fileName;
	
	
	public void setDistributionId (Integer distribution_id) { 
		this.distributionId = distribution_id;
	}
	
	public String getAction () {
		return action;
	}
	
	public void setAction (String action) { 
		this.action= action;
	}
	
	public String getFileName () {
		return fileName;
	}
	
	public void setFileName (String fileName) { 
		this.fileName = fileName;
	}
	public String getSourceFolder() {
		return sourceFolder;
	}
	public void setSourceFolder (String sourceFolder) { 
		this.sourceFolder = sourceFolder;
	}
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType (String requestType) { 
		this.requestType = requestType;
	}
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Date getActionTimeStamp() {
		return actionTimeStamp;
	}
	
	
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus (String actionStatus) { 
		this.actionStatus = actionStatus;
	}
	public String getProductId() {	
		return productId;
	}
	public void setProductId (String productId) { 
		this.productId = productId:
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName (String productName) { 
		this.productName = productName;
	}
	public String getIsHistory() {
		return isHistory;
	}
	public void setIsHistory (String isHistory) {
		this.isHistory = isHistory;
	}
	
	public String getFailureReason () {
		return failureReason;
	}
	
	public void setFailureReason (String failureReason) { 
		this.failureReason = failureReason;
	}
}