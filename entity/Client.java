package com.ms.datawise.distn.entity;
import java.util.Date;
import javax.persistence.*;|
import lombok.*;
import org.hibernate.annotations. CreationTimestamp;
import org.hibernate.annotations. UpdateTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "CLIENT")
public class Client {
	
	@Id
	@Column(name = "CLIENT_ID", columnDefinition = "char", length = 100) 
	private String clientId;
	
	@Column(name = "CLIENT_NAME", columnDefinition = "char", length = 100) 
	private String clientName;
	
	@Column(name = "CLIENT_TYPE", columnDefinition = "char", length = 100) 
	private String clientType:
	
	@Column(name = "INTERNAL", columnDefinition = "char", length = 100) 
	private String internal;
	
	@Column(name = "VENDOR", columnDefinition = "char", length = 30) 
	private String vendor;
	
	@Column(name = "CREATE_DATE", updatable = false) @
	CreationTimestamp
	private Date createDate;
	
	@Column(name = "CREATE_USER_ID", updatable = false, columnDefinition = "char", length = 30)
	private String createdBy;

	@Column(name = "UPDATE_DATE", updatable = true) @UpdateTimestamp
	private Date updateDate;
	
	@Column(name = "UPDATE_USER_ID", updatable = true, columnDefinition = "char", length = 30)
	private String updatedBy;
	
	@Column(name = "ROW_START") 
	private Date rowStart;
	
	@Column(name = "ROW_END") 
	private Date rowEnd;
	
	@Column(name = "RW_ID")
	private Date rwId;
	
	@Column (name = "CONTACT_ID", columnDefinition = "char", length = 30) 
	private Date contactID;
}
	