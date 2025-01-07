package com.DIC.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;

//import org.apache.log4j.Logger;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.BudgetModel;
import com.DIC.model.IndividualSiteModel;

import SMTPService.SMTPService;

@ManagedBean(name="budgetService2")
@ViewScoped
public class BudgetService2 implements Serializable{
	
	private static final Logger log = Logger.getLogger(BudgetService2.class.getName());
	
	private String locationMessage;
	private List<BudgetModel> budgetModelList;
	
	
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;
	 
	private BudgetModel selectedProperty;
	 
	 
	private String custName="";
	private String contactNumber="";
	private String email="";
	
	GeneralDAOImpl gDao;
	UserDAOImpl udo;
	
	
	
	public BudgetService2()
	{
		log.info("Loading BudgetService2 Constructor");
		gDao=new GeneralDAOImpl();
		udo=new UserDAOImpl();
		
		loadEntities();
		countTotalRecords();
	}
	

	 	
	 	public void loadEntities() {
	 		
	 		budgetModelList=gDao.getBudget1Details(2,pageSize,currentPage);
	 	
	        
	    }
	 	
	 	public void countTotalRecords() {
	 	
	 		totalRecords=gDao.getBudget1DetailsCountTotalRecords(2);
	        
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
        	log.info("Selected property  : "+selectedProperty.getPro_id()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
        	
        	
        	if(selectedProperty.getPro_id()!=0)
        	{
        		if(custName!=null && contactNumber!=null && contactNumber!=null)
        		{
        			if(selectedProperty.getUserId()!=0)
        			{
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getPro_id(),selectedProperty.getUserId(),selectedProperty.getPro_type());
        				SMTPService.sendVillaLeadEmail(custName,contactNumber,email,selectedProperty);
        				log.info("***** Successful submitted lead ******");
        			}
        			if(selectedProperty.getUserId()==0)
        			{
        				int defaultUserId=1;
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getPro_id(),defaultUserId,"villa");
        				log.info("***** Successful submitted lead ******");
        			}
        			
        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
        	        PrimeFaces.current().dialog().showMessageDynamic(message);
        		}
        	}
        	
        	//villaModel=gDao.getReadyToMove();
        	  loadEntities();
        }
        
		   public void reset() {
			       PrimeFaces.current().resetInputs("form1:panelDialog");
		  }
	 	
	
	 	
	
		public String getLocationMessage() {
			return locationMessage;
		}

		public List<BudgetModel> getBudgetModelList() {
			return budgetModelList;
		}

		public void setLocationMessage(String locationMessage) {
			this.locationMessage = locationMessage;
		}

		public void setBudgetModelList(List<BudgetModel> budgetModelList) {
			this.budgetModelList = budgetModelList;
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



		public BudgetModel getSelectedProperty() {
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



		public void setSelectedProperty(BudgetModel selectedProperty) {
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
	
	
	

}
