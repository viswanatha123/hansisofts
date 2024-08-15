package com.DIC.Service;

import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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


import SMTPService.SMTPService;
import framework.EventHandler;
import framework.utilities.DurationValidation;
import framework.utilities.SessionUtils;



@ManagedBean(name="userLoginService")
@SessionScoped
public class UserLoginService implements Serializable{
	
	
	private static final Logger log = LogManager.getLogger(UserLoginService.class);
	
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
	    private long remainDays;
	    
	    
	    
	    
	    
	    GeneralDAOImpl gDao;
	    UserDAOImpl uDao;
		
		@PostConstruct 
	    public void init()
	    {
			message="";
			//log.log(Level.INFO, "Loading UserLoginService init()");
				
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
	    
	    public String userLoingAction() throws Exception
	    {
	    	log.info("calling navegateToAdminLoginPage()");
	    	String pageName="adminLoginError";
	    	
	    	
	    	
	    	valid = gDao.loginValidate(userName, password);
	    	
	    	
	    	if (valid) {
	    		
	    		//SMTPService.sendRegiEmail();
	    		
	    		    log.info("This is an info message.");
		            log.debug("This is a debug message.");
		            log.error("This is an error message======================");
	    		
				HttpSession session = SessionUtils.getSession();
				
				UserDetails userDetails=gDao.getUserDeta(userName, password);
				System.out.println("User Details"+userDetails.getUserId()+"    "+userDetails.getUserName()+"    "+userDetails.getCreate_date());
				
				PackageModel packageModel=uDao.getPackageDetails(userDetails.getUserId());
				
				boolean duraValida=DurationValidation.durationValidation(userDetails,packageModel);
				
				remainDays=DurationValidation.getRemainDays(userDetails,packageModel);
				System.out.println(" Duration Validation : "+duraValida);
				
				if(duraValida==false)
				{
					
					HttpSession sessionlogout = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
					sessionlogout.invalidate();
			        EventHandler.alertUserInfo("Session terminated by user.", "Bye!");
			        
			        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorry..., Your account has been expired", "Please renewal your account ,Contact our support team 9632152255");
			        PrimeFaces.current().dialog().showMessageDynamic(message);         
				}
				
				
				SessionUtils.setUserDetails(userDetails,packageModel,remainDays);
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

		public long getRemainDays() {
			return remainDays;
		}

		public void setRemainDays(long remainDays) {
			this.remainDays = remainDays;
		}
		
		
		

}
