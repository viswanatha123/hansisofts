package com.DIC.model;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.StreamedContent;

public class BudgetModel implements Serializable {
	
	private int pro_id;
	private String name;
	private int cost;
	private String contactNo;
	private String pro_type;
	private StreamedContent streamedContent;
	private Date create_date;
	private String prim_location;
	private String seco_location;
	private String location;
	private int userId;
	
	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public String getContactNo() {
		return contactNo;
	}
	public String getPro_type() {
		return pro_type;
	}
	public StreamedContent getStreamedContent() {
		return streamedContent;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getPrim_location() {
		return prim_location;
	}
	public String getSeco_location() {
		return seco_location;
	}
	public void setPrim_location(String prim_location) {
		this.prim_location = prim_location;
	}
	public void setSeco_location(String seco_location) {
		this.seco_location = seco_location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	

}
