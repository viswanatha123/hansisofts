package com.DIC.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.LayoutMode;
import com.DIC.model.PlotsDataEntryModel;
import framework.utilities.GeneralConstants;
import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AllPropertyList;

import framework.utilities.SessionUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

@ManagedBean(name="propertyListService")
@ViewScoped
public class PropertyListService {
	
	private static final Logger log = Logger.getLogger(PropertyListService.class.getName());
	
	private List<AllPropertyList> allPropertyListVal;
	private AllPropertyList selectedProperty;
	private UploadedFiles files;


	
	UserDAOImpl uDao;
	ConnectionDAOImpl cDao;
	
	@PostConstruct 
    public void init()
    {
		uDao=new UserDAOImpl();
		cDao=new ConnectionDAOImpl();
			if(SessionUtils.getUserId() > 0)
		    {    	
				allPropertyListVal=uDao.getAllPropByUserId(SessionUtils.getUserId());
	    		
		    }

    }

	
	
	public void deleteProperty() {
	    
        System.out.println("deleted id and Property Type : "+this.selectedProperty.getPropId()+"      "+this.selectedProperty.getPropType());
        
        if(SessionUtils.getUserId() > 0)
	    {    	
        	
        	String delMessage=uDao.deleteProperty(this.selectedProperty.getPropId(),this.selectedProperty.getPropType());
			allPropertyListVal=uDao.getAllPropByUserId(SessionUtils.getUserId());
			System.out.println("************ ------------>"+SessionUtils.getUserId()+"  "+allPropertyListVal.size());
    		
	    }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        
    }

	public void uploadGalaryProperty(FilesUploadEvent event) throws IOException {
		List<InputStream> inputStreams = new ArrayList<>();
		List<UploadedFile> files = new ArrayList<>();
		for (UploadedFile f : event.getFiles().getFiles()) {
			System.out.println("Selected file :"+f.getFileName());
			if (f.getSize() > 0) {
				inputStreams.add(f.getInputStream());
			}

		}
		AllPropertyList selectedProperty = (AllPropertyList)event.getComponent().getAttributes().get("selectedProperty");
		log.info("Selected property ---- > :"+selectedProperty.getPropId());
		log.info("Login user id ------> :"+SessionUtils.getUserId());
		log.info("Property type ------> :"+selectedProperty.getPropType());
		int propCode=getPropertyCode(selectedProperty.getPropType());
		log.info("Property type code------> :"+propCode);

		cDao.uploadGalaryProp(inputStreams,SessionUtils.getUserId(),selectedProperty.getPropId(),getPropertyCode(selectedProperty.getPropType()));

	}


	public int getPropertyCode(String propName) {
		int propCode = 0;
		if (propName.equals("layout")) {
			propCode = GeneralConstants.PropertyType.layout;
		}
		if (propName.equals("agri")) {
			propCode = GeneralConstants.PropertyType.agri;
		}
		if (propName.equals("indi")) {
			propCode = GeneralConstants.PropertyType.indi;
		}
		if (propName.equals("villa")) {
			propCode = GeneralConstants.PropertyType.villa;
		}
		return propCode;
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

	public UploadedFiles getFiles() {
		return files;
	}

	public void setFiles(UploadedFiles files) {
		this.files = files;
	}
}
