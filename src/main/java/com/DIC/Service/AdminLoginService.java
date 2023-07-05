package com.DIC.Service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import org.apache.log4j.*;

//import org.apache.log4j.Logger;


@ManagedBean(name="adminLoginService")
@SessionScoped
public class AdminLoginService implements Serializable{
	
	  static final Logger log = Logger.getLogger(AdminLoginService.class.getName());
	
	 private String userName;
	    private String password;
	    
	    public AdminLoginService() {
	    	

	    	
	        log.info("loading AdminLoginService");
	    }
	    
	     public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getPassword() {
	        return password;
	    }
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    
	    public void clear()
	    {
	        
	    }
	    
	    public String navegateToAdminLoginPage()
	    {
	    	log.info("calling navegateToAdminLoginPage()");
	    	String pageName="adminLoginError";
	            if(userName.equals("admin") && password.equals("admin123"))
	            {
	                //setIncludePageResetView("/ui/adminMain.xhtml", "Successful login Admin");
	            	
	            	log.info("*************** Successful login ***********");
	            	//log.debug("*************** Successful login ***********");
	            	
	            
	            	pageName="adminMain";
	            }
	            else
	            {
	                //setIncludePageResetView("/ui/adminLoginError.xhtml", "Login Failed..");
	            	pageName="adminLoginError";
	            }
	            
	            log.info("User Name :"+userName+", Password :"+password+", Page Name :"+pageName);
	            //log.debug("User Name :"+userName+", Password :"+password+", Page Name :"+pageName);
	    return pageName;
	    }
	    

}
