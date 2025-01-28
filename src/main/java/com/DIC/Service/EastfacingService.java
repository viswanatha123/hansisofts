package com.DIC.Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.StreamedContent;


import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.SMSService;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.PromoImageModel;
import com.DIC.model.UserDetails;
import com.DIC.model.VillaModel;


import SMTPService.SMTPService;
//import SMTPService.SMTPService;
//import SMTPService.SMTPService;
import framework.database.ConnectionPool;

@ManagedBean(name="eastfacingService")
@RequestScoped
//@ViewScoped
public class EastfacingService implements Serializable {
	
	private static final Logger log = LogManager.getLogger(EastfacingService.class);
	
	private String locationMessage;
	private String errorMessage;
	
	
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;
	
	private int promoCurrentPage = 1;
	private int promoPageSize = 3;
	private int promoTotalRecords;
	
	private VillaModel selectedProperty;
	

	private String custName;
	private String contactNumber;
	private String email;
	
	private List<PromoImageModel> promoImageModel;
	private List<VillaModel> villaModel;
	
	  
		public GeneralDAOImpl getgDao() {
		return gDao;
	}


	public void setgDao(GeneralDAOImpl gDao) {
		this.gDao = gDao;
	}

	    GeneralDAOImpl gDao;
		
	    UserDAOImpl udo;
	    SMSService sms;
	    UserRoleService ur;
	    
			public EastfacingService()
			{
							
				log.info("Loading EastfacingService init()");
				  gDao=new GeneralDAOImpl();
		          udo=new UserDAOImpl();
		          sms=new SMSService();
		          ur=new UserRoleService();
		    	
		       loadEntities();
				countTotalRecords();
				
				System.out.println("============================================ EastfacingService ======Constructor=====================>");
			}
			
			

			
				
			
			public void loadEntities() {
		 		
	        	 //promoImageModel=gDao.getPromoImageVilla();
	        	 promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
	        	 villaModel=gDao.getEastfacing(pageSize,currentPage);
	        	
	        	
	        	for(VillaModel x:villaModel)
               {
                   System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getI_am());
               }
               if(villaModel.size() == 0)
       		   {
       			errorMessage="There are no records on ";
       		   }
               else {
               	errorMessage="";
               }
          
		        
		    }
	 	
		 	public void countTotalRecords() {
		 		
		 		
		 		totalRecords=gDao.getEastfacingCountTotalRecords();
		 		promoTotalRecords=gDao.getPromoCountTotalRecords();
		        
		    }
		 	

		 	public void nextPage() {
		        if ((currentPage * pageSize) < totalRecords) {
		            currentPage++;
		            loadEntities();
		        }
		        if ((promoCurrentPage * promoPageSize) < promoTotalRecords) {
		        	promoCurrentPage++;
		            loadEntities();
		            	
		        }
		    }
		 	
		 	
		 
		    public void previousPage() {
		        if (currentPage > 1) {
		            currentPage--;
		            loadEntities();
		        }
		        
		        if (promoCurrentPage > 1) {
		        	promoCurrentPage--;
		            loadEntities();
		        }
		    }
		 	
		
		    public int getTotalPages() {
		        return (int) Math.ceil((double) totalRecords / pageSize);
		    }
		    
		    public void storeSelectedPropertyInSession() {
		    
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		        session.setAttribute("selectedEastfacing", selectedProperty);
		    }
			
			   
		    
		    public void submit() {
		    	
		    	
		    	FacesContext facesContext = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		      
		        if (session != null) {
		         	selectedProperty= (VillaModel) session.getAttribute("selectedEastfacing");
		         	System.out.println("Selected property  : "+selectedProperty.getVillaId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
		          }
		    	
	        	log.info("Selected property  : "+selectedProperty.getVillaId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
	        	
	        	
	        	if(selectedProperty.getVillaId()!=0)
	        	{
	        		if(custName!=null && contactNumber!=null && contactNumber!=null)
	        		{
	        			if(selectedProperty.getUserId()!=0)
	        			{
	        				
	        				if(ur.getUserRole().contains("SMS"))
							{
								log.info("******** SMS Enabled *****************");
		        				UserDetails userDetails=udo.getUser(selectedProperty.getUserId());
		        				
		        				//Twilio service
		        				//sms.sendSMSLead(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber); 
		        				
		        				// test2sms service
		        				sms.sendSMSLeadText2sms(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);
		        				
		        				
							}
							else
							{
								log.info("******** SMS Didabled *****************");
							}
	        				
	        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getVillaId(),selectedProperty.getUserId(),"villa");
	        				SMTPService.sendVillaLeadEmail(custName,contactNumber,email,selectedProperty);
	        				log.info("***** Successful submitted lead ******");
	        			}
	        			if(selectedProperty.getUserId()==0)
	        			{
	        				int defaultUserId=1;
	        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getVillaId(),defaultUserId,"villa");
	        				log.info("***** Successful submitted lead ******");
	        			}
	        			
	        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
	        	        PrimeFaces.current().dialog().showMessageDynamic(message);
	        		}
	        	}
	        	
	            villaModel=gDao.getEastfacing(pageSize,currentPage);
	        	this.custName="";
	        	this.contactNumber="";
	        	this.email="";
	        	
		    }  	
			
			
			
	        
	   public void reset() {
		       PrimeFaces.current().resetInputs("form1:panelDialog");
	  }

	   
	   


			public List<VillaModel> getVillaModel() {
				return villaModel;
			}




			public void setVillaModel(List<VillaModel> villaModel) {
				this.villaModel = villaModel;
			}




			public String getLocationMessage() {
				return locationMessage;
			}




			public String getErrorMessage() {
				return errorMessage;
			}




			public VillaModel getSelectedProperty() {
				return selectedProperty;
			}




			public String getCustName() {
				return custName;
			}




			public String getContactNumber() {
				return contactNumber;
			}




			public String getEmail() {
				return email;
			}




			public void setLocationMessage(String locationMessage) {
				this.locationMessage = locationMessage;
			}




			public void setErrorMessage(String errorMessage) {
				this.errorMessage = errorMessage;
			}




			public void setSelectedProperty(VillaModel selectedProperty) {
				this.selectedProperty = selectedProperty;
			}




			public void setCustName(String custName) {
				this.custName = custName;
			}




			public void setContactNumber(String contactNumber) {
				this.contactNumber = contactNumber;
			}




			public void setEmail(String email) {
				this.email = email;
			}

	

			public int getCurrentPage() {
				return currentPage;
			}


			public int getPageSize() {
				return pageSize;
			}


			public int getTotalRecords() {
				return totalRecords;
			}


			public void setCurrentPage(int currentPage) {
				this.currentPage = currentPage;
			}


			public void setPageSize(int pageSize) {
				this.pageSize = pageSize;
			}


			public void setTotalRecords(int totalRecords) {
				this.totalRecords = totalRecords;
			}
			
			public List<PromoImageModel> getPromoImageModel() {
				return promoImageModel;
			}


			public void setPromoImageModel(List<PromoImageModel> promoImageModel) {
				this.promoImageModel = promoImageModel;
			}
			
			public void setPromoCurrentPage(int promoCurrentPage) {
				this.promoCurrentPage = promoCurrentPage;
			}




			public void setPromoPageSize(int promoPageSize) {
				this.promoPageSize = promoPageSize;
			}



		
		
       
        
               
    

}
