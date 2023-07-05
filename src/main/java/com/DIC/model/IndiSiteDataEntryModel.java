package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.file.UploadedFile;

public class IndiSiteDataEntryModel implements Serializable {
	
	
	private String ownerName;
	private String location;
	private String contactNo;
	private String siteNo;
	private int persqft;
	private int length;
	private int width;
	private int cost;
	private String wonership;
	private String transaction;
	private String primLocation;
	private String secoLocation;
	private String comment;
	private String facing;
	private String agentName;
	private InputStream inputStream;
	private UploadedFile file;
	    
	    
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getSiteNo() {
		return siteNo;
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}
	public int getPersqft() {
		return persqft;
	}
	public void setPersqft(int persqft) {
		this.persqft = persqft;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
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
	
	public String getFacing() {
		return facing;
	}
	public void setFacing(String facing) {
		this.facing = facing;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	

}
