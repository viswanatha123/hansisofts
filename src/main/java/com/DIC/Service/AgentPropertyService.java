package com.DIC.Service;


import SMTPService.SMTPService;
import com.DIC.DAO.Impl.PropertyDAOImpl;
import com.DIC.model.*;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name="agentPropertyService")
@ViewScoped
public class AgentPropertyService implements Serializable {


    private static final Logger log = Logger.getLogger(AgentPropertyService.class.getName());

    private int userId;

    private List<LayoutMode> layoutdetails;
    private List<AgriculturalModel> agriculturalModelList;
    private List<IndividualSiteModel> individualSiteList;
    private List<VillaModel> villaModel;


    private String updateResult;
    private String locationMessage;


    private String custName;
    private String contactNumber;
    private String email;
    private String comment;


    PropertyDAOImpl pDao;

    @PostConstruct
    public void init()
    {
        pDao=new PropertyDAOImpl();
    }

    public void getAgentPropertyDetails()
    {
            System.out.println("*************** called agent property details method ********************");
        layoutdetails=pDao.getLayoutDataByUserId(userId);
        agriculturalModelList=pDao.getAgriculturalDetailsByuserId(userId);
        individualSiteList=pDao.getIndiDataByUserIds(userId);
        villaModel=pDao.getVillaDataByUserId(userId);


        System.out.println("*********************** size **************** :"+layoutdetails.size());
        if(layoutdetails.size()!=0)
        {


        }else {

            updateResult="User id does not exist.";
        }


    }



    public void clearList() {
        userId=0;
        updateResult="";
       layoutdetails.clear();
        agriculturalModelList.clear();
       individualSiteList.clear();
        villaModel.clear();
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<LayoutMode> getLayoutdetails() {
        return layoutdetails;
    }

    public void setLayoutdetails(List<LayoutMode> layoutdetails) {
        this.layoutdetails = layoutdetails;
    }

    public String getUpdateResult() {
        return updateResult;
    }

    public void setUpdateResult(String updateResult) {
        this.updateResult = updateResult;
    }

    public String getLocationMessage() {
        return locationMessage;
    }

    public void setLocationMessage(String locationMessage) {
        this.locationMessage = locationMessage;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AgriculturalModel> getAgriculturalModelList() {
        return agriculturalModelList;
    }

    public void setAgriculturalModelList(List<AgriculturalModel> agriculturalModelList) {
        this.agriculturalModelList = agriculturalModelList;
    }

    public List<IndividualSiteModel> getIndividualSiteList() {
        return individualSiteList;
    }

    public void setIndividualSiteList(List<IndividualSiteModel> individualSiteList) {
        this.individualSiteList = individualSiteList;
    }

    public List<VillaModel> getVillaModel() {
        return villaModel;
    }

    public void setVillaModel(List<VillaModel> villaModel) {
        this.villaModel = villaModel;
    }
}
