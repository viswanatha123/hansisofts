package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

public class RentalDataEntryModel implements Serializable {
	
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
	private String prim_location;
	private String seco_location;
	private InputStream inputStream;
    private UploadedFile file;
    private Date create_date;
	private int is_active;
	private StreamedContent streamedContent;
	private Date avail_date;
	
	
	
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
	public String getPrim_location() {
		return prim_location;
	}
	public void setPrim_location(String prim_location) {
		this.prim_location = prim_location;
	}
	public String getSeco_location() {
		return seco_location;
	}
	public void setSeco_location(String seco_location) {
		this.seco_location = seco_location;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public StreamedContent getStreamedContent() {
		return streamedContent;
	}
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public Date getAvail_date() {
		return avail_date;
	}
	public void setAvail_date(Date avail_date) {
		this.avail_date = avail_date;
	}
    

    
    
}
