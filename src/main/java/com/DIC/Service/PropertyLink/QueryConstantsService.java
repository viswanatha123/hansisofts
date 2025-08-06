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
                public static String param_bangalore_two_bhk_count="param_bangalore_two_bhk_count";

                public static String param_bangalore_three_bhk="param_bangalore_three_bhk";
                public static String param_bangalore_three_bhk_count="param_bangalore_three_bhk_count";

                public static String param_bangalore_ready_to_move="param_bangalore_ready_to_move";
                public static String param_bangalore_ready_to_move_count="param_bangalore_ready_to_move_count";

                public static String param_bangalore_one_bhk_head="1 BHK Flat in Sale";
                public static String param_bangalore_two_bhk_head="2 BHK Flat in Sale";
                public static String param_bangalore_three_bhk_head="3 BHK Flat in Sale";
                public static String param_bangalore_ready_to_move_head="Ready To Move  Bangalore";





    public interface QueryLink{

                public static final String  sql_bangalore_one_bhk="select * from villa_plot where bed_rooms='1' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_one_bhk_count="select count(*) from villa_plot where bed_rooms='1' and prim_location ='Bangalore'";



                public static final String  sql_bangalore_two_bhk="select * from villa_plot where bed_rooms='2' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_two_bhk_count="select count(*) from villa_plot where bed_rooms='2' and prim_location ='Bangalore'";


                public static final String  sql_bangalore_three_bhk="select * from villa_plot where bed_rooms='3' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_three_bhk_count="select count(*) from villa_plot where bed_rooms='3' and prim_location ='Bangalore'";


                public static final String  sql_bangalore_ready_to_move="select * from villa_plot where pro_avail='Ready To Move' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_ready_to_move_count="select count(*) from villa_plot where pro_avail='Ready To Move' and prim_location ='Bangalore'";



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

    public  String getParam_bangalore_three_bhk() {
        return param_bangalore_three_bhk;
    }

    public  void setParam_bangalore_three_bhk(String param_bangalore_three_bhk) {
        this.param_bangalore_three_bhk = param_bangalore_three_bhk;
    }
    public  String getParam_bangalore_ready_to_move() {
        return param_bangalore_ready_to_move;
    }

    public  void setParam_bangalore_ready_to_move(String param_bangalore_ready_to_move) {
        this.param_bangalore_ready_to_move = param_bangalore_ready_to_move;
    }
    public String getParam_bangalore_one_bhk_count() {
        return param_bangalore_one_bhk_count;
    }

    public void setParam_bangalore_one_bhk_count(String param_bangalore_one_bhk_count) {
        this.param_bangalore_one_bhk_count = param_bangalore_one_bhk_count;
    }

    public String getParam_bangalore_two_bhk_count() {
        return param_bangalore_two_bhk_count;
    }

    public void setParam_bangalore_two_bhk_count(String param_bangalore_two_bhk_count) {
        this.param_bangalore_two_bhk_count = param_bangalore_two_bhk_count;
    }

    public String getParam_bangalore_three_bhk_count() {
        return param_bangalore_three_bhk_count;
    }

    public void setParam_bangalore_three_bhk_count(String param_bangalore_three_bhk_count) {
        this.param_bangalore_three_bhk_count = param_bangalore_three_bhk_count;
    }
    public String getParam_bangalore_ready_to_move_count() {
        return param_bangalore_ready_to_move_count;
    }

    public void setParam_bangalore_ready_to_move_count(String param_bangalore_ready_to_move_count) {
        this.param_bangalore_ready_to_move_count = param_bangalore_ready_to_move_count;
    }

    public  String getParam_bangalore_one_bhk_head() {
        return param_bangalore_one_bhk_head;
    }

    public  void setParam_bangalore_one_bhk_head(String param_bangalore_one_bhk_head) {
        this.param_bangalore_one_bhk_head = param_bangalore_one_bhk_head;
    }

    public String getParam_bangalore_two_bhk_head() {
        return param_bangalore_two_bhk_head;
    }

    public void setParam_bangalore_two_bhk_head(String param_bangalore_two_bhk_head) {
        this.param_bangalore_two_bhk_head = param_bangalore_two_bhk_head;
    }

    public String getParam_bangalore_three_bhk_head() {
        return param_bangalore_three_bhk_head;
    }

    public void setParam_bangalore_three_bhk_head(String param_bangalore_three_bhk_head) {
        this.param_bangalore_three_bhk_head = param_bangalore_three_bhk_head;
    }
    public String getParam_bangalore_ready_to_move_head() {
        return param_bangalore_ready_to_move_head;
    }

    public void setParam_bangalore_ready_to_move_head(String param_bangalore_ready_to_move_head) {
        this.param_bangalore_ready_to_move_head = param_bangalore_ready_to_move_head;
    }
}
