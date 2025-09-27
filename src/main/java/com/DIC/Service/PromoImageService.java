package com.DIC.Service;

import java.io.Serializable;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.DIC.DAO.Impl.LocationDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
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
public class PromoImageService implements Serializable {
	
	private static final Logger log = LogManager.getLogger(PromoImageService.class);


	LocationDAOImpl locationDao;
	private Map<String, String> primaryModel;
	private Map<String,String> primLocation;
	private TreeMap<String, String> primLocationSort;


	private String imageName;
	private UploadedFile file;
	private String updateResult;
	private String comment;
	private String country;
	private String defaultImage;
	
	private String statusMessage;
	
	
	private PromoImageModel selectedProduct;
	
	 ConnectionDAOImpl dao;
	
	private List<PromoImageModel> promoImageModelList;
	
	GeneralDAOImpl gDao;
	public PromoImageService()
	{
		log.info("Loading PromoImageService init()");

		locationDao=new LocationDAOImpl();
    	gDao=new GeneralDAOImpl();

		primaryModel=locationDao.getLayoutPrimaryLocation();
		primLocation  = new HashMap<>();
		for(Map.Entry<String, String> pp:primaryModel.entrySet())
		{
			log.info("Primary location details ---------->:"+pp.getKey()+"   "+pp.getValue());

			primLocation.put(pp.getKey(), pp.getValue());

		}
		primLocationSort=new TreeMap<>(primLocation);
    	
    	promoImageModelList=gDao.getPromoImage();
    	
	}


	
	public void upload()
    {
		updateResult="";
		log.info("**** Clicked on submit button uploadImage ---->******"+file.getFileName());
		promoImageModelList=gDao.getPromoImage();
		String fileName=file.getFileName();
		if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg"))
		{
			System.out.println("======================== this is not jpg file ============================== : "+file.getFileName());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid type, ", "Only JPG/JPEG files are allowed."));

			PrimeFaces.current().ajax().addCallbackParam("validationFailed", true);

			return;
		}

		if (file != null) {
            try {

  	         dao=new ConnectionDAOImpl();
  	          
  	       PromoImageModel promoImageModel=new PromoImageModel();
  	       promoImageModel.setInputStream(file.getInputStream());
  	       promoImageModel.setFile(file);
  	       promoImageModel.setImageName(imageName);
  	       promoImageModel.setComment(comment);
  	       promoImageModel.setPrimLocation(country);
		   promoImageModel.setDefaultImage(defaultImage);
  	        
  	    	          
  	          updateResult=dao.promoImageUpload(promoImageModel);
  	              
  	         
  	        
  	        log.info("**** After update call method******");
                
  	         this.imageName="";
    	     this.file=null;
    	     this.comment="";
				this.country = "";
    	     
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



	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ConnectionDAOImpl getDao() {
		return dao;
	}

	public void setDao(ConnectionDAOImpl dao) {
		this.dao = dao;
	}

	public TreeMap<String, String> getPrimLocationSort() {
		return primLocationSort;
	}

	public void setPrimLocationSort(TreeMap<String, String> primLocationSort) {
		this.primLocationSort = primLocationSort;
	}

	public Map<String, String> getPrimLocation() {
		return primLocation;
	}

	public void setPrimLocation(Map<String, String> primLocation) {
		this.primLocation = primLocation;
	}

	public Map<String, String> getPrimaryModel() {
		return primaryModel;
	}

	public void setPrimaryModel(Map<String, String> primaryModel) {
		this.primaryModel = primaryModel;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
}
