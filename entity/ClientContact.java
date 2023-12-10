package com.ms.datawise.distn.entity;
import com.ms.datawise.distn.annotation.CopyProp; 
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp; 
import org.hibernate.annotations.Dynamic Insert; 
import org.hibernate.annotations.UpdateTimestamp:
import javax.persistence.*; 
import java.util.Date;
@Data
@Entity
@DynamicInsert
@Table (name = "CLIENT_CONTACT") 
public class ClientContact {
	@Id
	@Column(name="CONTACT_ID")
	@GeneratedValue (strategy = GenerationType.TABLE) 
	private Integer contactId;
	
	@Column(name="CLIENT_ID") 
	private String clientId;
	
	@Column(name="EDG_CONTACT_ID") 
	private String edgContactId;
	
	@Column(name="CONTACT_TYPE") 
	private String contactType;
	
	@Column(name="IS_SUPPORT") 
	private String isSupport;
	
	@Column(name="IS_ACTIVE") 
	private String isActive;
	
	@Column(name = "CREATE_DATE", updatable = false) 
	
	@CreationTimestamp
	private Date createDate;
	
	@Column(name = "UPDATE_DATE", updatable = true)
	@CopyProp (targetPropName = 	"updateDate")
	@UpdateTimestamp
	private Date updateDate;

	@Column(name = "CREATE_USER_ID", updatable = false)
	private String createdBy;
	
	
	@Column (name = "UPDATE_USER_ID", updatable = true)
	@CopyProp (targetPropName = "updatedBy")
	private String updatedBy;
	

	@Override
	public String toString() {
		return "ClientContact Entity [contactId=" + contactId + ", clientId=" + clientId + ", edgContactId=" +edgContactId", 
		contactType=" + contactType+", isSupport=" + isSupport + ", isActive=" + isActive + " createDate=" + createDate + ", 
		updateDate=" + updateDate", createdBy=" + createdBy + " , updatedBy=" + updatedBy + "]";
	}
}