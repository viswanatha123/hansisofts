package com.DIC.DAO.Impl;

import com.DIC.DAO.QueryFactoryManager;
import com.DIC.Service.PropertyLink.QueryConstantsService;

public class QueryFactoryManagerImpl implements QueryFactoryManager {


    @Override
    public String getQueryByParam(String query) {

        String sql_query="";

        if(query.equals(QueryConstantsService.param_bangalore_one_bhk))
        {
            sql_query= QueryConstantsService.QueryLink.sql_bangalore_one_bhk;
        }
        if(query.equals(QueryConstantsService.param_bangalore_two_bhk))
        {
            sql_query= QueryConstantsService.QueryLink.sql_bangalore_two_bhk;
        }



        return sql_query;
    }
}
