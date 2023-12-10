package com.ms.datawise.distn.constants;

public class Enums {
	public enum EXTENSION {
		CSV(".csv"),
		ZIP(".zip"), 
		FTL(".ftl");
		
		String value;
		EXTENSION (String value) { 
			this.value = value;
		}
		
		public String getValue() { 
			return this.value;
		}
	}
}