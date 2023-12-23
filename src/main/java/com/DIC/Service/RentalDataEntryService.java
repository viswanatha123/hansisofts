package com.DIC.Service;

import java.io.InputStream;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.RentalDataEntryModel;


@ManagedBean(name="rentalDataEntryService")
@ViewScoped
public class RentalDataEntryService implements Serializable {

	private static final Logger log = Logger.getLogger(RentalDataEntryService.class.getName());
	
	private String ownername;
	private String address;
	private String ownContNum; 
	private String propType;
	private int totalBedRooms;
	private int totalFloors;
	private int totalBathRomms;
	private String furniture;
	private String rentPrefered;
	private int securityDeposit;
	private int monthlyRent;
	private String kitchenRoom;
	private String facing;
	private int totAreaSqft;
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
          log.log(Level.INFO, "RentalDataEntryService init()");
       
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
 	          	RentalDataEntryModel rentalDataModel=new RentalDataEntryModel();
			 	rentalDataModel.setOwnername(ownername);
			 	rentalDataModel.setAddress(address);
			 	rentalDataModel.setOwnContNum(ownContNum);
			 	rentalDataModel.setPropType(propType);
			 	rentalDataModel.setTotalBedRooms(totalBedRooms);
			 	rentalDataModel.setTotalFloors(totalFloors);
			 	rentalDataModel.setTotalBathRomms(totalBathRomms);
			 	rentalDataModel.setFurniture(furniture);
			 	rentalDataModel.setRentPrefered(rentPrefered);
			 	rentalDataModel.setSecurityDeposit(securityDeposit);
			 	rentalDataModel.setMonthlyRent(monthlyRent);
			 	rentalDataModel.setKitchenRoom(kitchenRoom);
			 	rentalDataModel.setFacing(facing);
			 	rentalDataModel.setTotAreaSqft(totAreaSqft);
			 	rentalDataModel.setPrimLocation(country);
			 	rentalDataModel.setSecoLocation(city);
			 	rentalDataModel.setFile(file);
			 	rentalDataModel.setInputStream(file.getInputStream());
			 	
 	        
 	          
 	       
 	          
 	         updateResult=dao.updateRentalDataEntry(rentalDataModel);
 	             
 	          this.ownername="";
 	          this.address="";
 	          this.ownContNum="";
 	          this.propType="";
 	          this.totalBedRooms=0;
 	          this.totalFloors=0;
 	          this.totalBathRomms=0;
 	          this.furniture="";
 	          this.rentPrefered="";
 	          this.securityDeposit=0;
 	          this.monthlyRent=0;
 	          this.kitchenRoom="";
 	          this.facing="";
 	          this.totAreaSqft=0;
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
		  
		  log.log(Level.INFO, "Primary value ---------->:"+country);
			  if(country !=null && !country.equals("")) 
		       {
					  secondryLocation=dao.getSecondryLocation(country);
					  Collections.sort(secondryLocation);
					  
					  
		       }
	        
          }  

	  
	  
      
      public void clear()
      {
    	  this.ownername="";
	          this.address="";
	          this.ownContNum="";
	          this.propType="";
	          this.totalBedRooms=0;
	          this.totalFloors=0;
	          this.totalBathRomms=0;
	          this.furniture="";
	          this.rentPrefered="";
	          this.securityDeposit=0;
	          this.monthlyRent=0;
	          this.kitchenRoom="";
	          this.facing="";
	          this.totAreaSqft=0;
	          this.country="";
		      this.city="";
          
          this.updateResult="";
          System.out.println("****** Clicked on Clear button*****");
      }
      
	  
      
  	public String getOwnername() {
		return ownername;
	}


	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getOwnContNum() {
		return ownContNum;
	}


	public void setOwnContNum(String ownContNum) {
		this.ownContNum = ownContNum;
	}


	public String getPropType() {
		return propType;
	}


	public void setPropType(String propType) {
		this.propType = propType;
	}


	public int getTotalBedRooms() {
		return totalBedRooms;
	}


	public void setTotalBedRooms(int totalBedRooms) {
		this.totalBedRooms = totalBedRooms;
	}


	public int getTotalFloors() {
		return totalFloors;
	}


	public void setTotalFloors(int totalFloors) {
		this.totalFloors = totalFloors;
	}


	public int getTotalBathRomms() {
		return totalBathRomms;
	}


	public void setTotalBathRomms(int totalBathRomms) {
		this.totalBathRomms = totalBathRomms;
	}


	public String getFurniture() {
		return furniture;
	}


	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}


	public String getRentPrefered() {
		return rentPrefered;
	}


	public void setRentPrefered(String rentPrefered) {
		this.rentPrefered = rentPrefered;
	}


	public int getSecurityDeposit() {
		return securityDeposit;
	}


	public void setSecurityDeposit(int securityDeposit) {
		this.securityDeposit = securityDeposit;
	}


	public int getMonthlyRent() {
		return monthlyRent;
	}


	public void setMonthlyRent(int monthlyRent) {
		this.monthlyRent = monthlyRent;
	}


	public String getKitchenRoom() {
		return kitchenRoom;
	}


	public void setKitchenRoom(String kitchenRoom) {
		this.kitchenRoom = kitchenRoom;
	}


	public String getFacing() {
		return facing;
	}


	public void setFacing(String facing) {
		this.facing = facing;
	}


	public int getTotAreaSqft() {
		return totAreaSqft;
	}


	public void setTotAreaSqft(int totAreaSqft) {
		this.totAreaSqft = totAreaSqft;
	}


	public UploadedFile getFile() {
		return file;
	}


	public void setFile(UploadedFile file) {
		this.file = file;
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


	public ConnectionDAOImpl getDao() {
		return dao;
	}


	public void setDao(ConnectionDAOImpl dao) {
		this.dao = dao;
	}


	public static Logger getLog() {
		return log;
	}

	  
}
