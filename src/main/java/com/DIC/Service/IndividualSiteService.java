package com.DIC.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.AgriculturalModel;
import com.DIC.model.IndividualSiteModel;


@ManagedBean(name="individualSiteService")
@ViewScoped
public class IndividualSiteService implements Serializable {

	
	private static final Logger log = Logger.getLogger(IndividualSiteService.class.getName());
	
	private final Map<String,Map<String,String>> data = new HashMap<>();
	private String country;   
	private String city;    
	private Map<String,String> countries;  
	private Map<String,String> cities; 

	 

	private String locationMessage;
	
	private List<IndividualSiteModel> individualSiteList;
	

	ConnectionDAOImpl dao; 
	
	
	@PostConstruct 
    public void init()
    {
        countries  = new HashMap<>();  
                  
                    countries.put("Anantapur", "Anantapur"); 
                    countries.put("Kadapa", "Kadapa");
                    countries.put("Kurnool", "Kurnool");
                    countries.put("Tirupati", "Tirupati");
                    countries.put("Chittoor", "Chittoor");
                    countries.put("Bangalore", "Bangalore");
                    countries.put("Ballari", "Ballari");
                    
                    Map<String,String> map = new HashMap<>();  
                   
                    
                    //****Anantapur*****//
                    map = new HashMap<>();  
                    map.put("Kadiri", "Kadiri");  
                    map.put("Kadiri Rural", "Kadiri Rural"); 
                    map.put("Anantapur", "Anantapur");
                    map.put("Anantapur Rural", "Anantapur Rural");
                    map.put("Hindupur", "Hindupur");  
                    map.put("Dharmavaram", "Dharmavaram"); 
                    map.put("Tadipatri", "Tadipatri"); 
                    map.put("Tadipatri Rural", "Tadipatri Rural");
                    map.put("Gooty", "Gooty");
                    map.put("Gooty Rural", "Gooty Rural");
                    data.put("Anantapur", map);
                    
                    map = new HashMap<>();  
                    map.put("Pulivendula", "Pulivendula");
                    map.put("Pulivendula Rural", "Pulivendula Rural");
                    map.put("Kadapa", "Kadapa");
                    map.put("Kadapa Rural", "Kadapa Rural");
                    map.put("Proddatur", "Proddatur");
	                map.put("Proddatur Rural", "Proddatur Rural");
                    data.put("Kadapa", map);
                    
                    map = new HashMap<>();  
	                map.put("Kurnool", "Kurnool");
	                map.put("Kurnool Rural", "Kurnool Rural");
	                data.put("Kurnool", map);
	                
	                map = new HashMap<>();  
	                map.put("Tirupati", "Tirupati");
	                map.put("Tirupati Rural", "Tirupati Rural");
	                data.put("Tirupati", map);
	                  
	                //****Chittoor*****  
	                map = new HashMap<>();  
	                map.put("Chittoor", "Chittoor");
	                map.put("Chittoor Rural", "Chittoor Rural");
	                data.put("Chittoor", map);
                    
                    //****Bangalore*****//
                    map = new HashMap<>();  
                    map.put("Hoskote", "Hoskote");  
                    map.put("TinFactory", "TinFactory");  
                    map.put("K R Puram", "K R Puram"); 
                    map.put("Maratha Halli", "Maratha Halli");
                    map.put("Belathuru", "Belathuru");
                    map.put("Whitefield", "Whitefield");
                    map.put("H Cross", "H Cross");
                    data.put("Bangalore", map);
                    
                    
                  //****Ballari*****
                    map = new HashMap<>();  
                    map.put("Ballari", "Ballari");  
                    map.put("Ballari Rural", "Ballari Rural");  
                    data.put("Ballari", map);
        
        
    }
	
	public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<String, String> countries) {
        this.countries = countries;
    }

    public Map<String, String> getCities() {
        return cities;
    }

    public void setCities(Map<String, String> cities) {
        this.cities = cities;
    }
    
    public List<IndividualSiteModel> getIndividualSiteList() {
		return individualSiteList;
	}

	public void setIndividualSiteList(List<IndividualSiteModel> individualSiteList) {
		this.individualSiteList = individualSiteList;
	}
    
    
    
    public void onCountryChange() {  
        if(country !=null && !country.equals(""))  
        cities = data.get(country);  
        else  
        cities = new HashMap<>();  
        }  


        public void displayLocation() {  
       
        /*
        FacesMessage msg;  
        if(city != null && country != null)  
        msg = new FacesMessage("Selected", city + " of " + country);  
        else  
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");   
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        */
        System.out.println(country+"     "+city);
        
        locationMessage=country+" ,    "+city;
        
        dao=new ConnectionDAOImpl();
        individualSiteList=dao.getIndividualSiteDetails(country,city);
                
                for(IndividualSiteModel x:individualSiteList)
                {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@ :"+x.getOwnerName());
                }
                
        
        }  
        
           public String getLocationMessage() {
        return locationMessage;
    }

    public void setLocationMessage(String locationMessage) {
        this.locationMessage = locationMessage;
    }
    
	
	
}
