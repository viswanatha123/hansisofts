package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.file.UploadedFile;

public class AgriculturalDataEntryModel implements Serializable {
	

	private String ownerName;
	private String contactNo;
	private String surveyNo;
	private String location;
	private String wonership;
	private String transaction;
	private int perCent;
	private int numberCents;
	private long cost;
	private String waterSource;
	private String crop;
	private String primLocation;
	private String secoLocation;
	private String comment;
	private String agentName;
	private InputStream inputStream;
    private UploadedFile file;
    private String power;
    private String cornerBit;

	private List<InputStream> inputStreams = new ArrayList<>();
	private List<UploadedFile> files = new ArrayList<>();
    
   public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	
  	
  	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
	public int getPerCent() {
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
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
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

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getCornerBit() {
		return cornerBit;
	}

	public void setCornerBit(String cornerBit) {
		this.cornerBit = cornerBit;
	}


	public List<InputStream> getInputStreams() {
		return inputStreams;
	}

	public void setInputStreams(List<InputStream> inputStreams) {
		this.inputStreams = inputStreams;
	}

	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}


}
