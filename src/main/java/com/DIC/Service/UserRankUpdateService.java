package com.DIC.Service;

import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.UserDetails;
import com.DIC.model.UserRoleModel;

@ManagedBean(name="userRankUpdateService")
@SessionScoped
public class UserRankUpdateService {
	
	private static final Logger log = Logger.getLogger(UserRankUpdateService.class.getName());
	
	private int userId;
	private UserDetails userDetails;
	private String fName;
	private String lName;
	private int statusMessage=-1;
	private int rank;
	private String userName;

	 
	UserDAOImpl uDao;
	
	 public UserRankUpdateService()
	 {
		 uDao=new UserDAOImpl();
	 }
	 
	 public void getUserDetials() {  
	      
		 this.statusMessage=-1;
		 userDetails=uDao.getRankByUserId(userId);
		 
		 if(userDetails.getUserName() == null || userDetails.getUserName().isEmpty())
	    	{
	    		//statusMessage="User does not have rank.";
			 statusMessage=0;
	    	}
	    	
	        	fName=userDetails.getfName();
	        	lName=userDetails.getlName();
	        	userName=userDetails.getUserName();
	        	rank=userDetails.getRank();
	        	 
	 }
	 
	 public void updateRank()
	 {
		 
		int val=uDao.updateRank(userId,rank);
		if(val > 0)
		{
			statusMessage=1;
		}
		 
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

	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public int getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(int statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
	 
	 
	 

}
