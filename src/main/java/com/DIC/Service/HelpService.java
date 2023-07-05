package com.DIC.Service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="helpService")
@SessionScoped
public class HelpService implements Serializable{
	
	private static final Logger log = Logger.getLogger(HelpService.class.getName());
	
	private String query;
	

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
	 public void clear()
     {
		 this.query="";
     }
	
	
		 public void submitHelp() { 
			 
			 
			 log.info("Clicked Help Submit  :"+query);
			 this.query="";
	
		 }
	 
	 
}
