package com.DIC.Service.Galary;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.Service.LayoutDetailService;
import com.DIC.model.LayoutMode;
import framework.utilities.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="layoutGalaryService")
@RequestScoped
public class LayoutGalaryService implements Serializable {

    private static final Logger log = LogManager.getLogger(LayoutGalaryService.class);

    private List<LayoutGalaryModel> layoutGalaryModel;
    private String layoutName;


    GeneralDAOImpl gDao;
    public LayoutGalaryService()
    {
        gDao=new GeneralDAOImpl();
        HttpSession session = SessionUtils.getSession();
        LayoutMode selectedProperty = (LayoutMode) session.getAttribute("LayoutId");
        System.out.println("====================================== Selected property id ==========================="+selectedProperty.getLayoutId());
        layoutName=selectedProperty.getName();
        layoutGalaryModel=gDao.getLayoutGalary(selectedProperty);
        System.out.println("Galary Size : "+layoutGalaryModel.size());
    }


    public List<LayoutGalaryModel> getLayoutGalaryModel() {
        return layoutGalaryModel;
    }

    public void setLayoutGalaryModel(List<LayoutGalaryModel> layoutGalaryModel) {
        this.layoutGalaryModel = layoutGalaryModel;
    }



    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }
}
