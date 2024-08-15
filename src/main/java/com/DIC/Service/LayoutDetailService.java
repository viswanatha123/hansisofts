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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.LayoutMode;
import com.DIC.model.UserDetails;

import SMTPService.SMTPService;





@ManagedBean(name="layoutDetailService")
@ViewScoped
public class LayoutDetailService implements Serializable{
	
	private static final Logger log = LogManager.getLogger(LayoutDetailService.class);
	
	private final Map<String,Map<String,String>> data = new HashMap<>();
	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 
	private String locationMessage;

	private LayoutMode selectedProperty;   
	
	private String custName;
	private String contactNumber;
	private String email;
	   

	    private List<LayoutMode> layoutdetails;
	    ConnectionDAOImpl dao;
	    LocationDAOImpl locationDao;
	    UserDAOImpl udo;
	    
	    @PostConstruct 
	    public void init()
	    {
	    	log.info("Loading LayoutDetailService init()");
	          dao=new ConnectionDAOImpl();
	          locationDao=new LocationDAOImpl();
	          udo=new UserDAOImpl();
	          
	          primaryModel=locationDao.getLayoutPrimaryLocation();
	          
	          primLocation  = new HashMap<>(); 
            for(Map.Entry<String, String> pp:primaryModel.entrySet())
            {
          	  log.info("Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
          	  
          	  primLocation.put(pp.getKey(), pp.getValue());
          	  
            }
            primLocationSort=new TreeMap<>(primLocation);
            
            
                   
	    }
	    
	        public Map<String, Map<String, String>> getData() {  
	           return data;  
	        } 
	        
	        public String getCountry() {  
	        return country;  
	        }  
	        
	        public void setCountry(String country) {  
	        this.country = country;  
	        }  
	        
	        public String getCity() {  
	        return city;  
	        }  
	        
	        public void setCity(String city) {  
	        this.city = city;  
	        }  
	        
	        	        
	        
	        public void onCountryChange() {  
	        	if(country !=null && !country.equals("")) 
		          {
				     secondryLocation=locationDao.getLayoutSecondryLocation(country);
					  
					  Collections.sort(secondryLocation);
		          }
	        }  


	        public void displayLocation() {  
	
	        System.out.println(country+"     "+city);
	        
	        locationMessage=country+" ,   "+city;
	        
	        //dao=new ConnectionDAOImpl();
	        layoutdetails=dao.getLayoutDetails(country,city);
	                
	                for(LayoutMode x:layoutdetails)
	                {
	                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getName());
	                }
	           
	        }  
	        
	        
	       
	        public void submit() {
	        	
	        	log.info("Selected property  : "+selectedProperty.getLayoutId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
	        	
	        	
	        	if(selectedProperty.getLayoutId()!=0)
	        	{
	        		if(custName!=null && contactNumber!=null && contactNumber!=null)
	        		{
	        			if(selectedProperty.getUserId()!=0)
	        			{
	        				
	        					        				
	        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getLayoutId(),selectedProperty.getUserId(),"layout");
	        				SMTPService.sendLayoutLeadEmail(custName,contactNumber,email,selectedProperty);
	        				log.info("***** Successful submitted lead ******");
	        				
	        			}
	        			if(selectedProperty.getUserId()==0)
	        			{
	        				int defaultUserId=1;
	        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getLayoutId(),defaultUserId,"layout");
	        				log.info("***** Successful submitted lead ******");
	        			}
	        			
	        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
	        	        PrimeFaces.current().dialog().showMessageDynamic(message);
	        		}
	        	}
	        	
	        	
	        	layoutdetails=dao.getLayoutDetails(country,city);
	        	this.custName="";
	        	this.contactNumber="";
	        	this.email="";
	        	
	        }
	        
	   public void reset() {
		       PrimeFaces.current().resetInputs("form1:panelDialog");
	  }
	        
	        
	        
	   public List<LayoutMode> getLayoutdetails() {
	        return layoutdetails;
	    }

	    public void setLayoutdetails(List<LayoutMode> layoutdetails) {
	        this.layoutdetails = layoutdetails;
	    }

	    
	    
	    public String getLocationMessage() {
	        return locationMessage;
	    }

	    public void setLocationMessage(String locationMessage) {
	        this.locationMessage = locationMessage;
	    }
	    
	    public Map<String, String> getPrimaryModel() {
			return primaryModel;
		}

		public Map<String, String> getPrimLocation() {
			return primLocation;
		}

		public List<String> getSecondryLocation() {
			return secondryLocation;
		}

		public void setPrimaryModel(Map<String, String> primaryModel) {
			this.primaryModel = primaryModel;
		}

		public void setPrimLocation(Map<String, String> primLocation) {
			this.primLocation = primLocation;
		}

		public void setSecondryLocation(List<String> secondryLocation) {
			this.secondryLocation = secondryLocation;
		}

		public TreeMap<String, String> getPrimLocationSort() {
			return primLocationSort;
		}

		public void setPrimLocationSort(TreeMap<String, String> primLocationSort) {
			this.primLocationSort = primLocationSort;
		}

		public LayoutMode getSelectedProperty() {
			return selectedProperty;
		}

		public void setSelectedProperty(LayoutMode selectedProperty) {
			this.selectedProperty = selectedProperty;
		}

		public String getCustName() {
			return custName;
		}

		public void setCustName(String custName) {
			this.custName = custName;
		}

		public String getContactNumber() {
			return contactNumber;
		}

		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	   
	                
	
	    

}
