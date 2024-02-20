package com.DIC.DAO.Impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;
import com.DIC.model.BudgetModel;
import com.DIC.model.ConnectorMode;
import com.DIC.model.HomeLoanDataEntryModel;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.PlotsDataEntryModel;
import com.DIC.model.UserDetails;
import com.DIC.model.UserRoleModel;
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
		
			
			String SQL_BUDGET_DETAILS="select * from (select name,cost,contact_owner,'layout' as pro_type ,create_date, image,prim_location ,seco_location,location as loca  from hansi_layout la \r\n"
					+ "UNION all \r\n"
					+ "select owner_name,cost,contact_no, 'agri' as pro_type ,create_date, image,prim_location ,seco_location,location  as loca  from hansi_agricultural ag \r\n"
					+ "UNION all \r\n"
					+ "select owner_name, cost,contact_no,'indu' as pro_type , create_date, image,prim_location ,seco_location,location  as loca from hansi_individual_site indu \r\n"
					+ "UNION all \r\n"
					+ "select owner_name, cost, contact_owner,'villa as pro_type', create_date,image,prim_location ,seco_location,address  as loca from villa_plot vp\r\n"
					+ ") dum where ";
			
			String SQL_USER_ROLE="select r.role_name from user_deta u, role r,user_map_role ur where u.user_id=ur.user_id and ur.role_id =r.role_id and u.is_active = '1'\r\n"
					+ "and r.is_active = '1' and ur.is_active = '1' and u.user_id = ?";
			
			
			String SQL_ROLE_BY_USER_ID="select user_role_id,u.user_id, u.fname,u.lname ,r.role_id,r.role_name,ur.is_active  from user_deta u, role r, user_map_role ur where u.user_id=ur.user_id and ur.role_id =r.role_id and u.is_active = '1'\r\n"
					+ "and r.is_active = '1' and u.user_id = ? order by role_name";
			
			String SQL_ALL_USERS="select * from user_deta order by fname ,lname";
			
			String SQL_USER_REGIST="INSERT INTO user_deta (user_id, fname, lname, user_name, user_pass, address, phone, create_date, is_active) VALUES (nextval('user_seq'), ?, ?,?, ?,?,?, current_timestamp, 1);";
			
			String SQL_FIND_USER_ID_BY_USER_DETAILS="select user_id from user_deta where fname=? and lname=? and user_name=? and  phone=?";
			String NEW_USER_DEFAULT_ROLE="select r.role_id,ur.is_active from user_deta u, role r, user_map_role ur where u.user_id=ur.user_id and ur.role_id =r.role_id and u.is_active = '1' and r.is_active = '1' and u.user_id = 2 order by role_id";
			String SQL_UPDATE_USER="update user_deta set fname=?, lname=?, user_name=?, user_pass=?, address=?, phone= ? , is_active= ? where user_id = ?";
			String SQL_FIND_USER_NAME="select * from user_deta where user_name= ? order by fname ,lname";
			String SQL_UPDATE_PASSWORD="update user_deta set user_pass=? where user_id = ?";
			String SQL_DEL_USER="delete from user_deta where user_id=?";
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
    
	// ************************************* Budget details *************
    /*
     * 
     *  Developed by viswanatha
     * 
     */
    
    
    public List<BudgetModel> getBudget1Details(int budVal)
	{
            
        
		log.info("### : get started :: getBudget1Details() ");
		List<BudgetModel> BudgetModelList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql_query="";
			
			StringBuilder sql_budget_details = new StringBuilder(Constants.SQL.SQL_BUDGET_DETAILS);
					if(budVal==1)
					{
						sql_query=sql_budget_details.append("dum.cost < 5000000 order by create_date ;").toString();
						log.info("###: Budget Query 1: "+sql_query);
					}
					if(budVal==2)
					{
						sql_query=sql_budget_details.append("dum.cost > 5000000 and dum.cost < 10000000 order by create_date ;").toString();
						log.info("###: Budget Query 2: "+sql_query);
					}
					if(budVal==3)
					{
						sql_query=sql_budget_details.append("dum.cost > 10000000 and dum.cost < 20000000 order by create_date ;").toString();
						log.info("###: Budget Query 2: "+sql_query);
					}
					if(budVal==4)
					{
						sql_query=sql_budget_details.append("dum.cost > 20000000 order by create_date ;").toString();
						log.info("###: Budget Query 2: "+sql_query);
					}
			
			
			
		
					
			
			
			
			con=ConnectionDAO.getConnection();
	                    pstmt = con.prepareStatement(sql_budget_details.toString());
	                    ResultSet rs = pstmt.executeQuery();
	         while ( rs.next() ) {
	        	 BudgetModel budgetModel=new BudgetModel();
	        	 
	        	 budgetModel.setName(rs.getString("name"));
	        	 budgetModel.setCost(rs.getInt("cost"));
	        	 budgetModel.setContactNo(rs.getString("contact_owner"));
	        	 budgetModel.setPro_type(properyType(rs.getString("pro_type")));
	        	 budgetModel.setCreate_date(rs.getDate("create_date"));
	        	 budgetModel.setPrim_location(rs.getString("prim_location"));
	        	 budgetModel.setSeco_location(rs.getString("seco_location"));
	        	 budgetModel.setLocation(rs.getString("loca"));
	  
    	        	 
    	        	 if(rs.getBytes("image").length!=0)
                      {
                      byte[] bb=rs.getBytes("image");
                      
                      budgetModel.setStreamedContent(DefaultStreamedContent.builder()
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
		                		      budgetModel.setStreamedContent(DefaultStreamedContent.builder()
		                             .name("US_Piechart.jpg")
		                             .contentType("image/jpg")
		                             .stream(() -> new ByteArrayInputStream(def)).build());
		                			 }
		                	 
		              }       
    	        	 BudgetModelList.add(budgetModel);
	    
	            
	         }
	         	
	         pstmt.close();
	         rs.close();
	         con.close();
	       
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        
	     }
	return BudgetModelList;		
	}

    
    public String properyType(String pro)
    {
    	String proType="";
    	if(pro.equals("layout"))
    	{
    		proType="Layout";
    	}
    	if(pro.equals("agri"))
    	{
    		proType="Agriculture land";
    	}
    	if(pro.equals("indu"))
    	{
    		proType="industrial property";
    	}
    	if(pro.equals("villa as pro_type"))
    	{
    		proType="Villa Propety";
    	}
    	return proType;
    }
    
    
    
 // ************************************* User Role  *************
    /*
     * 
     *  Developed by viswanatha
     * 
     */
    
    public List<String> getUserRole(int userId)
	{
    	
		log.info("### : get started :: getUserRole() ");
		List<String> userRoles = new ArrayList<>();
		try {
			Connection con = null;
			 PreparedStatement pstmt = null;
			
			StringBuilder sql_user_role = new StringBuilder(Constants.SQL.SQL_USER_ROLE);
			log.info("###: Role Query : "+sql_user_role.toString());
			
			con=ConnectionDAO.getConnection();
			pstmt = con.prepareStatement(sql_user_role.toString()); 
            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            		while ( rs.next() ) {
	     	        	 System.out.println("################ Role name ################### : "+rs.getString("role_name"));
			        	 userRoles.add(rs.getString("role_name"));
			         }
	         	
	         
	         rs.close();
	         con.close();
	         pstmt.close();
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //System.exit(0);
	     }
	return userRoles;		
	}
    
    // ************************************* User login  *************
    /*
     * 
     *  Developed by viswanatha
     * 
     */
    
    
    public static boolean loginValidate(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionDAO.getConnection();
			ps = con.prepareStatement("select * from user_deta where user_name = ? and user_pass = ?  and is_active = '1'");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        return false;
		}
		
		return false;
	}
    
    
    
    // ************************************* User already login  *************
    /*
     * 
     *  Developed by viswanatha
     * 
     */
    
    
    public static boolean loginValidate(String user) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionDAO.getConnection();
			ps = con.prepareStatement("select * from user_deta where user_name = ? and is_active = '1'");
			ps.setString(1, user);
			

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        return false;
		}
		
		return false;
	}
    
    
 // ************************************* get User id  *************
    /*
     * 
     *  Developed by viswanatha
     * 
     */
	
    public UserDetails getUserDeta(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		
		UserDetails userDetails=new UserDetails();

		try {
			con = ConnectionDAO.getConnection();
			ps = con.prepareStatement("select * from user_deta where user_name = ? and user_pass = ?");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				
				
				userDetails.setUserId(rs.getInt("user_id"));
				userDetails.setfName(rs.getString("fname"));
				userDetails.setlName(rs.getString("lname"));
				userDetails.setAddress(rs.getString("address"));
				userDetails.setUserName(rs.getString("user_name"));
				
					
								
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		return userDetails;
	}
    
    
    
 // ************************************* get roles by user id  *************
    /*
     * 
     *  Developed by viswanatha
     * 
     */
	
    public List<UserRoleModel> getRolesByUserId(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		List<UserRoleModel> userRoleModelList=new ArrayList<>();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_role_by_user_id = new StringBuilder(Constants.SQL.SQL_ROLE_BY_USER_ID);
			log.info("###: Qury roles by user id : "+sql_role_by_user_id.toString());
			
			ps = con.prepareStatement(sql_role_by_user_id.toString());
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				
				UserRoleModel userRoleModel=new UserRoleModel();
				
				userRoleModel.setUserRoleId(rs.getInt("user_role_id"));
				userRoleModel.setUserId(rs.getInt("user_id"));
				userRoleModel.setfName(rs.getString("fname"));
				userRoleModel.setlName(rs.getString("lname"));
				userRoleModel.setRoleId(rs.getInt("role_id"));
				userRoleModel.setRoleName(rs.getString("role_name"));
				userRoleModel.setActive(rs.getString("is_active").equals("1") ? true: false);
				
				userRoleModelList.add(userRoleModel);					
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		return userRoleModelList;
	}
	
    
    //*********************************************  Save Role  ***************************************
    /*
     * 
     * Development by viswanatha
     * 
     */
    
    
    public int saveRole(List<UserRoleModel> userRoleModelList)
    {
    	int count=0;
    	
				try {
				Connection con = null;
				Statement stmt= null;
		
				con=ConnectionDAO.getConnection();
				stmt = con.createStatement();
				
						for(UserRoleModel ur: userRoleModelList)
						{
							
							int isAct=ur.getActive()==true ? 1 : 0;
						count=count+= stmt.executeUpdate("update user_map_role set is_active= "+isAct+" where user_role_id= "+ur.getUserRoleId()+"");
						
						}
		       
		         stmt.close(); 
		         con.close();
				}catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
		     }
		return count;	
		}
    
    
    
    public List<UserDetails> getAllUsers() {
		Connection con = null;
		PreparedStatement ps = null;
		
		List<UserDetails> userDetailsList=new ArrayList<>();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_all_users = new StringBuilder(Constants.SQL.SQL_ALL_USERS);
			log.info("###: Qury roles by user id : "+sql_all_users.toString());
			
			ps = con.prepareStatement(sql_all_users.toString());
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
    
   /**
    * 
    * 
    * developed by viswanatha
    *
    */
    
 // ***************** Save User registation  **************
    
    public String saveUserRegist(UserDetails userDetails)
    {
    	String succVal="";
    	
        try {
        	int userId;
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_user_regist = new StringBuilder(Constants.SQL.SQL_USER_REGIST);
            pstmt = con.prepareStatement(sql_user_regist.toString());
            
            pstmt.setString(1, userDetails.getfName());
            pstmt.setString(2, userDetails.getlName());
            pstmt.setString(3, userDetails.getUserName());
            pstmt.setString(4, userDetails.getUserPassword());
            pstmt.setString(5, userDetails.getAddress());
            pstmt.setString(6, userDetails.getPhone());
            
           
            	int res=pstmt.executeUpdate();
	            if(res > 0)
	            {
	            	succVal="Successful Registered.";
	            }
	            
	            StringBuilder sql_find_user_id_by_user_details = new StringBuilder(Constants.SQL.SQL_FIND_USER_ID_BY_USER_DETAILS);
	            PreparedStatement ps = con.prepareStatement(sql_find_user_id_by_user_details.toString());
				ps.setString(1, userDetails.getfName());
				ps.setString(2, userDetails.getlName());
				ps.setString(3, userDetails.getUserName());
				ps.setString(4, userDetails.getPhone());

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					userId=rs.getInt("user_id");
					
						System.out.println("*********** User id ************ :"+userId);
						if(userId > 0)
						{
							
							
							PreparedStatement psRole = con.prepareStatement(createDefaultRoles(userId));
							int createdRolesCount=psRole.executeUpdate();
							System.out.println("*********** Role created count ************ :"+createdRolesCount+"     "+createDefaultRoles(userId));
						}
				}    
	            
	            
	            
	            
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	    
	        succVal=e.getMessage();
	        return succVal;
	       
          }
      

        return succVal;
    }
    
    
    
    public String createDefaultRoles(int userId)
    {
    	
    	Connection con = null;
		PreparedStatement ps = null;
		String newUserRole="";
		
		

		try {
			con = ConnectionDAO.getConnection();
		
			StringBuilder sql_new_user_default_role = new StringBuilder(Constants.SQL.NEW_USER_DEFAULT_ROLE);
			ps = con.prepareStatement(sql_new_user_default_role.toString());
			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				
				System.out.println(" ****** > role id :"+rs.getInt("role_id")+"     "+rs.getInt("is_active"));
				newUserRole+="INSERT INTO user_map_role (user_role_id, user_id, role_id, create_date, is_active) VALUES(nextval('user_map_role_seq'::regclass), "+userId+", "+rs.getInt("role_id")+", current_timestamp, "+rs.getInt("is_active")+");";
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		
    	return newUserRole;
    }
    
    
    public String updateUserDetails(UserDetails userDetails)
    {
    	
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_update_user = new StringBuilder(Constants.SQL.SQL_UPDATE_USER);
            pstmt = con.prepareStatement(sql_update_user.toString());
            
            pstmt.setString(1,userDetails.getfName());
            pstmt.setString(2, userDetails.getlName());
            pstmt.setString(3, userDetails.getUserName());
            pstmt.setString(4, userDetails.getUserPassword());
            pstmt.setString(5, userDetails.getAddress());
            pstmt.setString(6, userDetails.getPhone());
            pstmt.setInt(7, userDetails.getIs_active());
            pstmt.setInt(8, userDetails.getUserId());
            

           
           
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
    
    //********************** get User Name ******************
    
    
    public UserDetails getUserName(String userName) {
		Connection con = null;
		PreparedStatement ps = null;
		
		UserDetails userDetails=new UserDetails();
		

		try {
			con = ConnectionDAO.getConnection();
			
			StringBuilder sql_find_user_name = new StringBuilder(Constants.SQL.SQL_FIND_USER_NAME);
			log.info("###: Qury user login details : "+sql_find_user_name.toString());
			
			ps = con.prepareStatement(sql_find_user_name.toString());
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
			
				userDetails.setUserId(rs.getInt("user_id"));
				userDetails.setUserName(rs.getString("user_name"));
				userDetails.setUserPassword(rs.getString("user_pass"));
				
			
									
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        //return false;
		}
		
		return userDetails;
	}
    
    
    //********************** Update Password ******************
    
    public String updatePassword(UserDetails userDetails, String password)
    {
    	
    	System.out.println(" **********  Update password details : "+userDetails.getUserPassword()+"    "+userDetails.getUserId());
    	
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_update_password = new StringBuilder(Constants.SQL.SQL_UPDATE_PASSWORD);
            pstmt = con.prepareStatement(sql_update_password.toString());
            pstmt.setString(1,password);
            pstmt.setInt(2, userDetails.getUserId());
          	int res=pstmt.executeUpdate();
          	
          	System.out.println(" **********  Update password details Recod: "+res);
	            if(res > 0)
	            {
	            	succVal="Successful Reset Password";
	            }
          } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	       succVal=e.getMessage();
	        return succVal;
	       
          }
      

        return succVal;

    }
    
//********************** Update Password ******************
    
    public String deleteUser(int userId)
    {
    	
    	    	
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_del_user = new StringBuilder(Constants.SQL.SQL_DEL_USER);
            pstmt = con.prepareStatement(sql_del_user.toString());
            pstmt.setInt(1, userId);
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
    
    	
   
    
    
    

}
