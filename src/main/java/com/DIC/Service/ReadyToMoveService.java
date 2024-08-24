package com.DIC.Service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean(name="ReadyToMoveService")
@ViewScoped
public class ReadyToMoveService implements Serializable{
	
	private static final Logger log = LogManager.getLogger(ReadyToMoveService.class);
	
	
	
	 @PostConstruct 
	    public void init()
	    {
		 
	    }

}
