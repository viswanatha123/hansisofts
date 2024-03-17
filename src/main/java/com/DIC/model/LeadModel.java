package com.DIC.model;

import java.io.Serializable;
import java.util.Date;

public class LeadModel implements Serializable{
	
	
	private int leads_id;
	private String leadName;
	private String leadContact;
	private String leadEmail;
	private int proId;
	private int userId;
	private Date create_date;
	private int is_active;
	public int getLeads_id() {
		return leads_id;
	}
	public String getLeadName() {
		return leadName;
	}
	public String getLeadContact() {
		return leadContact;
	}
	public String getLeadEmail() {
		return leadEmail;
	}
	public int getProId() {
		return proId;
	}
	public int getUserId() {
		return userId;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setLeads_id(int leads_id) {
		this.leads_id = leads_id;
	}
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}
	public void setLeadContact(String leadContact) {
		this.leadContact = leadContact;
	}
	public void setLeadEmail(String leadEmail) {
		this.leadEmail = leadEmail;
	}
	public void setProId(int proId) {
		this.proId = proId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	
	

}
