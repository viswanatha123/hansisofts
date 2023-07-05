package com.DIC.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.AgriculturalDataEntryModel;


@ManagedBean(name="agriculturalDataEntryhService")
@ViewScoped
public class AgriculturalDataEntryhService implements Serializable {
	
	private static final Logger log = Logger.getLogger(AgriculturalDataEntryhService.class.getName());
	
	
	private String ownerName;
	private String contactNo;
	private String surveyNo;
	private String location;
	private String wonership;
	private String transaction;
	private int perCent;
	private int numberCents;
	private long cost;
	private String waterSource;
	private String crop;
	private String primLocation;
	private String secoLocation;
	private String comment;
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
          log.log(Level.INFO, "AgriculturalDataEntryhService init()");
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
                      map.put("H Cross", "H Cross");
                      data.put("Bangalore", map);
                      
                    //****Ballari*****
                      map = new HashMap<>();  
                      map.put("Ballari", "Ballari");  
                      map.put("Ballari Rural", "Ballari Rural");  
                      data.put("Ballari", map);
          
          
      }
	  
	  
	  public void upload() {
          if (file != null) {
           try {
                             
               log.log(Level.INFO, "Selected county and city ---------->:"+country+"     "+city);
 	             
 	           dao=new ConnectionDAOImpl();
 	          
 	          AgriculturalDataEntryModel agriculturalDataModel=new AgriculturalDataEntryModel();
 	          agriculturalDataModel.setOwnerName(ownerName);
 	          agriculturalDataModel.setContactNo(contactNo);
 	          agriculturalDataModel.setSurveyNo(surveyNo);
 	          agriculturalDataModel.setLocation(location);
 	          agriculturalDataModel.setWonership(wonership);
 	          agriculturalDataModel.setTransaction(transaction);
 	          agriculturalDataModel.setPerCent(perCent);
 	          agriculturalDataModel.setNumberCents(numberCents);
 	          agriculturalDataModel.setCost(cost);
 	          agriculturalDataModel.setWaterSource(waterSource);
 	          agriculturalDataModel.setCrop(crop);
 	          agriculturalDataModel.setPrimLocation(country);
 	          agriculturalDataModel.setSecoLocation(city);
 	          agriculturalDataModel.setComment(comment);
 	          agriculturalDataModel.setAgentName(agentName);
 	          agriculturalDataModel.setInputStream(file.getInputStream());
 	          agriculturalDataModel.setFile(file);
 	        
 	          
 	       
 	          
 	         updateResult=dao.updateAgriDataEntry(agriculturalDataModel);
 	             
 	          this.ownerName="";
 	          this.contactNo="";
 	          this.surveyNo="";
 	          this.location="";
 	          this.wonership="";
 	          this.transaction="";
 	          this.perCent=0;
 	          this.numberCents=0;
 	          this.cost=0;
 	          this.waterSource="";
 	          this.crop="";
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
	
	  
	 
	  
	  
	  public void onCountryChange() {  
          if(country !=null && !country.equals(""))  
          cities = data.get(country);  
          else  
          cities = new HashMap<>();  
          }  

	  
	  
      
      public void clear()
      {
    	  this.ownerName="";
          this.contactNo="";
          this.surveyNo="";
          this.location="";
          this.wonership="";
          this.transaction="";
          this.perCent=0;
          this.numberCents=0;
          this.cost=0;
          this.waterSource="";
          this.crop="";
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
	public Map<String, Map<String, String>> getData() {
		return data;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getSurveyNo() {
		return surveyNo;
	}
	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public int getPerCent() {
		return perCent;
	}
	public void setPerCent(int perCent) {
		this.perCent = perCent;
	}
	public int getNumberCents() {
		return numberCents;
	}
	public void setNumberCents(int numberCents) {
		this.numberCents = numberCents;
	}
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
		this.cost = cost;
	}
	public String getWaterSource() {
		return waterSource;
	}
	public void setWaterSource(String waterSource) {
		this.waterSource = waterSource;
	}
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
	}
	public String getPrimLocation() {
		return primLocation;
	}
	public void setPrimLocation(String primLocation) {
		this.primLocation = primLocation;
	}
	public String getSecoLocation() {
		return secoLocation;
	}
	public void setSecoLocation(String secoLocation) {
		this.secoLocation = secoLocation;
	}
	
	public static Logger getLog() {
		return log;
	}
	
	
	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	
	

}
