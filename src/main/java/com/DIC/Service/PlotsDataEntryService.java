package com.DIC.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.CommonDAOImpl;
import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
//import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;
import com.DIC.model.LayoutMode;
import com.DIC.model.PlotsDataEntryModel;

import SMTPService.SMTPService;
import framework.utilities.SessionUtils;
import framework.utilities.Utilities;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;





import org.primefaces.util.EscapeUtils;

@ManagedBean(name="plotsDataEntryService")
//@SessionScoped
@ViewScoped
public class PlotsDataEntryService implements Serializable{
	
	private static final Logger log = LogManager.getLogger(PlotsDataEntryService.class);
	  
	  private String name;
	  private String location;
	  private int persqft;
	  private int plotarea;
	  private String contactOwner;
	  private String ownerName;
	  private String wonership;
	  private String transaction;
	  private String comment;
	  private int length;
	  private int width;
	  private String updateResult;
	  private String swimingPool;
	  private String playground;
	  private String park;
	  private String wall;
	  private String community;
	  private String facing;
      private String agentName;
  	  private UploadedFile file;
  	  private int listLimit;
  	  private int listedCount=-1;
  	  private Boolean isEnable;
  	  private int userId;
  	  private String cornerBit;

      
  	  private String country;   
	  private String city;  
	  private Map<String, String> primaryModel;
	  private Map<String,String> primLocation; 
	  private TreeMap<String, String> primLocationSort;
	  private List<String> secondryLocation;
	  	  
	  
	   ConnectionDAOImpl dao;
	   UserDAOImpl uDao;
	   UserRoleService ur;
	   CommonDAOImpl comm;
	   
	   
	   public PlotsDataEntryService()
	   {
	   }
	   
	   @PostConstruct 
	   public void init()
	      {
	          log.info("Loading PlotsDataEntryService init()");
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
			    		    			 isEnable = Boolean.valueOf(session.getAttribute("isEnable").toString());
			    		    			 
			    		    			 
			    		    			 
			    		    			 listedCount=uDao.getAllPropByUserId(Integer.parseInt(session.getAttribute("userId").toString())).size();
			    		    			  log.info("listedCount  listLimit :"+listedCount+"  <=  "+listLimit+"     "+isEnable);
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
	  	         
	  	          PlotsDataEntryModel plotsDataEntryModel=new PlotsDataEntryModel();
	  	          plotsDataEntryModel.setName(name);
	  	          plotsDataEntryModel.setLocation(location);
	  	          plotsDataEntryModel.setPersqft(persqft);
	  	          plotsDataEntryModel.setContactOwner(contactOwner);
	  	          plotsDataEntryModel.setOwnerName(ownerName);
	  	          plotsDataEntryModel.setWonership(wonership);
	  	          plotsDataEntryModel.setTransaction(transaction);
	  	          plotsDataEntryModel.setComment(comment);
	  	          plotsDataEntryModel.setLength(length);
	  	          plotsDataEntryModel.setWidth(width);
	  	       
	  	          plotsDataEntryModel.setPrimLocation(country);
	  	          plotsDataEntryModel.setSecoLocation(city);
	  	          plotsDataEntryModel.setSwimingPool(swimingPool);
	  	          plotsDataEntryModel.setPlayground(playground);
	  	          plotsDataEntryModel.setPark(park);
	  	          plotsDataEntryModel.setWall(wall);
	  	          plotsDataEntryModel.setCommunity(community);
	  	          plotsDataEntryModel.setFacing(facing);
	  	          plotsDataEntryModel.setAgentName(agentName);
	  	          plotsDataEntryModel.setInputStream(file.getInputStream());
	  	          plotsDataEntryModel.setFile(file);
	  	          plotsDataEntryModel.setCornerBit(cornerBit);
	  	          
	  	    
	  	          
	  	          		HttpSession session = SessionUtils.getSession();
				       	if (session != null)
				    	{
				    		if(session.getAttribute("userId")!=null)
				    		{
				    		    int userId= Integer.parseInt(session.getAttribute("userId").toString());
				    		    if(userId > 0)
				    		    {    	
				          
				    		   		    	if(ur.getUserRole().contains("Rank"))
			        						{
						    		    		updateResult=dao.updatePlotDataEntry(plotsDataEntryModel, userId,comm.getUserRank(userId));
						    				}
						    		    	else
						    		    	{
						    		    		updateResult=dao.updatePlotDataEntry(plotsDataEntryModel, userId,0);
						    		    	}
							    	SMTPService.sendLayoutEmail(plotsDataEntryModel,userId);
				    		    		
				    		    }
				    		    
				    		}
				    		if(session.getAttribute("userId")==null)
			    		    {    	
			    		    	int defaultUserId=1;
			    		    	updateResult=dao.updatePlotDataEntry(plotsDataEntryModel, defaultUserId,0);
			    		    		
			    		    }
				    	}
	  	          
	  	          
	  	     
	  	         
	  	          this.name="";
	  	          this.location="";
	  	          this.persqft=0;
	  	          this.plotarea=0;
	  	          this.contactOwner="";
	  	          this.ownerName="";
	  	          this.wonership="";
	  	          this.transaction="";
	  	          this.comment="";
	  	          this.length=0;
	  	          this.width=0;
	  	          this.country="";
	  	          this.city="";
	  	          this.swimingPool="";
	  	          this.playground="";
	  	          this.park="";
	  	          this.wall="";
	  	          this.community="";
	  	          this.agentName="";
	  	          this.facing="";
	  	        
	  	  
	                
	 
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
	          {
				  secondryLocation=dao.getSecondryLocation(country);
				  Collections.sort(secondryLocation);
	          }
	          	
	     }
	    
	      
	      public void clear()
	      {
	          this.name="";
	          this.location="";
	          this.persqft=0;
	          this.plotarea=0;
	          this.contactOwner="";
	          this.ownerName="";
	          this.wonership="";
	          this.transaction="";
	          this.comment="";
	          this.length=0;
	          this.width=0;
	          this.agentName="";
	          this.country="";
  	          this.city="";
  	          this.swimingPool="";
	          this.playground="";
	          this.park="";
	          this.wall="";
	          this.community="";
  	          this.facing="";
	          
	          
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

		      public String getLocation() {
		          return location;
		      }

		      public void setLocation(String location) {
		          this.location = location;
		      }

		       public int getPlotarea() {
		          return plotarea;
		      }

		      public void setPlotarea(int plotarea) {
		          this.plotarea = plotarea;
		      }

		      public String getContactOwner() {
		          return contactOwner;
		      }

		      public void setContactOwner(String contactOwner) {
		          this.contactOwner = contactOwner;
		      }

		      public String getOwnerName() {
		          return ownerName;
		      }

		      public void setOwnerName(String ownerName) {
		          this.ownerName = ownerName;
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

		      public String getComment() {
		          return comment;
		      }

		      public void setComment(String comment) {
		          this.comment = comment;
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
	  
		    
		      
		      public String getName() {
		          return name;
		      }

		 
		      public void setName(String name) {
		          this.name = name;
		      }
		      
		          public String getUpdateResult() {
		          return updateResult;
		      }

		      public void setUpdateResult(String updateResult) {
		          this.updateResult = updateResult;
		      }
		      
		      public String getSwimingPool() {
		          return swimingPool;
		      }

		      public void setSwimingPool(String swimingPool) {
		          this.swimingPool = swimingPool;
		      }
		      
		        public String getPlayground() {
		          return playground;
		      }

		      public void setPlayground(String playground) {
		          this.playground = playground;
		      }
		      
		         public String getPark() {
		          return park;
		      }

		      public void setPark(String park) {
		          this.park = park;
		      }
		      
		         public String getWall() {
		          return wall;
		      }

		      public void setWall(String wall) {
		          this.wall = wall;
		      }
		      
		        public String getCommunity() {
		          return community;
		      }

		      public void setCommunity(String community) {
		          this.community = community;
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
			
			public int getPersqft() {
				return persqft;
			}

			public void setPersqft(int persqft) {
				this.persqft = persqft;
			}
			
			 public Map<String, String> getPrimaryModel() {
					return primaryModel;
			}

			public void setPrimaryModel(Map<String, String> primaryModel) {
					this.primaryModel = primaryModel;
			}
			
			
			public Map<String, String> getPrimLocation() {
				return primLocation;
			}

			public List<String> getSecondryLocation() {
				return secondryLocation;
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

			
	      

}
