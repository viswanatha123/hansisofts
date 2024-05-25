package com.DIC.model;

import java.io.Serializable;

public class PackPriceModel implements Serializable{
	
	private int packId;
	private String packName;
	private int packType;
	private int listLimit;
	private int cost;
	private int duration;
	
	
	public int getPackId() {
		return packId;
	}
	public String getPackName() {
		return packName;
	}
	public int getPackType() {
		return packType;
	}
	public int getListLimit() {
		return listLimit;
	}
	public int getCost() {
		return cost;
	}
	public int getDuration() {
		return duration;
	}
	public void setPackId(int packId) {
		this.packId = packId;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public void setPackType(int packType) {
		this.packType = packType;
	}
	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	

}
