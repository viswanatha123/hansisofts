package com.DIC.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.UserDetails;

@ManagedBean(name="IndiSiteListService")
@ViewScoped

public class IndiSiteListService{
	
	private static final Logger log = Logger.getLogger(IndiSiteListService.class.getName());

	private IndividualSiteModel selectedProduct;


	private List<IndividualSiteModel> indisitedetails;

	UserDAOImpl uDao;
	
	
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading IndiSiteDataEntryService init()");
    	uDao=new UserDAOImpl();
    	indisitedetails=uDao.getIndiSiteList();
              
    }
	
	
	public void deleteProduct() {
	    
        System.out.println("deleted id : "+this.selectedProduct.getInd_id());
        if(this.selectedProduct.getInd_id()!=0)
        {
        	String resMessage=uDao.deleteIndiList(this.selectedProduct.getInd_id());
        	indisitedetails=uDao.getIndiSiteList();
        }
        
        
        
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
	
	
	public IndividualSiteModel getSelectedProduct() {
		return selectedProduct;
	}


	public void setSelectedProduct(IndividualSiteModel selectedProduct) {
		this.selectedProduct = selectedProduct;
	}


	public List<IndividualSiteModel> getIndisitedetails() {
		return indisitedetails;
	}


	public void setIndisitedetails(List<IndividualSiteModel> indisitedetails) {
		this.indisitedetails = indisitedetails;
	}

}