package com.DIC.Service.PropertyNewTab;


import SMTPService.SMTPService;
import com.DIC.DAO.Impl.PropertyDAOImpl;
import com.DIC.DAO.Impl.SMSService;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.Service.UserRoleService;
import com.DIC.model.LayoutMode;
import com.DIC.model.UserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name="layoutNewTabService")
@ViewScoped
public class LayoutNewTabService implements Serializable {

    private static final Logger log = LogManager.getLogger(LayoutNewTabService.class);

    String idParam;
    private int propertyId;
    private LayoutMode  layoutDetails;


    private String custName;
    private String contactNumber;
    private String email;
    private String comment;


    private UserRoleService ur;
    private PropertyDAOImpl pdo;
    private UserDAOImpl udo;
    private SMSService sms;


    public LayoutNewTabService() {
        // constructor should be empty
    }

    @PostConstruct
    public void init() {
        pdo=new PropertyDAOImpl();
        ur=new UserRoleService();
        udo=new UserDAOImpl();
        sms=new SMSService();


        idParam = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("layoutPropertyId");
        if (idParam != null) {
            try {
                propertyId = Integer.parseInt(idParam);

                System.out.println(" *************** Property id : " + propertyId);
                layoutDetails=pdo.getLayoutData(Integer.parseInt(idParam)).get(0);

                System.out.println("***************** Layout id *************** Name ************* :"+layoutDetails.getLayoutId()+"    "+layoutDetails.getName());

            } catch (NumberFormatException e) {
                // Handle invalid number
            }
        }

    }


    public void submit() {

        log.info("Selected property  : "+layoutDetails.getLayoutId()+"  "+layoutDetails.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);


        if(layoutDetails.getLayoutId()!=0)
        {
            if(custName!=null && contactNumber!=null && contactNumber!=null)
            {
                if(layoutDetails.getUserId()!=0)
                {


                    if(ur.getUserRole().contains("SMS"))
                    {
                        log.info("******** SMS Enabled *****************");
                        UserDetails userDetails=udo.getUser(layoutDetails.getUserId());

                        //Twilio service
                        //sms.sendSMSLead(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);

                        // test2sms service
                        sms.sendSMSLeadText2sms(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);


                    }
                    else
                    {
                        log.info("******** SMS Didabled *****************");
                    }

                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,layoutDetails.getLayoutId(),layoutDetails.getUserId(),"layout");
                    SMTPService.sendLayoutLeadEmail(custName,contactNumber,email,layoutDetails);
                    log.info("***** Successful submitted lead ******");

                }
                if(layoutDetails.getUserId()==0)
                {
                    int defaultUserId=1;
                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,layoutDetails.getLayoutId(),defaultUserId,"layout");
                    log.info("***** Successful submitted lead ******");
                }

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }
        layoutDetails=pdo.getLayoutData(Integer.parseInt(idParam)).get(0);

        this.custName="";
        this.contactNumber="";
        this.email="";
        this.comment="";

    }
    public void reset() {
        PrimeFaces.current().resetInputs("form1:panelDialog");
    }


    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public LayoutMode getLayoutDetails() {
        return layoutDetails;
    }

    public void setLayoutDetails(LayoutMode layoutDetails) {
        this.layoutDetails = layoutDetails;
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
