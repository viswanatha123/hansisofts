package com.DIC.Service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.EnquiryDataEntryModel;

@ManagedBean(name="requestCallBackService")
@SessionScoped
public class RequestCallBackService implements Serializable{
	
 private static final Logger log = Logger.getLogger(RequestCallBackService.class.getName());
	 
	 private String name;
	 private String email;
	 private String phone;
	 
	 
	  ConnectionDAOImpl dao;
	 
	 
	 public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getPhone() {
			return phone;
		}


		public void setPhone(String phone) {
			this.phone = phone;
		}
		
	    public void clear()
	    {
	    	 this.name="";
	         this.email="";
	         this.phone="";
	    }
	 
	 
	 public String submitEnquiry()
	    {
	    	log.info("calling submitEnquiry()");
	    	String pageName="adminLoginError";
	    	
	            if(name!=null && email!=null && phone!=null)
	            {
	            	log.info("Name :"+name+", email :"+email+", phone :"+phone);
	            	
	            	
	            	EnquiryDataEntryModel enquiryDataEntryModel=new EnquiryDataEntryModel();
	            	enquiryDataEntryModel.setName(name);
	            	enquiryDataEntryModel.setEmail(email);
	            	enquiryDataEntryModel.setPhone(phone);
	                
	                
	            	dao=new ConnectionDAOImpl();
	            	int rec=dao.updateEnquiryDataEntry(enquiryDataEntryModel);
			                if(rec > 0)
			                {
			                	pageName="requestCallBackSuccess";
			                }
			                else
			                {
			                	pageName="adminLoginError";
			                }
	            	
	            
	            }
	            this.name="";
	            this.email="";
	            this.phone="";
	           
	            
	          
	    return pageName;
	    }

	

}
