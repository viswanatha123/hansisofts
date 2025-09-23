package com.DIC.DAO.Impl;

import com.DIC.DAO.QueryFactoryManager;
import com.DIC.Service.PropertyLink.QueryConstantsService;

public class QueryFactoryManagerImpl implements QueryFactoryManager {


    @Override
    public String getQueryByParam(String query) {

        System.out.println("******************* xxxxxxxxxxxxx *********************** " + query);
        String sql_query = "";


        if (query.equals(QueryConstantsService.param_bangalore_one_bhk)) {
            sql_query = QueryConstantsService.QueryLink.sql_bangalore_one_bhk;
        }
        if (query.equals(QueryConstantsService.param_bangalore_two_bhk)) {
            sql_query = QueryConstantsService.QueryLink.sql_bangalore_two_bhk;
        }
        if (query.equals(QueryConstantsService.param_bangalore_three_bhk)) {
            sql_query = QueryConstantsService.QueryLink.sql_bangalore_three_bhk;
        }
        if (query.equals(QueryConstantsService.param_bangalore_ready_to_move)) {
            sql_query = QueryConstantsService.QueryLink.sql_bangalore_ready_to_move;
        }
        if(query.equals(QueryConstantsService.param_flats_in_marthahalli))
        {
            sql_query= QueryConstantsService.QueryLink.sql_flats_in_marthahalli;
        }
        if(query.equals(QueryConstantsService.param_properties_in_marthahalli))
        {
            sql_query= QueryConstantsService.QueryLink.sql_properties_in_marthahalli;
        }
        if (query.equals(QueryConstantsService.param_hyderabad_one_bhk)) {
            sql_query = QueryConstantsService.QueryLink.sql_hyderabad_one_bhk;

        }

        return sql_query;
    }


    public String getQueryCountByParam(String query) {

        String sql_queryCount="";

        if(query.equals(QueryConstantsService.param_bangalore_one_bhk_count))
        {
            sql_queryCount= QueryConstantsService.QueryLink.sql_bangalore_one_bhk_count;

        }
        if(query.equals(QueryConstantsService.param_bangalore_two_bhk_count))
        {
            sql_queryCount= QueryConstantsService.QueryLink.sql_bangalore_two_bhk_count;

        }
        if(query.equals(QueryConstantsService.param_bangalore_three_bhk_count))
        {
            sql_queryCount= QueryConstantsService.QueryLink.sql_bangalore_three_bhk_count;

        }
        if(query.equals(QueryConstantsService.param_bangalore_ready_to_move_count))
        {
            sql_queryCount= QueryConstantsService.QueryLink.sql_bangalore_ready_to_move_count;

        }
        if(query.equals(QueryConstantsService.param_flats_in_marthahalli_count))
        {
            sql_queryCount= QueryConstantsService.QueryLink.sql_flats_in_marthahalli_count;

        }
        if(query.equals(QueryConstantsService.param_properties_in_marthahalli_count))
        {
            sql_queryCount= QueryConstantsService.QueryLink.sql_properties_in_marthahalli_count;

        }
        if(query.equals(QueryConstantsService.param_hyderabad_one_bhk_count))
        {
            sql_queryCount = QueryConstantsService.QueryLink.sql_hyderabad_one_bhk_count;
        }

        return sql_queryCount;

    }


}
