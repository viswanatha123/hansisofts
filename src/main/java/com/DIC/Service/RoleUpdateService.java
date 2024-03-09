package com.DIC.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.UserRoleModel;
import com.DIC.model.VillaModel;

@ManagedBean(name="roleUpdateService")
@ViewScoped
public class RoleUpdateService {
	
	private static final Logger log = Logger.getLogger(RoleUpdateService.class.getName());
	
	
	
	private int userId;
	private List<UserRoleModel> userRoleModelList;
	private int recordSize;
	private String statusMessage;
	private String fName;
	private String lName;
	
	
	GeneralDAOImpl gDao;
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading RoleUpdateService init()");
     
    	gDao=new GeneralDAOImpl();
             
        
    }
	
	
	public void getUserRoles() {  
		this.statusMessage="";
		this.fName="";
		this.lName="";
    	System.out.println(" **** User role submited button ****** :"+userId);
  	        
      
    	userRoleModelList=gDao.getRolesByUserId(userId);
    	
    	
    	
    	
    	recordSize=userRoleModelList.size();
    	
    	
    	
	    	if(recordSize==0)
	    	{
	    		statusMessage="User id does not exist please try differnt user id.";
	    	}
	    	
	        for(UserRoleModel urm:userRoleModelList)
	        {
	        	fName=urm.getfName();
	        	lName=urm.getlName();
	        	System.out.println("**** Role by user id "+urm.getUserRoleId()+"    "+urm.getUserId()+"   "+urm.getfName()+"   "+urm.getlName()+"     "+urm.getRoleId()+"   "+urm.getRoleName()+"   "+urm.getActive()+"   "+urm.getProfileRole()+"  "+recordSize);    
	        }
	}
	
	public void save()
	{
		statusMessage="";
		
		 for(UserRoleModel urm:userRoleModelList)
	        {
	        	System.out.println("**** Save "+urm.getUserRoleId()+"    "+urm.getUserId()+"   "+urm.getfName()+"   "+urm.getlName()+"   "+urm.getRoleId()+"   "+urm.getRoleName()+"   "+urm.getActive());    
	        }
		
		 
		    int updatedRecord=gDao.saveRole(userRoleModelList);
		    if(updatedRecord > 0)
		    {
		    	statusMessage="Successful saved user roles.";
		    }else
		    {
		    	statusMessage="Error Occurred, Please contact support team.";
		    }
		 
	}
	
	public void clear()
	{
		userRoleModelList=null;
		statusMessage="";
		recordSize=0;
		fName="";
		lName="";
	}

	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public List<UserRoleModel> getUserRoleModelList() {
		return userRoleModelList;
	}


	public void setUserRoleModelList(List<UserRoleModel> userRoleModelList) {
		this.userRoleModelList = userRoleModelList;
	}


	public int getRecordSize() {
		return recordSize;
	}


	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}


	public String getStatusMessage() {
		return statusMessage;
	}


	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
	
	
	
	
	
	
	
	
}
