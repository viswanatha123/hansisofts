package com.DIC.model;

import java.io.Serializable;

public class AllPropertyList implements Serializable{
	
	private int propId;
	private String propName;
	private String propType;
	private int count;
	
	
	
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
	
	
	
	

}
