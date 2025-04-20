package com.DIC.DAO.Impl;


import com.DIC.DAO.ConnectionDAO;
import com.DIC.model.AgriculturalModel;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.VillaModel;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class PropertyDAOImpl {

    private static final Logger log = LogManager.getLogger(PropertyDAOImpl.class);

    interface Constants {
        // SQL
        interface SQL {
            String SQL_layout_DIALOG="select * from hansi_layout where layout_id = ?";
            String SQL_AGRI_DIALOG="select * from hansi_agricultural where agri_id = ?";
            String SQL_INDI_DIALOG="select * from hansi_individual_site where ind_id = ?";
            String SQL_VILLA_DIALOG="select * from villa_plot where villa_id = ?";


            String SQL_layout_BY_USERID="select * from hansi_layout where user_id = ?";
            String SQL_agri_BY_USERID="select * from hansi_agricultural where user_id = ?";
            String SQL_INDI_BY_USERID="select * from hansi_individual_site where user_id = ?";
            String SQL_VILLA_BY_USERID="select * from villa_plot where user_id = ?";
        }

    }

    public List<LayoutMode> getLayoutData(int layoutId)
    {
        ConnectionDAO condao;
        BasicDataSource bds=null;
        Connection con = null;
        PreparedStatement pstmt = null;

        List<LayoutMode> layoutModeList = new ArrayList<>();

        try {

            condao=new ConnectionDAO();
            bds=condao.getDataSource();
            con=bds.getConnection();

            StringBuilder sq_layout_dialog = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_layout_DIALOG);

            pstmt = con.prepareStatement(sq_layout_dialog.toString());
            pstmt.setInt(1, layoutId);

            ResultSet rs = pstmt.executeQuery();

            while ( rs.next() ) {
                LayoutMode layoutMode=new LayoutMode();
                layoutMode.setLayoutId(rs.getInt("layout_id"));
                layoutMode.setName(rs.getString("name"));
                layoutMode.setLocation(rs.getString("location"));
                layoutMode.setPersqft(rs.getInt("persqft"));
                layoutMode.setContactOwner(rs.getString("contact_owner"));
                layoutMode.setOwnerName(rs.getString("owner_name"));
                layoutMode.setWonership(rs.getString("wonership"));
                layoutMode.setIs_active(rs.getInt("is_active"));
                layoutMode.setTransaction(rs.getString("transaction"));
                layoutMode.setComment(rs.getString("comment"));
                layoutMode.setLength(rs.getInt("length"));
                layoutMode.setWidth(rs.getInt("width"));
                int plotArea=rs.getInt("length")*rs.getInt("width");
                layoutMode.setCost(plotArea*rs.getInt("persqft"));
                layoutMode.setPrimLocation(rs.getString("prim_location"));
                layoutMode.setSecoLocation(rs.getString("seco_location"));
                layoutMode.setSwimingPool(rs.getString("swimingpool"));
                layoutMode.setPlayground(rs.getString("playground"));
                layoutMode.setPark(rs.getString("park"));
                layoutMode.setWall(rs.getString("wall"));
                layoutMode.setCommunity(rs.getString("community"));
                layoutMode.setFacing(rs.getString("facing"));
                layoutMode.setAgentName(rs.getString("agent_name"));
                layoutMode.setTotalPrice(indianCurrence(plotArea*rs.getInt("persqft")));
                layoutMode.setCreatedOnDate(rs.getDate("create_date"));
                layoutMode.setUserId(rs.getInt("user_id"));



                InputStream imageStream = rs.getBinaryStream("image");
                if (rs.getBytes("image").length!=0)  {


                    BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

                    layoutMode.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> bufferedStream) // Stream the content directly
                            .build());
                }
                else
                {


                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        layoutMode.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());


                    }

                }

                layoutModeList.add(layoutMode);
            }


            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }finally {
            // Close the pool (important for proper shutdown)
            try {
                if (bds != null) {
                    bds.close();
                }
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return layoutModeList;
    }


    //********************************* get layout details by user id *************************
    public List<LayoutMode> getLayoutDataByUserId(int userId)
    {
        ConnectionDAO condao;
        BasicDataSource bds=null;
        Connection con = null;
        PreparedStatement pstmt = null;

        List<LayoutMode> layoutModeList = new ArrayList<>();

        try {

            condao=new ConnectionDAO();
            bds=condao.getDataSource();
            con=bds.getConnection();

            StringBuilder sq_layout_by_userid = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_layout_BY_USERID);

            pstmt = con.prepareStatement(sq_layout_by_userid.toString());
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while ( rs.next() ) {
                LayoutMode layoutMode=new LayoutMode();
                layoutMode.setLayoutId(rs.getInt("layout_id"));
                layoutMode.setName(rs.getString("name"));
                layoutMode.setLocation(rs.getString("location"));
                layoutMode.setPersqft(rs.getInt("persqft"));
                layoutMode.setContactOwner(rs.getString("contact_owner"));
                layoutMode.setOwnerName(rs.getString("owner_name"));
                layoutMode.setWonership(rs.getString("wonership"));
                layoutMode.setIs_active(rs.getInt("is_active"));
                layoutMode.setTransaction(rs.getString("transaction"));
                layoutMode.setComment(rs.getString("comment"));
                layoutMode.setLength(rs.getInt("length"));
                layoutMode.setWidth(rs.getInt("width"));
                int plotArea=rs.getInt("length")*rs.getInt("width");
                layoutMode.setCost(plotArea*rs.getInt("persqft"));
                layoutMode.setPrimLocation(rs.getString("prim_location"));
                layoutMode.setSecoLocation(rs.getString("seco_location"));
                layoutMode.setSwimingPool(rs.getString("swimingpool"));
                layoutMode.setPlayground(rs.getString("playground"));
                layoutMode.setPark(rs.getString("park"));
                layoutMode.setWall(rs.getString("wall"));
                layoutMode.setCommunity(rs.getString("community"));
                layoutMode.setFacing(rs.getString("facing"));
                layoutMode.setAgentName(rs.getString("agent_name"));
                layoutMode.setTotalPrice(indianCurrence(plotArea*rs.getInt("persqft")));
                layoutMode.setCreatedOnDate(rs.getDate("create_date"));
                layoutMode.setUserId(rs.getInt("user_id"));



                InputStream imageStream = rs.getBinaryStream("image");
                if (rs.getBytes("image").length!=0)  {


                    BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

                    layoutMode.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> bufferedStream) // Stream the content directly
                            .build());
                }
                else
                {


                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        layoutMode.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());


                    }

                }

                layoutModeList.add(layoutMode);
            }


            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }finally {
            // Close the pool (important for proper shutdown)
            try {
                if (bds != null) {
                    bds.close();
                }
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return layoutModeList;
    }



    //************************************AgriculturalDetails******************************************//

    public List<AgriculturalModel> getAgriData(int agriId)
    {
        ConnectionDAO condao;
        BasicDataSource bds=null;
        Connection con = null;
        PreparedStatement pstmt = null;

        log.info("### : get started :: getAgriculturalDetails() ");
        List<AgriculturalModel> agriculturalModelList = new ArrayList<>();
        try {
            condao=new ConnectionDAO();
            bds=condao.getDataSource();
            con=bds.getConnection();

            StringBuilder sql_agri_dialog = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_AGRI_DIALOG);
            log.info("###: Query : "+sql_agri_dialog.toString());

            pstmt = con.prepareStatement(sql_agri_dialog.toString());
            pstmt.setInt(1, agriId);
            ResultSet rs = pstmt.executeQuery();
            while ( rs.next() ) {
                AgriculturalModel agriculturalModel=new AgriculturalModel();

                agriculturalModel.setAgriId(rs.getInt("agri_id"));
                agriculturalModel.setOwnerName(rs.getString("owner_name"));
                agriculturalModel.setContactNo(rs.getString("contact_no"));
                agriculturalModel.setSurveyNo(rs.getString("survey_no"));
                agriculturalModel.setLocation(rs.getString("location"));
                agriculturalModel.setWonership(rs.getString("wonership"));
                agriculturalModel.setTransaction(rs.getString("transaction"));
                agriculturalModel.setPerCent(rs.getInt("per_cent"));
                agriculturalModel.setNumberCents(rs.getInt("number_cents"));
                agriculturalModel.setWaterSource(rs.getString("water_source"));
                agriculturalModel.setCrop(rs.getString("crop"));
                agriculturalModel.setPrimLocation(rs.getString("prim_location"));
                agriculturalModel.setSecoLocation(rs.getString("seco_location"));
                agriculturalModel.setComment(rs.getString("comment"));
                agriculturalModel.setAgentName(rs.getString("agent_Name"));

                agriculturalModel.setTotalPrice(indianCurrence(rs.getInt("per_cent")*rs.getInt("number_cents")));
                agriculturalModel.setCreatedOnDate(rs.getDate("create_date"));
                agriculturalModel.setUserId(rs.getInt("user_id"));

                log.info("getAgriculturalDetails () : "+rs.getInt("agri_id")+"  "+rs.getString("owner_name"));

                InputStream imageStream = rs.getBinaryStream("image");
                if(rs.getBytes("image").length!=0) {
                    BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

                    agriculturalModel.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> bufferedStream) // Stream the content directly
                            .build());
                }

                else
                {
                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        agriculturalModel.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());
                    }

                }
                agriculturalModelList.add(agriculturalModel);


            }

            rs.close();
            //log.info("### : *** Connection Closed from getActiveModelList()");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred: {}", e.getMessage());
        }finally {
            // Close the pool (important for proper shutdown)
            try {
                if (bds != null) {
                    bds.close();
                }
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return agriculturalModelList;
    }


    //************************************AgriculturalDetails******************************************//

    public List<AgriculturalModel> getAgriculturalDetailsByuserId(int userId)
    {
        ConnectionDAO condao;
        BasicDataSource bds=null;
        Connection con = null;
        PreparedStatement pstmt = null;

        log.info("### : get started :: getAgriculturalDetails() ");
        List<AgriculturalModel> agriculturalModelList = new ArrayList<>();
        try {
            condao=new ConnectionDAO();
            bds=condao.getDataSource();
            con=bds.getConnection();

            StringBuilder sql_agri_by_userid = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_agri_BY_USERID);
            log.info("###: Query : "+sql_agri_by_userid.toString());

            pstmt = con.prepareStatement(sql_agri_by_userid.toString());
            pstmt.setInt(1, userId);


            ResultSet rs = pstmt.executeQuery();
            while ( rs.next() ) {
                AgriculturalModel agriculturalModel=new AgriculturalModel();

                agriculturalModel.setAgriId(rs.getInt("agri_id"));
                agriculturalModel.setOwnerName(rs.getString("owner_name"));
                agriculturalModel.setContactNo(rs.getString("contact_no"));
                agriculturalModel.setSurveyNo(rs.getString("survey_no"));
                agriculturalModel.setLocation(rs.getString("location"));
                agriculturalModel.setWonership(rs.getString("wonership"));
                agriculturalModel.setTransaction(rs.getString("transaction"));
                agriculturalModel.setPerCent(rs.getInt("per_cent"));
                agriculturalModel.setNumberCents(rs.getInt("number_cents"));
                agriculturalModel.setWaterSource(rs.getString("water_source"));
                agriculturalModel.setCrop(rs.getString("crop"));
                agriculturalModel.setPrimLocation(rs.getString("prim_location"));
                agriculturalModel.setSecoLocation(rs.getString("seco_location"));
                agriculturalModel.setComment(rs.getString("comment"));
                agriculturalModel.setAgentName(rs.getString("agent_Name"));

                agriculturalModel.setTotalPrice(indianCurrence(rs.getInt("per_cent")*rs.getInt("number_cents")));
                agriculturalModel.setCreatedOnDate(rs.getDate("create_date"));
                agriculturalModel.setUserId(rs.getInt("user_id"));

                log.info("getAgriculturalDetails () : "+rs.getInt("agri_id")+"  "+rs.getString("owner_name"));

                InputStream imageStream = rs.getBinaryStream("image");
                if(rs.getBytes("image").length!=0) {
                    BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

                    agriculturalModel.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> bufferedStream) // Stream the content directly
                            .build());
                }

                else
                {
                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        agriculturalModel.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());
                    }

                }
                agriculturalModelList.add(agriculturalModel);


            }

            rs.close();
            //log.info("### : *** Connection Closed from getActiveModelList()");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred: {}", e.getMessage());
        }finally {
            // Close the pool (important for proper shutdown)
            try {
                if (bds != null) {
                    bds.close();
                }
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return agriculturalModelList;
    }



    //************************************IndividualSiteDetails******************************************//

    public List<IndividualSiteModel> getIndiData(int indiiD)
    {
        ConnectionDAO condao;
        BasicDataSource bds=null;
        Connection con = null;
        PreparedStatement pstmt = null;

        log.info("### : get started :: getIndividualSiteDetails() ");
        List<IndividualSiteModel> individualSiteModelList = new ArrayList<>();
        try {

            condao=new ConnectionDAO();
            bds=condao.getDataSource();
            con=bds.getConnection();

            StringBuilder sql_INDI_DIALOG = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_INDI_DIALOG);
            log.info("###: Query : "+sql_INDI_DIALOG.toString());
            pstmt = con.prepareStatement(sql_INDI_DIALOG.toString());
            pstmt.setInt(1, indiiD);

            ResultSet rs = pstmt.executeQuery();
            while ( rs.next() ) {
                IndividualSiteModel individualSiteModel=new IndividualSiteModel();


                individualSiteModel.setInd_id(rs.getInt("ind_id"));
                individualSiteModel.setOwnerName(rs.getString("owner_name"));
                individualSiteModel.setLocation(rs.getString("location"));
                individualSiteModel.setContactNo(rs.getString("contact_no"));
                individualSiteModel.setSiteNo(rs.getString("site_no"));
                individualSiteModel.setPersqft(rs.getInt("persqft"));
                individualSiteModel.setLength(rs.getInt("length"));
                individualSiteModel.setWidth(rs.getInt("width"));
                int plotArea=rs.getInt("length")*rs.getInt("width");
                individualSiteModel.setCost(plotArea*rs.getInt("persqft"));
                individualSiteModel.setWonership(rs.getString("wonership"));
                individualSiteModel.setTransaction(rs.getString("transaction"));
                individualSiteModel.setPrimLocation(rs.getString("prim_location"));
                individualSiteModel.setSecoLocation(rs.getString("seco_location"));
                individualSiteModel.setComment(rs.getString("comment"));
                individualSiteModel.setFacing(rs.getString("facing"));
                individualSiteModel.setAgentName(rs.getString("agent_name"));
                individualSiteModel.setTotalPrice(indianCurrence(rs.getInt("persqft") * (rs.getInt("length") * rs.getInt("width"))));
                individualSiteModel.setCreatedOnDate(rs.getDate("create_date"));
                individualSiteModel.setUserId(rs.getInt("user_id"));


                InputStream imageStream = rs.getBinaryStream("image");
                if(rs.getBytes("image").length!=0) {
                    BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

                    individualSiteModel.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> bufferedStream) // Stream the content directly
                            .build());
                }
                else
                {
                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        individualSiteModel.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());
                    }

                }
                individualSiteModelList.add(individualSiteModel);


            }


            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }finally {
            // Close the pool (important for proper shutdown)
            try {
                if (bds != null) {
                    bds.close();
                }
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return individualSiteModelList;
    }


    //************************************IndividualSiteDetails******************************************//

    public List<IndividualSiteModel> getIndiDataByUserIds(int userId)
    {
        ConnectionDAO condao;
        BasicDataSource bds=null;
        Connection con = null;
        PreparedStatement pstmt = null;

        log.info("### : get started :: getIndividualSiteDetails() ");
        List<IndividualSiteModel> individualSiteModelList = new ArrayList<>();
        try {

            condao=new ConnectionDAO();
            bds=condao.getDataSource();
            con=bds.getConnection();

            StringBuilder sql_INDI_DIALOG_by_userid = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_INDI_BY_USERID);
            log.info("###: Query : "+sql_INDI_DIALOG_by_userid.toString());
            pstmt = con.prepareStatement(sql_INDI_DIALOG_by_userid.toString());
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while ( rs.next() ) {
                IndividualSiteModel individualSiteModel=new IndividualSiteModel();


                individualSiteModel.setInd_id(rs.getInt("ind_id"));
                individualSiteModel.setOwnerName(rs.getString("owner_name"));
                individualSiteModel.setLocation(rs.getString("location"));
                individualSiteModel.setContactNo(rs.getString("contact_no"));
                individualSiteModel.setSiteNo(rs.getString("site_no"));
                individualSiteModel.setPersqft(rs.getInt("persqft"));
                individualSiteModel.setLength(rs.getInt("length"));
                individualSiteModel.setWidth(rs.getInt("width"));
                int plotArea=rs.getInt("length")*rs.getInt("width");
                individualSiteModel.setCost(plotArea*rs.getInt("persqft"));
                individualSiteModel.setWonership(rs.getString("wonership"));
                individualSiteModel.setTransaction(rs.getString("transaction"));
                individualSiteModel.setPrimLocation(rs.getString("prim_location"));
                individualSiteModel.setSecoLocation(rs.getString("seco_location"));
                individualSiteModel.setComment(rs.getString("comment"));
                individualSiteModel.setFacing(rs.getString("facing"));
                individualSiteModel.setAgentName(rs.getString("agent_name"));
                individualSiteModel.setTotalPrice(indianCurrence(rs.getInt("persqft") * (rs.getInt("length") * rs.getInt("width"))));
                individualSiteModel.setCreatedOnDate(rs.getDate("create_date"));
                individualSiteModel.setUserId(rs.getInt("user_id"));


                InputStream imageStream = rs.getBinaryStream("image");
                if(rs.getBytes("image").length!=0) {
                    BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

                    individualSiteModel.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> bufferedStream) // Stream the content directly
                            .build());
                }
                else
                {
                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        individualSiteModel.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());
                    }

                }
                individualSiteModelList.add(individualSiteModel);


            }


            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }finally {
            // Close the pool (important for proper shutdown)
            try {
                if (bds != null) {
                    bds.close();
                }
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return individualSiteModelList;
    }





    //********************************************** Get Villa details *************************************************************

    public List<VillaModel> getVillaData(int villaId)
    {


        List<VillaModel> VillaModelList = new ArrayList<>();
        try {
            Connection con = null;
            PreparedStatement pstmt = null;

            StringBuilder sql_villa_details = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_VILLA_DIALOG);

            con=ConnectionDAO.getConnection();

            System.out.println("Villa Query : "+sql_villa_details.toString());
            pstmt = con.prepareStatement(sql_villa_details.toString());
            pstmt.setInt(1, villaId);

            ResultSet rs = pstmt.executeQuery();
            while ( rs.next() ) {
                VillaModel villaModel=new VillaModel();


                villaModel.setVillaId(rs.getInt("villa_id"));
                villaModel.setI_am(rs.getString("i_am"));
                villaModel.setOwner_name(rs.getString("owner_name"));
                villaModel.setContact_owner(rs.getString("contact_owner"));
                villaModel.setEmail(rs.getString("email"));
                villaModel.setProperty_type(rs.getString("property_type"));
                villaModel.setAddress(rs.getString("address"));
                villaModel.setRoad_width(rs.getInt("road_width"));
                villaModel.setFloors(rs.getInt("floors"));
                villaModel.setBed_rooms(rs.getInt("bed_rooms"));
                villaModel.setBath_rooms(rs.getInt("bath_rooms"));
                villaModel.setFurnished(rs.getString("furnished"));
                villaModel.setPlot_area(rs.getInt("plot_area"));
                villaModel.setS_build_are(rs.getInt("s_build_are"));
                villaModel.setPro_avail(rs.getString("pro_avail"));
                villaModel.setPersqft(rs.getInt("persqft"));
                villaModel.setPrim_location(rs.getString("prim_location"));
                villaModel.setSeco_location(rs.getString("seco_location"));
                villaModel.setTotal_feets(rs.getInt("total_feets"));
                villaModel.setCost(rs.getInt("cost"));
                villaModel.setCreate_date(rs.getDate("create_date"));
                villaModel.setIs_active(rs.getInt("is_active"));
                villaModel.setUserId(rs.getInt("user_id"));
                villaModel.setFloorNum(rs.getInt("floor_num"));
                villaModel.setComment(rs.getString("comment"));

                // below for Image

                System.out.println(" Villa image : "+rs.getString("owner_name")+" --->"+rs.getBytes("image").length);

                if(rs.getBytes("image").length!=0)
                {
                    byte[] bb=rs.getBytes("image");

                    villaModel.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> new ByteArrayInputStream(bb)).build());
                }
                else
                {
                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        villaModel.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());
                    }

                }


                VillaModelList.add(villaModel);
            }

            pstmt.close();
            rs.close();
            con.close();
            //log.info("### : *** Connection Closed from getActiveModelList()");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }
        return VillaModelList;
    }


    //********************************************** Get Villa details by user id *************************************************************

    public List<VillaModel> getVillaDataByUserId(int userId)
    {


        List<VillaModel> VillaModelList = new ArrayList<>();
        try {
            Connection con = null;
            PreparedStatement pstmt = null;

            StringBuilder sql_villa_by_userid = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_VILLA_BY_USERID);

            con=ConnectionDAO.getConnection();

            System.out.println("Villa Query : "+sql_villa_by_userid.toString());
            pstmt = con.prepareStatement(sql_villa_by_userid.toString());
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while ( rs.next() ) {
                VillaModel villaModel=new VillaModel();


                villaModel.setVillaId(rs.getInt("villa_id"));
                villaModel.setI_am(rs.getString("i_am"));
                villaModel.setOwner_name(rs.getString("owner_name"));
                villaModel.setContact_owner(rs.getString("contact_owner"));
                villaModel.setEmail(rs.getString("email"));
                villaModel.setProperty_type(rs.getString("property_type"));
                villaModel.setAddress(rs.getString("address"));
                villaModel.setRoad_width(rs.getInt("road_width"));
                villaModel.setFloors(rs.getInt("floors"));
                villaModel.setBed_rooms(rs.getInt("bed_rooms"));
                villaModel.setBath_rooms(rs.getInt("bath_rooms"));
                villaModel.setFurnished(rs.getString("furnished"));
                villaModel.setPlot_area(rs.getInt("plot_area"));
                villaModel.setS_build_are(rs.getInt("s_build_are"));
                villaModel.setPro_avail(rs.getString("pro_avail"));
                villaModel.setPersqft(rs.getInt("persqft"));
                villaModel.setPrim_location(rs.getString("prim_location"));
                villaModel.setSeco_location(rs.getString("seco_location"));
                villaModel.setTotal_feets(rs.getInt("total_feets"));
                villaModel.setCost(rs.getInt("cost"));
                villaModel.setCreate_date(rs.getDate("create_date"));
                villaModel.setIs_active(rs.getInt("is_active"));
                villaModel.setUserId(rs.getInt("user_id"));
                villaModel.setFloorNum(rs.getInt("floor_num"));
                villaModel.setComment(rs.getString("comment"));

                // below for Image

                System.out.println(" Villa image : "+rs.getString("owner_name")+" --->"+rs.getBytes("image").length);

                if(rs.getBytes("image").length!=0)
                {
                    byte[] bb=rs.getBytes("image");

                    villaModel.setStreamedContent(DefaultStreamedContent.builder()
                            .name("US_Piechart.jpg")
                            .contentType("image/jpg")
                            .stream(() -> new ByteArrayInputStream(bb)).build());
                }
                else
                {
                    // Defalut Image
                    PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =1");
                    ResultSet rsDef = pstmtDefault.executeQuery();
                    while ( rsDef.next())
                    {
                        byte[] def=rsDef.getBytes("image");
                        villaModel.setStreamedContent(DefaultStreamedContent.builder()
                                .name("US_Piechart.jpg")
                                .contentType("image/jpg")
                                .stream(() -> new ByteArrayInputStream(def)).build());
                    }

                }


                VillaModelList.add(villaModel);
            }

            pstmt.close();
            rs.close();
            con.close();
            //log.info("### : *** Connection Closed from getActiveModelList()");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }
        return VillaModelList;
    }

//********************** Currence ************************************



    public String indianCurrence(int price)
    {
        String number=Integer.toString(price);
        String totalPrice="";
        if(number.length()>1)
        {


            String first=number.substring(0,number.length()-3);

            char[] x=first.toCharArray();

            String sum="";
            int count=1;
            for(int i=first.length()-1;i>=0;i--)
            {
                sum=sum+x[i];

                if(count==2)
                {
                    count=count=+1;
                    sum=sum+",";
                    count=0;
                }
                count++;
            }

            System.out.println("Test 1:"+sum);


            StringBuilder sb=new StringBuilder(sum);
            sb.reverse();
            String firstOrig=sb.toString();
            String second=number.substring((number.length()-3),number.length());
            totalPrice=firstOrig+","+second+" /-   ";
        }
        else
        {
            totalPrice=number;
        }
        return totalPrice;
    }


}
