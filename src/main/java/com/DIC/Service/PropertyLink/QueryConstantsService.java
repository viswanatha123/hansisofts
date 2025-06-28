package com.DIC.Service.PropertyLink;


import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean(name="queryConstantsService")
@ApplicationScoped
public class QueryConstantsService implements Serializable {



                public static String param_bangalore_one_bhk="param_bangalore_one_bhk";
                public static String param_bangalore_one_bhk_count="param_bangalore_one_bhk_count";

                public static String param_bangalore_two_bhk="param_bangalore_two_bhk";





            public interface QueryLink{

                public static final String  sql_bangalore_one_bhk="select * from villa_plot where bed_rooms='1' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_one_bhk_count="select count(*) from villa_plot where bed_rooms='1'";



                public static final String  sql_bangalore_two_bhk="select * from villa_plot where bed_rooms='2' order by create_date desc LIMIT ? OFFSET ?";

            }


    public String getParam_bangalore_one_bhk() {
        return param_bangalore_one_bhk;
    }

    public void setParam_bangalore_one_bhk(String param_bangalore_one_bhk) {
        this.param_bangalore_one_bhk = param_bangalore_one_bhk;
    }

    public  String getParam_bangalore_two_bhk() {
        return param_bangalore_two_bhk;
    }

    public  void setParam_bangalore_two_bhk(String param_bangalore_two_bhk) {
        this.param_bangalore_two_bhk = param_bangalore_two_bhk;
    }

    public String getParam_bangalore_one_bhk_count() {
        return param_bangalore_one_bhk_count;
    }

    public void setParam_bangalore_one_bhk_count(String param_bangalore_one_bhk_count) {
        this.param_bangalore_one_bhk_count = param_bangalore_one_bhk_count;
    }
}
