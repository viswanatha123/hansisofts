package com.DIC.Service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.BudgetModel;
import com.DIC.model.PromoImageModel;
import SMTPService.SMTPService;

@ManagedBean(name="cornerBitService")
@ViewScoped
public class CornerBitService implements Serializable {

	private static final Logger log = Logger.getLogger(CornerBitService.class.getName());

	private String locationMessage;
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalRecords;

	private int promoCurrentPage = 1;
	private int promoPageSize = 3;
	private int promoTotalRecords;

	private List<BudgetModel> budgetModelList;
	private List<PromoImageModel> promoImageModel;

	private BudgetModel selectedProperty;

	private String custName = "";
	private String contactNumber = "";
	private String email = "";

	private GeneralDAOImpl gDao;
	private UserDAOImpl udo;

	public CornerBitService() {
		gDao = new GeneralDAOImpl();
		udo = new UserDAOImpl();
		loadEntities();
		countTotalRecords();
	}

	public void loadEntities() {
		budgetModelList = gDao.getBudget1Details(5, pageSize, currentPage);
		promoImageModel = gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
	}

	public void countTotalRecords() {
		totalRecords = gDao.getBudget1DetailsCountTotalRecords(5);
		promoTotalRecords = gDao.getPromoCountTotalRecords();
	}

	public void nextPage() {
		if ((currentPage * pageSize) < totalRecords) currentPage++;
		if ((promoCurrentPage * promoPageSize) < promoTotalRecords) promoCurrentPage++;
		loadEntities();
	}

	public void previousPage() {
		if (currentPage > 1) currentPage--;
		if (promoCurrentPage > 1) promoCurrentPage--;
		loadEntities();
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) totalRecords / pageSize);
	}

	public void storeSelectedPropertyInSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("selectedCornerBitProperty", selectedProperty);
	}

	public void submit() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (session != null) {
			selectedProperty = (BudgetModel) session.getAttribute("selectedCornerBitProperty");
		}

		if (selectedProperty != null && selectedProperty.getPro_id() != 0 && custName != null && contactNumber != null) {
			int userId = (selectedProperty.getUserId() != 0) ? selectedProperty.getUserId() : 1;
			udo.saveLeads(custName, contactNumber, email, selectedProperty.getPro_id(), userId, selectedProperty.getPro_type());
			SMTPService.sendVillaLeadEmail(custName, contactNumber, email, selectedProperty);

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"We received your contact details",
					"Our representative will contact you soon, Thank you..");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		}

		loadEntities();
	}

	public void reset() {
		PrimeFaces.current().resetInputs("form1:panelDialog");
	}

	@PreDestroy
	public void cleanup() {
		if (budgetModelList != null) budgetModelList.clear();
	}

	// Getters and Setters
	public String getLocationMessage() { return locationMessage; }
	public void setLocationMessage(String locationMessage) { this.locationMessage = locationMessage; }

	public List<BudgetModel> getBudgetModelList() { return budgetModelList; }
	public void setBudgetModelList(List<BudgetModel> budgetModelList) { this.budgetModelList = budgetModelList; }

	public int getCurrentPage() { return currentPage; }
	public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

	public int getPageSize() { return pageSize; }
	public void setPageSize(int pageSize) { this.pageSize = pageSize; }

	public int getTotalRecords() { return totalRecords; }
	public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }

	public BudgetModel getSelectedProperty() { return selectedProperty; }
	public void setSelectedProperty(BudgetModel selectedProperty) { this.selectedProperty = selectedProperty; }

	public String getCustName() { return custName; }
	public void setCustName(String custName) { this.custName = custName; }

	public String getContactNumber() { return contactNumber; }
	public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public int getPromoCurrentPage() { return promoCurrentPage; }
	public void setPromoCurrentPage(int promoCurrentPage) { this.promoCurrentPage = promoCurrentPage; }

	public int getPromoPageSize() { return promoPageSize; }
	public void setPromoPageSize(int promoPageSize) { this.promoPageSize = promoPageSize; }

	public List<PromoImageModel> getPromoImageModel() { return promoImageModel; }
	public void setPromoImageModel(List<PromoImageModel> promoImageModel) { this.promoImageModel = promoImageModel; }
}