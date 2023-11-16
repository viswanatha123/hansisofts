package com.DIC.model;

import java.io.Serializable;
import java.util.Date;

public class HomeLoanDataEntryModel implements Serializable{

	
	 private String agentName;
	 private int age;
	 private String gender;
	 private String contactNo;
	 private String email;
	 private int loanAmt;
	 private int monthlyInc;
	 private String empType;
	 private Date create_date;
		private int is_active;
	 
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(int loanAmt) {
		this.loanAmt = loanAmt;
	}
	public int getMonthlyInc() {
		return monthlyInc;
	}
	public void setMonthlyInc(int monthlyInc) {
		this.monthlyInc = monthlyInc;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
} 
	