package com.DIC.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AllPropertyList;
import com.DIC.model.LayoutMode;
import com.DIC.model.LeadModel;

import framework.utilities.SessionUtils;

@ManagedBean(name="leadsService")
@ViewScoped
public class LeadsService {
	private static final Logger log = Logger.getLogger(LeadsService.class.getName());
	
	
	
	private List<AllPropertyList> allPropertyListVal;
	private AllPropertyList selectedProperty;
	
	private List<LeadModel> leadModelList;
	
	 UserDAOImpl uDao;
	@PostConstruct 
    public void init()
    {
		uDao=new UserDAOImpl();
		
		HttpSession session = SessionUtils.getSession();
      	
    	if (session != null)
    	{
    		if(session.getAttribute("userId")!=null)
    		{
    		    int userId= Integer.parseInt(session.getAttribute("userId").toString());
    		    if(userId > 0)
    		    {    	
          
    		    		
    		    	allPropertyListVal=uDao.getAllPropByUserId(userId);
    	
    		    		
    		    }
    		}
    	}
		
    }
	
	
	public void propVal()
	{
		
		leadModelList=uDao.getLeads(selectedProperty.getPropId(),selectedProperty.getPropType());
		
		
	}

	public List<AllPropertyList> getAllPropertyListVal() {
		return allPropertyListVal;
	}

	public void setAllPropertyListVal(List<AllPropertyList> allPropertyListVal) {
		this.allPropertyListVal = allPropertyListVal;
	}

	public AllPropertyList getSelectedProperty() {
		return selectedProperty;
	}

	public void setSelectedProperty(AllPropertyList selectedProperty) {
		this.selectedProperty = selectedProperty;
	}

	public List<LeadModel> getLeadModelList() {
		return leadModelList;
	}

	public void setLeadModelList(List<LeadModel> leadModelList) {
		this.leadModelList = leadModelList;
	}
	
	
	

}
