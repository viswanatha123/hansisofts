package com.DIC.Service.PropertyNewTab;


import SMTPService.SMTPService;
import com.DIC.DAO.Impl.PropertyDAOImpl;
import com.DIC.DAO.Impl.SMSService;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.Service.UserRoleService;
import com.DIC.model.UserDetails;
import com.DIC.model.VillaModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name="villaNewTabService")
@ViewScoped
public class VillaNewTabService implements Serializable {


    private static final Logger log = LogManager.getLogger(VillaNewTabService.class);

    String idParam;
    private int propertyId;
    private VillaModel villadata;

    private String menuId;


    private String custName;
    private String contactNumber;
    private String email;
    private String comment;


    private UserRoleService ur;
    private PropertyDAOImpl pdo;
    private UserDAOImpl udo;
    private SMSService sms;


    public VillaNewTabService() {
        // constructor should be empty
    }

    @PostConstruct
    public void init() {
        pdo=new PropertyDAOImpl();
        ur=new UserRoleService();
        udo=new UserDAOImpl();
        sms=new SMSService();


        idParam = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("villaPropertyId");

        FacesContext context = FacesContext.getCurrentInstance();
        menuId = context.getExternalContext().getRequestParameterMap().get("id");

        if (idParam != null) {
            try {
                propertyId = Integer.parseInt(idParam);

                System.out.println(" *************** Property id : " + propertyId);
                villadata=pdo.getVillaData(Integer.parseInt(idParam)).get(0);

                System.out.println("***************** Layout id *************** Name ************* :"+villadata.getVillaId()+"    "+villadata.getOwner_name());

            } catch (NumberFormatException e) {
                // Handle invalid number
            }
        }

    }

    public void submit() {
        System.out.println("-------------submit ----------------------");
        log.info("Selected property  : "+villadata.getVillaId()+"  "+villadata.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);


        if(villadata.getVillaId()!=0)
        {
            if(custName!=null && contactNumber!=null && contactNumber!=null)
            {
                if(villadata.getUserId()!=0)
                {

                    if(ur.getUserRole().contains("SMS"))
                    {
                        log.info("******** SMS Enabled *****************");
                        UserDetails userDetails=udo.getUser(villadata.getUserId());

                        //Twilio service
                        //sms.sendSMSLead(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);

                        // test2sms service
                        sms.sendSMSLeadText2sms(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);


                    }
                    else
                    {
                        log.info("******** SMS Didabled *****************");
                    }

                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,villadata.getVillaId(),villadata.getUserId(),"villa");
                    SMTPService.sendVillaLeadEmail(custName,contactNumber,email,villadata);
                    log.info("***** Successful submitted lead ******");
                }
                if(villadata.getUserId()==0)
                {
                    int defaultUserId=1;
                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,villadata.getVillaId(),defaultUserId,"villa");
                    log.info("***** Successful submitted lead ******");
                }

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }

        villadata=pdo.getVillaData(Integer.parseInt(idParam)).get(0);
        this.custName="";
        this.contactNumber="";
        this.email="";



    }

    public void reset() {
        PrimeFaces.current().resetInputs("form1:panelDialog");
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

    public VillaModel getVilladata() {
        return villadata;
    }

    public void setVilladata(VillaModel villadata) {
        this.villadata = villadata;
    }
}
