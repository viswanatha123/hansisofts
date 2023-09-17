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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;



import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.LayoutMode;





@ManagedBean(name="layoutDetailService")
@ViewScoped
public class LayoutDetailService implements Serializable{
	
	private static final Logger log = Logger.getLogger(LayoutDetailService.class.getName());
	
	private final Map<String,Map<String,String>> data = new HashMap<>();
	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 
	private String locationMessage;

	    
	   

	    private List<LayoutMode> layoutdetails;
	    ConnectionDAOImpl dao;
	    LocationDAOImpl locationDao;
	    
	    @PostConstruct 
	    public void init()
	    {
	    	log.log(Level.INFO, "Loading LayoutDetailService init()");
	          dao=new ConnectionDAOImpl();
	          locationDao=new LocationDAOImpl();
	          primaryModel=locationDao.getLayoutPrimaryLocation();
	          
	          primLocation  = new HashMap<>(); 
            for(Map.Entry<String, String> pp:primaryModel.entrySet())
            {
          	  log.log(Level.INFO, "Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());
          	  
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
	        	
	        	
	        	
	        	
	        /*	
	        FacesMessage msg;  
	        if(city != null && country != null)  
	        msg = new FacesMessage("Selected", city + " of " + country);  
	        else  
	        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");   
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	        */
	        System.out.println(country+"     "+city);
	        
	        locationMessage=country+" ,   "+city;
	        
	        //dao=new ConnectionDAOImpl();
	        layoutdetails=dao.getLayoutDetails(country,city);
	                
	                for(LayoutMode x:layoutdetails)
	                {
	                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getName());
	                }
	                
	              
	              
	                
	                // updated image in database
	               
	               /*
	                try
	                {
	                	
	                	 Connection con = null;
	                     PreparedStatement pstmt = null;
	                     con=ConnectionDAO.getConnection();
	                	
	                	File file = new File("D:/img/default1.jpg");
	                	FileInputStream fis = new FileInputStream(file);
	                	PreparedStatement ps = con.prepareStatement("INSERT INTO hansi_property_image VALUES (?,?,?)");
	                	ps.setInt(1, 101);
	                	ps.setString(2, "Default Image");
	                	ps.setBinaryStream(3, fis, (int)file.length());
	                	ps.executeUpdate();
	                	ps.close();
	                	fis.close();


	                	
	                }catch(Exception e)
	                {
	                	System.out.println("Error message : "+e.getMessage());
	                }
	                */
	                
	                
	        
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
	   
	                
	
	    

}
