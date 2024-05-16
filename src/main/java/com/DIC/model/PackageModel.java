package com.DIC.model;

import java.io.Serializable;

public class PackageModel implements Serializable{
	
	private int packId;
	private String PackName;
	private int packType;
	private int listLimit;
	private int packCost;
	private int packDuration;
	private Boolean isEnable;
	
	public int getPackId() {
		return packId;
	}
	public String getPackName() {
		return PackName;
	}
	public int getPackType() {
		return packType;
	}
	public int getListLimit() {
		return listLimit;
	}
	public int getPackCost() {
		return packCost;
	}
	public int getPackDuration() {
		return packDuration;
	}

	public void setPackId(int packId) {
		this.packId = packId;
	}
	public void setPackName(String packName) {
		PackName = packName;
	}
	public void setPackType(int packType) {
		this.packType = packType;
	}
	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}
	public void setPackCost(int packCost) {
		this.packCost = packCost;
	}
	public void setPackDuration(int packDuration) {
		this.packDuration = packDuration;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
	
	

}
