package com.DIC.model;

import java.io.Serializable;
import java.util.Date;

public class AllPropertyList implements Serializable{
	
	private int propId;
	private String propName;
	private String propType;
	private int count;
	private Date createdOnDate;
	
	
	
	public String getPropName() {
		return propName;
	}
	public String getPropType() {
		return propType;
	}
	public int getCount() {
		return count;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public void setPropType(String propType) {
		this.propType = propType;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public Date getCreatedOnDate() {
		return createdOnDate;
	}
	public void setCreatedOnDate(Date createdOnDate) {
		this.createdOnDate = createdOnDate;
	}
	
	
	
	

}
