package com.DIC.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.DIC.model.Photo;
import com.vaadin.server.Responsive;


@ManagedBean(name="imagesViewService")
@RequestScoped
public class ImagesViewService implements Serializable {
	
	
	private List<String> images;
	private List<String> building;
	private List<String> siteMapImage;
	
	private List<Photo> photos;
	private int activeIndex = 0;

	
	@PostConstruct
    public void init() {
		
		
        images = new ArrayList<String>();
        for (int i = 1; i <= 29; i++) {
            images.add(i + ".jpg");
        }
        
        
        building = new ArrayList<String>();
        for (int i = 1; i <= 18; i++) {
        	building.add(i + ".jpg");
        }
        
        
        siteMapImage = new ArrayList<String>();
        for (int i = 10; i <= 16; i++) {
        	siteMapImage.add(i + ".jpg");
        }
        
        
       
        
        photos = new ArrayList<>();
        
        photos.add(new Photo("10.jpg", "10.jpg", "Description for Image 1", "Title 1"));
        photos.add(new Photo("11.jpg", "11.jpg", "Description for Image 2", "Title 2"));
        photos.add(new Photo("12.jpg", "12.jpg", "Description for Image 3", "Title 3"));
        photos.add(new Photo("13.jpg", "13.jpg", "Description for Image 4", "Title 4"));
        
        System.out.println("Photos :"+photos.size());
        
     
    }
	

	
	public void changeActiveIndex() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.activeIndex = Integer.valueOf(params.get("index"));
    }

    public List<String> getImages() {
        return images;
    }
    
    public List<String> getBuilding() {
		return building;
	}

	public void setBuilding(List<String> building) {
		this.building = building;
	}
	
	public List<String> getSiteMapImage() {
		return siteMapImage;
	}

	public void setSiteMapImage(List<String> siteMapImage) {
		this.siteMapImage = siteMapImage;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	
	

}
