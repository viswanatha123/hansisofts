package com.DIC.model;

import java.io.Serializable;

public class UserProfileRoleModel implements Serializable{
	
	
	private String roleName;
	private String profUrl;
	
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getProfUrl() {
		return profUrl;
	}

	public void setProfUrl(String profUrl) {
		this.profUrl = profUrl;
	}
	
	
	

}
