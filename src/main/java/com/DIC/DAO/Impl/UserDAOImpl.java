package com.DIC.DAO.Impl;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;
import com.DIC.model.LayoutMode;
import com.DIC.model.UserDetails;
import com.DIC.model.UserProfileRoleModel;

@ManagedBean
@ApplicationScoped
public class UserDAOImpl {
	
	
	private static final Logger log = Logger.getLogger(UserDAOImpl.class.getName());
	
	interface Constants {
		// SQL
		interface SQL {
			
			
			String SQL_LAYOUT_LIST="select * from hansi_layout order by create_date desc";
			String SQL_DEL_LAYOUT="delete from hansi_layout where layout_id = ?";
			String SQL_USER="select * from user_deta where user_id = ?";
			
			
			String SQL_PROFILE_ROLE="select r.role_name,r.profile_url  \r\n"
					+ "from user_deta u, role r, user_map_role ur where u.user_id=ur.user_id and ur.role_id =r.role_id and u.is_active = '1'\r\n"
					+ "and r.is_active = '1' and ur.is_active='1' and r.is_profile ='Yes' and r.is_profile_dis ='Yes' and u.user_id = ? order by role_name;";
			
			
		}
	}
	
	
	
	public List<LayoutMode> getLayoutList()
	{
            
      	
		List<LayoutMode> layoutModeList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
		
			
			con=ConnectionDAO.getConnection();
			StringBuilder sq_layout_list = new StringBuilder(Constants.SQL.SQL_LAYOUT_LIST);
			
							pstmt = con.prepareStatement(sq_layout_list.toString());
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
                         
                         
                        
                         if(rs.getBytes("image").length!=0)
                         {
                        	 System.out.println("********** not null ********* : "+rs.getBytes("image"));
                         byte[] bb=rs.getBytes("image");
                         
                         layoutMode.setStreamedContent(DefaultStreamedContent.builder()
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
			                		      layoutMode.setStreamedContent(DefaultStreamedContent.builder()
			                             .name("US_Piechart.jpg")
			                             .contentType("image/jpg")
			                             .stream(() -> new ByteArrayInputStream(def)).build());
			                			 }
			                	 
			              }
                        	 
                       
	        	 layoutModeList.add(layoutMode);
	         }
	         	
	         pstmt.close();
	         rs.close();
	         con.close();
	         //log.info("### : *** Connection Closed from getActiveModelList()");
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //System.exit(0);
	     }
	return layoutModeList;		
	}
	
	
	//************************************ Price Indian format ******************************************//  
	  
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
    
    
    public String deleteLayout(int layoutId)
    {
    	
    	    	
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_del_layout = new StringBuilder(Constants.SQL.SQL_DEL_LAYOUT);
            pstmt = con.prepareStatement(sql_del_layout.toString());
            pstmt.setInt(1, layoutId);
          	int res=pstmt.executeUpdate();
          	
          	System.out.println(" **********  Deleted Record: "+res);
	            if(res > 0)
	            {
	            	succVal="Successful Deleted User";
	            }
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	       succVal=e.getMessage();
	        return succVal;
	       
          }
      

        return succVal;

    }
    
    
    public List<UserDetails> getUser(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		List<UserDetails> userDetailsList=new ArrayList();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_user = new StringBuilder(Constants.SQL.SQL_USER);
			log.info("###: Qury User profile by user id : "+sql_user.toString());
			ps = con.prepareStatement(sql_user.toString());
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				UserDetails userDetails=new UserDetails();
				userDetails.setUserId(rs.getInt("user_id"));
				userDetails.setfName(rs.getString("fname"));
				userDetails.setlName(rs.getString("lname"));
				userDetails.setUserName(rs.getString("user_name"));
				userDetails.setUserPassword(rs.getString("user_pass"));
				userDetails.setAddress(rs.getString("address"));
				userDetails.setPhone(rs.getString("phone"));
				userDetails.setCreate_date(rs.getDate("create_date"));
				userDetails.setIs_active(rs.getInt("is_active"));
				
				userDetailsList.add(userDetails);
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		return userDetailsList;
	}
    
    /*
     * 
     *  User profile roles
     */
    //=============================================================================================
    
    public List<UserProfileRoleModel> getUserProfileRoles(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		List<UserProfileRoleModel> userProfileRoleModelList=new ArrayList();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_profile_role = new StringBuilder(Constants.SQL.SQL_PROFILE_ROLE);
			log.info("###: Qury User profile Role : "+sql_profile_role.toString());
			ps = con.prepareStatement(sql_profile_role.toString());
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				UserProfileRoleModel userProfileRoleModel=new UserProfileRoleModel();
				
				userProfileRoleModel.setRoleName(rs.getString("role_name"));
				userProfileRoleModel.setProfUrl(rs.getString("profile_url"));
				
				
				userProfileRoleModelList.add(userProfileRoleModel);
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		return userProfileRoleModelList;
	}
    
	
	

}