package com.DIC.Service;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.UserDetails;
import com.DIC.model.UserRoleModel;

@ManagedBean(name="agentAccountSettingService")
@ViewScoped
public class AgentAccountSettingService {
	
	private static final Logger log = Logger.getLogger(AgentAccountSettingService.class.getName());
	
	
		private int userId;
		
		private String fName;
		private String lName;
		private String userName;
		private String userPassword;
		private String address;
		private String phone;
		private Date create_date;
		private int is_active;
		
		
		private UserDetails userDetails;
		private String updateResult;
		
		
	
		UserDAOImpl uDao;
		
		@PostConstruct 
	    public void init()
	    {
			
			uDao=new UserDAOImpl();
	    }
		
		
		public void getAgentAccount() 
		{  
			this.userDetails=null;
			this.updateResult="";
			userDetails=uDao.getUser(userId);
			
			if(userDetails.getUserName() != null)
			{
				
				fName=userDetails.getfName();
				lName=userDetails.getlName();
				userName=userDetails.getUserName();
				userPassword=userDetails.getUserPassword();
				address=userDetails.getAddress();
				phone=userDetails.getPhone();
				create_date=userDetails.getCreate_date();
				is_active=userDetails.getIs_active();
				
				
			}
			else
			{
				updateResult="User id dose not exist.";
				System.out.println("*****************Package details null ******************");
				this.fName=null;
				this.lName=null;
				this.userName=null;
				this.userPassword=null;
				this.address=null;
				this.phone=null;
				//this.create_date=null;
				
				
				
			}
		}
		
		
		public void updateUser() 
		{
			if(fName!=null && lName!=null && userName!=null && userPassword!=null && create_date!=null)
			{
			
					this.updateResult="";
				    updateResult=uDao.updateUser(fName, lName, userName, userPassword, address, phone, create_date, is_active,userId);
					
			}else
			{
				this.updateResult="Please provide details";
			}
		}
		
		
		public void accountRenewel() 
		{
			if(fName!=null && lName!=null && userName!=null && userPassword!=null && create_date!=null)
			{
			
					this.updateResult="";
				    int val=uDao.accountRenewel(userId);
				    if(val > 0)
				    {	
				    	this.getAgentAccount();
				    	updateResult="Successful account has been renewed.";
				    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful account has been renewed.", "Thank you, Please continue your accouint.");
			        PrimeFaces.current().dialog().showMessageDynamic(message); 
							        
				    }
				    else
				    {
				    	this.updateResult="Error occured, Please contact support team.";
				    }
					
			}else
			{
				this.updateResult="Error occured, Please contact support team.";
			}
		}
		
		 public void reset() {
		        this.userId=0;
		        this.userName=null;
		        this.updateResult="";
		    }
		
		

		public String getUpdateResult() {
			return updateResult;
		}

		public void setUpdateResult(String updateResult) {
			this.updateResult = updateResult;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
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


	


		public int getIs_active() {
			return is_active;
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



		public void setIs_active(int is_active) {
			this.is_active = is_active;
		}




		public UserDetails getUserDetails() {
			return userDetails;
		}


		public void setUserDetails(UserDetails userDetails) {
			this.userDetails = userDetails;
		}


		public Date getCreate_date() {
			return create_date;
		}


		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}




		
	

}
