package com.DIC.model;

import java.io.Serializable;

public class UserRoleModel implements Serializable{
	
	private int userRoleId;
	private int userId;
	private String fName;
	private String lName;
	private int roleId;
	private String roleName;
	private boolean isActive;
	private String profileRole;
	private int listLimit;
	
	
	
	
	
	public String getfName() {
		return fName;
	}
	public String getlName() {
		return lName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public int getUserId() {
		return userId;
	}

	public String getRoleName() {
		return roleName;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean getActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getProfileRole() {
		return profileRole;
	}
	public void setProfileRole(String profileRole) {
		this.profileRole = profileRole;
	}
	public int getListLimit() {
		return listLimit;
	}
	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}
	
	
	
	
	
	

}
