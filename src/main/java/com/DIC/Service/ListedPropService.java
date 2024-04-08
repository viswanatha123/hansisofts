package com.DIC.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.util.Constants.DialogFramework;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.LayoutMode;

import framework.utilities.SessionUtils;



@ManagedBean(name="listedPropService")
@ViewScoped
public class ListedPropService {
	private static final Logger log = Logger.getLogger(ListedPropService.class.getName());



	
	
private LayoutMode selectedProduct;
	
	private List<LayoutMode> layoutdetails;
	private LayoutMode selectedProperty;
	
	UserDAOImpl uDao;
	
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading LayoutDetailService init()");
    	uDao=new UserDAOImpl();
    	
    	    	HttpSession session = SessionUtils.getSession();
		      	
		    	if (session != null)
		    	{
		    		if(session.getAttribute("userId")!=null)
		    		{
		    		    int userId= Integer.parseInt(session.getAttribute("userId").toString());
		    		    if(userId > 0)
		    		    {    	
		          
		    		    		layoutdetails=uDao.getLayoutListByUserId(userId);
		    		    		log.log(Level.INFO, " list Size : "+layoutdetails.size());
		    		    		
		    		    }
		    		}
		    	}
              
    }
	
	
	public void deleteProduct() {
    
        System.out.println("deleted id : "+this.selectedProduct.getLayoutId());
        if(this.selectedProduct.getLayoutId()!=0)
        {
        	String resMessage=uDao.deleteLayout(this.selectedProduct.getLayoutId());
        	layoutdetails=uDao.getLayoutList();
        }
        
        
        
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
	
	
	public void propVal()
	{
		log.log(Level.INFO, " Select Property val :"+selectedProduct.getLayoutId());
		
	}
	

	
	public List<LayoutMode> getLayoutdetails() {
		return layoutdetails;
	}


	public void setLayoutdetails(List<LayoutMode> layoutdetails) {
		this.layoutdetails = layoutdetails;
	}


	public LayoutMode getSelectedProduct() {
		return selectedProduct;
	}



	public void setSelectedProduct(LayoutMode selectedProduct) {
		this.selectedProduct = selectedProduct;
	}


	public LayoutMode getSelectedProperty() {
		return selectedProperty;
	}


	public void setSelectedProperty(LayoutMode selectedProperty) {
		this.selectedProperty = selectedProperty;
	}




	
}