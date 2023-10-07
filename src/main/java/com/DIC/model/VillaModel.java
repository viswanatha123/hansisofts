package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;


import org.primefaces.model.file.UploadedFile;

public class VillaModel implements Serializable{
	
	
	private String i_am;
	private String owner_name;
	private String contact_owner;
	private String email;
	private String property_type;
	private String  address;
	private int road_width;
	private int floors;
	private int bed_rooms;
	private int bath_rooms;
	private String furnished;
	private int plot_area;
	private int s_build_are;
	private String pro_avail;
	private Date avail_date;
	private int persqft;
	private String prim_location;
	private String seco_location;
	private int total_feets;
	private int cost;
	private Date create_date;
	private int is_active;
	
	private InputStream inputStream;
    private UploadedFile file;
	
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
	public Date getCreate_date() {
		return create_date;
	}
	public int getIs_active() {
		return is_active;
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
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public Date getAvail_date() {
		return avail_date;
	}
	public void setAvail_date(Date avail_date) {
		this.avail_date = avail_date;
	}

	
	
	
}
