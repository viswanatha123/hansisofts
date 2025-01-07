package com.DIC.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.StreamedContent;


import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.VillaModel;

import SMTPService.SMTPService;

@ManagedBean(name="plot4bhkService")

//@RequestScoped
@ViewScoped
//@SessionScoped
public class Plot4bhkService {
	
private static final Logger log = LogManager.getLogger(Plot4bhkService.class);
	
	private String locationMessage;
	private String errorMessage;
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;
	
	


    private VillaModel selectedProperty;   
	
	private String custName="";
	private String contactNumber="";
	private String email="";

		private List<VillaModel> villaModel;
		
		
	    GeneralDAOImpl gDao;
	    UserDAOImpl udo;
	    
	    
	  
	
	    public Plot4bhkService()
		{
			
			log.info("Loading  Plot4bhkService init()");
	    	gDao=new GeneralDAOImpl();
	    	udo=new UserDAOImpl();
	        
	 
	        
	        loadEntities();
			countTotalRecords();
			
		}
		
		
		public void loadEntities() {
	 		
			villaModel=gDao.getPlot4bhkProperties(pageSize,currentPage);
	 		
	        
	    }
	 	
	 	public void countTotalRecords() {
	 	
	 		totalRecords=gDao. getplot4bhkCountTotalRecords();
	        
	    }
	 	public void nextPage() {
	        if ((currentPage * pageSize) < totalRecords) {
	            currentPage++;
	            loadEntities();
	        }
	    }

	    public void previousPage() {
	        if (currentPage > 1) {
	            currentPage--;
	            loadEntities();
	        }
	    }
	 	
	    public int getTotalPages() {
	        return (int) Math.ceil((double) totalRecords / pageSize);
	    }
		
		
		
		

		public void submit() {
	    	System.out.println("-------------submit ----------------------");
        	log.info("Selected property  : "+selectedProperty.getVillaId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
        	
        	
        	if(selectedProperty.getVillaId()!=0)
        	{
        		if(custName!=null && contactNumber!=null && contactNumber!=null)
        		{
        			if(selectedProperty.getUserId()!=0)
        			{
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
        	
        	
        	  loadEntities();
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
		
		        
}