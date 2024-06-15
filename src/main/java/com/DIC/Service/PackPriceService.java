package com.DIC.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.PackPriceModel;

import framework.utilities.UtilConstants;

@ManagedBean(name="packPriceService")
@ViewScoped
public class PackPriceService {
	
	private static final Logger log = Logger.getLogger(PackPriceService.class.getName());
	
	private String custName;
	private String contactNumber;
	private String email;
	
	private List<PackPriceModel> packPriceModelList;
	
	
	ConnectionDAOImpl cdao;
	
	@PostConstruct 
    public void init()
    {
		cdao=new ConnectionDAOImpl();
		
    }
	
	
	
	
	public void submit() {
    	
    			if(contactNumber != null)
    			{
    				
    				int saveMessage=cdao.savePackageEnquiry(custName,contactNumber,email,UtilConstants.PACKAGE_ENQUIRY_TYPE);
    				
    				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
        	        PrimeFaces.current().dialog().showMessageDynamic(message);
        	        this.custName="";
    	        	this.contactNumber="";
    	        	this.email="";
    			}
      	
    }
    
  public void reset() {
       PrimeFaces.current().resetInputs("form1:panelDialog");
 }

	public List<PackPriceModel> getPackPriceModelList() {
		return packPriceModelList;
	}

	public void setPackPriceModelList(List<PackPriceModel> packPriceModelList) {
		this.packPriceModelList = packPriceModelList;
	}

	public String getCustName() {
		return custName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	
	
}
