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

@ManagedBean(name="budgetService2")
@ViewScoped
public class BudgetService2 implements Serializable{
	
	private static final Logger log = Logger.getLogger(BudgetService2.class.getName());
	
	private String locationMessage;
	private List<BudgetModel> budgetModelList;
	
	GeneralDAOImpl gDao;
	
	 	@PostConstruct 
	    public void init()
	    {
	    	log.info("Loading BudgetService init()");
	    	gDao=new GeneralDAOImpl();
	    	  
	    	  
	    	getBudget2();  
	        
         
	    }
	 	
	 	
	 	public void getBudget2()
	 	{
	 		
	 		budgetModelList=gDao.getBudget1Details(2);
           
	 	}
	 	
	 	
	 	public String budgetAction2(){
	 		
	 		
	  		System.out.println("******* Budget 2 **********");
	  		//budgetModelList=gDao.getBudget1Details(50);
	  		
	 		//getBudget1();
	 	return "budget2";
			
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
