
package com.ms.datawise.distn.entity;
import java.util.Date;
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.FetchType; 
import javax.persistence.Id;
import javax.persistence.JoinColumn; 
import javax.persistence.OneToOne; 
import javax.persistence.Table;
import lombok. AllArgsConstructor; 
import lombok.Data;
import lombok. NoArgsConstructor;
import org.hibernate.annotations. CreationTimestamp: 
import org.hibernate.annotations. Dynamic Insert; 
import org.hibernate.annotations. UpdateTimestamp:
import com.fasterxml.jackson.annotation.JsonIgnore; 
import com.ms.datawise.distn.annotation.CopyProp;
import com.ms.datawise.distn.annotation.EventPayloadParam;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table (name = "Product Detail")
public class ProductDetail {
	@Id
	@Column(name = "PRODUCT_ID") 
	@Event PayloadParam
	@CopyProp (targetPropName = "productId")
	private String productId;
	
	// @OneToOne (cascade CascadeType.ALL, mappedBy="productDetailEntity") 
	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "PRODUCT_ID")
	private ProductAdditionalDetail additionalProductdetails;
	
	@Column(name = "PRODUCT_CATEGORY")
	@CopyProp (targetPropName = "productCategory")
	private String productCategory;
	
	
	@Column(name = "PRODUCT_NAME") 
	@EventPayloadParam
	@CopyProp (targetPropName = "productName")
	private String productName;
	
	@Column(name = "MODEL")
	@CopyProp (targetPropName = "model")
	private String model;
	
	@Column(name = "SOURCE_PATH")
	@Event PayloadParam
	@CopyProp (targetPropName ="sourcePath")
	private String sourcePath;
	
	@Column(name = "DIST_FILE_PATTERN")
	@CopyProp (targetPropName = "distFilePattern")
	@Event PayloadParam
	private String distFilePattern;
	
	@Column(name = "FILE_FORMAT")
	@CopyProp (targetPropName = "fileFormat")
	private String fileFormat;
	
	@Column(name = "STD_CUSTOM")
	@CopyProp (targetPropName = "stdCustom")
	private String stdCustom;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "UPDATE_DATE", updatable = true) 
	@CopyProp (targetPropName = "updatedDate") 
	@UpdateTimestamp
	private Date updatedDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false) 
	private String createdBy;
	
	@Column(name = "UPDATE_USER_ID", updatable = true)
	@CopyProp (targetPropName="updatedBy")
	private String updatedBy;
	
	@Column(name = "AUTOSYS_SCHEDULE")
	@CopyProp (targetPropName="autosysSchedule")
	private String autosysSchedule;
	
	
	@Column (name = "AUTOSYS_DETAILS")
	@CopyProp (targetPropName = "autosysName") 
	private String autosysName;
	
	@Column(name = "IS_ACTIVE")
	@CopyProp (targetPropName = "active")
	@Event PayloadParam
	private String active;
	
	@CopyProp (targetPropName = "esclateEmail")
	@Event PayloadParam
	
	@Column(name = "ESCLATE_EMAIL")
	private String esclate Email;
	
	@Column(name = "IS_HISTORY")
	@CopyProp (targetPropName = "isHistory") 
	private String isHistory;
	
	@Column(name = "ASOF_CONFIG")
	@CopyProp (targetPropName = "asof_config") 
	private String asof_config;
	
	@Column(name = "CALENDAR_CODE")
	@CopyProp (targetPropName = "calendar_code") 
	private String calendar_code;
	
	@JsonIgnore
	public boolean isActive Product() {
		return (active != null && active.equalsIgnoreCase ("Y"));
	}
}