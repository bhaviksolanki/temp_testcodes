
package com.ms.datawise.distn.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Product_Distribution")
public class ProductDistribution implements Serializable { 
	@EmbeddedId
	private ProDistUnique ID proDistUniqueID;
	
	@Column(name = "IS_ACTIVE") 
	private String active;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "CREATE_USER_ID")
	private String createdBy;
	
	@Column(name = "UPDATE_DATE", updatable = true) 
	@UpdateTimestamp
	private Date updatedDateTime;
	
	@Column(name = "UPDATE_USER_ID")
	private String updatedBy;
	
	@Column(name = "ROW_START")
	private Date rowStart;
	
	@Column(name = "ROW_END") 
	private Date rowEnd;
	
	@Column(name = "RW_ID")
	private Date rwId;
	
	public boolean isActive() {
		return (active != null && active.equalsIgnoreCase ("Y"));
	}
}