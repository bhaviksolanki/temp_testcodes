
package com.ms.datawise.distn.entity;
import com.ms.datawise.distn.annotation.CopyProp; 
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp:
import javax.persistence.*; 
import java.util.Date;
@NamedNativeQueries ({
	@NamedNativeQuery(
	name = "callgetactivewindows",
	query="{ call RES_DW_DSTN.SP_GET_ATCIVE_WINDOWS }", 
	resultClass = WindowDistributionLog.class
	),
	@NamedNativeQuery(
	name = "call completewindow",
	query = "{ call RES_DW_DSTN.SP_COMPLETE_WINDOW (:schedule_id_in) }"
	),
	}
@Data
@Entity
@Table (name = "WINDOW_DISTRIBUTION_LOG")
public class WindowDistributionLog {
	
	@Id
	@GeneratedValue (strategy = GenerationType.TABLE) @Column(name = "RUN_ID")
	@CopyProp (targetPropName = "runId")
	private Integer runId;
	
	@Column(name = "PRODUCT_ID")
	@CopyProp (targetPropName = "productId")
	private String productId;
	
	@Column(name = "DISTRIBUTION_ID")
	@CopyProp (targetPropName = "distributionId") 
	private Integer distributionId;
	
	@Column(name = "ACTION")
	@CopyProp (targetPropName = "action")
	private String action;
	
	@Column(name = "ACTION_TIMESTAMP") 
	@CreationTimestamp
	@CopyProp (targetPropName = "actionTimeStamp") 
	private Date actionTimeStamp;
	
	@Column(name = "ACTION_STATUS")
	@CopyProp (targetPropName = "actionStatus") 
	private String actionStatus;
	
	@Column(name = "WINDOW")
	@CopyProp (targetPropName = "window")
	private String window;
	
	@Column(name = "WINDOW_ID")
	@CopyProp (targetPropName = "windowID")
	private String windowID;
	
	@Column(name = "WINDOW_STATUS")
	@CopyProp (targetPropName = "windowStatus") 
	private String windowStatus;
	
	@Column(name = "ALERT_LEVEL")
	@CopyProp (targetPropName = "alertLevel")
	private String alertLevel;
	
	@Column(name = "SCHEDULE_ID")
	@CopyProp (targetPropName = "scheduleID") 
	private String scheduleID;
}
