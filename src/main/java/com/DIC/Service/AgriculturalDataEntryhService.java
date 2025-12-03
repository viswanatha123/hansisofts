package com.DIC.Service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;

import com.DIC.DAO.Impl.CommonDAOImpl;
import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AgriculturalDataEntryModel;

import SMTPService.SMTPService;
import framework.utilities.SessionUtils;


@ManagedBean(name="agriculturalDataEntryhService")
@ViewScoped
public class AgriculturalDataEntryhService implements Serializable {
	
	private static final Logger log = LogManager.getLogger(AgriculturalDataEntryhService.class);
	
	
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
	private String power;
	private int listLimit;
 	private int listedCount=-1;
 	private Boolean isEnable;
	private int userId;
	private String surveyNum;
	private String cornerBit;
	
	
	
	  
	  private String country;   
	  private String city;    
	  private Map<String, String> primaryModel;
	  private Map<String,String> primLocation; 
	  private TreeMap<String, String> primLocationSort;
	  private List<String> secondryLocation;

	private UploadedFiles filesx;
	  
	  	
	  
	  ConnectionDAOImpl dao;
	  UserDAOImpl uDao;
	  UserRoleService ur;
	  CommonDAOImpl comm;
	  
	  @PostConstruct 
      public void init()
      {
          log.info("AgriculturalDataEntryhService init()");
       
          dao=new ConnectionDAOImpl();
          primaryModel=dao.getPrimaryLocation();
          uDao=new UserDAOImpl();
          ur=new UserRoleService();
          comm=new CommonDAOImpl();
          
          primLocation  = new HashMap<>();
          for(Map.Entry<String, String> pp:primaryModel.entrySet())
          {
        	  log.info("Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
        	  
        	  primLocation.put(pp.getKey(), pp.getValue());
        	  
          }
          primLocationSort=new TreeMap<>(primLocation);
          
          HttpSession session = SessionUtils.getSession();
	       	if (session != null)
	    	{
	    		if(session.getAttribute("userId")!=null)
	    		{
	    			userId= Integer.parseInt(session.getAttribute("userId").toString());
	    		    		if(Integer.parseInt(session.getAttribute("listLimit").toString()) > 0)
	    		    		{
	    		    			 listLimit = Integer.parseInt(session.getAttribute("listLimit").toString());
	    		    			 listedCount=uDao.getAllPropByUserId(Integer.parseInt(session.getAttribute("userId").toString())).size();
	    		    			  log.info("listedCount  listLimit :"+listedCount+"  <=  "+listLimit);
	    		    			  
	    		    		   		    			  
	    		    			  isEnable = Boolean.valueOf(session.getAttribute("isEnable").toString());
	    		    			  
	    		    		}
	    		    		    		    
	    		}
	    		if(session.getAttribute("userId")==null)
	    		{    	
  		    	
	    			userId=-1;
	    			listLimit=1;
	    			listedCount=0;
	    			
	    			  log.info("listedCount  listLimit :"+listedCount+"  <=  "+listLimit);
	    		}
	    		
	    	}
          
          
      }
	  
	  
	  public void upload() {
          if (file != null) {
           try {
                             
               log.info("Selected county and city ---------->:"+country+"     "+city);
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
 	          agriculturalDataModel.setPower(power);
 	         agriculturalDataModel.setCornerBit(cornerBit);

			   System.out.println("Service Upload image size :" + filesx.getFiles().size());
			   List<InputStream> inputStreams = new ArrayList<>();
			   List<UploadedFile> files = new ArrayList<>();
			   for (UploadedFile f : filesx.getFiles()) {
				   if (f.getSize() > 0) {
					   inputStreams.add(f.getInputStream());
					   files.add(file);
				   }
			   }
			   agriculturalDataModel.setInputStreams(inputStreams);
			   agriculturalDataModel.setFiles(files);
 	          
 	        
 	        HttpSession session = SessionUtils.getSession();
	       	if (session != null)
	    	{
	    		if(session.getAttribute("userId")!=null)
	    		{
	    		    int userId= Integer.parseInt(session.getAttribute("userId").toString());
	    		    if(userId > 0)
	    		    {    	
	              	//updateResult=dao.updateAgriDataEntry(agriculturalDataModel,userId);
			              	if(ur.getUserRole().contains("Rank"))
							{
		    		    		
		    		    				updateResult=dao.updateAgriDataEntry(agriculturalDataModel,userId,comm.getUserRank(userId));
				    		
							}
		    		    	else
		    		    	{
		    		    		updateResult=dao.updateAgriDataEntry(agriculturalDataModel,userId,0);
		    		    	}
	              	
	              	
	              	
	              	SMTPService.sendAgeriEmail(agriculturalDataModel,userId);
	    		    		
	    		    }
	    		}
	    		if(session.getAttribute("userId")==null)
    		    {    	
    		    	int defaultUserId=1;
    		    	updateResult=dao.updateAgriDataEntry(agriculturalDataModel,defaultUserId,0);
    		    	
    		    		
    		    }
	    	}
  	             
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
 	          this.power="";
 	          this.country="";
 	          this.city="";
 	          
          
 	          
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
		  
		  log.info("Primary value ---------->:"+country);
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
          this.power="";
          this.country="";
	      this.city="";
          
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


	public String getPower() {
		return power;
	}


	public void setPower(String power) {
		this.power = power;
	}

	
	public int getListLimit() {
		return listLimit;
	}

	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}

	public int getListedCount() {
		return listedCount;
	}

	public void setListedCount(int listedCount) {
		this.listedCount = listedCount;
	}
	
	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCornerBit() {
		return cornerBit;
	}

	public void setCornerBit(String cornerBit) {
		this.cornerBit = cornerBit;
	}


	public UploadedFiles getFilesx() {
		return filesx;
	}

	public void setFilesx(UploadedFiles filesx) {
		this.filesx = filesx;
	}
}
