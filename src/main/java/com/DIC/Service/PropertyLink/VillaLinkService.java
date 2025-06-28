package com.DIC.Service.PropertyLink;


import SMTPService.SMTPService;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.QueryFactoryManagerImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.DAO.QueryFactoryManager;
import com.DIC.model.PromoImageModel;
import com.DIC.model.VillaModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="villaLinkService")
@RequestScoped
public class VillaLinkService implements Serializable {

    private static final Logger log = LogManager.getLogger(VillaLinkService.class);

    public String nameVal;
    private List<VillaModel> villaModel;

    private String locationMessage;
    private String errorMessage;
    private int currentPage=1;
    private int pageSize = 10;
    private int totalRecords=10;
    private int promoCurrentPage = 1;
    private int promoPageSize = 3;
    private int promoTotalRecords;
    private List<PromoImageModel> promoImageModel;
    String query;
    String queryCount;


    private VillaModel selectedProperty;

    private String custName="";
    private String contactNumber="";
    private String email="";




    GeneralDAOImpl gDao;
    UserDAOImpl udo;


    private String linkParam;
    private String linkParamdCount;

    GeneralDAOImpl gdo;
    QueryFactoryManager qfm;


    public VillaLinkService(){

        linkParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("villaLinkParam");
        linkParamdCount = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("villaLinkParamCount");



        qfm=new QueryFactoryManagerImpl();
        query=qfm.getQueryByParam(linkParam);
        queryCount=qfm.getQueryCountByParam(linkParamdCount);

        System.out.println("******************* query count *********************** "+linkParamdCount);


        gdo=new GeneralDAOImpl();



        //villaModel=gdo.getVillaLink(query, pageSize,currentPage);
        //promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
        countTotalRecords(queryCount);
        loadEntities();

        System.out.println("*********** Query Link *************** :"+query);

    }


    public void loadEntities() {

        //villaModel=gDao.getPlot1bhkProperties(pageSize,currentPage);
       // promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);

        villaModel=gdo.getVillaLink(query, pageSize,currentPage);
        promoImageModel=gDao.getPromoImageVilla(promoPageSize, promoCurrentPage);
    }

    public void countTotalRecords(String queryCount) {

        totalRecords=gDao. getVillaLinkCountTotalRecords(queryCount);
        promoTotalRecords=gDao.getPromoCountTotalRecords();

        System.out.println("************************** count records ***********************"+totalRecords);

    }
    public void nextPage() {

        System.out.println("======================currentPage===========pageSize============totalRecords=====:"+currentPage+"  "+pageSize+"  "+totalRecords);
        if ((currentPage * pageSize) < totalRecords) {
            //currentPage++;
            loadEntities();
        }

        if ((promoCurrentPage * promoPageSize) < promoTotalRecords) {
            //promoCurrentPage++;
            loadEntities();

        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            //currentPage--;
            loadEntities();
        }

        if (promoCurrentPage > 1) {
            //promoCurrentPage--;
            loadEntities();
        }
    }


    public int getTotalPages() {
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public void storeSelectedPropertyInSession() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("plot1bhkKey", selectedProperty);
    }



    public void submit() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session != null) {
            selectedProperty= (VillaModel) session.getAttribute("plot1bhkKey");
            System.out.println("Selected property  : "+selectedProperty.getVillaId()+"  "+selectedProperty.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);
        }


        if(selectedProperty.getVillaId()!=0)
        {
            if(custName!=null && contactNumber!=null && contactNumber!=null)
            {
                if(selectedProperty.getUserId()!=0)
                {
                    String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getVillaId(),selectedProperty.getUserId(),"villa");
                    SMTPService.sendVillaLeadEmail(custName,contactNumber,email,selectedProperty);
                    log.info("***** Successful submitted lead ******");
                }
                if(selectedProperty.getUserId()==0)
                {
                    int defaultUserId=1;
                    String saveMessage=udo.saveLeads(custName,contactNumber,email,selectedProperty.getVillaId(),defaultUserId,"villa");
                    log.info("***** Successful submitted lead ******");
                }

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }


        loadEntities();
    }

    public void reset() {
        PrimeFaces.current().resetInputs("form1:panelDialog");
    }




    public List<PromoImageModel> getPromoImageModel() {
        return promoImageModel;
    }


    public void setPromoImageModel(List<PromoImageModel> promoImageModel) {
        this.promoImageModel = promoImageModel;
    }




    public String getLocationMessage() {
        return locationMessage;
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




    public void setLocationMessage(String locationMessage) {
        this.locationMessage = locationMessage;
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




    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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



    @PreDestroy
    public void cleanup() {
        if (villaModel != null) {
            villaModel.clear();
        }
    }

    public String getNameVal() {
        return nameVal;
    }

    public void setNameVal(String nameVal) {
        this.nameVal = nameVal;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }

    public List<VillaModel> getVillaModel() {
        return villaModel;
    }

    public void setVillaModel(List<VillaModel> villaModel) {
        this.villaModel = villaModel;
    }

    public int getPromoTotalRecords() {
        return promoTotalRecords;
    }

    public void setPromoTotalRecords(int promoTotalRecords) {
        this.promoTotalRecords = promoTotalRecords;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}
