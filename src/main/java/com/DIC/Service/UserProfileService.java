package com.DIC.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AllPropertyList;
import com.DIC.model.LayoutMode;
import com.DIC.model.LeadModel;
import com.DIC.model.PackageModel;
import com.DIC.model.UserDetails;
import com.DIC.model.UserProfileRoleModel;

import framework.utilities.SessionUtils;

@ManagedBean(name="userProfileService")
@ViewScoped
public class UserProfileService {
	
	
	private static final Logger log = Logger.getLogger(UserProfileService.class.getName());
	
	 private UserDetails userDetails;
	 private List<UserProfileRoleModel> userProfileRoleModel;
	 private int listedCount;
	 private PackageModel packageModel;
	 
	 UserDAOImpl uDao;
	 
	 
	 private String firstname;
	 private String lastname;
	 
      
  	 private int disLeadFlag=0;
	 
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
		          
		    		    	    packageModel=uDao.getPackageDetails(userId);
		    		    	    
		    		    	
		    		    		userDetails=uDao.getUser(userId);
		    		    		userDetails.setListLimit(packageModel.getListLimit());
		    		    		userDetails.setPackName(packageModel.getPackName());
		    		    		userDetails.setIsEnable(packageModel.getIsEnable());
		     		    		userProfileRoleModel=uDao.getUserProfileRoles(userId);
			    		    	listedCount=uDao.getAllPropByUserId(Integer.parseInt(session.getAttribute("userId").toString())).size();
			    		    		
		    		    }
		    		}
		    	}
		    	
	    	
		    	
	    }
	 
	 	 
	





	public List<UserProfileRoleModel> getUserProfileRoleModel() {
		return userProfileRoleModel;
	}


	public void setUserProfileRoleModel(List<UserProfileRoleModel> userProfileRoleModel) {
		this.userProfileRoleModel = userProfileRoleModel;
	}

	
	
    /*
    public void save() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Welcome " + firstname + " " + lastname));
        
        log.log(Level.INFO,"***** Save ******"+ firstname + " " + lastname);
    }
    */
    
    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }



	public int getDisLeadFlag() {
		return disLeadFlag;
	}


	public void setDisLeadFlag(int disLeadFlag) {
		this.disLeadFlag = disLeadFlag;
	}






	public int getListedCount() {
		return listedCount;
	}


	public void setListedCount(int listedCount) {
		this.listedCount = listedCount;
	}


	public PackageModel getPackageModel() {
		return packageModel;
	}


	public void setPackageModel(PackageModel packageModel) {
		this.packageModel = packageModel;
	}


	public UserDetails getUserDetails() {
		return userDetails;
	}


	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	} 
	
	
	 
	 

}
