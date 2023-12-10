
package com.ms.datawise.distn.entity;
import com.ms.datawise.distn.annotation.CopyProp; 
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp:  
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@DynamicInsert
@Table (name = "DISTRIBUTION_ENDPOINT_MASTER") 
public class DistributionEndpoint {
	
	@Id
	@Column(name="ENDPOINT_DETAIL_ID")
	@GeneratedValue (strategy = GenerationType.TABLE) 
	@CopyProp (targetPropName = "endPointDetailId") 
	private Integer endPointDetailId;
	
	@Column(name="ENDPOINT_TYPE")
	@CopyProp (targetPropName = "endPointType") 
	private String endPointType;
	
	@Column(name="FTP_HOST")
	@CopyProp (targetPropName = "ftpHost")
	private String ftpHost;
	
	@Column(name="FTP_ID")
	@CopyProp (targetPropName = "ftpId")
	private String ftpId;
	
	@Column(name="DIRECTORY")
	@CopyProp (targetPropName = "directory")
	private String directory;
	
	@Column(name="IS_ACTIVE")
	@CopyProp (targetPropName = "isActive")
	private String isActive;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Date createDate;
	
		
	@Column(name = "UPDATE_DATE", updatable = true)
	@CopyProp (targetPropName = "updateDate")
	@UpdateTimestamp
	private Date updateDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false)
	private String createdBy:
	
	@Column(name = "UPDATE_USER_ID", updatable = true)
	@CopyProp (targetPropName ="updatedBy") 
	private String updatedBy;
	
	@Override
	public String toString() {
	return "DistributionEndpoint_Entity [endPoint DetailId=" + endPoint DetailId", endPointType=" + endPointType 
		+ ", ftpHost="+ftpHost +", ftpId=" + ftpId + ", directory=" + directory + ", isActive=" + isActive 
		+ " createDate=" + createDate + ", updateDate=" + updateDate + ", createdBy=" + createdBy + " , updatedBy=" + updatedBy + "]";
	}
	
	@Override
	public boolean equals (Object o) {
		if (0 == this) return true;
		if (o == null || o.getClass() != getClass()) return false;
		
		DistributionEndpoint e = (DistributionEndpoint) o;
		
		return this.getEndPointType().trim().equalsIgnoreCase (e.getEndPointType().trim()) && 
			   this.getFtpHost().trim().equalsIgnoreCase (e.getFtpHost().trim()) && 
			   this.getFtpId().trim().equalsIgnoreCase (e.getFtpId().trim()) && 
			   this.getDirectory().trim().equalsIgnoreCase (e.getDirectory().trim());
	}
	
	@Override
	public int hashCode(){
		final int prime = 23;
		int result = 1;
		result = prime * result + this.endPointDetailId;
		result = prime * result + result + ((this.endPointType == null) ? 0: endPointType.hashCode());
		resutl = prime * result + ((this.ftpHost == null)? 0: ftpHost.hashCode());
		result = prime * result + ((this.ftpId == null) ? 0: ftpId. hashCode());
		return result;
	}
}