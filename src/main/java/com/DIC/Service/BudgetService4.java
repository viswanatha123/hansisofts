package com.DIC.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

//import org.apache.log4j.Logger;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.BudgetModel;
import com.DIC.model.IndividualSiteModel;

@ManagedBean(name="budgetService4")
@ViewScoped
public class BudgetService4 implements Serializable{
	
	private static final Logger log = Logger.getLogger(BudgetService4.class.getName());
	
	private String locationMessage;
	private List<BudgetModel> budgetModelList;
	
	GeneralDAOImpl gDao;
	
	 	@PostConstruct 
	    public void init()
	    {
	    	log.info("Loading BudgetService init()");
	    	gDao=new GeneralDAOImpl();
	    	  
	    	  
	    	getBudget4();  
	        
         
	    }
	 	
	 	
	 	public void getBudget4()
	 	{
	 		
	 		budgetModelList=gDao.getBudget1Details(4);
        
	 	}
	 	
	 	
	 	public String budgetAction4(){
	 		
	 		
	  		System.out.println("******* Budget 3 **********");
	  		//budgetModelList=gDao.getBudget1Details(50);
	  		
	 		//getBudget1();
	 	return "budget4";
			
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
