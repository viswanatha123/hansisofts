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
	
	
	private int currentPage = 1;
	 private int pageSize = 10;
	 private int totalRecords;
	
	GeneralDAOImpl gDao;
	
	
	
	public BudgetService2()
	{
		log.info("Loading BudgetService1 Constructor");
		gDao=new GeneralDAOImpl();
		
		loadEntities();
		countTotalRecords();
	}
	

	 	
	 	public void loadEntities() {
	 		
	 		budgetModelList=gDao.getBudget1Details(2,pageSize,currentPage);
	 		//budgetModelList=gDao.getBudget1Details(1,pageSize,currentPage);
	        
	    }
	 	
	 	public void countTotalRecords() {
	 	
	 		totalRecords=gDao.getBudget1DetailsCountTotalRecords(2);
	        
	    }
	 	
	 	public void nextPage() {
	        if ((currentPage * pageSize) < totalRecords) {
	            currentPage++;
	            loadEntities();
	        }
	    }

	    public void previousPage() {
	        if (currentPage > 1) {
	            currentPage--;
	            loadEntities();
	        }
	    }
	 	
	    public int getTotalPages() {
	        return (int) Math.ceil((double) totalRecords / pageSize);
	    }
	 

	 	
	 	
	 	public String budgetAction2(){
	  		System.out.println("******* Budget 2 **********");
	  	
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
	
		public int getCurrentPage() {
			return currentPage;
		}


		public int getPageSize() {
			return pageSize;
		}


		public int getTotalRecords() {
			return totalRecords;
		}


		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}


		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}


		public void setTotalRecords(int totalRecords) {
			this.totalRecords = totalRecords;
		}
	
	
	

}
