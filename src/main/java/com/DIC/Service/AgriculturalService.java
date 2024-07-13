package com.DIC.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AgriculturalModel;
import com.DIC.model.LayoutMode;

import SMTPService.SMTPService;

@ManagedBean(name="agriculturalService")
@ViewScoped
public class AgriculturalService implements Serializable{
	private static final Logger log = Logger.getLogger(AgriculturalService.class.getName());

    /**
     * Creates a new instance of AgriculturalService
     */
    
	private final Map<String,Map<String,String>> data = new HashMap<>();
	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 

	private String locationMessage;

    
	private List<AgriculturalModel> agriculturalModelList;
	ConnectionDAOImpl dao; 
	LocationDAOImpl locationDao;
	 UserDAOImpl udo;
	
	
	private AgriculturalModel selectedProperty;   
	
	private String custName;
	private String contactNumber;
	private String email;
    
    
    @PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading AgriculturalService init()");
        dao=new ConnectionDAOImpl();
        locationDao=new LocationDAOImpl();
        udo=new UserDAOImpl();
        primaryModel=locationDao.getAgriPrimaryLocation();
        primLocation  = new HashMap<>(); 
	      for(Map.Entry<String, String> pp:primaryModel.entrySet())
	      {
	    	  log.log(Level.INFO, "Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
	    	  
	    	  primLocation.put(pp.getKey(), pp.getValue());
	    	  
	      }
	      primLocationSort=new TreeMap<>(primLocation);
        
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

    
    
     public List<AgriculturalModel> getAgriculturalModelList() {
        return agriculturalModelList;
    }

    public void setAgriculturalModelList(List<AgriculturalModel> agriculturalModelList) {
        this.agriculturalModelList = agriculturalModelList;
    }
    
    public void onCountryChange() {  
	    	if(country !=null && !country.equals("")) 
	        {
			      secondryLocation=locationDao.getAgriSecondryLocation(country);
				  
				  Collections.sort(secondryLocation);
	        }
        }  


        public void displayLocation() {  
       
        /*
        FacesMessage msg;  
        if(city != null && country != null)  
        msg = new FacesMessage("Selected", city + " of " + country);  
        else  
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");   
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        */
        System.out.println(country+"     "+city);
        
        locationMessage=country+" ,    "+city;
        
        dao=new ConnectionDAOImpl();
        agriculturalModelList=dao.getAgriculturalDetails(country,city);
                
                for(AgriculturalModel x:agriculturalModelList)
                {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getOwnerName());
                }
                
        
        }  
        
        
        
    public String getLocationMessage() {
        return locationMessage;
    }
           
           
           
           
           public void submit() {
	        	
	        	log.log(Level.INFO,"Selected property  : "+selectedProperty.getAgriId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
	        	
	        	
	        	if(selectedProperty.getAgriId()!=0)
	        	{
	        		if(custName!=null && contactNumber!=null && contactNumber!=null)
	        		{
	        			if(selectedProperty.getUserId()!=0)
	        			{
	        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getAgriId(),selectedProperty.getUserId(),"agri");
	        				SMTPService.sendAgriLeadEmail(custName,contactNumber,email,selectedProperty);
	        				log.log(Level.INFO,"***** Successful submitted lead ******");
	        			}
	        			if(selectedProperty.getUserId()==0)
	        			{
	        				int defaultUserId=1;
	        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getAgriId(),defaultUserId,"agri");
	        				log.log(Level.INFO,"***** Successful submitted lead ******");
	        			}
	        			
	        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
	        	        PrimeFaces.current().dialog().showMessageDynamic(message);
	        		}
	        	}
	        	
	        	
	        	agriculturalModelList=dao.getAgriculturalDetails(country,city);
	        	this.custName="";
	        	this.contactNumber="";
	        	this.email="";
	        	
	        }
	        
	   public void reset() {
		       PrimeFaces.current().resetInputs("form1:panelDialog");
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



	public String getCustName() {
		return custName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getEmail() {
		return email;
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

	public AgriculturalModel getSelectedProperty() {
		return selectedProperty;
	}

	public void setSelectedProperty(AgriculturalModel selectedProperty) {
		this.selectedProperty = selectedProperty;
	}
	
	
	
    
}
