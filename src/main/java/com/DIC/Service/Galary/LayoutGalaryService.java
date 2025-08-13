package com.DIC.Service.Galary;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.Service.LayoutDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="layoutGalaryService")
@ViewScoped
public class LayoutGalaryService implements Serializable {

    private static final Logger log = LogManager.getLogger(LayoutGalaryService.class);

    private List<LayoutGalaryModel> layoutGalaryModel;
    private int activeIndex = 0;

    GeneralDAOImpl gDao;
    public LayoutGalaryService()
    {
        gDao=new GeneralDAOImpl();
        layoutGalaryModel=gDao.getLayoutGalary();
        System.out.println("Galary Size : "+layoutGalaryModel.size());
    }


    public List<LayoutGalaryModel> getLayoutGalaryModel() {
        return layoutGalaryModel;
    }

    public void setLayoutGalaryModel(List<LayoutGalaryModel> layoutGalaryModel) {
        this.layoutGalaryModel = layoutGalaryModel;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }
}
