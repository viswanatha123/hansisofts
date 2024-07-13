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
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;

import SMTPService.SMTPService;


@ManagedBean(name="individualSiteService")
@ViewScoped
public class IndividualSiteService implements Serializable {

	
	private static final Logger log = Logger.getLogger(IndividualSiteService.class.getName());
	
	private final Map<String,Map<String,String>> data = new HashMap<>();
	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 

	private String locationMessage;
	
	private List<IndividualSiteModel> individualSiteList;
	

	ConnectionDAOImpl dao; 
	LocationDAOImpl locationDao;
	UserDAOImpl udo;
	
    private IndividualSiteModel selectedProperty;   
	
	private String custName;
	private String contactNumber;
	private String email;
	
	
	@PostConstruct 
    public void init()
    {

		log.log(Level.INFO, "Loading IndividualSiteService init()");
        dao=new ConnectionDAOImpl();
        locationDao=new LocationDAOImpl();
        udo=new UserDAOImpl();
        primaryModel=locationDao.getIndivPrimaryLocation();
        
        
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

   
    
    public List<IndividualSiteModel> getIndividualSiteList() {
		return individualSiteList;
	}

	public void setIndividualSiteList(List<IndividualSiteModel> individualSiteList) {
		this.individualSiteList = individualSiteList;
	}
    
    
    
    public void onCountryChange() {  
		    	if(country !=null && !country.equals("")) 
		        {
					secondryLocation=locationDao.getIndivSecondryLocation(country);
					  
					  Collections.sort(secondryLocation);
		        }
        }  


        public void displayLocation() {  
       
       
        System.out.println(country+"     "+city);
        
        locationMessage=country+" ,    "+city;
   
        individualSiteList=dao.getIndividualSiteDetails(country,city);
                
                for(IndividualSiteModel x:individualSiteList)
                {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getOwnerName());
                }
                
        
        }  
        
        
        public void submit() {
        	
        	log.log(Level.INFO,"Selected property  : "+selectedProperty.getInd_id()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
        	
        	
        	if(selectedProperty.getInd_id()!=0)
        	{
        		if(custName!=null && contactNumber!=null && contactNumber!=null)
        		{
        			if(selectedProperty.getUserId()!=0)
        			{
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getInd_id(),selectedProperty.getUserId(),"indi");
        				SMTPService.sendIndiLeadEmail(custName,contactNumber,email,selectedProperty);
        				log.log(Level.INFO,"***** Successful submitted lead ******");
        			}
        			if(selectedProperty.getUserId()==0)
        			{
        				int defaultUserId=1;
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getInd_id(),defaultUserId,"indi");
        				log.log(Level.INFO,"***** Successful submitted lead ******");
        			}
        			
        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
        	        PrimeFaces.current().dialog().showMessageDynamic(message);
        		}
        	}
        	
        	
          	
        	individualSiteList=dao.getIndividualSiteDetails(country,city);
        	this.custName="";
        	this.contactNumber="";
        	this.email="";
        	
        }
        
   public void reset() {
	       PrimeFaces.current().resetInputs("form1:panelDialog");
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

	public IndividualSiteModel getSelectedProperty() {
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

	public void setSelectedProperty(IndividualSiteModel selectedProperty) {
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
