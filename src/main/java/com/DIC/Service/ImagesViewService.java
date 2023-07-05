package com.DIC.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;


@ManagedBean(name="imagesViewService")
@RequestScoped
public class ImagesViewService {
	
	
	private List<String> images;
	private List<String> building;
	private List<String> siteMapImage;
	

    



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
        for (int i = 1; i <= 5; i++) {
        	siteMapImage.add(i + ".jpg");
        }
        
        
        
        
        
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

}
