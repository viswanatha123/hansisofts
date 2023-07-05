package com.DIC.Service;

import java.io.Serializable;
import java.util.Map;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;


@ManagedBean(name="dataEntryService")
@SessionScoped
public class DataEntryService implements Serializable{
	
	private static final Logger log = Logger.getLogger(DataEntryService.class.getName());

	
	private int index;
	
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
