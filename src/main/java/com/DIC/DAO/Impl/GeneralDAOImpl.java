package com.DIC.DAO.Impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;
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
    
    
	
	
	

}
