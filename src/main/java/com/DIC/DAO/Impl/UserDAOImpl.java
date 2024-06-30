package com.DIC.DAO.Impl;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;
import com.DIC.model.AllPropertyList;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.LeadModel;
import com.DIC.model.PackageModel;
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
					+ "and r.is_active = '1' and ur.is_active='1' and r.is_profile ='Yes' and r.is_prof_menu ='Yes' and u.user_id = ? order by role_name;";
			
			String SQL_LEAD_SAVE="INSERT INTO leads (leads_id, lead_name, lead_contact, lead_email, pro_id, user_id, create_date, is_active,prop_type) VALUES (nextval('leads_seq'), ?, ?, ?, ?, ?, current_timestamp, 1,?);";

			//String SQL_LISTED_PROP="select * from hansi_layout where user_id = ? order by create_date desc";
			
			String SQL_LISTED_PROP="select * ,(select count(*) from leads ls where pro_id =la.layout_id) lead_Count from hansi_layout la where la.user_id = ? order by create_date desc";
		
			String SQL_LEADS_BY_PROP_ID="select * from leads where pro_id = ? and prop_type= ? and is_active ='1'";
			
			
			/*
			 * 		Direct sql query
			 * 
			select prop_id , name,property_type, "Create Date",lead_update ,count,"Cost","Location" from
					(SELECT lay.layout_id as "prop_id", lay.name as name, 'layout' as "property_type",create_date as "Create Date",lead_update,(select count(*) from leads ls where pro_id =lay.layout_id and prop_type='layout') as "count",cost as "Cost", prim_location as "Location" FROM hansi_layout lay where lay.user_id= 118
					UNION ALL
					SELECT agri.agri_id as "prop_id" ,agri.owner_name as name, 'agri' as "property_type" ,create_date as "Create Date",lead_update,(select count(*) from leads ls where pro_id =agri.agri_id and prop_type='agri') as "count" ,cost as "Cost", prim_location as "Location" FROM hansi_agricultural agri where agri.user_id= 118
					UNION ALL
					SELECT ind.ind_id as "prop_id" ,ind.owner_name as name,'indi' as "property_type",create_date as "Create Date",lead_update,(select count(*) from leads ls where pro_id =ind.ind_id and prop_type='indi') as "count" , cost as "Cost", prim_location as "Location" FROM hansi_individual_site ind where ind.user_id= 118
					UNION ALL
					SELECT villa.villa_id as "prop_id", villa.owner_name as name,'villa' as "property_type" ,create_date as "Create Date", lead_update,(select count(*) from leads ls where pro_id =villa.villa_id and prop_type='villa') as "count"  , cost as "Cost", prim_location as "Location" FROM villa_plot  villa where villa.user_id= 118)
					as alldata order by CASE 
        				WHEN alldata.lead_update IS NULL THEN 1 
        				ELSE 0 
    				END,
					alldata.lead_update desc;
			*/
			
			/*
			String SQL_ALL_PROP="select prop_id , name,property_type, \"Create Date\" ,count,\"Cost\",\"Location\" from \r\n"
					+ "					(SELECT lay.layout_id as \"prop_id\", lay.name as name, 'layout' as \"property_type\",create_date as \"Create Date\",(select count(*) from leads ls where pro_id =lay.layout_id and prop_type='layout') as \"count\", cost as \"Cost\", prim_location as \"Location\" FROM hansi_layout lay where lay.user_id= ?\r\n"
					+ "					UNION ALL\r\n"
					+ "					SELECT agri.agri_id as \"prop_id\" ,agri.owner_name as name, 'agri' as \"property_type\" ,create_date as \"Create Date\",(select count(*) from leads ls where pro_id =agri.agri_id and prop_type='agri') as \"count\" , cost as \"Cost\", prim_location as \"Location\" FROM hansi_agricultural agri where agri.user_id= ?\r\n"
					+ "					UNION ALL\r\n"
					+ "					SELECT ind.ind_id as \"prop_id\" ,ind.owner_name as name,'indi' as \"property_type\",create_date as \"Create Date\",(select count(*) from leads ls where pro_id =ind.ind_id and prop_type='indi') as \"count\" , cost as \"Cost\", prim_location as \"Location\" FROM hansi_individual_site ind where ind.user_id= ?\r\n"
					+ "					UNION ALL\r\n"
					+ "					SELECT villa.villa_id as \"prop_id\", villa.owner_name as name,'villa' as \"property_type\" ,create_date as \"Create Date\", (select count(*) from leads ls where pro_id =villa.villa_id and prop_type='villa') as \"count\" , cost as \"Cost\", prim_location as \"Location\" FROM villa_plot  villa where villa.user_id= ?)\r\n"
					+ "					as alldata order by alldata.\"Create Date\" desc;";
			*/
			String SQL_ALL_PROP="select prop_id , name,property_type, \"Create Date\",lead_update ,count,\"Cost\",\"Location\" from\r\n"
					+ "					(SELECT lay.layout_id as \"prop_id\", lay.name as name, 'layout' as \"property_type\",create_date as \"Create Date\",lead_update,(select count(*) from leads ls where pro_id =lay.layout_id and prop_type='layout') as \"count\",cost as \"Cost\", prim_location as \"Location\" FROM hansi_layout lay where lay.user_id= ?\r\n"
					+ "					UNION ALL\r\n"
					+ "					SELECT agri.agri_id as \"prop_id\" ,agri.owner_name as name, 'agri' as \"property_type\" ,create_date as \"Create Date\",lead_update,(select count(*) from leads ls where pro_id =agri.agri_id and prop_type='agri') as \"count\" ,cost as \"Cost\", prim_location as \"Location\" FROM hansi_agricultural agri where agri.user_id= ?\r\n"
					+ "					UNION ALL\r\n"
					+ "					SELECT ind.ind_id as \"prop_id\" ,ind.owner_name as name,'indi' as \"property_type\",create_date as \"Create Date\",lead_update,(select count(*) from leads ls where pro_id =ind.ind_id and prop_type='indi') as \"count\" , cost as \"Cost\", prim_location as \"Location\" FROM hansi_individual_site ind where ind.user_id= ?\r\n"
					+ "					UNION ALL\r\n"
					+ "					SELECT villa.villa_id as \"prop_id\", villa.owner_name as name,'villa' as \"property_type\" ,create_date as \"Create Date\", lead_update,(select count(*) from leads ls where pro_id =villa.villa_id and prop_type='villa') as \"count\"  , cost as \"Cost\", prim_location as \"Location\" FROM villa_plot  villa where villa.user_id= ?)\r\n"
					+ "					as alldata order by CASE \r\n"
					+ "        				WHEN alldata.lead_update IS NULL THEN 1 \r\n"
					+ "        				ELSE 0 \r\n"
					+ "    				END,\r\n"
					+ "					alldata.lead_update desc;";
			
			
			 String SQL_INDISITE_LIST="select * from hansi_individual_site order by create_date desc";
			 String SQL_DEL_INDISITE="delete from hansi_individual_site where ind_id = ?";
			 String SQL_PACKAGE_DETAILS="select u.user_id,p.pack_id, p.pack_name,p.pack_type ,p.list_limit,p.pack_cost ,p.pack_duration, up.is_enable from user_deta u,\r\n"
			 		+ "user_map_package up,\r\n"
			 		+ "package p\r\n"
			 		+ "where u.user_id=up.user_id\r\n"
			 		+ "and up.pack_id=p.pack_id\r\n"
			 		+ "and u.user_id=?";
			 
			 String SQL_UPDATE_USER="update user_deta set fname = ?, lname = ?, user_name = ?, user_pass = ?, address = ?, phone = ?, create_date = ? ,is_active = ? where user_id = ?";
			 String SQL_ACCOUNT_RENEWEL="update user_deta set create_date = now() where user_id= ?";
		
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
                         //layoutMode.setComment(rs.getString("comment"));
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
    
    
    public UserDetails getUser(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		UserDetails userDetails=new UserDetails();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_user = new StringBuilder(Constants.SQL.SQL_USER);
			log.info("###: Qury User profile by user id : "+sql_user.toString());
			ps = con.prepareStatement(sql_user.toString());
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				
				userDetails.setUserId(rs.getInt("user_id"));
				userDetails.setfName(rs.getString("fname"));
				userDetails.setlName(rs.getString("lname"));
				userDetails.setUserName(rs.getString("user_name"));
				userDetails.setUserPassword(rs.getString("user_pass"));
				userDetails.setAddress(rs.getString("address"));
				userDetails.setPhone(rs.getString("phone"));
				userDetails.setCreate_date(rs.getDate("create_date"));
				userDetails.setIs_active(rs.getInt("is_active"));
				userDetails.setEmail(rs.getString("email"));
				
				//userDetails.setListLimit(rs.getInt("list_limit"));
				
				//userDetailsList.add(userDetails);
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		return userDetails;
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
    
    
    
// ***************** Save Leads details  **************
    
    public String saveLeads(String leadName,String leadContact,String leadEmail,int proId, int userId, String propType)
    {
    	String saveMessage="";
    	
        try {
        	
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_lead_save = new StringBuilder(Constants.SQL.SQL_LEAD_SAVE);
            pstmt = con.prepareStatement(sql_lead_save.toString());
            
            pstmt.setString(1,leadName );
            pstmt.setString(2, leadContact);
            pstmt.setString(3, leadEmail);
            pstmt.setInt(4, proId);
            pstmt.setInt(5, userId);
            pstmt.setString(6, propType);
            
            
            
           
            	int res=pstmt.executeUpdate();
	            if(res > 0)
	            {
	            	saveMessage="Successful Registered.";
	            }
	            
	            
	            if(propType.equals("layout"))
	            {
		            PreparedStatement pstmt1=con.prepareStatement("update hansi_layout set lead_update=current_timestamp where layout_id = ?");
		            pstmt1.setInt(1, proId);
		            int res1=pstmt1.executeUpdate();
	            }
	            if(propType.equals("agri"))
	            {
		            PreparedStatement pstmt1=con.prepareStatement("update hansi_agricultural set lead_update=current_timestamp where agri_id = ?");
		            pstmt1.setInt(1, proId);
		            int res1=pstmt1.executeUpdate();
	            }
	            if(propType.equals("indi"))
	            {
		            PreparedStatement pstmt1=con.prepareStatement("update hansi_individual_site set lead_update=current_timestamp where ind_id = ?");
		            pstmt1.setInt(1, proId);
		            int res1=pstmt1.executeUpdate();
	            }
	            if(propType.equals("villa"))
	            {
		            PreparedStatement pstmt1=con.prepareStatement("update villa_plot set lead_update=current_timestamp where villa_id = ?");
		            pstmt1.setInt(1, proId);
		            int res1=pstmt1.executeUpdate();
	            }
	            
	            
	            
	            
	            
	            
	            log.log(Level.INFO,"***** Succful saved lead values *******");
	            
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println("Leads save Error :"+e.getClass().getName()+": "+e.getMessage());
	        log.log(Level.WARNING, "Leads save Error :"+e.getClass().getName()+": "+e.getMessage());
	        saveMessage=e.getMessage();
	        return saveMessage;
	       
          }
      

        return saveMessage;
    }
    
    
    
    //**************** listed propertys *************
    
    public List<LayoutMode> getLayoutListByUserId(int userId)
	{
            
      	
		List<LayoutMode> layoutModeList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
		
			
			con=ConnectionDAO.getConnection();
			StringBuilder sq_listed_prop = new StringBuilder(Constants.SQL.SQL_LISTED_PROP);
			
							pstmt = con.prepareStatement(sq_listed_prop.toString());
							pstmt.setInt(1,userId);
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
                         //layoutMode.setComment(rs.getString("comment"));
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
                         layoutMode.setLeadCount(rs.getInt("lead_Count"));
                         
                         
                         
                      
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
    
    //************************* get all property by user id
    
    
    public List<AllPropertyList> getAllPropByUserId(int userId)
	{
            
      	
		List<AllPropertyList> allPropertyListList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
		
			
			con=ConnectionDAO.getConnection();
			StringBuilder sql_all_prop = new StringBuilder(Constants.SQL.SQL_ALL_PROP);
			
							pstmt = con.prepareStatement(sql_all_prop.toString());
							pstmt.setInt(1,userId);
							pstmt.setInt(2,userId);
							pstmt.setInt(3,userId);
							pstmt.setInt(4,userId);
                            ResultSet rs = pstmt.executeQuery();
	         while ( rs.next() ) {
	        	 AllPropertyList allPropertyList=new AllPropertyList();
	        	 
	        	 allPropertyList.setPropId(rs.getInt("prop_id"));
	        	 allPropertyList.setPropName(rs.getString("name"));
	        	 allPropertyList.setPropType(rs.getString("property_type"));
	        	 allPropertyList.setCount(rs.getInt("count"));
	        	 allPropertyList.setCreatedOnDate(rs.getDate("Create Date"));
	        	 allPropertyList.setLeadUpdate(rs.getDate("lead_update"));
	        	 allPropertyList.setCost(rs.getInt("Cost"));
	        	 allPropertyList.setPrimLocation(rs.getString("Location"));
	        	 
	        
	        	 
	        	 
	        	 
                         
                     
                allPropertyListList.add(allPropertyList);
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
	return allPropertyListList;		
	}
    
    
    
    //************************ Leads by prop id ******************
    
    public List<LeadModel> getLeads(int propId,String propType)
	{
            
      	
		List<LeadModel> leadModelList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
		
			
			con=ConnectionDAO.getConnection();
			StringBuilder sq_leads_by_prop_id = new StringBuilder(Constants.SQL.SQL_LEADS_BY_PROP_ID);
			
							pstmt = con.prepareStatement(sq_leads_by_prop_id.toString());
							pstmt.setInt(1,propId);
							pstmt.setString(2,propType);
							
                            ResultSet rs = pstmt.executeQuery();
	         while ( rs.next() ) {
	        	 LeadModel leadModel=new LeadModel();
	        	 
	        	 leadModel.setLeads_id(rs.getInt("leads_id"));
	        	 leadModel.setLeadName(rs.getString("lead_name"));
	        	 leadModel.setLeadContact(rs.getString("lead_contact"));
	        	 leadModel.setLeadEmail(rs.getString("lead_email"));
	        	 leadModel.setProId(rs.getInt("pro_id"));
	        	 leadModel.setUserId(rs.getInt("user_id"));
	        	 leadModel.setCreate_date(rs.getDate("create_date"));
	        	 leadModel.setIs_active(rs.getInt("is_active"));
	        	
             leadModelList.add(leadModel);
	         }
	         	
	         pstmt.close();
	         rs.close();
	         con.close();
	        } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.log(Level.WARNING, "Error : "+e.getClass().getName()+": "+e.getMessage());
	     }
	return leadModelList;		
	}
    
    
  //************************************ IndividualSite List ******************************************// 
	
  		public List<IndividualSiteModel> getIndiSiteList(){
  			List<IndividualSiteModel> individualSiteModelList = new ArrayList<>();
      		try {
      			Connection con = null;
      			PreparedStatement pstmt = null;
      			
      			StringBuilder sql_IndividualSite_List = new StringBuilder(Constants.SQL.SQL_INDISITE_LIST);
      			log.info("###: Query : "+sql_IndividualSite_List.toString());
      			
      			con=ConnectionDAO.getConnection();
      	                    pstmt = con.prepareStatement(sql_IndividualSite_List.toString());
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
          	        	 individualSiteModel.setIs_active(rs.getInt("is_active"));
          	     
                   individualSiteModelList.add(individualSiteModel);
      	    
      	            
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
      	return individualSiteModelList;
  		}
  	
  		
  		
  	    
  	    public String deleteIndiList(long indviId)
  	    {
  	    	
  	    	    	
  	    	String succVal="";
  	    	
  	        try {
  	            Connection con = null;
  	            PreparedStatement pstmt = null;
  	            con=ConnectionDAO.getConnection();
  	            
  	            StringBuilder sql_del_indiList = new StringBuilder(Constants.SQL.SQL_DEL_INDISITE);
  	            pstmt = con.prepareStatement(sql_del_indiList.toString());
  	            pstmt.setLong(1, indviId);
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
  	    
  	    //************************ getPackage details ****************
  	    
  	 public PackageModel getPackageDetails(int userId)
  	{
    	PackageModel packageModel=new PackageModel();
     
  		try {
  			Connection con = null;
  			PreparedStatement pstmt = null;
  			con=ConnectionDAO.getConnection();
  			StringBuilder sql_package_details = new StringBuilder(Constants.SQL.SQL_PACKAGE_DETAILS);
  			
  							pstmt = con.prepareStatement(sql_package_details.toString());
  							pstmt.setInt(1,userId);
  							ResultSet rs = pstmt.executeQuery();
  	         while ( rs.next() ) {
  	        	 
  	        	System.out.println("------------------------package name --------------->"+rs.getString("pack_name"));
  	        	
  	        	packageModel.setPackId(rs.getInt("pack_id"));
  	        	packageModel.setPackName(rs.getString("pack_name"));
  	        	packageModel.setPackType(rs.getInt("pack_type"));
  	        	packageModel.setListLimit(rs.getInt("list_limit"));
  	        	packageModel.setPackCost(rs.getInt("pack_cost"));
  	        	packageModel.setPackDuration(rs.getInt("pack_duration"));
  	           	packageModel.setIsEnable(rs.getBoolean("is_enable"));
  	        	
  	        	  	        	
  	            }
  	         	
  	         pstmt.close();
  	         rs.close();
  	         con.close();
  	      } catch (Exception e) {
  	        e.printStackTrace();
  	        System.err.println(e.getClass().getName()+": "+e.getMessage());
  	       }
  	return packageModel;		
  	}
      
  	 
  	// ***************************** Update User details **************
  	 
  	public String updateUser(String fName, String lName, String userName,String userPassword, String address, String phone,Date create_date, int is_active, int userId)
    {
    	String saveMessage="";
    	
        try {
        	
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_update_user = new StringBuilder(Constants.SQL.SQL_UPDATE_USER);
            pstmt = con.prepareStatement(sql_update_user.toString());
            
            pstmt.setString(1,fName );
            pstmt.setString(2, lName);
            pstmt.setString(3, userName);
            pstmt.setString(4, userPassword);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);
            pstmt.setDate(7,new java.sql.Date(create_date.getTime()));
            pstmt.setInt(8, is_active);
            pstmt.setInt(9, userId);
           
            	int res=pstmt.executeUpdate();
	            if(res > 0)
	            {
	            	saveMessage="Successful Updated user details.";
	            }
	            log.log(Level.INFO,"***** Succful saved lead values *******");
	            
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println("Leads save Error :"+e.getClass().getName()+": "+e.getMessage());
	        log.log(Level.WARNING, "Leads save Error :"+e.getClass().getName()+": "+e.getMessage());
	        saveMessage=e.getMessage();
	        return saveMessage;
	       
          }
      

        return saveMessage;
    }
  	
  	
 // ***************************** User Account Renewel **************
 	 
   	public int accountRenewel(int userId)
     {
     	String saveMessage="";
     	
         try {
         	
             Connection con = null;
             PreparedStatement pstmt = null;
             con=ConnectionDAO.getConnection();
             
             StringBuilder sql_account_renewel = new StringBuilder(Constants.SQL.SQL_ACCOUNT_RENEWEL);
             pstmt = con.prepareStatement(sql_account_renewel.toString());
             pstmt.setInt(1, userId);
            
             	int res=pstmt.executeUpdate();
 	            if(res > 0)
 	            {
 	            	return 1;
 	            }
 	            log.log(Level.INFO,"***** Succful saved lead values *******");
 	            
           } catch (Exception e) {
          
 	        e.printStackTrace();
 	        System.err.println("Leads save Error :"+e.getClass().getName()+": "+e.getMessage());
 	        log.log(Level.WARNING, "Leads save Error :"+e.getClass().getName()+": "+e.getMessage());
 	        saveMessage=e.getMessage();
 	        return 0;
 	       
           }
       

         return 1;
     }
   	
   	//***************************  deletet property ****************
   	
   	public String deleteProperty(int propertyId, String propertyType)
    {
    	
   		String query="";;	
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            		if(propertyType.equals("layout"))
            		{
            		 query="delete from hansi_layout where layout_id = ?";
            		}	
            		if(propertyType.equals("agri"))
            		{
            		 query="delete from hansi_agricultural where agri_id = ?";
            		}
            		if(propertyType.equals("indi"))
            		{
            		 query="delete from hansi_individual_site where ind_id = ?";
            		}
            		if(propertyType.equals("villa"))
            		{
            		 query="delete from villa_plot where villa_id = ?";
            		}
            
            pstmt = con.prepareStatement(query.toString());
            pstmt.setInt(1, propertyId);
          	int res=pstmt.executeUpdate();
          	
          	System.out.println(" **********  Deleted Record: "+res);
	            if(res > 0)
	            {
	            	succVal="Successful Deleted Property";
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
