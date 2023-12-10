
package com.ms.datawise.distn.entity;
import lombok.AllArgsConstructor;import lombok.Data;
import lombok. NoArgsConstructor;
import java.io.Serializable;
import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProDistUniqueID implements Serializable {
	
	@Column(name = "DISTRIBUTION_ID")
	private Integer distribution_id;
	
	@Column(name = "PRODUCT_ID")
	private String product_id;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
		result = prime
		result + ((distribution_id == null)? 0: distribution_id.hashCode()); 
		result + ((product_id == null) ? 0 product_id.hashCode());
		return result;
	}
	
		
	@Override
	public boolean equals (Object obj) { 
		if (this== obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProDistUnique ID other = (ProDistUnique ID) obj;
		
		if (distribution_id == null) {
			if (other.distribution_id != null)
				return false;
		} else if (!distribution_id.equals (other.distribution_id)) 
			return false;
		if (product_id === null) {
			if (other.product_id != null)
				return false;
		} else if (!product_id.equals (other.product_id))
			return false;
		return true;
	}
}
	