package com.DIC.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import com.DIC.model.UserDetails;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.GeneralDAOImpl;



@ManagedBean(name="userRegistService")
@SessionScoped

public class UserRegistService implements Serializable {
	
	
	private static final Logger log = Logger.getLogger(UserRegistService.class.getName());
	
		
	private String fName;
	private String lName;
	private String userName;
	private String userPassword;
	private String address;
	private String phone;
	
	
	private String disName;
	
	private String statusMessage;
	
	 GeneralDAOImpl gdao;
	 
	 
	 public UserRegistService()
	 {
		 
	 }
	
	
	@PostConstruct 
	   public void init()
	      {
	          log.info("Loading UserRegistService init()");
	          
	          gdao=new GeneralDAOImpl();
	       
	           
	      }
		      
	public void save() {
		
		this.statusMessage="";
		
		boolean valid = gdao.loginValidate(userName);
			if(valid)
			{
				statusMessage="User name already exists";
			}else
			{
		
		
					UserDetails userDetails=new UserDetails();
					
					userDetails.setfName(fName);
					userDetails.setlName(lName);
					userDetails.setUserName(userName);
					userDetails.setUserPassword(userPassword);
					userDetails.setAddress(address);
					userDetails.setPhone(phone);
					
					statusMessage=gdao.saveUserRegist(userDetails);
					
					this.fName="";
					this.lName="";
					this.userName="";
					this.userPassword="";
					this.address="";
					this.phone="";
			}
	}
	
	/*
	public void clear()
	{
		
		this.fName="";
		this.lName="";
		this.userName="";
		this.userPassword="";
		this.address="";
		this.phone="";
		
	}
	*/
	
	
	 public void reset() {
	        PrimeFaces.current().resetInputs("form:panel");
	    }
	
	


	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}



	public String getDisName() {
		return disName;
	}



	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public void setDisName(String disName) {
		this.disName = disName;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	
	
	

}
