package com.DIC.Service;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.ImageUploadModel;
import com.DIC.model.PromoImageModel;
import com.DIC.model.UserDetails;
import com.DIC.model.UserRoleModel;
import com.DIC.model.VillaModel;

@ManagedBean(name="promoImageService")

//@RequestScoped
@ViewScoped
public class PromoImageService {
	
	private static final Logger log = LogManager.getLogger(PromoImageService.class);
	
	private String imageName;
	private UploadedFile file;
	private String updateResult;
	private String comment;
	
	private String statusMessage;
	
	
	private PromoImageModel selectedProduct;
	
	 ConnectionDAOImpl dao;
	
	private List<PromoImageModel> promoImageModelList;
	
	GeneralDAOImpl gDao;
	public PromoImageService()
	{
		log.info("Loading PromoImageService init()");
    	gDao=new GeneralDAOImpl();
    	
    	promoImageModelList=gDao.getPromoImage();
    	
	}
	
	public void upload()
    {
		updateResult="";
		log.info("**** Clicked on submit button uploadImage ---->******"+file);
		
		if (file != null) {
            try {
               
            
  	          
  	     
  	         dao=new ConnectionDAOImpl();
  	          
  	       PromoImageModel promoImageModel=new PromoImageModel();
  	       promoImageModel.setInputStream(file.getInputStream());
  	       promoImageModel.setFile(file);
  	       promoImageModel.setImageName(imageName);
  	       promoImageModel.setComment(comment);
  	        
  	    	          
  	          updateResult=dao.promoImageUpload(promoImageModel);
  	              
  	         
  	        
  	        log.info("**** After update call method******");
                
  	         this.imageName="";
    	     this.file=null;
    	     this.comment="";
    	     
    	     promoImageModelList=gDao.getPromoImage();
    	     
            } catch (Exception e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        }
        else{
        FacesMessage msg = new FacesMessage("Please select image!!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
	        
    }
	
	public void clear()
    {
  	    this.imageName="";
  	    this.file=null;
  
       
  	  log.info("****** Clicked on Clear button*****");
    }
    
	
	public void deleteProduct() {
          
        System.out.println("deleted id : "+this.selectedProduct.getPromoId());
        if(this.selectedProduct.getPromoId()!=0)
        {
        	
        	String resMessage=gDao.delPromoImage(this.selectedProduct.getPromoId());
        	promoImageModelList=gDao.getPromoImage();
        	
        }
        
        
        
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
	
	
	
	public void save()
	{
		statusMessage="";
		
		 for(PromoImageModel urm:promoImageModelList)
	        {
	        	System.out.println("**** Save "+urm.getPromoId()+"   "+urm.getIs_active());
	        }
		
		 int updatedRecord=gDao.savePromoImgIsActive(promoImageModelList);
		    if(updatedRecord > 0)
		    {
		    	updateResult="Successful saved Active";
		    }else
		    {
		    	updateResult="Error Occurred, Please contact support team.";
		    }
		 
		   
	}
    
    
    public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public UploadedFile getFile() {
  		return file;
  	}

  	public void setFile(UploadedFile file) {
  		this.file = file;
  	}

	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<PromoImageModel> getPromoImageModelList() {
		return promoImageModelList;
	}

	public void setPromoImageModelList(List<PromoImageModel> promoImageModelList) {
		this.promoImageModelList = promoImageModelList;
	}

	public PromoImageModel getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(PromoImageModel selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	
    
	

}
