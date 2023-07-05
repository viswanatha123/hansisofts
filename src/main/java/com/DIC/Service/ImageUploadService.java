package com.DIC.Service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.ImageUploadModel;



import org.primefaces.model.file.UploadedFile;


@ManagedBean(name="imageUploadService")
@SessionScoped
public class ImageUploadService implements Serializable {
	
	private static final Logger log = Logger.getLogger(ImageUploadService.class.getName());
	
	

	private String imageName;
	private UploadedFile file;
	private String updateResult;
	
	
	
	
	 ConnectionDAOImpl dao;
	
	
	 public void upload()
    {
		log.info("**** Clicked on submit button uploadImage ---->******"+file);
		
		if (file != null) {
            try {
               
            
  	          
  	     
  	         dao=new ConnectionDAOImpl();
  	          
  	        ImageUploadModel imageUploadModel=new ImageUploadModel();
  	        imageUploadModel.setInputStream(file.getInputStream());
  	        imageUploadModel.setFile(file);
  	        imageUploadModel.setImageName(imageName);
  	        
  	    	          
  	          updateResult=dao.uploadImageDao(imageUploadModel);
  	              
  	         
  	        
  	        log.info("**** After update call method******");
                
  	         this.imageName="";
    	     this.file=null;
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
    

}
