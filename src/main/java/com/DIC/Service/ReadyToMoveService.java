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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.StreamedContent;


import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.PromoImageModel;
import com.DIC.model.VillaModel;

import SMTPService.SMTPService;

@ManagedBean(name="readyToMoveService")

@RequestScoped
public class ReadyToMoveService {
	
	private static final Logger log = LogManager.getLogger(ReadyToMoveService.class);
	
	
	
	private String errorMessage;
	
	
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;
	private int promoCurrentPage = 1;
	private int promoPageSize = 3;
	private int promoTotalRecords;
	private List<PromoImageModel> promoImageModel;
	


    private VillaModel selectedProperty; 
   
	
	private String custName="";
	private String contactNumber="";
	private String email="";

	private List<VillaModel> villaModel;
		
	 
	    GeneralDAOImpl gDao;
	    UserDAOImpl udo;
	    
			public ReadyToMoveService()
			{
							
				log.info("Loading ReadyToMoveService init()");
		    	gDao=new GeneralDAOImpl();
		    	udo=new UserDAOImpl();
		        
		 
		        
		        loadEntities();
				countTotalRecords();
				
				FacesContext context = FacesContext.getCurrentInstance();
		        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		        
		        String pageParam = request.getParameter("readytomove");
		        String promoParam = request.getParameter("readytomovepromo");
		     	        
		        if (pageParam != null && !pageParam.isEmpty()) {
		            try {
		            	
		                currentPage = Integer.parseInt(pageParam);
		                System.out.println("=========================== try =================="+currentPage);
	             
		            } catch (NumberFormatException e) {
		            	
		            	System.out.println("=========================== Catch page =================="+currentPage);
		                currentPage = 1; // Default to page 1 if the parameter is invalid
		            }
		        } else {
		        
		           //Default to page 1 if no page parameter is provided
		        	currentPage = 1;
		        	System.out.println("=========================== else x=================="+currentPage);
		        	
		        }
		        
		        if (promoParam != null && !promoParam.isEmpty()) {
		            try {
		            	promoCurrentPage = Integer.parseInt(promoParam);
		            } catch (NumberFormatException e) {
		            	promoCurrentPage = 1; // Default to page 1 if the parameter is invalid
		            }
		        } else {
		        	promoCurrentPage = 1; // Default to page 1 if no page parameter is provided
		        }
				
			}
			
			
			public void loadEntities() {
		 		
				villaModel=gDao.getReadyToMove(pageSize,currentPage);
				promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
		 		  
				 
		    }
		 	
		 	public void countTotalRecords() {
		 	
		 		totalRecords=gDao.getReadyToMoveCountTotalRecords();
		 		promoTotalRecords=gDao.getPromoCountTotalRecords();
		        
		    }
		 	public void nextPage() {
		
		      if ((currentPage * pageSize) < totalRecords) {
		            //currentPage++;
		           loadEntities();
		            
		        }
		        
		        if ((promoCurrentPage * promoPageSize) < promoTotalRecords) {
		        	//promoCurrentPage++;
		            loadEntities();
		            	
		        }
		    }
		 	    

		    public void previousPage() {
		        if (currentPage > 1) {
		            //currentPage--;
		            loadEntities();
		        }
		        if (promoCurrentPage > 1) {
		        	//promoCurrentPage--;
		            loadEntities();
		        }
		    }
		 	
		    public int getTotalPages() {
		        return (int) Math.ceil((double) totalRecords / pageSize);
		    }
			
		    public void storeSelectedPropertyInSession() {
			    
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		        session.setAttribute("selectedReadyToMove", selectedProperty);
		    }
			
			
	
			public void submit() {
				FacesContext facesContext = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		      
		        if (session != null) {
		         	selectedProperty= (VillaModel) session.getAttribute("selectedReadyToMove");
		         	System.out.println("Selected property  : "+selectedProperty.getVillaId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
		          }	
	        	
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
	        	
	        	//villaModel=gDao.getReadyToMove();
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
				public List<PromoImageModel> getPromoImageModel() {
					return promoImageModel;
				}


				public void setPromoImageModel(List<PromoImageModel> promoImageModel) {
					this.promoImageModel = promoImageModel;
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
			public int getPromoCurrentPage() {
				return promoCurrentPage;
			}




			public int getPromoPageSize() {
				return promoPageSize;
			}




			public void setPromoCurrentPage(int promoCurrentPage) {
				this.promoCurrentPage = promoCurrentPage;
			}




			public void setPromoPageSize(int promoPageSize) {
				this.promoPageSize = promoPageSize;
			}


			
			@PreDestroy
		    public void cleanup() {
		        if (villaModel != null) {
		        	villaModel.clear();
		        }
		    }
       
        
               
    

}
