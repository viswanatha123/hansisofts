package com.DIC.DAO.Impl;


import com.DIC.DAO.ConnectionDAO;
import com.DIC.model.LayoutMode;
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
            String SQL_PROPERT_IMAGE="select * from hansi_layout where layout_id = ?";
        }

    }

    public List<LayoutMode> getPropertyImage(int layout_id)
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

            StringBuilder sq_property_image = new StringBuilder(PropertyDAOImpl.Constants.SQL.SQL_PROPERT_IMAGE);

            pstmt = con.prepareStatement(sq_property_image.toString());
            pstmt.setInt(1, layout_id);

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
