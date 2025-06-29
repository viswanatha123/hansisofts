package com.DIC.DAO;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


public interface QueryFactoryManager {

    /* this method provide query based on pass parameter */
        public String getQueryByParam(String query);

        public String getQueryCountByParam(String query);
}
