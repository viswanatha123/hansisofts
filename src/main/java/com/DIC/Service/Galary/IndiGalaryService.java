package com.DIC.Service.Galary;


import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import framework.utilities.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="indiGalaryService")
@RequestScoped
public class IndiGalaryService implements Serializable {
    private static final Logger log = LogManager.getLogger(IndiGalaryService.class);

    private List<IndiGalaryModel> indiGalaryModel;
    private String intiName;


    GeneralDAOImpl gDao;
    public IndiGalaryService()
    {
        gDao=new GeneralDAOImpl();
        HttpSession session = SessionUtils.getSession();
        IndividualSiteModel selectedProperty = (IndividualSiteModel) session.getAttribute("indiId");
        System.out.println("====================================== Selected property id ==========================="+selectedProperty.getInd_id());
        intiName=selectedProperty.getOwnerName();
        indiGalaryModel=gDao.getIndiGalary(selectedProperty);
        System.out.println("Galary Size : "+indiGalaryModel.size());
    }

    public List<IndiGalaryModel> getIndiGalaryModel() {
        return indiGalaryModel;
    }

    public void setIndiGalaryModel(List<IndiGalaryModel> indiGalaryModel) {
        this.indiGalaryModel = indiGalaryModel;
    }

    public String getIntiName() {
        return intiName;
    }

    public void setIntiName(String intiName) {
        this.intiName = intiName;
    }
}
