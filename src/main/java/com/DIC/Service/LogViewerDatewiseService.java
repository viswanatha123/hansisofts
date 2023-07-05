package com.DIC.Service;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import java.util.*;
import java.util.Date;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.LogViewerDatewiseModel;



@ManagedBean(name="logViewerDatewiseService")
@SessionScoped
public class LogViewerDatewiseService implements Serializable{
	
	  
	private List<SelectItem> items;
	  

	private String datasetID;
	

	 List<LogViewerDatewiseModel> logViewerDatewiseModel;
	  private ConnectionDAOImpl dao; 
	  
	  public LogViewerDatewiseService()
	  {
		  this.sDate = new java.util.Date(System.currentTimeMillis()); 
		  
		  items = new ArrayList<>();
		  items.add(new SelectItem("Success"));
		  items.add(new SelectItem("Failed"));
	  }
	  
	  public List<LogViewerDatewiseModel> getLogViewerDatewiseModel() {
			return logViewerDatewiseModel;
		}

		public void setLogViewerDatewiseModel(List<LogViewerDatewiseModel> logViewerDatewiseModel) {
			this.logViewerDatewiseModel = logViewerDatewiseModel;
		}
		
		  

		public String getDatasetID() {
			return datasetID;
		}

		public void setDatasetID(String datasetID) {
			this.datasetID = datasetID;
		}
		
		private java.util.Date sDate = new Date(System.currentTimeMillis());
		  public java.util.Date getsDate() {
			return sDate;
		}

		public void setsDate(java.util.Date sDate) {
			this.sDate = sDate;
		}

		public java.util.Date geteDate() {
			return eDate;
		}

		public void seteDate(java.util.Date eDate) {
			this.eDate = eDate;
		}

		private java.util.Date eDate = new Date(System.currentTimeMillis());
		
		 //Submit Button
	    public void logviewerDateWise()
	    {
	        dao=new ConnectionDAOImpl(); 
	        logViewerDatewiseModel=dao.getLogViewerDateWise(datasetID, sDate, eDate);
	                 
	        
	        //logViewerDatewiseModel=dao.getLogViewerDateWise(datasetID, sDate, eDate);
	        System.out.println(" ***** clicked submit button log viewer datawise *****");
	        
	        System.out.println("DatatSet Id :"+datasetID);
	        System.out.println("Start Date :"+sDate+" End Date :"+eDate);       
	    }
	    
	    public String navegateToPage(LogViewerDatewiseModel logViewerDatewiseModel)
	    {
	        FacesContext fctx = FacesContext.getCurrentInstance();
	        fctx.getExternalContext().getSessionMap().put("dataSetId", Long.valueOf(logViewerDatewiseModel.getDatasetId()));
	        fctx.getExternalContext().getSessionMap().put("step_information",(String)logViewerDatewiseModel.getStepInformation());
	        fctx.getExternalContext().getSessionMap().put("createdOnDateVal",(java.util.Date)logViewerDatewiseModel.getCreatedOnDate());
	        fctx.getExternalContext().getSessionMap().put("modelname",(String)logViewerDatewiseModel.getModelName());
	        
	        
	        System.out.println(" Data set id :"+logViewerDatewiseModel.getDatasetId()+", Page Name: "+logViewerDatewiseModel.getPageName()+" createdOnDate :"+logViewerDatewiseModel.getCreatedOnDate());
	        
	       //setIncludePageResetView(logViewerDatewiseModel.getPageName(), "ExceptionPage") ;
	        String page=logViewerDatewiseModel.getPageName();
	        System.out.println("Selected Page Name : "+page);
	        return page;
	    }
	    
		  public List<SelectItem> getItems() {
				return items;
			}

			public void setItems(List<SelectItem> items) {
				this.items = items;
			}
	
	
}
