package com.DIC.Service;

import java.io.Serializable;
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
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.PackageModel;
import com.DIC.model.UserDetails;

import framework.utilities.SessionUtils;



@ManagedBean(name="userLoginService")
@SessionScoped
public class UserLoginService implements Serializable{
	
	
	static final Logger log = Logger.getLogger(UserLoginService.class.getName());
	
		private int userId;
	    private String userName;
	    private String password;
	    private String fName;
	    private String lName;
	    private String address;
	    private String message;
	    private boolean valid;
	    private String disName;
	    private String fullName;
	    
	    
	    
	    
	    
	    GeneralDAOImpl gDao;
	    UserDAOImpl uDao;
		
		@PostConstruct 
	    public void init()
	    {
			message="";
			log.log(Level.INFO, "Loading UserLoginService init()");
				
	    	gDao=new GeneralDAOImpl();
	    	uDao=new UserDAOImpl();
	    	
	    	this.message="Welcome to HansiSoft Solutions";
	    	valid=true;
	    	
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

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getUserId() {
			
			
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getDisName() {
			return disName;
		}

		public void setDisName(String disName) {
			this.disName = disName;
		}


		
		
		 public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public void reset() {
		        PrimeFaces.current().resetInputs("form:panel1");
		    }
	    
	    public String userLoingAction()
	    {
	    	log.info("calling navegateToAdminLoginPage()");
	    	String pageName="adminLoginError";
	    	
	    	
	    	
	    	valid = gDao.loginValidate(userName, password);
	    	
	    	
	    	if (valid) {
				HttpSession session = SessionUtils.getSession();
				
				UserDetails userDetails=gDao.getUserDeta(userName, password);
				System.out.println("User Details"+userDetails.getUserId()+"    "+userDetails.getDisName()+"    "+userDetails.getUserName());
				
				PackageModel packageModel=uDao.getPackageDetails(userDetails.getUserId());
				
				SessionUtils.setUserDetails(userDetails,packageModel);
				
				disName=SessionUtils.getUserDisName();
				fullName=SessionUtils.getUserFullName();
				
				
				
				userId=SessionUtils.getUserId();
				this.message="Welcome to HansiSoft Solutions";
				System.out.println("******** Successful logged in  **********");
				pageName= "index";
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				valid=false;
				this.message="Incorrect Username and Passowrd.";
				System.out.println("******** Invalied login details  **********");
				pageName= "userLogin";
			}
	    	
	    	
	    	
	            log.info("User Name :"+userName+", Password :"+password+", Page Name :"+pageName);
	  
	    return pageName;
	    }
	    
	    
	    
	    
	    
	  //logout event, invalidate session
		public String logout() {
			HttpSession session = SessionUtils.getSession();
			session.invalidate();
			System.out.println("******** Successful logout **********");
			return "userLogin";
		}
		
		
		

}
