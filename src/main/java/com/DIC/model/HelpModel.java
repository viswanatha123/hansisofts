package com.DIC.model;

import java.io.Serializable;

public class HelpModel implements Serializable{
	
	
	private String query;
	private long phone;
	
	
	public String getQuery() {
		return query;
	}
	public long getPhone() {
		return phone;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	
	

}
