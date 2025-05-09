package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

public class PromoImageModel implements Serializable{
	
	private int promoId;
	private StreamedContent streamedContent;
	private Date createDate;
	private int is_active;
	private String comment;
	private String primLocation;
	private String defaultImage;
	
	 private InputStream inputStream;
	 private UploadedFile file;
     private String imageName;
     private int displayOrder;


    
	
	
	public int getPromoId() {
		return promoId;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public int getIs_active() {
		return is_active;
	}
	public String getComment() {
		return comment;
	}
	public void setPromoId(int promoId) {
		this.promoId = promoId;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public UploadedFile getFile() {
		return file;
	}

	public String getImageName() {
		return imageName;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public String getPrimLocation() {
		return primLocation;
	}

	public void setPrimLocation(String primLocation) {
		this.primLocation = primLocation;
	}
}
