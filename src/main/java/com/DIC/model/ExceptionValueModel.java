package com.DIC.model;

import java.io.Serializable;

public class ExceptionValueModel implements Serializable{
	
	private long datasetId;
	private String dimensionName;
	private int isHierarchyCreated;
	private int isHierarchyRebuilt;
	private String exception;
	private int statusCode;
	private String lastupdated;
	private long statusId;

	
	
	public long getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(long datasetId) {
		this.datasetId = datasetId;
	}
	public String getDimensionName() {
		return dimensionName;
	}
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
	public int getIsHierarchyCreated() {
		return isHierarchyCreated;
	}
	public void setIsHierarchyCreated(int isHierarchyCreated) {
		this.isHierarchyCreated = isHierarchyCreated;
	}
	public int getIsHierarchyRebuilt() {
		return isHierarchyRebuilt;
	}
	public void setIsHierarchyRebuilt(int isHierarchyRebuilt) {
		this.isHierarchyRebuilt = isHierarchyRebuilt;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	
}
