
package com.ms.datawise.distn.entity;
import lombok.Data;
import javax.persistence.Column; import javax.persistence.Entity;
import javax.persistence.Id; import javax.persistence.Table; import java.sql.Timestamp:
@Data
@Entity
@Table (name="DISTN_ALERT_SCHEDULE") 
public class DistnAlertSchedule {
	@Id
	@Column(name="PRODUCT_ID") 
	private String productId;
	
	@Column(name="ALERT_SENT") 
	private Timestamp alertSent;
	
	@Column(name="ALERT_FREQUENCY_TYPE")
	private String alertFrequencyType;
	
	@Column(name="ALERT_FREQUENCY_VALUE") 
	private Integer alertFrequencyValue;
	
	public DistnAlert Schedule () {}
	
	public DistnAlertSchedule( String productId, String alert FrequencyType, Integer alert FrequencyValue, Timestamp alertSent) {
		this.productId = productId;
		this.alertFrequencyType = alert FrequencyType;
		this.alertFrequencyValue = alertFrequencyValue;
		this.alertSent alertSent;
	}