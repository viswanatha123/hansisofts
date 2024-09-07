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

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.LayoutMode;
import com.DIC.model.VillaModel;

import SMTPService.SMTPService;


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
	
	


    private VillaModel selectedProperty;   
	
	private String custName;
	private String contactNumber;
	private String email;

		private List<VillaModel> villaModel;
	    GeneralDAOImpl gDao;
	    LocationDAOImpl locationDao;
	    UserDAOImpl udo;
	    
	    
	    @PostConstruct 
	    public void init()
	    {
	    	log.info("Loading LayoutDetailService init()");
	    	gDao=new GeneralDAOImpl();
	          locationDao=new LocationDAOImpl();
	          udo=new UserDAOImpl();
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
	      	        
		        locationMessage=country+" ,   "+city;
		        //villaModel.clear();
		        villaModel=gDao.getVillaDetails(country,city, proType);
		                 
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
        	
        	
        	villaModel=gDao.getVillaDetails(country,city, proType);
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


	

	    
		
		
	    

}
