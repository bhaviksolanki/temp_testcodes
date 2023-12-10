
package com.ms.datawise.distn.entity;
import lombok.Data;
import javax.persistence.Column; 
import javax.persistence. EmbeddedId;
import javax.persistence.Entity; 
import javax.persistence.Table; 
import java.util.Date;
@Data
@Entity
@Table (name = "Product Distribution_Hist") 
public class ProductDistributionHist {
	
	@EmbeddedId
	private ProDistUniqueID proDistUniqueID;
	@Column(name = "IS_ACTIVE") 
	private String active;
	
	@Column(name = "CREATE_DATE", updatable = false) 
	private Date createdDate;
	
	@Column(name = "UPDATE_DATE", updatable = true) 
	private Date updatedDateTime;
	
	@Column(name = "CREATE_USER_ID")
	private String createdBy;
	
	@Column(name = "UPDATE_USER_ID")
	private String updatedBy;
}