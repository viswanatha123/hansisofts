package com.DIC.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.IndiSiteDataEntryModel;


@ManagedBean(name="indiSiteDataEntryService")
@ViewScoped
public class IndiSiteDataEntryService implements Serializable {
	
	private static final Logger log = Logger.getLogger(IndiSiteDataEntryService.class.getName());
	
	  private String ownerName;
	  private String location;
	  private String contactNo;
	  private String siteNo;
	  private int persqft;
	  private int length;
	  private int width;
	  private String wonership;
	  private String transaction;
	  private String comment;
	  private String facing;
	  private String agentName;
	  private UploadedFile file;
	  private String updateResult;
	  
	  
	  
	  private final Map<String,Map<String,String>> data = new HashMap<>();
	  private String country;   
	  private String city;    
	  private Map<String,String> countries;  
	  private Map<String,String> cities; 



	   ConnectionDAOImpl dao;
	  
	  
	  
	  @PostConstruct 
      public void init()
      {
          log.log(Level.INFO, "Loading IndiDataEntryService init()");
          countries  = new HashMap<>();  
                      //countries.put("USA", "USA");  
                      //countries.put("India", "India");  
                      //countries.put("Russia", "Russia"); 
                      countries.put("Anantapur", "Anantapur"); 
                      countries.put("Kadapa", "Kadapa");
                      countries.put("Kurnool", "Kurnool");
                      countries.put("Tirupati", "Tirupati");
                      countries.put("Chittoor", "Chittoor");
                      countries.put("Bangalore", "Bangalore");
                      countries.put("Ballari", "Ballari");
                      
                      Map<String,String> map = new HashMap<>();  
                     
                      
                      //****Anantapur*****//
                      map = new HashMap<>();  
                      map.put("Kadiri", "Kadiri");  
                      map.put("Kadiri Rural", "Kadiri Rural"); 
                      map.put("Anantapur", "Anantapur");
                      map.put("Anantapur Rural", "Anantapur Rural");
	                  map.put("Hindupur", "Hindupur");  
                      map.put("Dharmavaram", "Dharmavaram"); 
                      map.put("Tadipatri", "Tadipatri"); 
                      map.put("Tadipatri Rural", "Tadipatri Rural");
                      map.put("Gooty", "Gooty");
                      map.put("Gooty Rural", "Gooty Rural");
                      data.put("Anantapur", map);
                      
                      
                      map = new HashMap<>();  
                      map.put("Pulivendula", "Pulivendula");
                      map.put("Pulivendula Rural", "Pulivendula Rural");
                      map.put("Kadapa", "Kadapa");
                      map.put("Kadapa Rural", "Kadapa Rural");
                      map.put("Proddatur", "Proddatur");
	                  map.put("Proddatur Rural", "Proddatur Rural");
                      data.put("Kadapa", map);
                      
                      map = new HashMap<>();  
	                  map.put("Kurnool", "Kurnool");
	                  map.put("Kurnool Rural", "Kurnool Rural");
	                  data.put("Kurnool", map);
	                  
	                  
	                  map = new HashMap<>();  
	                  map.put("Tirupati", "Tirupati");
	                  map.put("Tirupati Rural", "Tirupati Rural");
	                  data.put("Tirupati", map);
	                  
	                  //****Chittoor*****
	                  map = new HashMap<>();  
	                  map.put("Chittoor", "Chittoor");
	                  map.put("Chittoor Rural", "Chittoor Rural");
	                  data.put("Chittoor", map);
	                  
	                  
                      
                      //****Bangalore*****//
                      map = new HashMap<>();  
                      map.put("Hoskote", "Hoskote");  
                      map.put("TinFactory", "TinFactory");  
                      map.put("K R Puram", "K R Puram"); 
                      map.put("Maratha Halli", "Maratha Halli");
                      map.put("Belathuru", "Belathuru");
                      map.put("Whitefield", "Whitefield");
                      map.put("H Cross", "H Cross");
                      data.put("Bangalore", map);
                      
                    //****Ballari*****
                      map = new HashMap<>();  
                      map.put("Ballari", "Ballari");  
                      map.put("Ballari Rural", "Ballari Rural");  
                      data.put("Ballari", map);
          
          
      }
	  
	 	  	
	  
	  public void onCountryChange() {  
          if(country !=null && !country.equals(""))  
          cities = data.get(country);  
          else  
          cities = new HashMap<>();  
          } 
	  
	  
	  
	  
		public void upload() {
	           if (file != null) {
	            try {
	               
	                 log.log(Level.INFO, "Selected county and city ---------->:"+country+"     "+city);
	  	             dao=new ConnectionDAOImpl();
	  	          
	  	
	  	          
	  	          IndiSiteDataEntryModel indiSiteDataEntryModel=new IndiSiteDataEntryModel();
	  	          indiSiteDataEntryModel.setOwnerName(ownerName);
	  	          indiSiteDataEntryModel.setLocation(location);
	  	          indiSiteDataEntryModel.setContactNo(contactNo);
	  	          indiSiteDataEntryModel.setSiteNo(siteNo);
	  	          indiSiteDataEntryModel.setPersqft(persqft);
	  	          indiSiteDataEntryModel.setLength(length);
	  	          indiSiteDataEntryModel.setWidth(width);
	  	          indiSiteDataEntryModel.setWonership(wonership);
	  	          indiSiteDataEntryModel.setOwnerName(ownerName);
	  	          indiSiteDataEntryModel.setTransaction(transaction);
	  	          indiSiteDataEntryModel.setPrimLocation(country);
	  	          indiSiteDataEntryModel.setSecoLocation(city);
	  	          indiSiteDataEntryModel.setComment(comment);
	  	          indiSiteDataEntryModel.setFacing(facing);
	  	          indiSiteDataEntryModel.setAgentName(agentName);
	  	          indiSiteDataEntryModel.setInputStream(file.getInputStream());
	  	          indiSiteDataEntryModel.setFile(file);
	  	       
	  	            
	  	       
	  	        updateResult=dao.updateIndiDataEntry(indiSiteDataEntryModel);
	  	             
	  	  
	  	              
	  	          this.ownerName="";
	  	          this.location="";
	  	          this.contactNo="";
	  	          this.siteNo="";
	  	          this.persqft=0;
	  	          this.length=0;
	  	          this.width=0;
	  	          this.wonership="";
	  	          this.ownerName="";
	  	          this.transaction="";
	  	          this.agentName="";
	  	          
	 	          
	  	          
	            } catch (Exception e) {
	                System.out.println("Exception-File Upload." + e.getMessage());
	            }
	        }
	        else{
	        FacesMessage msg = new FacesMessage("Please select image!!");
	                FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    }
		
		
		
		

	  
	 
      
      public void clear()
      {
    	  this.ownerName="";
          this.location="";
          this.contactNo="";
          this.siteNo="";
          this.persqft=0;
          this.length=0;
          this.width=0;
          this.wonership="";
          this.ownerName="";
          this.transaction="";
          this.agentName="";
          
          this.updateResult="";
          System.out.println("****** Clicked on Clear button*****");
      }
      
      
      
      
        public UploadedFile getFile() {
	  		return file;
	  	}

	  	public void setFile(UploadedFile file) {
	  		this.file = file;
	  	}
      

	  
	  
	  public String getOwnerName() {
			return ownerName;
		}



		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}



		public String getLocation() {
			return location;
		}



		public void setLocation(String location) {
			this.location = location;
		}



		public String getContactNo() {
			return contactNo;
		}



		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}



		public String getSiteNo() {
			return siteNo;
		}



		public void setSiteNo(String siteNo) {
			this.siteNo = siteNo;
		}



		public int getPersqft() {
			return persqft;
		}



		public void setPersqft(int persqft) {
			this.persqft = persqft;
		}



		public int getLength() {
			return length;
		}



		public void setLength(int length) {
			this.length = length;
		}



		public int getWidth() {
			return width;
		}



		public void setWidth(int width) {
			this.width = width;
		}



		public String getWonership() {
			return wonership;
		}



		public void setWonership(String wonership) {
			this.wonership = wonership;
		}



		public String getTransaction() {
			return transaction;
		}



		public void setTransaction(String transaction) {
			this.transaction = transaction;
		}



	
		public String getUpdateResult() {
			return updateResult;
		}



		public void setUpdateResult(String updateResult) {
			this.updateResult = updateResult;
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



		public Map<String, String> getCountries() {
			return countries;
		}



		public void setCountries(Map<String, String> countries) {
			this.countries = countries;
		}



		public Map<String, String> getCities() {
			return cities;
		}



		public void setCities(Map<String, String> cities) {
			this.cities = cities;
		}



		public static Logger getLog() {
			return log;
		}
		
	 public String getComment() {
				return comment;
	  }

	  public void setComment(String comment) {
				this.comment = comment;
	  }
      
	  
	  public String getFacing() {
				return facing;
	  }

	  public void setFacing(String facing) {
				this.facing = facing;
	  }
			
	  public String getAgentName() {
					return agentName;
	  }

	  public void setAgentName(String agentName) {
					this.agentName = agentName;
	   }

	  

}
