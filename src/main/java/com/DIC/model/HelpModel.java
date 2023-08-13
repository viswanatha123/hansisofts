package com.DIC.model;

import java.io.Serializable;

public class HelpModel implements Serializable{
	
	
	private String query;
	private String phone;
	
	
	public String getQuery() {
		return query;
	}
	public String getPhone() {
		return phone;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	

}
