package com.DIC.Service;

import SMTPService.SMTPService;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.BankAgentModel;
import com.DIC.model.UserDetails;
import framework.utilities.Constants;
import framework.utilities.UtilConstants;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;

@ManagedBean(name="bankAgentService")
@SessionScoped
public class BankAgentService implements Serializable {

    private static final Logger log = Logger.getLogger(BankAgentService.class.getName());

    private int bankAgentId;

    private String agentName;
    private String gender="Male";
    private String bankName;
    private String contactNo;
    private String email;
    private String address;
    private String comment;

    private UploadedFile file;
    private StreamedContent streamedContent;
    private InputStream inputStream;
    private Date create_date;
    private int is_active;


    private String statusMessage;


    GeneralDAOImpl gdao;

    public BankAgentService()
    {

    }

    @PostConstruct
    public void init()
    {
        log.info("Loading Bank Agent RegistService init()");
        gdao=new GeneralDAOImpl();
   }
    public void save() {
        if (file != null) {
            try {

                BankAgentModel bankAgentModel = new BankAgentModel();
                bankAgentModel.setAgentName(agentName);
                bankAgentModel.setGender(gender);
                bankAgentModel.setBankName(bankName);
                bankAgentModel.setContactNo(contactNo);
                bankAgentModel.setEmail(email);
                bankAgentModel.setAddress(address);
                bankAgentModel.setComment(comment);
                bankAgentModel.setInputStream(file.getInputStream());
                bankAgentModel.setFile(file);
                bankAgentModel.setCreate_date(create_date);
                bankAgentModel.setIs_active(is_active);
               statusMessage = gdao.saveAgentRegist(bankAgentModel);
                    this.agentName = "";
                    this.gender = "Male";
                    this.bankName = "";
                    this.contactNo = "";
                    this.email = "";
                    this.address = "";
                    this.comment = "";


            } catch(Exception e){
                    System.out.println("Exception-File Upload." + e.getMessage());
            }
        }
    }


    public void clearMessage() {
             statusMessage = "";
    }
    public int getBankAgentId() {
        return bankAgentId;
    }

    public void setBankAgentId(int bankAgentId) {
        this.bankAgentId = bankAgentId;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
