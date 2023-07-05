package com.DIC.Service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.DIC.model.LogViewerDatewiseModel;

@ManagedBean
@SessionScoped
public class MenuBean {
	
	public MenuBean()
	{
		
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
        System.out.println("Menu bean Selected Page Name : "+page);
        return page;
    }

}
