package com.DIC.Service;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AllPropertyList;

import framework.utilities.SessionUtils;

@ManagedBean(name="propertyListService")
@ViewScoped
public class PropertyListService {
	
	private static final Logger log = Logger.getLogger(PropertyListService.class.getName());
	
	private List<AllPropertyList> allPropertyListVal;
	private AllPropertyList selectedProperty;
	
	UserDAOImpl uDao;
	
	@PostConstruct 
    public void init()
    {
		uDao=new UserDAOImpl();
		
		
		/*
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
		 */
		
			
			if(SessionUtils.getUserId() > 0)
		    {    	
				allPropertyListVal=uDao.getAllPropByUserId(SessionUtils.getUserId());
	    		
		    }
		
		
    }

	
	
	public void deleteProperty() {
	    
        System.out.println("deleted id and Property Type : "+this.selectedProperty.getPropId()+"      "+this.selectedProperty.getPropType());
        String delMessage=uDao.deleteProperty(this.selectedProperty.getPropId(),this.selectedProperty.getPropType());
        System.out.println("Property "+delMessage);
        if(SessionUtils.getUserId() > 0)
	    {    	
        	
        	
			allPropertyListVal=uDao.getAllPropByUserId(SessionUtils.getUserId());
			System.out.println("************ ------------>"+SessionUtils.getUserId()+"  "+allPropertyListVal.size());
    		
	    }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        
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

	
	
}
