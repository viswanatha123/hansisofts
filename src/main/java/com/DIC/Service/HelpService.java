package com.DIC.Service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.HelpModel;



@ManagedBean(name="helpService")
@SessionScoped
public class HelpService implements Serializable{
	
	private static final Logger log = Logger.getLogger(HelpService.class.getName());
	
	private String query;
	private long phone;
	
	
	 ConnectionDAOImpl dao;
	

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
	 public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public void clear()
     {
		 this.query="";
		 this.phone=0;
     }
	
	
		 public String submitHelp() { 
			 
			 
			 log.info("Clicked Help Submit  :"+query+"   "+phone);
			 
			 
			 log.info("calling submitEnquiry()");
		    	String pageName="adminLoginError";
		    	
		            if(query!=null &&  phone!=0)
		            {
		            	log.info("Query :"+query+",  phone :"+phone);
		            	
		            	
		            	HelpModel helpModel=new HelpModel();
		            	helpModel.setQuery(query);
		            	helpModel.setPhone(phone);
		                
		                
		            	dao=new ConnectionDAOImpl();
		            	int rec=dao.updateHelpDataEntry(helpModel);
				                if(rec > 0)
				                {
				                	pageName="enquirySuccess";
				                }
				                else
				                {
				                	pageName="adminLoginError";
				                }
		            	
		            
		            }
		            this.query="";
		            this.phone=0;
		           
		            
		          
		    return pageName;
			 
			 
	
		 }
	 
	 
}
