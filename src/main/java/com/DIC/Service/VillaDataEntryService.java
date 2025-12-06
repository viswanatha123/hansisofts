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

import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.Impl.CommonDAOImpl;
import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.PlotsDataEntryModel;

import com.DIC.model.VillaModel;

import SMTPService.SMTPService;
import framework.utilities.SessionUtils;
import org.primefaces.model.file.UploadedFiles;


@ManagedBean(name="villaDataEntryService")
@ViewScoped
public class VillaDataEntryService implements Serializable{
	
	private static final Logger log = LogManager.getLogger(VillaDataEntryService.class);
	
	
	private String i_am="Owner";
	private String owner_name;
	private String contact_owner;
	private String email;
	private String property_type="Villa";
	private String  address;
	private int road_width;
	private int floors;
	private int bed_rooms;
	private int bath_rooms;
	private String furnished;
	private int plot_area;
	private int s_build_are;
	private String pro_avail;
	//private String avail_date;
	private Date avail_date=new Date();
	private int persqft;
	private String prim_location;
	private String seco_location;
	private InputStream inputStream;
    private UploadedFile file;
	private int total_feets;
	private int cost;
	private int listLimit;
	private int listedCount=0;
	private Boolean isEnable;
	private int userId;
	private int floorNum;
	private String facing;
	private String cornerBit;
	private String comment;


	private UploadedFiles filesx;
	
	
	
	private String country;   
	private String city;  
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation; 
	private TreeMap<String, String> primLocationSort;
	private List<String> secondryLocation;
	
	private String updateResult;
	
	 ConnectionDAOImpl dao;
	 GeneralDAOImpl gdao;
	 UserDAOImpl uDao;
	 UserRoleService ur;
	 CommonDAOImpl comm;
	
	public VillaDataEntryService()
	{
		
	}
	
	@PostConstruct 
	   public void init()
	      {
	          log.info("Loading VillaDataEntryService init()");
	          dao=new ConnectionDAOImpl();
	          gdao=new GeneralDAOImpl();
	          primaryModel=dao.getPrimaryLocation();
	          uDao=new UserDAOImpl();
	          ur=new UserRoleService();
	          comm=new CommonDAOImpl();
			  checkDefault();
	  }

	public void checkDefault()
	{
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

	public void upload() {
		checkDefault();
		if (listedCount < listLimit) {
			System.out.println(" True-listedCount : " + listedCount);
			if (file != null) {
				try {
					this.updateResult = "";

					log.info("Selected county and city ---------->:" + country + "     " + city);

					VillaModel villaModel = new VillaModel();

					villaModel.setI_am(i_am);
					villaModel.setOwner_name(owner_name);
					villaModel.setContact_owner(contact_owner);
					villaModel.setEmail(email);
					villaModel.setProperty_type(property_type);
					villaModel.setAddress(address);
					villaModel.setRoad_width(road_width);
					villaModel.setFloors(floors);
					villaModel.setBed_rooms(bed_rooms);
					villaModel.setBath_rooms(bath_rooms);
					villaModel.setFurnished(furnished);
					villaModel.setPlot_area(plot_area);
					villaModel.setS_build_are(s_build_are);
					villaModel.setPro_avail(pro_avail);
					//villaModel.setAvail_date(avail_date);
					villaModel.setAvail_date(avail_date);
					villaModel.setPersqft(persqft);
					villaModel.setPrim_location(country);
					villaModel.setSeco_location(city);
					villaModel.setInputStream(file.getInputStream());
					villaModel.setFile(file);
					villaModel.setFloorNum(floorNum);
					villaModel.setFacing(facing);
					villaModel.setCornerBit(cornerBit);
					villaModel.setComment(comment);


					System.out.println("Service Upload image size :" + filesx.getFiles().size());
					List<InputStream> inputStreams = new ArrayList<>();
					List<UploadedFile> files = new ArrayList<>();
					for (UploadedFile f : filesx.getFiles()) {
						if (f.getSize() > 0) {
							inputStreams.add(f.getInputStream());
							files.add(file);
						}
					}
					villaModel.setInputStreams(inputStreams);
					villaModel.setFiles(files);

					HttpSession session = SessionUtils.getSession();
					if (session != null) {
						if (session.getAttribute("userId") != null) {
							int userId = Integer.parseInt(session.getAttribute("userId").toString());
							if (userId > 0) {
								//updateResult=gdao.updateVillaDataEntry(villaModel,userId);

								if (ur.getUserRole().contains("Rank")) {

									updateResult = gdao.updateVillaDataEntry(villaModel, userId, comm.getUserRank(userId));

								} else {
									updateResult = gdao.updateVillaDataEntry(villaModel, userId, 0);
								}
								SMTPService.sendVillaEmail(villaModel, userId);
							}
						}
						if (session.getAttribute("userId") == null) {
							int defaultUserId = 1;
							updateResult = gdao.updateVillaDataEntry(villaModel, defaultUserId, 0);

						}
					}


					this.i_am = "Owner";
					this.owner_name = "";
					this.contact_owner = "";
					this.email = "";
					this.property_type = "Villa";
					this.address = "";
					this.road_width = 0;
					this.floors = 0;
					this.bed_rooms = 0;
					this.bath_rooms = 0;
					this.furnished = "";
					this.plot_area = 0;
					this.s_build_are = 0;
					this.pro_avail = "";
					this.avail_date = new Date();
					this.persqft = 0;
					this.country = "";
					this.city = "";
					this.comment = "";
					//private InputStream inputStream;
					//private UploadedFile file;
					this.total_feets = 0;
					;
					this.cost = 0;
					this.floorNum = 0;


				} catch (Exception e) {
					System.out.println("Exception-File Upload." + e.getMessage());
				}
			} else {
				FacesMessage msg = new FacesMessage("Please select image!!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
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
 
   
   public void clear()
   {
	    this.i_am="Owner";
    	this.owner_name="";
    	this.contact_owner="";
    	this.email="";
    	this.property_type="Villa";
    	this.address="";
    	this.road_width=0;
    	this.floors=0;
    	this.bed_rooms=1;
     	this.bath_rooms=1;
    	this.furnished="";
    	this.plot_area=0;
    	this.s_build_are=0;
    	this.pro_avail="";
    	this.avail_date=new Date();
    	this.persqft=0;
    	this.country="";
        this.city="";
    	//private InputStream inputStream;
        //private UploadedFile file;
    	this.total_feets=0;;
    	this.cost=0;
	   
       
       this.updateResult="";
       System.out.println("****** Clicked on Clear button*****");
   }
   
	
	
	
	public String getI_am() {
		return i_am;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public String getContact_owner() {
		return contact_owner;
	}
	public String getEmail() {
		return email;
	}
	public String getProperty_type() {
		return property_type;
	}
	public String getAddress() {
		return address;
	}
	public int getRoad_width() {
		return road_width;
	}
	public int getFloors() {
		return floors;
	}
	public int getBed_rooms() {
		return bed_rooms;
	}
	public int getBath_rooms() {
		return bath_rooms;
	}
	public String getFurnished() {
		return furnished;
	}
	public int getPlot_area() {
		return plot_area;
	}
	public int getS_build_are() {
		return s_build_are;
	}
	public String getPro_avail() {
		return pro_avail;
	}
	
	public int getPersqft() {
		return persqft;
	}
	public String getPrim_location() {
		return prim_location;
	}
	public String getSeco_location() {
		return seco_location;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public UploadedFile getFile() {
		return file;
	}
	public int getTotal_feets() {
		return total_feets;
	}
	public int getCost() {
		return cost;
	}

	public void setI_am(String i_am) {
		this.i_am = i_am;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public void setContact_owner(String contact_owner) {
		this.contact_owner = contact_owner;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setRoad_width(int road_width) {
		this.road_width = road_width;
	}
	public void setFloors(int floors) {
		this.floors = floors;
	}
	public void setBed_rooms(int bed_rooms) {
		this.bed_rooms = bed_rooms;
	}
	public void setBath_rooms(int bath_rooms) {
		this.bath_rooms = bath_rooms;
	}
	public void setFurnished(String furnished) {
		this.furnished = furnished;
	}
	public void setPlot_area(int plot_area) {
		this.plot_area = plot_area;
	}
	public void setS_build_are(int s_build_are) {
		this.s_build_are = s_build_are;
	}
	public void setPro_avail(String pro_avail) {
		this.pro_avail = pro_avail;
	}
	
	public void setPersqft(int persqft) {
		this.persqft = persqft;
	}
	public void setPrim_location(String prim_location) {
		this.prim_location = prim_location;
	}
	public void setSeco_location(String seco_location) {
		this.seco_location = seco_location;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public void setTotal_feets(int total_feets) {
		this.total_feets = total_feets;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}


	public String getCountry() {
		return country;
	}
	public String getCity() {
		return city;
	}
	public Map<String, String> getPrimaryModel() {
		return primaryModel;
	}

	public Map<String, String> getPrimLocation() {
		return primLocation;
	}
	public TreeMap<String, String> getPrimLocationSort() {
		return primLocationSort;
	}
	public List<String> getSecondryLocation() {
		return secondryLocation;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setPrimaryModel(Map<String, String> primaryModel) {
		this.primaryModel = primaryModel;
	}

	public void setPrimLocation(Map<String, String> primLocation) {
		this.primLocation = primLocation;
	}

	public void setPrimLocationSort(TreeMap<String, String> primLocationSort) {
		this.primLocationSort = primLocationSort;
	}

	public void setSecondryLocation(List<String> secondryLocation) {
		this.secondryLocation = secondryLocation;
	}

	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}

	public Date getAvail_date() {
		return avail_date;
	}

	public void setAvail_date(Date avail_date) {
		this.avail_date = avail_date;
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

	public int getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(int floorNum) {
		this.floorNum = floorNum;
	}

	public String getCornerBit() {
		return cornerBit;
	}

	public void setCornerBit(String cornerBit) {
		this.cornerBit = cornerBit;
	}
	
	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public UploadedFiles getFilesx() {
		return filesx;
	}

	public void setFilesx(UploadedFiles filesx) {
		this.filesx = filesx;
	}
}
