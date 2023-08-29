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
	private String comment;
	private String agentName;
	private UploadedFile file;
	private String updateResult;
	
	
	
	  
	  private String country;   
	  private String city;    
	  private Map<String, String> primaryModel;
	  private Map<String,String> primLocation; 
	  private TreeMap<String, String> primLocationSort;
	  private List<String> secondryLocation;
	  
	  
	  	
	  
	  ConnectionDAOImpl dao;
	  
	  @PostConstruct 
      public void init()
      {
          log.log(Level.INFO, "AgriculturalDataEntryhService init()");
       
          dao=new ConnectionDAOImpl();
          primaryModel=dao.getPrimaryLocation();
          primLocation  = new HashMap<>();
          for(Map.Entry<String, String> pp:primaryModel.entrySet())
          {
        	  log.log(Level.INFO, "Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
        	  
        	  primLocation.put(pp.getKey(), pp.getValue());
        	  
          }
          primLocationSort=new TreeMap<>(primLocation);
          
          
      }
	  
	  
	  public void upload() {
          if (file != null) {
           try {
                             
               log.log(Level.INFO, "Selected county and city ---------->:"+country+"     "+city);
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
 	          this.comment="";
 	          
          
 	          
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
		  
		  log.log(Level.INFO, "Primary value ---------->:"+country);
			  if(country !=null && !country.equals("")) 
		       {
					  secondryLocation=dao.getSecondryLocation(country);
					  Collections.sort(secondryLocation);
					  
					  
		       }
	        
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
          this.comment="";
          
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


	public void setPrimLocation1(Map<String, String> primLocation) {
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

	
	
	
	

}
