package com.DIC.Service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
import com.DIC.model.IndiSiteDataEntryModel;

import SMTPService.SMTPService;
import framework.utilities.SessionUtils;


@ManagedBean(name="indiSiteDataEntryService")
@ViewScoped
public class IndiSiteDataEntryService implements Serializable {
	
	private static final Logger log = LogManager.getLogger(IndiSiteDataEntryService.class);
	
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
	  private int listLimit;
  	  private int listedCount=-1;
  	  private Boolean isEnable;
 	  private int userId;
 	  private String cornerBit;
	  
	  
	  
	  private final Map<String,Map<String,String>> data = new HashMap<>();
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
          log.info("Loading IndiDataEntryService init()");
          dao=new ConnectionDAOImpl();
          primaryModel=dao.getPrimaryLocation();
          uDao=new UserDAOImpl();
          ur=new UserRoleService();
          comm=new CommonDAOImpl();
          
          
          primLocation  = new HashMap<>(); 
          for(Map.Entry<String, String> pp:primaryModel.entrySet())
          {
        	  log.info("Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
        	  
        	  primLocation.put(pp.getValue(), pp.getValue());
        	  
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
	    		    			 isEnable = Boolean.valueOf(session.getAttribute("isEnable").toString());
	    		    			 
	    		    			  log.info("listedCount  listLimit :"+listedCount+"  <=  "+listLimit);
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
	  
	 	  	
	  
	  public void onCountryChange() {  
			  if(country !=null && !country.equals("")) 
	          {
				  secondryLocation=dao.getSecondryLocation(country);
				  Collections.sort(secondryLocation);
	          }
          } 
	  
	  
	  
	  
		public void upload() {
	           if (file != null) {
	            try {
	               
	                 log.info("Selected county and city ---------->:"+country+"     "+city);
	  	             //dao=new ConnectionDAOImpl();
	  	          
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
	  	          indiSiteDataEntryModel.setCornerBit(cornerBit);

					System.out.println("Service Upload image size :" + filesx.getFiles().size());
					List<InputStream> inputStreams = new ArrayList<>();
					List<UploadedFile> files = new ArrayList<>();
					for (UploadedFile f : filesx.getFiles()) {
						if (f.getSize() > 0) {
							inputStreams.add(f.getInputStream());
							files.add(file);
						}
					}
					indiSiteDataEntryModel.setInputStreams(inputStreams);
					indiSiteDataEntryModel.setFiles(files);
	  	  	        
	  	      HttpSession session = SessionUtils.getSession();
		       	if (session != null)
		    	{
		    		if(session.getAttribute("userId")!=null)
		    		{
		    		    int userId= Integer.parseInt(session.getAttribute("userId").toString());
		    		    if(userId > 0)
		    		    {    	
			              	//updateResult=dao.updateIndiDataEntry(indiSiteDataEntryModel,userId);
			              	
				              	if(ur.getUserRole().contains("Rank"))
	    						{
			    		    		
			    		    			updateResult=dao.updateIndiDataEntry(indiSiteDataEntryModel,userId,comm.getUserRank(userId));
		    		    			
	    						}
			    		    	else
			    		    	{
			    		    		updateResult=dao.updateIndiDataEntry(indiSiteDataEntryModel,userId,0);
			    		    	}
			              	SMTPService.sendIndiEmail(indiSiteDataEntryModel,userId);
		    		    }
		    		}
		    		if(session.getAttribute("userId")==null)
	    		    {    	
	    		    	int defaultUserId=1;
	      		    	updateResult=dao.updateIndiDataEntry(indiSiteDataEntryModel, defaultUserId,0);
	    		    		
	    		    }
		    	}
	  	             
	  	  
	  	              
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
	  	          this.comment="";
	  	          this.facing="";
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
          this.comment="";
          this.facing="";
          this.country="";
	      this.city="";
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
