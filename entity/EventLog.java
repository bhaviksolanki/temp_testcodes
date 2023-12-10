
package com.ms.datawise.distn.entity;
import com.fasterxml.jackson.annotation.JsonProperty; 
import lombok.Data;
import lombok. NoArgsConstructor;
import org.hibernate.annotations. CreationTimestamp: 
import org.hibernate.annotations. DynamicUpdate;
import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@Entity
@Table (name = "EVENT_LOG") @DynamicUpdate
public class EventLog {
	@Id
	@GeneratedValue (strategy-GenerationType.TABLE)
	@Column(name="LOG_ID")
	private int logId;
	
	@Column(name="BATCH_ID") 
	private String batchId;
	
	@Column(name="PRODUCT_ID") 
	private String productId;
	
	@Column(name="EVENT_ID") 
	private String eventId;
	
	@Column(name="EVENT_DESC") 
	private String eventDesc:
	
	@Column(name="ERROR_CODE") 
	private String errorCode;
	
	@Column(name="PROCESS_DESC") 
	private String processDesc;
	
	@Column(name = "CREATE_DATE", updatable = false) 
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false) 
	private String createdBy;
	
	
	@Transient
	private String model;
	
	public EventLog(
		@JsonProperty("batchId") String batchId,
		@JsonProperty("productId") String productId, 
		@JsonProperty("eventId") String eventId, 
		@JsonProperty("eventDesc") String event Desc, 
		@JsonProperty("errorCode") String errorCode,
		@JsonProperty("processDesc") String processDesc, 
		@JsonProperty("model") String model
	) {
		this.batchId = batchId;
		this.productId = productId;
		this.eventId = eventId;
		this.event Desc event Desc; 
		this.errorCode = errorCode;
		this.processDesc= processDesc; 
		this.createdDate = new Date(); 
		this.model model;
	}
	
	@Override
	public String toString() {
		return "EventLog_Entity [logId=" + logId + ",batchId=" + batchId", productId=" + productId", + eventId=" 
		+eventId+ ", event Desc=" + event Desc + ", errorCode=" + errorCode + ", processDesc=" + processDesc 
		+ ". createdDate=" + createdDate + ", ', createdBy=" + createdBy+", model=" + model + "]";
	}
}
	