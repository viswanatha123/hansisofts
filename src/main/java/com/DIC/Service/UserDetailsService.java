package com.DIC.Service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.UserDetails;
import com.DIC.model.UserRoleModel;
import com.DIC.model.VillaModel;

@ManagedBean(name="userDetailsService")
@ViewScoped
public class UserDetailsService implements Serializable {
	
	private static final Logger log = Logger.getLogger(UserDetailsService.class.getName());
	
	private List<UserDetails> selectedProducts;
	
	private UserDetails selectedProduct;
	
	
	 private List<UserDetails> userDetailsList;
	    GeneralDAOImpl gDao;
	    
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading UserDetailsService init()");
    	gDao=new GeneralDAOImpl();
          
    	userDetailsList=gDao.getAllUsers();
          
    }


	public List<UserDetails> getUserDetailsList() {
		return userDetailsList;
	}


	public void setUserDetailsList(List<UserDetails> userDetailsList) {
		this.userDetailsList = userDetailsList;
	}


	public void onRowEdit(RowEditEvent<UserDetails> event) {
		/*
        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getUserId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        */
      
		int  userid=Integer.parseInt(String.valueOf(event.getObject().getUserId()));
        		
        		System.out.println("***********Selected user id ******* "+userid);
 
        		
        		UserDetails userDetails = userDetailsList.stream().filter(customer ->userid== customer.getUserId()).findAny().orElse(null);
        		
        		System.out.println("***********Selected Row ******* "+ userDetails.getUserId()+"    "+userDetails.getfName()+"   "+userDetails.getlName());
        		
        		String statusMessage=gDao.updateUserDetails(userDetails);
        		System.out.println("Successful Updated user details. ");
        		
    }
	
	
	  public void onRowCancel(RowEditEvent<UserDetails> event) {
		  
		  /*
	        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getCode()));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	      */  
	        
	        System.out.println("*********** Cancelled ******* "+String.valueOf(event.getObject().getUserId()));
	    }
	  
	  
	  public void deleteProduct() {
	        //this.products.remove(this.selectedProduct);
	       // this.selectedProducts.remove(this.selectedProduct);
	        //this.selectedProduct = null;
	        
	        System.out.println("deleted id : "+this.selectedProduct.getUserId());
	        if(this.selectedProduct.getUserId()!=0)
	        {
	        	String resMessage=gDao.deleteUser(this.selectedProduct.getUserId());
	        	userDetailsList=gDao.getAllUsers();
	        }
	        
	        
	        
	        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
	    }


	public List<UserDetails> getSelectedProducts() {
		return selectedProducts;
	}


	public void setSelectedProducts(List<UserDetails> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}


	public UserDetails getSelectedProduct() {
		return selectedProduct;
	}


	public void setSelectedProduct(UserDetails selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	
	
	
}
