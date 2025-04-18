package com.DIC.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.DIC.DAO.Impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.DIC.model.AgriculturalModel;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.PromoImageModel;
import com.DIC.model.UserDetails;
import com.DIC.model.VillaModel;

import SMTPService.SMTPService;


@ManagedBean(name="individualSiteService")
@ViewScoped
public class IndividualSiteService implements Serializable {

	
	private static final Logger log = LogManager.getLogger(IndividualSiteService.class);
	
	private final Map<String,Map<String,String>> data = new HashMap<>();
	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 

	private String locationMessage;
	private List<IndividualSiteModel> individualSiteList;
	private List<PromoImageModel> promoImageModel;
	
	
	
	private IndividualSiteModel selectedProperty;
	private List<IndividualSiteModel> indiData;
	
	private String custName;
	private String contactNumber;
	private String email;
	private String comment;
	
	
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;
	private int promoCurrentPage = 1;
	private int promoPageSize = 3;
	private int promoTotalRecords;
	
	
	
	
	ConnectionDAOImpl dao; 
	LocationDAOImpl locationDao;
	UserDAOImpl udo;
	GeneralDAOImpl gDao;
	SMSService sms;
	UserRoleService ur;
	PropertyDAOImpl pDao;
	
	
	 	public IndividualSiteService()
 	    {		 
	 		  dao=new ConnectionDAOImpl();
	 		  locationDao=new LocationDAOImpl();
	          udo=new UserDAOImpl();
	          sms=new SMSService();
	          ur=new UserRoleService();
	          gDao=new GeneralDAOImpl();
			  pDao=new PropertyDAOImpl();
        
        primaryModel=locationDao.getIndivPrimaryLocation();
        
        
        primLocation  = new HashMap<>(); 
	      for(Map.Entry<String, String> pp:primaryModel.entrySet())
	      {
	    	  log.info("Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
	    	  
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


    
    
    public void onCountryChange() {  
		    	if(country !=null && !country.equals("")) 
		        {
					secondryLocation=locationDao.getIndivSecondryLocation(country);
					  
					  Collections.sort(secondryLocation);
		        }
        }  


        public void getindividualsiteDetails() {  
       
       
        System.out.println(country+"     "+city);
        
        locationMessage=country+" ,    "+city;
   
      
        loadEntities(); 
    	countTotalRecords();
        }
    
                
                public void loadEntities() {
                	individualSiteList=dao.getIndividualSiteDetails(country,city,pageSize,currentPage);
                	promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
                	
                	for(IndividualSiteModel x:individualSiteList)
                    {
                        System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getOwnerName());
                    }
                }
                public void countTotalRecords() {
    			 	
    	        	System.out.println("================>"+country+"  "+city+"   ");
    		 		totalRecords=dao.getIndividualSiteDetailsCountTotalRecords(country,city);
    		 		promoTotalRecords=dao.getPromoCountTotalRecords();

    		        
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
        	
        	log.info("Selected property  : "+selectedProperty.getInd_id()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
        	
        	
        	if(selectedProperty.getInd_id()!=0)
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
        				
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,selectedProperty.getInd_id(),selectedProperty.getUserId(),"indi");
        				SMTPService.sendIndiLeadEmail(custName,contactNumber,email,selectedProperty);
        				log.info("***** Successful submitted lead ******");
        			}
        			if(selectedProperty.getUserId()==0)
        			{
        				int defaultUserId=1;
        				String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,selectedProperty.getInd_id(),defaultUserId,"indi");
        				log.info("***** Successful submitted lead ******");
        			}
        			
        			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
        	        PrimeFaces.current().dialog().showMessageDynamic(message);
        		}
        	}
        	
        	
          	
        	individualSiteList=dao.getIndividualSiteDetails(country,city,pageSize,currentPage);
        	promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
        	this.custName="";
        	this.contactNumber="";
        	this.email="";
        	
        }
        
   public void reset() {
	       PrimeFaces.current().resetInputs("form1:panelDialog");
  }
	public void getIndiDialog()
	{
		indiData=pDao.getIndiData(selectedProperty.getInd_id());
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

	public List<IndividualSiteModel> getIndividualSiteList() {
		return individualSiteList;
	}

	public void setIndividualSiteList(List<IndividualSiteModel> individualSiteList) {
		this.individualSiteList = individualSiteList;
	}
	public List<PromoImageModel> getPromoImageModel() {
		return promoImageModel;
	}


	public void setPromoImageModel(List<PromoImageModel> promoImageModel) {
		this.promoImageModel = promoImageModel;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public List<IndividualSiteModel> getIndiData() {
		return indiData;
	}

	public void setIndiData(List<IndividualSiteModel> indiData) {
		this.indiData = indiData;
	}
}





       



    
	
	
	