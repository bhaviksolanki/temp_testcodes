
package com.ms.datawise.distn.entity;
import com.ms.datawise.distn.annotation.CopyProp; 
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp:  
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@DynamicInsert
@Table (name = "PRODUCT_CONTACT") 
public class ProductContact {
	@Id
	@Column(name="CONTACT_ID")
	@GeneratedValue (strategy = GenerationType.TABLE) 
	private Integer contactId;
	@Column(name="PRODUCT_ID") 
	private String productId;
	
	@Column(name="CONTACT_NAME") 
	private String contactName;
	
	@Column(name="CONTACT_EMAIL") 
	private String contactEmail;
	
	@Column(name="CONTACT_TYPE") 
	private String contactType;
	
	@Column(name="IS_SUPPORT") 
	private String isSupport;
	
	@Column(name="IS_ACTIVE") 
	private String isActive;
	
	@Column(name = "CREATE_DATE", updatable = false) @CreationTimestamp
	private Date createDate;
	
	
	@Column(name = "UPDATE_DATE", updatable = true)
	@CopyProp (targetPropName
	@UpdateTimestamp = "updateDate")
	private Date updateDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false)
	private String createdBy;
	
	@Column(name = "UPDATE_USER_ID", updatable = true)
	@CopyProp (targetPropName = "updatedBy")
	private String updatedBy;
	
	@Override
	public String toString() {
		return "ProductContact_Entity [contactId=" + contactId + ", productId=" + productId", contactName=" + contactName + ", contactEmail=" 
		+ contactEmail + ", contactType=" + contactType + ", isSupport="
		+ isSupport + ", isActive=" + isActive", createDate=" + createDate + ", updateDate=" + updateDate 
		+", createdBy=" + createdBy+", updatedBy=" + updatedBy+ "]";
	}
}