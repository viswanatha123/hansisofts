package com.DIC.Service.PropertyNewTab;


import SMTPService.SMTPService;
import com.DIC.DAO.Impl.PropertyDAOImpl;
import com.DIC.DAO.Impl.SMSService;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.Service.UserRoleService;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.UserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="indiSiteNewTabService")
@ViewScoped
public class IndiSiteNewTabService implements Serializable {


    private static final Logger log = LogManager.getLogger(IndiSiteNewTabService.class);

    String idParam;
    private int propertyId;

    private String custName;
    private String contactNumber;
    private String email;
    private String comment;


    private IndividualSiteModel indiData;

    private UserRoleService ur;
    private PropertyDAOImpl pdo;
    private UserDAOImpl udo;
    private SMSService sms;


    public IndiSiteNewTabService() {
        // constructor should be empty
    }

    @PostConstruct
    public void init() {
        pdo = new PropertyDAOImpl();
        ur = new UserRoleService();
        udo = new UserDAOImpl();
        sms = new SMSService();


        idParam = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("indiPropertyId");

        if (idParam != null) {
            try {
                propertyId = Integer.parseInt(idParam);

                System.out.println(" *************** Property id : " + propertyId);
                indiData=pdo.getIndiData(Integer.parseInt(idParam)).get(0);

                System.out.println("***************** India *************** Name ************* :"+indiData.getInd_id()+"    "+indiData.getOwnerName());

            } catch (NumberFormatException e) {
                // Handle invalid number
            }
        }
    }

    public void submit() {

        log.info("Selected property  : "+indiData.getInd_id()+"  "+indiData.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);


        if(indiData.getInd_id()!=0)
        {
            if(custName!=null && contactNumber!=null && contactNumber!=null)
            {
                if(indiData.getUserId()!=0)
                {

                    if(ur.getUserRole().contains("SMS"))
                    {
                        log.info("******** SMS Enabled *****************");
                        UserDetails userDetails=udo.getUser(indiData.getUserId());

                        //Twilio service
                        //sms.sendSMSLead(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);

                        // test2sms service
                        sms.sendSMSLeadText2sms(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);


                    }
                    else
                    {
                        log.info("******** SMS Didabled *****************");
                    }

                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,indiData.getInd_id(),indiData.getUserId(),"indi");
                    SMTPService.sendIndiLeadEmail(custName,contactNumber,email,indiData);
                    log.info("***** Successful submitted lead ******");
                }
                if(indiData.getUserId()==0)
                {
                    int defaultUserId=1;
                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,indiData.getInd_id(),defaultUserId,"indi");
                    log.info("***** Successful submitted lead ******");
                }

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }

        indiData=pdo.getIndiData(Integer.parseInt(idParam)).get(0);
        this.custName="";
        this.contactNumber="";
        this.email="";

    }

    public void reset() {
        PrimeFaces.current().resetInputs("form1:panelDialog");
    }

    public String getIdParam() {
        return idParam;
    }

    public void setIdParam(String idParam) {
        this.idParam = idParam;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public IndividualSiteModel getIndiData() {
        return indiData;
    }

    public void setIndiData(IndividualSiteModel indiData) {
        this.indiData = indiData;
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
}
