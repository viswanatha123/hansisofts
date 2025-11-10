package com.DIC.Service.Galary;


import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.VillaModel;
import framework.utilities.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="villaGalaryService")
@RequestScoped
public class VillaGalaryService implements Serializable {
    private static final Logger log = LogManager.getLogger(VillaGalaryService.class);

    private List<VillaGalaryModel> villaGalaryModel;
    private String villaName;


    GeneralDAOImpl gDao;
    public VillaGalaryService()
    {
        gDao=new GeneralDAOImpl();
        HttpSession session = SessionUtils.getSession();
        VillaModel selectedProperty = (VillaModel) session.getAttribute("villaId");
        System.out.println("====================================== Selected property id ==========================="+selectedProperty.getVillaId());
        villaName=selectedProperty.getOwner_name();
        villaGalaryModel=gDao.getVillaGalary(selectedProperty);
        System.out.println("Galary Size : "+villaGalaryModel.size());
    }

    public List<VillaGalaryModel> getVillaGalaryModel() {
        return villaGalaryModel;
    }

    public void setVillaGalaryModel(List<VillaGalaryModel> villaGalaryModel) {
        this.villaGalaryModel = villaGalaryModel;
    }

    public String getVillaName() {
        return villaName;
    }

    public void setVillaName(String villaName) {
        this.villaName = villaName;
    }
}
