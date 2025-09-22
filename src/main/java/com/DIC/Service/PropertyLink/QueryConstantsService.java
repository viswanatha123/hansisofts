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

                public static String param_flats_in_marthahalli="param_flats_in_marthahalli";
                public static String param_flats_in_marthahalli_count="param_flats_in_marthahalli_count";

                public static String param_flats_in_whitefield = "param_flats_in_whitefield";
                public static String param_flats_in_whitefield_count = "param_flats_in_whitefield_count";

                public static String param_hyderabad_one_bhk="param_hyderabad_one_bhk";
                public static String param_hyderabad_one_bhk_count="param_hyderabad_one_bhk_count";




                public static String param_bangalore_one_bhk_head="1 BHK Flat in Sale";
                public static String param_bangalore_two_bhk_head="2 BHK Flat in Sale";
                public static String param_bangalore_three_bhk_head="3 BHK Flat in Sale";
                public static String param_bangalore_ready_to_move_head="Ready To Move  Bangalore";
                public static String param_flats_in_marthahalli_head="Flats in Marthahalli";
                public static String param_flats_in_whitefield_head = "Flats in Whitefield";
                public static String param_hyderabad_one_bhk_head="1 BHK Flat in Sale";





    public interface QueryLink{

                public static final String  sql_bangalore_one_bhk="select * from villa_plot where bed_rooms='1' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_one_bhk_count="select count(*) from villa_plot where bed_rooms='1' and prim_location ='Bangalore'";



                public static final String  sql_bangalore_two_bhk="select * from villa_plot where bed_rooms='2' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_two_bhk_count="select count(*) from villa_plot where bed_rooms='2' and prim_location ='Bangalore'";


                public static final String  sql_bangalore_three_bhk="select * from villa_plot where bed_rooms='3' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_three_bhk_count="select count(*) from villa_plot where bed_rooms='3' and prim_location ='Bangalore'";


                public static final String  sql_bangalore_ready_to_move="select * from villa_plot where pro_avail='Ready To Move' and prim_location ='Bangalore' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_bangalore_ready_to_move_count="select count(*) from villa_plot where pro_avail='Ready To Move' and prim_location ='Bangalore'";


                public static final String  sql_flats_in_marthahalli="select * from villa_plot where property_type ='Flat' and prim_location ='Bangalore' and seco_location ='Maratha Halli' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_flats_in_marthahalli_count="select count(*) from villa_plot where property_type ='Flat' and prim_location ='Bangalore' and seco_location ='Maratha Halli'";

                public static final String  sql_flats_in_whitefield = "select * from villa_plot where property_type ='Flat' and prim_location ='Bangalore' and seco_location ='Whitefield' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_flats_in_whitefield_count = "select count(*) from villa_plot where property_type ='Flat' and prim_location ='Bangalore' and seco_location ='Whitfield'";

                public static final String  sql_hyderabad_one_bhk="select * from villa_plot where bed_rooms='1' and prim_location ='Hyderabad' order by create_date desc LIMIT ? OFFSET ?";
                public static final String  sql_hyderabad_one_bhk_count="select count(*) from villa_plot where bed_rooms='1' and prim_location ='Hyderabad'";

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


    public  String getParam_flats_in_marthahalli() {
        return param_flats_in_marthahalli;
    }

    public  void setParam_flats_in_marthahalli(String param_flats_in_marthahalli) {
        this.param_flats_in_marthahalli = param_flats_in_marthahalli;
    }

    public String getParam_flats_in_whitefield() {return param_flats_in_whitefield; }

    public void setParam_flats_in_whitefield(String param_flats_in_whitefield) {
        this.param_flats_in_whitefield = param_flats_in_whitefield;
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


    public String getParam_flats_in_marthahalli_count() {
        return param_flats_in_marthahalli_count;
    }

    public void setParam_flats_in_marthahalli_count(String param_flats_in_marthahalli_count) {
        this.param_flats_in_marthahalli_count = param_flats_in_marthahalli_count;
    }
    public String getParam_flats_in_whitefield_count() {return param_flats_in_whitefield_count;}

    public void setParam_flats_in_whitefield_count(String param_flats_in_whitefield_count) {
        this.param_flats_in_whitefield_count = param_flats_in_whitefield_count;
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

    public String getParam_flats_in_marthahalli_head() {
        return param_flats_in_marthahalli_head;
    }

    public void setParam_flats_in_marthahalli_head(String param_flats_in_marthahalli_head) {
        this.param_flats_in_marthahalli_head = param_flats_in_marthahalli_head;
    }
    public String getParam_flats_in_whitefield_head() {
        return param_flats_in_whitefield_head;
    }

    public void setParam_flats_in_whitefield_head(String param_flats_in_whitefield_head) {
        this.param_flats_in_whitefield_head = param_flats_in_whitefield_head;
    }


        public String getParam_hyderabad_one_bhk() {
        return param_hyderabad_one_bhk;
    }

    public void setParam_hyderabad_one_bhk(String param_hyderabad_one_bhk) {
        QueryConstantsService.param_hyderabad_one_bhk = param_hyderabad_one_bhk;
    }

    public String getParam_hyderabad_one_bhk_count() {
        return param_hyderabad_one_bhk_count;
    }

    public void setParam_hyderabad_one_bhk_count(String param_hyderabad_one_bhk_count) {
        QueryConstantsService.param_hyderabad_one_bhk_count = param_hyderabad_one_bhk_count;
    }

    public String getParam_hyderabad_one_bhk_head() {
        return param_hyderabad_one_bhk_head;
    }

    public void setParam_hyderabad_one_bhk_head(String param_hyderabad_one_bhk_head) {
        QueryConstantsService.param_hyderabad_one_bhk_head = param_hyderabad_one_bhk_head;
    }



}
