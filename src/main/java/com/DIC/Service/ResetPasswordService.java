package com.DIC.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.UserDetails;

import framework.utilities.SessionUtils;

@ManagedBean(name="resetPasswordService")
@SessionScoped
public class ResetPasswordService {
	
	private static final Logger log = Logger.getLogger(ResetPasswordService.class.getName());
	
	
	private int userId;
    private String userName;
    private String password;
	private String statusMessage;
	private boolean valied;
	
	private UserDetails userDetails;
	
	
	
	 GeneralDAOImpl gdao;
	
	public ResetPasswordService()
	{
		
	}
	
	
	@PostConstruct 
    public void init()
    {
		valied=false;
		log.log(Level.INFO, "Loading UserLoginService init()");
			
		gdao=new GeneralDAOImpl();
    	
    }
	
	
	
	public void resetPassword()
    {
    	log.info("calling reset Actioon()");
    	
    	statusMessage="";
    	valied=false;
    	
    	
    	 userDetails = gdao.getUserName(userName);
    	 System.out.println(" User id --> :"+userDetails.getUserId());
    	 
    		if(userDetails.getUserId()>0)
    		{
    				
    			valied=true;
			
			} else {
				
				statusMessage="User name dose not exist";
				valied=false;
			}
  	
    }
	
	
	public void updatePassword()
	{
		
		if(userDetails!=null)
		{
		statusMessage = gdao.updatePassword(userDetails,password);
		}
		
	}
	
	
    public void reset() {
 		       PrimeFaces.current().resetInputs("form:panel1");
    }	
  



	public boolean isValied() {
		return valied;
	}


	public void setValied(boolean valied) {
		this.valied = valied;
	}


	public int getUserId() {
		return userId;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public String getStatusMessage() {
		return statusMessage;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}


	public UserDetails getUserDetails() {
		return userDetails;
	}


	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	
	
	

}
