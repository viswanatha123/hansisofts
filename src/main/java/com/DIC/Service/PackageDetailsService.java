package com.DIC.Service;


import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.PackPriceModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name="packageDetailsService")
@ViewScoped
public class PackageDetailsService implements Serializable {

    private static final Logger log = Logger.getLogger(PackageDetailsService.class.getName());

    public List<PackPriceModel> packPriceModel;

    GeneralDAOImpl gDao;



    @PostConstruct
    public void init()
    {

        gDao=new GeneralDAOImpl();
       packPriceModel=gDao.getPackagePriceDetails();

        for(PackPriceModel p: packPriceModel)
        {
            System.out.println(" Package :"+p.getPackName());
        }



    }


    public List<PackPriceModel> getPackPriceModel() {
        return packPriceModel;
    }

    public void setPackPriceModel(List<PackPriceModel> packPriceModel) {
        this.packPriceModel = packPriceModel;
    }


}
