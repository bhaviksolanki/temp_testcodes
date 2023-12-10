
package com.ms.datawise.distn.entity;
import com.ms.datawise.distn.annotation.CopyProp; 
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp; 
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.Id; 
import javax.persistence.Table; 
import java.util.Date;
@Data
@Entity
@Table (name = "DIST_TYPE") 
public class DistType {
	@Id
	@Column(name = "DIST_TYPE")
	private String distType;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Date createDate;
	
	@Column(name = "UPDATE_DATE", updatable = true) 
	@CopyProp (target PropName = "updateDate")
	@UpdateTimestamp
	private Date updateDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false) 
	private String createdBy;
	
	@Column(name = "UPDATE_USER_ID", updatable = true) 
	@CopyProp (targetPropName = "updatedBy")
	private String updatedBy;
	
	public String getDistType() {
		return distType;
	}
	
	public void setDistType(String distType) {
		this.distType = distType;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate (Date createDate) { 
		this.createDate= createDate;
	}
	public Date getUpdateDate() {	
		return updateDate;
	}
	public void setUpdateDate (Date updateDate) { 
		this.updateDate= updateDate;
	}
	
	public String getCreatedBy() {	
		return createdBy;
	}
	public void setCreatedBy (String createdBy) { 
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy (String updatedBy) {
		this.updatedBy = updatedBy;
	}
}