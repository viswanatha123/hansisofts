package com.DIC.Service;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.DIC.DAO.Impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.DIC.model.LayoutMode;
import com.DIC.model.PromoImageModel;
import com.DIC.model.UserDetails;
import com.DIC.model.VillaModel;

import SMTPService.SMTPService;

//import SMTPService.SMTPService;


/*
 * 
 * developed by viswanatha;
 */



@ManagedBean(name="villaDetailsService")

//@RequestScoped
@ViewScoped
public class VillaDetailsService implements Serializable {
	private static final Logger log = LogManager.getLogger(VillaDetailsService.class);

	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 
	private String locationMessage;
	private String proType;
	private String errorMessage;
	
	private String menuId;
	private int fetchRecords;
	
	
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;
	
	private int promoCurrentPage = 1;
	private int promoPageSize = 3;
	private int promoTotalRecords;
	
	
	
	


    private VillaModel selectedProperty;
	private List<VillaModel> villaData;
	
	private String custName;
	private String contactNumber;
	private String email;
	private String comment;

	private List<PromoImageModel> promoImageModel;
	
	private List<VillaModel> villaModel;
		
	    GeneralDAOImpl gDao;
	    LocationDAOImpl locationDao;
	    UserDAOImpl udo;
	    SMSService sms;
	    UserRoleService ur;
		PropertyDAOImpl pDao;
		ConnectionDAOImpl dao;
	    
	    
	     
	    public VillaDetailsService()
	    {
	    	log.info("Loading LayoutDetailService init()");
	    	  gDao=new GeneralDAOImpl();
	          locationDao=new LocationDAOImpl();
	          udo=new UserDAOImpl();
	          sms=new SMSService();
	          ur=new UserRoleService();
			  pDao=new PropertyDAOImpl();
			  dao=new ConnectionDAOImpl();
	          
	          primaryModel=locationDao.getVillaPrimaryLocation();
	          
	          
	          
	          
	          
	          primLocation  = new HashMap<>(); 
            for(Map.Entry<String, String> pp:primaryModel.entrySet())
            {
          	  log.info("Villa Primar location details ---------->:"+pp.getKey()+"   "+pp.getValue());
          	  
          	  primLocation.put(pp.getKey(), pp.getValue());
          	  
            }
            primLocationSort=new TreeMap<>(primLocation);
            
            
            
            FacesContext context = FacesContext.getCurrentInstance();
            menuId = context.getExternalContext().getRequestParameterMap().get("id");
            
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@22 : Menu id"+menuId);
            
            
            if(menuId != null )
            {
            	 fetchRecords=0;
            	 villaModel=null;
             	 villaModel=gDao.getVillaDetails(menuId);
             	fetchRecords=villaModel.size();
            
            }
	        
           
            
                   
	    }
	    
	  

		    
	    public void onCountryChange() {  
        	if(country !=null && !country.equals("")) 
	          {
			     secondryLocation=locationDao.getVillaSecondryLocation(country);
				  
				  Collections.sort(secondryLocation);
	          }
        }
	    
	    

	        public void getVillaDetails() {  
		    	
		    	System.out.println(" **** submited button ******");
		    	loadEntities(); 
		    	countTotalRecords();
		        locationMessage=country+" ,   "+city;
		        //villaModel.clear();
		        
		        loadEntities();
				countTotalRecords();
		        
		        //promoImageModel=gDao.getPromoImageVilla();
		        
		                 
		                
			             
		     }  
	        
	        public void loadEntities() {
		 		
	        	 //promoImageModel=gDao.getPromoImageVilla();
	        	// promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
				promoImageModel=dao.getPromoImage(promoPageSize, promoCurrentPage,country);
	        	villaModel=gDao.getVillaDetails(country,city, proType,pageSize,currentPage);
	        	
	        	
	        	for(VillaModel x:villaModel)
                {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getI_am());
                }
                if(villaModel.size() == 0)
        		{
        			errorMessage="There are no records on "+proType;
        		}
                else {
                	errorMessage="";
                }
           
		        
		    }
	        public void countTotalRecords() {
			 	
	        	System.out.println("================>"+country+"  "+city+"   "+proType);
		 		totalRecords=gDao.getVillaCountTotalRecords(country,city, proType);
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
			
	        
	    
	    public void submit() {
	    	System.out.println("-------------submit ----------------------");
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
        				
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,selectedProperty.getVillaId(),selectedProperty.getUserId(),"villa");
        				SMTPService.sendVillaLeadEmail(custName,contactNumber,email,selectedProperty);
        				log.info("***** Successful submitted lead ******");
        			}
        			if(selectedProperty.getUserId()==0)
        			{
        				int defaultUserId=1;
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,selectedProperty.getVillaId(),defaultUserId,"villa");
        				log.info("***** Successful submitted lead ******");
        			}
        			
        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
        	        PrimeFaces.current().dialog().showMessageDynamic(message);
        		}
        	}
        	
        	
        	villaModel=gDao.getVillaDetails(country,city, proType,pageSize,currentPage);
			promoImageModel=dao.getPromoImage(promoPageSize, promoCurrentPage,country);
        	this.custName="";
        	this.contactNumber="";
        	this.email="";
        	
        	if(menuId != null )
            {
	        	 if(menuId.equals("ReadytoMove") || menuId.equals("UnderConstruction") || menuId.equals("OwnerProperties"))
	             {
	        		 villaModel=gDao.getVillaDetails(menuId);
	             	this.custName="";
	             	this.contactNumber="";
	             	this.email="";
	             }
            }
        	
        }
        
   public void reset() {
	       PrimeFaces.current().resetInputs("form1:panelDialog");
  }
	public void getVillaDialog()
	{
		villaData=pDao.getVillaData(selectedProperty.getVillaId());
	}
   
   
	    
	    


		public String getCountry() {
			return country;
		}


		public String getCity() {
			return city;
		}


		public Map<String, String> getPrimaryModel() {
			return primaryModel;
		}


		public List<String> getSecondryLocation() {
			return secondryLocation;
		}


		public String getLocationMessage() {
			return locationMessage;
		}


		public void setCountry(String country) {
			this.country = country;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public void setPrimaryModel(Map<String, String> primaryModel) {
			this.primaryModel = primaryModel;
		}


		public void setSecondryLocation(List<String> secondryLocation) {
			this.secondryLocation = secondryLocation;
		}


		public void setLocationMessage(String locationMessage) {
			this.locationMessage = locationMessage;
		}


		public List<VillaModel> getVillaModel() {
			return villaModel;
		}


		public void setVillaModel(List<VillaModel> villaModel) {
			this.villaModel = villaModel;
		}  

	    
		public void setPrimLocation(Map<String, String> primLocation) {
			this.primLocation = primLocation;
		}
		public Map<String, String> getPrimLocation() {
			return primLocation;
		}


		public TreeMap<String, String> getPrimLocationSort() {
			return primLocationSort;
		}


		public void setPrimLocationSort(TreeMap<String, String> primLocationSort) {
			this.primLocationSort = primLocationSort;
		}


		public String getProType() {
			return proType;
		}


		public void setProType(String proType) {
			this.proType = proType;
		}


		public String getErrorMessage() {
			return errorMessage;
		}


		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
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


		public String getMenuId() {
			return menuId;
		}


		public void setMenuId(String menuId) {
			this.menuId = menuId;
		}


		public int getFetchRecords() {
			return fetchRecords;
		}


		public void setFetchRecords(int fetchRecords) {
			this.fetchRecords = fetchRecords;
		}


		public List<PromoImageModel> getPromoImageModel() {
			return promoImageModel;
		}


		public void setPromoImageModel(List<PromoImageModel> promoImageModel) {
			this.promoImageModel = promoImageModel;
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




		public int getPromoTotalRecords() {
			return promoTotalRecords;
		}




		public void setPromoTotalRecords(int promoTotalRecords) {
			this.promoTotalRecords = promoTotalRecords;
		}




		public String getComment() {
			return comment;
		}




		public void setComment(String comment) {
			this.comment = comment;
		}

	public List<VillaModel> getVillaData() {
		return villaData;
	}

	public void setVillaData(List<VillaModel> villaData) {
		this.villaData = villaData;
	}
}
