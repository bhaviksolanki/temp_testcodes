
package com.ms.datawise.distn.entity;
import java.io.Serializable; 
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok. NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Distribution_Detail")
public class DistributionDetail implements Serializable {
	@Id
	@Column(name = "DISTRIBUTION_ID")
	@GeneratedValue (strategy = GenerationType.TABLE)
	private Integer distributionId;
	
	@Column(name = "CADENCE") 
	private String cadence;
	
	@Column(name = "CLIENT_ID") 
	private String clientId;
	
	@Column(name = "CREATE_USER_ID")
	private String createdBy;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	
	private Date createdDate;
	@Column(name = "DIST_HANDLE") 
	private String dist Handle;
	
	@Column(name = "DIST_TYPE") 
	private String distType;
	
	@Column(name = "UPDATE_USER_ID")
	private String updatedBy;
	
	@Column(name = "UPDATE_DATE", updatable = true)
	@UpdateTimestamp
	private Date updatedDateTime;
	
	
	@Column(name = "ROW_START") 
	private Date rowStart;
	
	@Column(name = "ROW_END") 
	private Date rowEnd;
	
	@Column(name = "RW_ID") 
	private Date rwId;
	
	@Column(name = "ENDPOINT DETAIL ID") 
	private Integer endpoint DetailId;
}