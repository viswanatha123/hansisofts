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
import com.DIC.model.LayoutMode;
import com.DIC.model.UserDetails;

@ManagedBean(name="layoutListService")
@ViewScoped
public class LayoutListService {
	
	private static final Logger log = Logger.getLogger(LayoutListService.class.getName());
	
	private LayoutMode selectedProduct;
	
	private List<LayoutMode> layoutdetails;
	
	UserDAOImpl uDao;
	
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading LayoutDetailService init()");
    	uDao=new UserDAOImpl();
    	layoutdetails=uDao.getLayoutList();
              
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




	
	
	

}
