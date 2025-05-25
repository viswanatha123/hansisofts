package com.DIC.Service.PropertyNewTab;


import SMTPService.SMTPService;
import com.DIC.DAO.Impl.PropertyDAOImpl;
import com.DIC.DAO.Impl.SMSService;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.Service.UserRoleService;
import com.DIC.model.AgriculturalModel;

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

@ManagedBean(name="agriculturalNewTabService")
@ViewScoped
public class AgriculturalNewTabService implements Serializable {

    private static final Logger log = LogManager.getLogger(AgriculturalNewTabService.class);


    private String idParam;
    private int propertyId;
    String name="Agricultue";


    private String custName;
    private String contactNumber;
    private String email;
    private String comment;


    private AgriculturalModel agriData;

    private PropertyDAOImpl pdo;
    private UserRoleService ur;
    private UserDAOImpl udo;
    private SMSService sms;

    @PostConstruct
    public void init() {

        pdo=new PropertyDAOImpl();
        ur=new UserRoleService();
        udo=new UserDAOImpl();
        sms=new SMSService();

        idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("agriPropertyId");
        System.out.println(" ___________________ Iparam -------------------- "+idParam);
            if (idParam != null) {
                try {
                    propertyId = Integer.parseInt(idParam);

                    System.out.println(" *************** Property id : " + propertyId);
                    agriData=pdo.getAgriData(Integer.parseInt(idParam)).get(0);


                    System.out.println("***************** Layout id *************** Name ************* :"+agriData.getAgriId()+"    "+agriData.getOwnerName());

                } catch (NumberFormatException e) {
                    // Handle invalid number
                }
            }

        }


    public void submit() {

        log.info("Selected property  : "+agriData.getAgriId()+"  "+agriData.getUserId()+"    "+custName+"  "+contactNumber+"    "+email);


        if(agriData.getAgriId()!=0)
        {
            if(custName!=null && contactNumber!=null && contactNumber!=null)
            {
                if(agriData.getUserId()!=0)
                {

                    if(ur.getUserRole().contains("SMS"))
                    {
                        log.info("******** SMS Enabled *****************");
                        UserDetails userDetails=udo.getUser(agriData.getUserId());
                      // test2sms service
                        sms.sendSMSLeadText2sms(userDetails.getPhone(), userDetails.getfName()+" "+userDetails.getlName(),custName,contactNumber);


                    }
                    else
                    {
                        log.info("******** SMS Didabled *****************");
                    }

                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,agriData.getAgriId(),agriData.getUserId(),"agri");
                    SMTPService.sendAgriLeadEmail(custName,contactNumber,email,agriData);
                    log.info("***** Successful submitted lead ******");
                }
                if(agriData.getUserId()==0)
                {
                    int defaultUserId=1;
                    String saveMessage=udo.saveLeads(custName,contactNumber,email,comment,agriData.getAgriId(),defaultUserId,"agri");
                    log.info("***** Successful submitted lead ******");
                }

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "We received your contact details", "Our representative contact you soon, Thank you..");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }

        agriData=pdo.getAgriData(Integer.parseInt(idParam)).get(0);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public AgriculturalModel getAgriData() {
        return agriData;
    }

    public void setAgriData(AgriculturalModel agriData) {
        this.agriData = agriData;
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
