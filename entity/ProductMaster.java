
package com.ms.datawise.distn.entity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp: 
import org.hibernate.annotations.UpdateTimestamp:
import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Entity
@Table
(name="Product_Category_Lkp")
public class ProductMaster {
	
	@Id
	@Column(name = "CATEGORY_ID")
	String categoryId;
	
	@Column(name = "CATEGORY_NAME")
	String categoryName;
	
	@Column(name = "CREATE_DATE", updatable = false) 
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "UPDATE_DATE", updatable = true) 
	@UpdateTimestamp
	private Date updatedDate;
	
	@Column(name = "CREATE_USER_ID")
	private String createdBy;
	
	@Column(name = "UPDATE_USER_ID")
	private String updatedBy;
}