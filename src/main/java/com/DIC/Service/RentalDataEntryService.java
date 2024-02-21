package com.DIC.Service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
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
	



	private String own_name;
	private String address;
	private String own_con_no; 
	private String pro_type;
	private int tot_bed_rooms;
	private int tot_floors;
	private int tot_bath_rooms;
	private String furniture;
	private String rent_pref;
	private int sec_depo;
	private int mon_rent;
	private String kitc_room;
	private String facing;
	private int tot_area_sqft;
    private UploadedFile file;
    private String updateResult;
    private Date avail_date=new Date();
    
  

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
			 	rentalDataModel.setOwn_name(own_name);
			 	rentalDataModel.setAddress(address);
			 	rentalDataModel.setOwn_con_no(own_con_no);
			 	rentalDataModel.setPro_type(pro_type);
			 	rentalDataModel.setTot_bed_rooms(tot_bed_rooms);
			 	rentalDataModel.setTot_floors(tot_floors);
			 	rentalDataModel.setTot_bath_rooms(tot_bath_rooms);
			 	rentalDataModel.setFurniture(furniture);
			 	rentalDataModel.setRent_pref(rent_pref);
			 	rentalDataModel.setSec_depo(sec_depo);
			 	rentalDataModel.setMon_rent(mon_rent);
			 	rentalDataModel.setKitc_room(kitc_room);
			 	rentalDataModel.setFacing(facing);
			 	rentalDataModel.setTot_area_sqft(tot_area_sqft);
			 	rentalDataModel.setPrim_location(country);
			 	rentalDataModel.setSeco_location(city);
			 	rentalDataModel.setFile(file);
			 	rentalDataModel.setInputStream(file.getInputStream());
			 	rentalDataModel.setAvail_date(avail_date);
			 	
 	        
 	          
 	       
 	          
 	         updateResult=dao.updateRentalDataEntry(rentalDataModel);
 	             
 	          this.own_name="";
 	          this.address="";
 	          this.own_con_no="";
 	          this.pro_type="";
 	          this.tot_bed_rooms=0;
 	          this.tot_floors=0;
 	          this.tot_bath_rooms=0;
 	          this.furniture="";
 	          this.rent_pref="";
 	          this.sec_depo=0;
 	          this.mon_rent=0;
 	          this.kitc_room="";
 	          this.facing="";
 	          this.tot_area_sqft=0;
 	          this.country="";
 		      this.city="";
 		      this.avail_date=new Date();
 	          
          
 	          
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
    	  this.own_name="";
	          this.address="";
	          this.own_con_no="";
	          this.pro_type="";
	          this.tot_bed_rooms=0;
	          this.tot_floors=0;
	          this.tot_bath_rooms=0;
	          this.furniture="";
	          this.rent_pref="";
	          this.sec_depo=0;
	          this.mon_rent=0;
	          this.kitc_room="";
	          this.facing="";
	          this.tot_area_sqft=0;
	          this.country="";
		      this.city="";
		      this.avail_date=new Date();
          
          this.updateResult="";
          System.out.println("****** Clicked on Clear button*****");
      }
      
	 
      
  	public String getOwn_name() {
		return own_name;
	}


	public void setOwn_name(String own_name) {
		this.own_name = own_name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getOwn_con_no() {
		return own_con_no;
	}


	public void setOwn_con_no(String own_con_no) {
		this.own_con_no = own_con_no;
	}


	public String getPro_type() {
		return pro_type;
	}


	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}


	public int getTot_bed_rooms() {
		return tot_bed_rooms;
	}


	public void setTot_bed_rooms(int tot_bed_rooms) {
		this.tot_bed_rooms = tot_bed_rooms;
	}


	public int getTot_floors() {
		return tot_floors;
	}


	public void setTot_floors(int tot_floors) {
		this.tot_floors = tot_floors;
	}


	public int getTot_bath_rooms() {
		return tot_bath_rooms;
	}


	public void setTot_bath_rooms(int tot_bath_rooms) {
		this.tot_bath_rooms = tot_bath_rooms;
	}


	public String getFurniture() {
		return furniture;
	}


	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}


	public String getRent_pref() {
		return rent_pref;
	}


	public void setRent_pref(String rent_pref) {
		this.rent_pref = rent_pref;
	}


	public int getSec_depo() {
		return sec_depo;
	}


	public void setSec_depo(int sec_depo) {
		this.sec_depo = sec_depo;
	}


	public int getMon_rent() {
		return mon_rent;
	}


	public void setMon_rent(int mon_rent) {
		this.mon_rent = mon_rent;
	}


	public String getKitc_room() {
		return kitc_room;
	}


	public void setKitc_room(String kitc_room) {
		this.kitc_room = kitc_room;
	}


	public String getFacing() {
		return facing;
	}


	public void setFacing(String facing) {
		this.facing = facing;
	}


	public int getTot_area_sqft() {
		return tot_area_sqft;
	}


	public void setTot_area_sqft(int tot_area_sqft) {
		this.tot_area_sqft = tot_area_sqft;
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

    public Date getAvail_date() {
		return avail_date;
	}


	public void setAvail_date(Date avail_date) {
		this.avail_date = avail_date;
	}

	
	

	  
}
