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
import com.DIC.model.UserDetails;
import com.DIC.model.UserProfileRoleModel;

import framework.utilities.SessionUtils;

@ManagedBean(name="userProfileService")
@ViewScoped
public class UserProfileService {
	
	
	private static final Logger log = Logger.getLogger(UserProfileService.class.getName());
	
	 private List<UserDetails> userDetailsList;
	 private List<UserProfileRoleModel> userProfileRoleModel;
	 private int listedCount;
	 
	 UserDAOImpl uDao;
	 
	 
	 private String firstname;
	 private String lastname;
	 
	 
	 private List<LayoutMode> layoutdetails;
     private AllPropertyList selectedProperty;
     
     private List<LeadModel> leadModelList;
     
     
     private List<AllPropertyList> allPropertyList;
	 
	
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
		          
		    		    		userDetailsList=uDao.getUser(userId);
		    		    		userProfileRoleModel=uDao.getUserProfileRoles(userId);
		    		    		layoutdetails=uDao.getLayoutListByUserId(userId);
		    		    		
		    		    		allPropertyList=uDao.getAllPropByUserId(userId);
		    		    		listedCount=uDao.getAllPropByUserId(Integer.parseInt(session.getAttribute("userId").toString())).size();
		    		    		
		    		    		
		    		    		for(LayoutMode lm:layoutdetails )
		    					{
		    		    			log.log(Level.INFO,"---------------- lead count ------->"+lm.getLeadCount());
		    					}
		    		    		
		    		    }
		    		}
		    	}
		    	
	    	
		    	
	    }
	 
	 	 
	 public void propVal()
		{
		 log.log(Level.INFO,"---->Leads parameter : "+selectedProperty.getPropId()+"   "+selectedProperty.getPropType());		
			
			leadModelList=uDao.getLeads(selectedProperty.getPropId(),selectedProperty.getPropType());
			
    		
    		for(LeadModel lm:leadModelList )
			{
				log.log(Level.INFO,"---->Leads "+lm.getLeads_id()+"  "+lm.getLeadName()+"  "+lm.getLeadContact()+"  "+lm.getLeadEmail()+"  "+lm.getProId()+"  "+lm.getUserId());
			}
			
    		disLeadFlag=1;
    		
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

	
	
    
    public void save() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Welcome " + firstname + " " + lastname));
        
        log.log(Level.INFO,"***** Save ******"+ firstname + " " + lastname);
    }
    
    
    

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


	public List<LayoutMode> getLayoutdetails() {
		return layoutdetails;
	}



	public void setLayoutdetails(List<LayoutMode> layoutdetails) {
		this.layoutdetails = layoutdetails;
	}




	
	public List<LeadModel> getLeadModelList() {
		return leadModelList;
	}

	public void setLeadModelList(List<LeadModel> leadModelList) {
		this.leadModelList = leadModelList;
	}


	public int getDisLeadFlag() {
		return disLeadFlag;
	}


	public void setDisLeadFlag(int disLeadFlag) {
		this.disLeadFlag = disLeadFlag;
	}


	public List<AllPropertyList> getAllPropertyList() {
		return allPropertyList;
	}



	public void setAllPropertyList(List<AllPropertyList> allPropertyList) {
		this.allPropertyList = allPropertyList;
	}


	public AllPropertyList getSelectedProperty() {
		return selectedProperty;
	}


	public void setSelectedProperty(AllPropertyList selectedProperty) {
		this.selectedProperty = selectedProperty;
	}


	public int getListedCount() {
		return listedCount;
	}


	public void setListedCount(int listedCount) {
		this.listedCount = listedCount;
	} 
	
	
	 
	 

}
