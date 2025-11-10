package com.DIC.Service.Galary;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.AgriculturalModel;
import framework.utilities.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="agriGalaryService")
@RequestScoped
public class AgriGalaryService implements Serializable {
    private static final Logger log = LogManager.getLogger(AgriGalaryService.class);

    private List<AgriGalaryModel> agriGalaryModel;
    private String agriName;


    GeneralDAOImpl gDao;
    public AgriGalaryService()
    {
        gDao=new GeneralDAOImpl();
        HttpSession session = SessionUtils.getSession();
        AgriculturalModel selectedProperty = (AgriculturalModel) session.getAttribute("agriId");
        System.out.println("====================================== Selected property id ==========================="+selectedProperty.getAgriId());
        agriName=selectedProperty.getOwnerName();
        agriGalaryModel=gDao.getAgriGalary(selectedProperty);
        System.out.println("Galary Size : "+agriGalaryModel.size());
    }

    public List<AgriGalaryModel> getAgriGalaryModel() {
        return agriGalaryModel;
    }

    public void setAgriGalaryModel(List<AgriGalaryModel> agriGalaryModel) {
        this.agriGalaryModel = agriGalaryModel;
    }

    public String getAgriName() {
        return agriName;
    }

    public void setAgriName(String agriName) {
        this.agriName = agriName;
    }
}
