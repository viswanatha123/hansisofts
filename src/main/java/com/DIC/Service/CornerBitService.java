package com.DIC.Service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
	 
	 
	private String custName="";
	private String contactNumber="";
	private String email="";
	
	private GeneralDAOImpl gDao;
	private UserDAOImpl udo;
	
	
		public CornerBitService()
		{
			log.info("Loading CornerBitService Constructor");
			gDao=new GeneralDAOImpl();
			udo=new UserDAOImpl();

			FacesContext context = FacesContext.getCurrentInstance();
			if (context != null) {
				             HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

				             String pageParam = request.getParameter("cornerbit");
				           if (pageParam != null && !pageParam.isEmpty()) {
					                 try {
						                   currentPage = Integer.parseInt(pageParam);
						              } catch (NumberFormatException e) {
						                    currentPage = 1;
						                  }
					              }

				            String promoParam = request.getParameter("cornerbitpromo");
				             if (promoParam != null && !promoParam.isEmpty()) {
					                  try {
						                     promoCurrentPage = Integer.parseInt(promoParam);
						                 } catch (NumberFormatException e) {
						                      promoCurrentPage = 1;
						                  }
					              }
				          }

			
			loadEntities();
			countTotalRecords();
		}
	
	
	
	 	
	 	public void loadEntities() {
	 		
	 		budgetModelList=gDao.getBudget1Details(5,pageSize,currentPage);
			promoImageModel = gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);

			log.info("CornerBit properties loaded, count: " +
					(budgetModelList != null ? budgetModelList.size() : 0));
	 		
//	 		System.out.println("Cout : "+budgetModelList.size());
	 		
	        
	    }
	 	
	 	public void countTotalRecords() {
	 	
	 		totalRecords=gDao.getBudget1DetailsCountTotalRecords(5);
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
		          session.setAttribute("selectedCornerBitProperty", selectedProperty);
		    }
	    
	    public void submit() {

			log.info("Submit called for CornerBit property");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			if (session != null) {
				selectedProperty = (BudgetModel) session.getAttribute("selectedCornerBitProperty");
			}

//	    	System.out.println("-------------submit ----------------------");
//        	log.info("Selected property  : "+selectedProperty.getPro_id()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
			if (selectedProperty != null && selectedProperty.getPro_id() != 0) {
				if (custName != null && contactNumber != null) {
					int userId = (selectedProperty.getUserId() != 0) ? selectedProperty.getUserId() : 1;
					udo.saveLeads(custName, contactNumber, email,
							            selectedProperty.getPro_id(),
							                userId, selectedProperty.getPro_type());
					       SMTPService.sendVillaLeadEmail(custName, contactNumber, email, selectedProperty);
					                log.info("***** Successful submitted CornerBit lead ******");

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
		       if (budgetModelList != null) {
			             budgetModelList.clear();
			        }
		   }
	 
	 	
	 
	    
	   

		public String getLocationMessage() {
			return locationMessage;
		}

		public List<BudgetModel> getBudgetModelList() {
			return budgetModelList;
		}

		public void setLocationMessage(String locationMessage) {
			this.locationMessage = locationMessage;
		}

		public void setBudgetModelList(List<BudgetModel> budgetModelList) {
			this.budgetModelList = budgetModelList;
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




		public BudgetModel getSelectedProperty() {
			return selectedProperty;
		}





		public void setSelectedProperty(BudgetModel selectedProperty) {
			this.selectedProperty = selectedProperty;
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
	public int getPromoCurrentPage() { return promoCurrentPage; }
	public void setPromoCurrentPage(int promoCurrentPage) { this.promoCurrentPage = promoCurrentPage; }

	public int getPromoPageSize() { return promoPageSize; }
      public void setPromoPageSize(int promoPageSize) { this.promoPageSize = promoPageSize; }

	public List<PromoImageModel> getPromoImageModel() { return promoImageModel; }
    public void setPromoImageModel(List<PromoImageModel> promoImageModel) { this.promoImageModel = promoImageModel; }

}
