
package com.ms.datawise.distn.entity;
import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.Id; 
import javax.persistence.Table;
import lombok. AllArgsConstructor; 
import lombok.Data;
import lombok. NoArgsConstructor;
import org.hibernate.annotations. DynamicInsert;
import com.ms.datawise.distn.annotation.CopyProp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "PRODUCT_ADDITIONAL_DETAIL") @DynamicInsert
public class ProductAdditionalDetail {
	@Id
	@Column(name = "PRODUCT_ID")
	@CopyProp (targetPropName = "productId") 
	private String productId;
	
	@Column(name = "WINDOW")
	@CopyProp (targetPropName = "window", targetPropType = "DOUBLE")
	private double window; 
	
	@Column(name = "CHECK_INTERVAL")
	@CopyProp (targetPropName = "checkInterval", targetPropType = "DOUBLE") 
	private double checkInterval;
	
	@Column(name = "QUIET_DURATION")
	@CopyProp (targetPropName = "quiet Duration", targetPropType = "DOUBLE") 
	private double quiet Duration;
	
	@Column(name = "START_ALERT_LEVEL")
	@CopyProp (targetPropName = "startAlertLevel")
	private String startAlertLevel;
	
	@Column(name = "QUIET_ALERT_LEVEL")
	@CopyProp (targetPropName = "quietAlertLevel") 
	private String quietAlertLevel;
	
	
	
	@Column (name = "DEFAULT_ALERT_LEVEL") 
	@CopyProp (targetPropName = "defaultAlertLevel")
	private String defaultAlertLevel;
	
	@Column(name = "ALERT_LEVEL_SEVERITY")
	@CopyProp (targetPropName = "alertLevelSeverity")
	private String alertLevelSeverity;
	
	@Column(name = "ALERT_RECIPIENT_GROUP") 
	@CopyProp (targetPropName = "alertRecipientGroup")
	private String alertRecipientGroup;
	
	@Column(name = "HARD_EXPIRY_BUFFER") 
	@CopyProp (targetPropName = "hard_expiry_buffer"," targetPropType = "DOUBLE")
	private double hard_expiry_buffer;
	
	
	@Column(name = "ASOF_CONFIG") 
	@CopyProp (targetPropName = "asof_config")
	private String asof_config;
}
	