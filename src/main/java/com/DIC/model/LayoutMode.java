package com.DIC.model;

import java.awt.Image;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class LayoutMode implements Serializable{
	
	private String name;
	private String location;
	private int persqft;
	private int cost;
	private int plotarea;
	private String contactOwner;
	private String ownerName;
	private String wonership;
	private int is_active;
	private String dimensiond;
	private String transaction;
	private String comment;
	private int length;
	private int width;
	private String primLocation;
	private String secoLocation;
	private String swimingPool;
	private String playground;
	private String park;
	private String wall;
	private String community;
	private String facing;
	private String agentName;
	private String costFirst;
	private String totalPrice;
	private StreamedContent streamedContent;
	private Date createdOnDate;
	
	
	
	public Date getCreatedOnDate() {
		return createdOnDate;
	}

	public void setCreatedOnDate(Date createdOnDate) {
		this.createdOnDate = createdOnDate;
	}

		
	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	
		

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}


		public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getLocation() {
	        return location;
	    }

	    public void setLocation(String location) {
	        this.location = location;
	    }

	    public int getPersqft() {
	        return persqft;
	    }

	    public void setPersqft(int persqft) {
	        this.persqft = persqft;
	    }

	    public int getCost() {
	        return cost;
	    }

	    public void setCost(int cost) {
	        this.cost = cost;
	    }

	   public int getPlotarea() {
	        return plotarea;
	    }

	    public void setPlotarea(int plotarea) {
	        this.plotarea = plotarea;
	    }

	    public String getContactOwner() {
	        return contactOwner;
	    }

	    public void setContactOwner(String contactOwner) {
	        this.contactOwner = contactOwner;
	    }

	    public String getOwnerName() {
	        return ownerName;
	    }

	    public void setOwnerName(String ownerName) {
	        this.ownerName = ownerName;
	    }

	    public String getWonership() {
	        return wonership;
	    }

	    public void setWonership(String wonership) {
	        this.wonership = wonership;
	    }

	    public int getIs_active() {
	        return is_active;
	    }

	    public void setIs_active(int is_active) {
	        this.is_active = is_active;
	    }

	    public String getDimensiond() {
	        return dimensiond;
	    }

	    public void setDimensiond(String dimensiond) {
	        this.dimensiond = dimensiond;
	    }

	    public String getTransaction() {
	        return transaction;
	    }

	    public void setTransaction(String transaction) {
	        this.transaction = transaction;
	    }

	    public String getComment() {
	        return comment;
	    }

	    public void setComment(String comment) {
	        this.comment = comment;
	    }
	    
	    
	    
	    public int getLength() {
	        return length;
	    }

	    public void setLength(int length) {
	        this.length = length;
	    }

	    public int getWidth() {
	        return width;
	    }

	    public void setWidth(int width) {
	        this.width = width;
	    }
	    
	    public String getPrimLocation() {
	        return primLocation;
	    }

	    public void setPrimLocation(String primLocation) {
	        this.primLocation = primLocation;
	    }

	    public String getSecoLocation() {
	        return secoLocation;
	    }

	    public void setSecoLocation(String secoLocation) {
	        this.secoLocation = secoLocation;
	    }
	    
	   	    
	    public String getSwimingPool() {
	        return swimingPool;
	    }

	    public void setSwimingPool(String swimingPool) {
	        this.swimingPool = swimingPool;
	    }
	    
	    public String getPlayground() {
	        return playground;
	    }

	    public void setPlayground(String playground) {
	        this.playground = playground;
	    }
	    
	       public String getPark() {
	        return park;
	    }

	    public void setPark(String park) {
	        this.park = park;
	    }
	    
	        public String getWall() {
	        return wall;
	    }

	    public void setWall(String wall) {
	        this.wall = wall;
	    }
	    
	        public String getCommunity() {
	        return community;
	    }

	    public void setCommunity(String community) {
	        this.community = community;
	    }
	    
	    public String getFacing() {
			return facing;
		}

		public void setFacing(String facing) {
			this.facing = facing;
		}
		
		public String getAgentName() {
			return agentName;
		}

		public void setAgentName(String agentName) {
			this.agentName = agentName;
		}
		
	    

}
