package com.DIC.DAO.Impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;
import com.DIC.model.HomeLoanDataEntryModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.PlotsDataEntryModel;
import com.DIC.model.VillaModel;

@ManagedBean
@ApplicationScoped
public class GeneralDAOImpl {
	
	private static final Logger log = Logger.getLogger(GeneralDAOImpl.class.getName());
	
	
	interface Constants {
		// SQL
		interface SQL {
			
			String SQL_VILLA_INSERT="insert into villa_plot (villa_id,i_am,owner_name,contact_owner,email,property_type,address,road_width,floors,bed_rooms,bath_rooms,furnished,plot_area,s_build_are,pro_avail,avail_date,persqft,prim_location,seco_location,image,total_feets,cost,create_date,is_active) \n"+ 
					"values (nextval('hansi_villa_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,1);";
			
			String SQL_VILLA_DETAILS="select * from villa_plot where prim_location = ? and seco_location = ?";
					//+ " and property_type = ? order by create_date desc";
			String SQL_HOME_LOAN_INSERT="insert into home_loan (home_id,agent_name,cont_num,age,gender,email,loan_amt,monthly_inc,emp_type,create_date,is_active) \n"+ 
					"values (nextval('home_loan_seq'),?,?,?,?,?,?,?,?,current_timestamp,1);";
		}
	}
	
	
	// ***************** update Villa data entry **************
    public String updateVillaDataEntry(VillaModel villaModel)
    {
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_villa_insert = new StringBuilder(Constants.SQL.SQL_VILLA_INSERT);
            pstmt = con.prepareStatement(sql_villa_insert.toString());
            
            pstmt.setString(1,villaModel.getI_am());
            pstmt.setString(2, villaModel.getOwner_name());
            pstmt.setString(3, villaModel.getContact_owner());
            pstmt.setString(4, villaModel.getEmail());
            pstmt.setString(5, villaModel.getProperty_type());
            pstmt.setString(6, villaModel.getAddress());
            pstmt.setInt(7, villaModel.getRoad_width());
            pstmt.setInt(8, villaModel.getFloors());
            pstmt.setInt(9, villaModel.getBed_rooms());
            pstmt.setInt(10, villaModel.getBath_rooms());
            pstmt.setString(11, villaModel.getFurnished());
            pstmt.setInt(12, villaModel.getPlot_area());
            pstmt.setInt(13, villaModel.getS_build_are());
            pstmt.setString(14, villaModel.getPro_avail());
            pstmt.setString(15, (villaModel.getAvail_date().toString()!=null) ? villaModel.getAvail_date().toString() : "" );
            pstmt.setInt(16, villaModel.getPersqft());
            pstmt.setString(17, villaModel.getPrim_location());
            pstmt.setString(18, villaModel.getSeco_location());
                    InputStream fin2=villaModel.getInputStream();
		            UploadedFile file=villaModel.getFile();
		    pstmt.setBinaryStream(19, fin2, file.getSize());  
		    pstmt.setInt(20, (villaModel.getPlot_area() + villaModel.getS_build_are()) );
		    pstmt.setDouble(21, (villaModel.getPlot_area() + villaModel.getS_build_are()) * villaModel.getPersqft());
            
           
            	int res=pstmt.executeUpdate();
	            if(res > 0)
	            {
	            	succVal="Successful updated record";
	            }
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	    
	        succVal=e.getMessage();
	        return succVal;
	       
          }
      

        return succVal;
    }
    
    
    //********************************************** Get Villa details *************************************************************
    
    public List<VillaModel> getVillaDetails(String priLocation, String secLocation,String proType)
	{
            
       System.out.println("********* Villa database : "+priLocation+"   "+secLocation+"   "+proType);
	
		List<VillaModel> VillaModelList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			StringBuilder sql_villa_details = new StringBuilder(Constants.SQL.SQL_VILLA_DETAILS);
		
				if(proType.equals("All"))
				{
					sql_villa_details.append(" and property_type in ('Villa','House','Plot') order by create_date desc");
				}
				else
				{
					sql_villa_details.append(" and property_type='"+proType+"' order by create_date desc");
				}
			con=ConnectionDAO.getConnection();
			
			System.out.println("Villa Query : "+sql_villa_details.toString());
							pstmt = con.prepareStatement(sql_villa_details.toString());
                            pstmt.setString(1, priLocation);
                            pstmt.setString(2, secLocation);
                            ResultSet rs = pstmt.executeQuery();
	         while ( rs.next() ) {
	        	 VillaModel villaModel=new VillaModel();
	        	 
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
	     }
	return VillaModelList;		
	}
    
    
 // ***************** update home loan data entry **************
    /*
     * 
     * developed by Parameswari
     */
    
    public String updateHomeLoanDataEntry(HomeLoanDataEntryModel HomeLoanDataEntryModel)
    {
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_home_loan_insert = new StringBuilder(Constants.SQL.SQL_HOME_LOAN_INSERT);
            pstmt = con.prepareStatement(sql_home_loan_insert.toString());
            
            pstmt.setString(1,HomeLoanDataEntryModel.getAgentName());
            pstmt.setString(2, HomeLoanDataEntryModel.getContactNo());
            pstmt.setInt(3, HomeLoanDataEntryModel.getAge());
            pstmt.setString(4, HomeLoanDataEntryModel.getGender());
            pstmt.setString(5, HomeLoanDataEntryModel.getEmail());
            pstmt.setInt(6, HomeLoanDataEntryModel.getLoanAmt());
            pstmt.setInt(7, HomeLoanDataEntryModel.getMonthlyInc());
            pstmt.setString(8, HomeLoanDataEntryModel.getEmpType());
           
           
            	int res=pstmt.executeUpdate();
	            if(res > 0)
	            {
	            	succVal="Successful updated record";
	            }
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	    
	        succVal=e.getMessage();
	        return succVal;
	       
          }
      

        return succVal;
    }
    
	
    
	
	
	

}
