package com.DIC.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.UserDetails;
import com.DIC.model.UserProfileRoleModel;

import framework.utilities.SessionUtils;

@ManagedBean(name="userProfileService")
@ViewScoped
public class UserProfileService {
	
	
	private static final Logger log = Logger.getLogger(UserProfileService.class.getName());
	
	 private List<UserDetails> userDetailsList;
	 private List<UserProfileRoleModel> userProfileRoleModel;
	 
	 UserDAOImpl uDao;
	 
	 
	 @PostConstruct 
	    public void init()
	    {
	    	log.log(Level.INFO, "Loading UserProfileService init()");
	    	uDao=new UserDAOImpl();
	    	
	    	
	    	   HttpSession session = SessionUtils.getSession();
	      	
		    	if (session != null)
		    	{
		    		if(session.getAttribute("userId")!=null)
		    		{
		    		    int userId= Integer.parseInt(session.getAttribute("userId").toString());
		    		    if(userId > 0)
		    		    {    	
		          
		    		    		userDetailsList=uDao.getUser(userId);
		    		    		userProfileRoleModel=uDao.getUserProfileRoles(userId);
		    		    		
		    		    }
		    		}
		    	}
	    }


	public List<UserDetails> getUserDetailsList() {
		return userDetailsList;
	}


	public void setUserDetailsList(List<UserDetails> userDetailsList) {
		this.userDetailsList = userDetailsList;
	}


	public List<UserProfileRoleModel> getUserProfileRoleModel() {
		return userProfileRoleModel;
	}


	public void setUserProfileRoleModel(List<UserProfileRoleModel> userProfileRoleModel) {
		this.userProfileRoleModel = userProfileRoleModel;
	}


	
	 
	 
	 

}
