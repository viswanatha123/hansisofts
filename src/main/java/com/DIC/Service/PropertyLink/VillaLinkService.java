package com.DIC.Service.PropertyLink;


import com.DIC.DAO.Impl.QueryFactoryManagerImpl;
import com.DIC.DAO.QueryFactoryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name="villaLinkService")
@RequestScoped
public class VillaLinkService implements Serializable {

    private static final Logger log = LogManager.getLogger(VillaLinkService.class);

    public String nameVal;


    private String linkParam;

    QueryFactoryManager qfm;
    public VillaLinkService(){

        linkParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("villaLinkParam");

        qfm=new QueryFactoryManagerImpl();
        String query=qfm.getQueryByParam(linkParam);
        System.out.println("*********** Query Link *************** :"+query);

    }

    public String getNameVal() {
        return nameVal;
    }

    public void setNameVal(String nameVal) {
        this.nameVal = nameVal;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }
}
