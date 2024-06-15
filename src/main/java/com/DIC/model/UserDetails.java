package com.DIC.model;

import java.io.Serializable;
import java.util.Date;


public class UserDetails implements Serializable {
	
	private int userId;
	private String fName;
	private String lName;
	private String userName;
	private String userPassword;
	private String address;
	private String phone;
	private Date create_date;
	private int is_active;
	private int listLimit;
	private String packName;
	private Boolean isEnable;
	
	
	private String disName;
	

	public String getUserPassword() {
		return userPassword;
	}


	public String getPhone() {
		return phone;
	}





	public int getIs_active() {
		return is_active;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}





	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}


	public String getDisName() {
		return disName;
	}


	public void setDisName(String disName) {
		this.disName = disName;
	}


	public String getfName() {
		return fName;
	}


	public String getlName() {
		return lName;
	}


	public String getAddress() {
		return address;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getUserId() {
		return userId;
	}
	

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getListLimit() {
		return listLimit;
	}


	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}


	public String getPackName() {
		return packName;
	}


	public void setPackName(String packName) {
		this.packName = packName;
	}


	public Boolean getIsEnable() {
		return isEnable;
	}


	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}


	public Date getCreate_date() {
		return create_date;
	}


	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}


	

}
