package com.DIC.model;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.StreamedContent;

public class AgriculturalModel implements Serializable{
	
	private long agriId;
	private String ownerName;
	private String contactNo;
	private String surveyNo;
	private String location;
	private String wonership;
	private String transaction;
	private int perCent;
	private int numberCents;
	private int cost;
	private String waterSource;
	private String crop;
	private String primLocation;
	private String secoLocation;
	private String comment;
	private String agentName;
	private String totalPrice;
	private StreamedContent streamedContent;
    private Date createdOnDate;
	
	
	
	public Date getCreatedOnDate() {
		return createdOnDate;
	}

	public void setCreatedOnDate(Date createdOnDate) {
		this.createdOnDate = createdOnDate;
	}
	

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	
	

	
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public long getAgriId() {
		return agriId;
	}
	public void setAgriId(long agriId) {
		this.agriId = agriId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getSurveyNo() {
		return surveyNo;
	}
	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getWonership() {
		return wonership;
	}
	public void setWonership(String wonership) {
		this.wonership = wonership;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public long getPerCent() {
		return perCent;
	}
	public void setPerCent(int perCent) {
		this.perCent = perCent;
	}
	public int getNumberCents() {
		return numberCents;
	}
	public void setNumberCents(int numberCents) {
		this.numberCents = numberCents;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getWaterSource() {
		return waterSource;
	}
	public void setWaterSource(String waterSource) {
		this.waterSource = waterSource;
	}
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
	}
	public String getPrimLocation() {
		return primLocation;
	}
	public void setPrimLocation(String primLocation) {
		this.primLocation = primLocation;
	}
	public String getSecoLocation() {
		return secoLocation;
	}
	public void setSecoLocation(String secoLocation) {
		this.secoLocation = secoLocation;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	

}
