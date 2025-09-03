package com.DIC.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;
import com.DIC.model.UserDetails;

import SMTPService.SMTPService;
import framework.utilities.Constants;
import framework.utilities.UtilConstants;

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
	private String confirmPassword;
	private String address;
	private String phone;
	private String email;
	
	private String errorMessage;
	private String disName;
	private String statusMessage;
	
	 GeneralDAOImpl gdao;
	 
	 
	 private Integer spinnerValue=1;
	 
	 public UserRegistService()
	 {
		 
	 }
	 
	 
	  public Integer getSpinnerValue() {
	        return spinnerValue;
	    }

	    public void setSpinnerValue(Integer spinnerValue) {
	        this.spinnerValue = spinnerValue;
	    }

	    public void performAction() {
	        // Simulate some background processing
	        try {
	            Thread.sleep(2000); // Simulates a delay
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	
	
	@PostConstruct 
	   public void init()
	      {
	          log.info("Loading UserRegistService init()");
	          
	          gdao=new GeneralDAOImpl();
	       
	           
	      }
		      
	public void save() {
		
		
		
		boolean valid = gdao.loginValidate(userName);
			if(valid)
			{
				statusMessage="User name already exists, Please try with different user name.";
			}else
			{
				if(userPassword.equals(confirmPassword)) {


					UserDetails userDetails = new UserDetails();

					userDetails.setfName(fName);
					userDetails.setlName(lName);
					userDetails.setUserName(userName.trim());
					userDetails.setUserPassword(userPassword.trim());
					userDetails.setAddress(address);
					userDetails.setPhone(phone);
					userDetails.setEmail(email);

					int userId = gdao.saveUserRegist(userDetails, UtilConstants.BASIC_PACKAGE_LIST_LIMIT);

					if (userId > 0) {


						String body = "Hi " + fName + " " + lName + ",\n\n Congratulation...\n Your account has been created successfully.\n\n"
								+ " Customer ID : " + userId + "\n User Name : " + userName + "\n First Name : " + fName + "\n Last Name : " + lName + "\n Contact Number : " + phone + " \n Email : " + email + " \n Address : " + address + " \n Date : " + LocalDate.now().toString() + ". \n\n\n Thank you\n HansiSoft Solutions..";

						SMTPService.sendRegiEmail(email, Constants.SMTPServer.SUBJECT, body);

						statusMessage = "Successful Registerd.";

						this.fName = "";
						this.lName = "";
						this.userName = "";
						this.userPassword = "";
						this.address = "";
						this.phone = "";
						this.email = "";

					} else {
						statusMessage = "Error Occured, Please contact support..";
					}

				}
				else{
					statusMessage = "Both password fields must match . Please try again";
				}
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
	
	


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public String getConfirmPassword() {
		return confirmPassword;
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

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
