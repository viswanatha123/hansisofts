package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.file.UploadedFile;

public class RentalDataEntryModel implements Serializable {
	
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
	private String primLocation;
	private String SecoLocation;
	private InputStream inputStream;
    private UploadedFile file;
    
    
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
	public String getPrimLocation() {
		return primLocation;
	}
	public void setPrimLocation(String primLocation) {
		this.primLocation = primLocation;
	}
	public String getSecoLocation() {
		return SecoLocation;
	}
	public void setSecoLocation(String secoLocation) {
		SecoLocation = secoLocation;
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
    
    
    
    
}
