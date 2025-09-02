package com.DIC.Service;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.PromoImageModel;
import com.DIC.model.VillaModel;

import SMTPService.SMTPService;

@ManagedBean(name = "ownerPropertiesService")
//@RequestScoped
@ViewScoped
public class OwnerPropertiesService {

	private static final Logger log = LogManager.getLogger(OwnerPropertiesService.class);

	private String errorMessage;

	// Pagination for properties
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;

	// Pagination for promo images
	private int promoCurrentPage = 1;
	private int promoPageSize = 3;
	private int promoTotalRecords;

	private List<VillaModel> villaModel;
	private List<PromoImageModel> promoImageModel;

	private VillaModel selectedProperty;

	private String custName = "";
	private String contactNumber = "";
	private String email = "";

	private GeneralDAOImpl gDao;
	private UserDAOImpl udo;

	public OwnerPropertiesService() {
		log.info("Loading OwnerPropertiesService init()");
		gDao = new GeneralDAOImpl();
		udo = new UserDAOImpl();

		loadEntities();
		countTotalRecords();
	}


	public void loadEntities() {
		villaModel = gDao.getOwnerProperties(pageSize, currentPage);
		promoImageModel = gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
	}

	public void countTotalRecords() {
		totalRecords = gDao.getOwnerPropertiesCountTotalRecords();
		promoTotalRecords = gDao.getPromoCountTotalRecords();
	}

	public void nextPage() {
		if ((currentPage * pageSize) < totalRecords) {
			currentPage++;
			loadEntities();
		}
		if ((promoCurrentPage * promoPageSize) < promoTotalRecords) {
			promoCurrentPage++;
			loadEntities();
		}
	}

	public void previousPage() {
		if (currentPage > 1) {
			currentPage--;
			loadEntities();
		}
		if (promoCurrentPage > 1) {
			promoCurrentPage--;
			loadEntities();
		}
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) totalRecords / pageSize);
	}

	public void storeSelectedPropertyInSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("selectedOwnerProperty", selectedProperty);
	}

	public void submit() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		if (session != null) {
			selectedProperty = (VillaModel) session.getAttribute("selectedOwnerProperty");
		}

		if (selectedProperty != null && selectedProperty.getVillaId() != 0) {
			if (custName != null && contactNumber != null) {
				int userId = (selectedProperty.getUserId() != 0) ? selectedProperty.getUserId() : 1;

				udo.saveLeads(custName, contactNumber, email, selectedProperty.getVillaId(), userId, "villa");
				SMTPService.sendVillaLeadEmail(custName, contactNumber, email, selectedProperty);
				log.info("***** Successful submitted lead ******");

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"We received your contact details",
						"Our representative will contact you soon, Thank you..");
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}

		loadEntities();
	}

	public void reset() {
		PrimeFaces.current().resetInputs("form1:panelDialog");
	}

	@PreDestroy
	public void cleanup() {
		if (villaModel != null) {
			villaModel.clear();
		}
	}

	// Getters and setters
	public List<VillaModel> getVillaModel() {
		return villaModel;
	}

	public void setVillaModel(List<VillaModel> villaModel) {
		this.villaModel = villaModel;
	}

	public List<PromoImageModel> getPromoImageModel() {
		return promoImageModel;
	}

	public void setPromoImageModel(List<PromoImageModel> promoImageModel) {
		this.promoImageModel = promoImageModel;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public VillaModel getSelectedProperty() {
		return selectedProperty;
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

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setSelectedProperty(VillaModel selectedProperty) {
		this.selectedProperty = selectedProperty;
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

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPromoCurrentPage() {
		return promoCurrentPage;
	}

	public int getPromoPageSize() {
		return promoPageSize;
	}

	public void setPromoCurrentPage(int promoCurrentPage) {
		this.promoCurrentPage = promoCurrentPage;
	}

	public void setPromoPageSize(int promoPageSize) {
		this.promoPageSize = promoPageSize;
	}
}
