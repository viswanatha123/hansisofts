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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.LayoutMode;
import com.DIC.model.RentalDataEntryModel;
import com.DIC.model.VillaModel;


@ManagedBean(name="RentalDetailsService")
@ViewScoped
public class RentalDetailsService implements Serializable{
	private static final Logger log = Logger.getLogger(VillaDetailsService.class.getName());

	private String country;   
	private String city;    
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation; 
	private String locationMessage;
	private String proType;
	private String errorMessage;
	
	
    private List<RentalDataEntryModel> rentalDataEntryModel;
   



	ConnectionDAOImpl dao;
    LocationDAOImpl locationDao;
    
    
    
    @PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading RentalDetailsService init()");
    	dao=new ConnectionDAOImpl();
          locationDao=new LocationDAOImpl();
          primaryModel=locationDao.getRentalPrimaryLocation();
          
          
          
          primLocation  = new HashMap<>(); 
        for(Map.Entry<String, String> pp:primaryModel.entrySet())
        {
      	  log.log(Level.INFO, "Rental Primar location details ---------->:"+pp.getKey()+"   "+pp.getValue());
      	  
      	  primLocation.put(pp.getKey(), pp.getValue());
      	  
        }
        primLocationSort=new TreeMap<>(primLocation);
        
        
               
    }
    
    

    public void onCountryChange() {  
    	if(country !=null && !country.equals("")) 
          {
		     secondryLocation=locationDao.getRentalSecondryLocation(country);
			  
			  Collections.sort(secondryLocation);
          }
    }
    
    
    
    public void getRentalDetails() {  
    	
    	System.out.println(" **** submited button ******");
  	        
        locationMessage=country+" ,   "+city;
        rentalDataEntryModel=dao.getRentalDetails(country,city,proType);
                 
                for(RentalDataEntryModel x:rentalDataEntryModel)
                {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getOwn_name());
                }
                if(rentalDataEntryModel.size() == 0)
        		{
        			errorMessage="There are no rented records on "+proType;
        		}
                else {
                	errorMessage="";
                }
	             
               
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



	public Map<String, String> getPrimaryModel() {
		return primaryModel;
	}



	public void setPrimaryModel(Map<String, String> primaryModel) {
		this.primaryModel = primaryModel;
	}



	public Map<String, String> getPrimLocation() {
		return primLocation;
	}



	public void setPrimLocation(Map<String, String> primLocation) {
		this.primLocation = primLocation;
	}



	public TreeMap<String, String> getPrimLocationSort() {
		return primLocationSort;
	}



	public void setPrimLocationSort(TreeMap<String, String> primLocationSort) {
		this.primLocationSort = primLocationSort;
	}



	public List<String> getSecondryLocation() {
		return secondryLocation;
	}



	public void setSecondryLocation(List<String> secondryLocation) {
		this.secondryLocation = secondryLocation;
	}



	public String getLocationMessage() {
		return locationMessage;
	}



	public void setLocationMessage(String locationMessage) {
		this.locationMessage = locationMessage;
	}



	public String getProType() {
		return proType;
	}



	public void setProType(String proType) {
		this.proType = proType;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	 public List<RentalDataEntryModel> getRentalDataEntryModel() {
			return rentalDataEntryModel;
		}



		public void setRentalDataEntryModel(List<RentalDataEntryModel> rentalDataEntryModel) {
			this.rentalDataEntryModel = rentalDataEntryModel;
		}



	public ConnectionDAOImpl getDao() {
		return dao;
	}



	public void setDao(ConnectionDAOImpl dao) {
		this.dao = dao;
	}



	public LocationDAOImpl getLocationDao() {
		return locationDao;
	}



	public void setLocationDao(LocationDAOImpl locationDao) {
		this.locationDao = locationDao;
	}



	public static Logger getLog() {
		return log;
	}




}