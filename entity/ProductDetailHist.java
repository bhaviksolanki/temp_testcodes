
package com.ms.datawise.distn.entity;
import lombok.Data;
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Table; 
import java.util.Date;
@Data
@Entity
@Table (name = "Product_Detail_Hist") 
public class ProductDetailHist {
	@Id
	@Column(name = "PRODUCT_ID") 
	private String productId;
	
	@Column(name = "PRODUCT_CATEGORY") 
	private String productCategory;
	
	@Column(name = "PRODUCT_NAME") 
	private String productName;
	
	@Column(name = "MODEL") 
	private String model;
	
	@Column(name = "SOURCE_PATH")
	private String sourcePath;
	
	@Column(name = "DIST_FILE_PATTERN") 
	private String distFilePattern;
	
	@Column(name = "FILE_FORMAT")
	private String file Format;
	
	@Column(name = "STD_CUSTOM") 
	private String stdCustom;
	
	@Column(name = "CREATE_DATE", updatable = false) 
	private Date createdDate;
	
	@Column(name = "UPDATE_DATE", updatable = true)
	private Date updatedDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false) 
	private String createdBy;
	
	
	@Column(name = "UPDATE_USER_ID", updatable = true) 
	private String updatedBy;
	
	@Column(name = "AUTOSYS_SCHEDULE") 
	private String autosysSchedule;
	
	@Column(name = "AUTOSYS_DETAILS")
	private String autosysName;
	
	@Column(name = "IS_ACTIVE") 
	private String active;
	
	@Column(name = "ESCLATE_EMAIL") 
	private String esclateEmail;
	
	@Column(name = "IS_HISTORY") 
	private String isHistory;
}