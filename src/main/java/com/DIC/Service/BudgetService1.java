package com.DIC.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

//import org.apache.log4j.Logger;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.BudgetModel;
import com.DIC.model.IndividualSiteModel;

@ManagedBean(name="budgetService1")
@ViewScoped
public class BudgetService1 implements Serializable{
	
	private static final Logger log = LogManager.getLogger(BudgetService1.class);
	
	private String locationMessage;
	private List<BudgetModel> budgetModelList;
	
	GeneralDAOImpl gDao;
	
	 	@PostConstruct 
	    public void init()
	    {
	    	log.info("Loading BudgetService init()");
	    	gDao=new GeneralDAOImpl();
	    	  
	    	  
	    	getBudget1();  
	        
         
	    }
	 	
	 	
	 	public void getBudget1()
	 	{
	 		
	 		budgetModelList=gDao.getBudget1Details(1);
         
	 	}
	 	
	 	
	 	public String budgetAction1(){
	 		
	 		
	  		System.out.println("******* Budget 1 **********");
	  		//budgetModelList=gDao.getBudget1Details(50);
	  		
	 		//getBudget1();
	 	return "budget1";
			
		}
	 	
	 	
	 	
	 	
	 	

		public String getLocationMessage() {
			return locationMessage;
		}

		public List<BudgetModel> getBudgetModelList() {
			return budgetModelList;
		}

		public void setLocationMessage(String locationMessage) {
			this.locationMessage = locationMessage;
		}

		public void setBudgetModelList(List<BudgetModel> budgetModelList) {
			this.budgetModelList = budgetModelList;
		}
	
	
	

}
