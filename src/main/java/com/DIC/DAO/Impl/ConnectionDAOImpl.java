package com.DIC.DAO.Impl;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import framework.utilities.GeneralConstants;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.file.UploadedFile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DIC.model.AgriculturalDataEntryModel;
import com.DIC.model.AgriculturalModel;
import com.DIC.model.ConnectorMode;
import com.DIC.model.EnquiryDataEntryModel;
import com.DIC.model.ExceptionValueModel;
import com.DIC.model.HelpModel;
import com.DIC.model.ImageUploadModel;
import com.DIC.model.IndiSiteDataEntryModel;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.LogViewerDatewiseModel;
import com.DIC.model.PlotsDataEntryModel;
import com.DIC.model.PromoImageModel;
import com.DIC.model.RentalDataEntryModel;

import java.sql.PreparedStatement;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean
@ApplicationScoped
public class ConnectionDAOImpl {
	
	private static final Logger log = LogManager.getLogger(ConnectionDAOImpl.class);
	
	interface Constants {
		// SQL
		interface SQL {
			
			//String SQL_LAYOUT="select * from hansi_layout where prim_location = ? and seco_location = ? order by create_date desc";
			String SQL_LAYOUT="select * from hansi_layout where prim_location = ? and seco_location = ? order by last_updated_date desc NULLS last LIMIT ? OFFSET ?";
			String SQL_EXCEPTION="select * from connector_model_status where dataset_id = ? and last_updated_on::date >= ? and last_updated_on::date <= ?";
			String SQL_CONNECTOR = "select * from trn_connector_model where is_active = 1;";
			String QUERY_STRING="select c.dataset_id,c.server_host,c.server_port, c.model_name,a.data_file, a.created_on, a.created_by, a.job_name,a.job_status,  b.step_information,case when b.step_status=1 then 'In Progess' when b.step_status=2 then 'Success' else 'Failed' end as Status,d.job_exception\n" +
                    "from cnr_mdl_rep_d1.trn_job_summary a,\n" +
                    "cnr_mdl_rep_d1.trn_job_step b,\n" +
                    "cnr_mdl_rep_d1.trn_connector_model c,\n" +
                    "cnr_mdl_rep_d1.trn_scheduler_job d\n" +
                    "where a.job_id=b.job_id\n" +
                    "and a.template_id=c.dataset_id\n" +
                    "and b.job_id =d.sch_job_id\n" +
                    "and b.step_information\n" +
                    "not in ('Set model access for LDAP Groups') ";
			//String SQL_AGRICULTURAL="select * from hansi_agricultural where prim_location = ? and seco_location = ? order by create_date desc";
			String SQL_AGRICULTURAL="select * from hansi_agricultural where prim_location = ? and seco_location = ? order by last_updated_date desc NULLS last LIMIT ? OFFSET ?";
			String SQL_LAYOUT_INSERT="insert into hansi_layout (layout_id,name,location,persqft,contact_owner,owner_name,wonership,is_active,transaction,comment,length,width,prim_location,seco_location,create_date,swimingpool,playground,park,wall,community,facing,agent_name,image,cost,user_id,corner_bit,rank,last_updated_date) \n" +
					"values (nextval('hansi_layout_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp);";
				
			//String SQL_IndividualSite="select * from hansi_individual_site where prim_location = ? and seco_location = ? order by create_date desc";
			String SQL_IndividualSite="select * from hansi_individual_site where prim_location = ? and seco_location = ? order by last_updated_date desc NULLS last LIMIT ? OFFSET ?";
			
			String SQL_INDI_INSERT="INSERT INTO hansi_individual_site (ind_id,owner_name, location, contact_no, site_no, persqft, length, width, wonership, transaction, prim_location, seco_location, create_date, is_active,comment,facing,agent_name,cost,image,user_id,corner_bit,rank,last_updated_date) VALUES(nextval('hansi_individual_site_seq'),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,current_timestamp, 1, ?,?,?,?,?,?,?,?,current_timestamp)";
			String SQL_AGRIDATAENTRY_INSERT = "INSERT INTO hansi_agricultural (agri_id,owner_name, contact_no, survey_no, location, wonership, transaction, per_cent, number_cents, water_source, crop, prim_location, seco_location, create_date, is_active, comment,agent_name,cost,image,user_id,corner_bit,rank,last_updated_date) VALUES(nextval('hansi_agricultural_seq'),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp, 1, ?,?,?,?,?,?,?,current_timestamp)";
			String SQL_ENQU_INSERT="INSERT INTO hansi_enquiry (enqi_id, name, email, phone, create_date, is_active) VALUES(nextval('hansi_enquiry_seq'),?, ?, ?, current_timestamp, 1)";
			String SQL_HELP_INSERT="INSERT INTO hansi_help (query_id, query, phone, create_date,is_active) VALUES(nextval('hansi_help_seq'),?, ?, current_timestamp,1)";
			String SQL_IMAGE_UPLOAD="insert into hansi_property_image (prop_img_id,img_name,image) values (nextval('hansi_imageUpload_seq'),?,?)";
			String SQL_IMAGE_DEFAULT="select image from hansi_property_image where prop_img_id=6";
			String SQL_PRIMERY_LOCATION="select prim_id,prim_name from hansi_prim_location order by prim_name";
			String SQL_SECONDRY_LOCATION="select seco_name from hansi_seco_location where prim_code=? order by seco_name desc";
			String SQL_RENTAL_DATA_INSERT="INSERT INTO rental_plot (rental_id,own_name,address,own_con_no,pro_type,tot_bed_rooms,tot_floors,tot_bath_rooms,furniture,rent_pref,sec_depo,mon_rent,kitc_room,facing,tot_area_sqft,prim_location,seco_location,image,create_date, is_active,avail_date) VALUES (nextval('rental_plot_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,1,?)";
			String SQL_RENTAL_DETAILS="select * from rental_plot where prim_location = ? and seco_location = ?";
			String SQL_PACKAGE_ENQUIRY="INSERT INTO hansi_enquiry (enqi_id, name, email, phone, create_date, is_active,enq_type) VALUES(nextval('hansi_enquiry_seq'),?, ?, ?, current_timestamp, 1,?)";
			//String SQL_PROMO_IMAGE="insert into promo_img (promo_id,image,create_date, is_active,comment,img_name,display_order) values (nextval('promo_seq'),?,current_timestamp, 1,?,?,?)";
			//String SQL_PROMO_IMAGE="insert into promo_img (promo_id,image,create_date, is_active,comment,img_name,display_order,prim_location,default_dis) values (nextval('promo_seq'),?,current_timestamp, 1,?,?,?,?,?)";
			String SQL_PROMO_IMAGE="insert into promo_img (promo_id,image,create_date, is_active,comment,img_name,display_order,prim_location,default_dis) values (nextval('promo_seq'),?,current_timestamp, 1,?,?,nextval('display_order_seq'),?,?)";

			String SQL_Individual_COUNT="select count(*) from hansi_individual_site where prim_location = ? and seco_location = ?";
			//String SQL_PROMO_IMAGE_LAYOUT="select * from promo_img where is_active ='1' order by display_order LIMIT ? OFFSET ?";
			String SQL_PROMO_IMAGE_LAYOUT="select * from promo_img where prim_location = ? and is_active ='1' order by promo_id LIMIT ? OFFSET ?";


			String SQL_LAYOUT_COUNT="select count(*) from hansi_layout where prim_location = ? and seco_location = ?";
			String SQL_PROMO_COUNT="select count(*) from promo_img where is_active ='1'";
			String SQL_AGRI_COUNT="select count(*)  from hansi_agricultural where prim_location = ? and seco_location = ?";
			String SQL_PROMO_IMAGE_VILLA="select * from promo_img where is_active ='1' LIMIT ? OFFSET ?";
			String SQL_IMAGE_UPLOAD_GALARY="INSERT INTO prop_galary (galary_id,image, create_date, is_active, user_id,prop_id,prop_type) values (nextval('prop_galary_seq'), ?, current_timestamp,1, ?, ?, ?)";
			String SQL_LATEST_LAYOUT_ID="select layout_id from hansi_layout order by create_date desc limit 1";
			String SQL_LATEST_INDI_ID = "select ind_id from hansi_individual_site order by create_date desc limit 1";
			String SQL_LATEST_AGRI_ID = "select agri_id from hansi_agricultural order by create_date desc limit 1";

		}
                
	}
	
		
	//****************************************** Primary Location ****************************************
	
		public Map<String, String> getPrimaryLocation()
		{	
			
			
			Map<String, String> primaryModelList = new java.util.HashMap();
				try {
				Connection con = null;
				Statement stmt= null;
				
				StringBuilder sql_primary_data = new StringBuilder(Constants.SQL.SQL_PRIMERY_LOCATION);
				con=ConnectionDAO.getConnection();
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql_primary_data.toString());
				log.info("****************** log x***************** :"+rs.getFetchSize());
						while(rs.next())
						{ 
				  		 primaryModelList.put(rs.getString("prim_name"), rs.getString("prim_name"));
					  		
						}
		         rs.close();
		         stmt.close();
		         con.close();
				}catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
		        log.error("An error occurred: {}", e.getMessage());
		     }
		return primaryModelList;		
		}

		
		//****************************************** Secondry Locaton ****************************************
		
			public List<String> getSecondryLocation(String primCode)
			{		
				
				
				//log.log(Level.INFO, "Primary value  in db--------->:"+primCode);
				List<String> secondryModelList = new ArrayList<>();
					try {
					Connection con = null;
					PreparedStatement pstmt = null;
					StringBuilder sql_secondry_data = new StringBuilder(Constants.SQL.SQL_SECONDRY_LOCATION);
					log.info("###: Secondry location query : "+sql_secondry_data.toString());
					
					
					con=ConnectionDAO.getConnection();
					pstmt = con.prepareStatement(sql_secondry_data.toString());
	                pstmt.setString(1, primCode);
	                ResultSet rs = pstmt.executeQuery();
	                      	while ( rs.next() ) {
	    	            		secondryModelList.add(rs.getString("seco_name"));
	    	            		
	    	            		//log.log(Level.INFO, "debug x----->"+rs.getString("seco_name"));
				          	 }	
	                
	                 rs.close();
			         con.close();
			         pstmt.close();
			         log.info("### : *** Connection Closed from getSecondryLocation");
			     } catch (Exception e) {
			        e.printStackTrace();
			        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
			        log.error("An error occurred: {}", e.getMessage());
			     }
			return secondryModelList;		
			}
			
	
	
	//********************************************************************
	
	public int getCount()
	{
		//log.info("### : get started :: getActiveModelList() ");
		
		int count=0;
		try {
			Connection con = null;
			Statement stmt= null;
			
			StringBuilder sql_count = new StringBuilder(Constants.SQL.SQL_CONNECTOR);
			//log.info("###: Query : "+sql_connector.toString());
			
			con=ConnectionDAO.getConnection();
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql_count.toString());
		
					while(rs.next())
					{
						count=count+1;
					}
					
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ count @@@@@@@@@@@@@@@@@@@ :"+count);
			
	         rs.close();
	         stmt.close();
	         con.close();
	         //log.info("### : *** Connection Closed from getActiveModelAll()");
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	     }
	return count;		
	}
	
	
 // ************************** getActiveModelList()*******************//
    
    public List<ConnectorMode> getActiveModelList()
	{


		List<ConnectorMode> connectorModelList = new ArrayList<>();
		try {
			Connection con = null;
			Statement stmt= null;
			
			StringBuilder sql_connector = new StringBuilder(Constants.SQL.SQL_CONNECTOR);
			//log.info("###: Query : "+sql_connector.toString());
			
			con=ConnectionDAO.getConnection();
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql_connector.toString());
	         while ( rs.next() ) {
	        	 ConnectorMode conM=new ConnectorMode();
	        	 
	        	 conM.setDatasetId(rs.getLong("dataset_id"));
	        	 conM.setServerHost(rs.getString("server_host"));
	        	 conM.setServerPort(rs.getString("server_port"));
	        	 conM.setModelName(rs.getString("model_name"));
	        	 conM.setModel_register_env(rs.getString("model_register_env"));
	        	 conM.setSource_db(rs.getString("source_db"));
	        	 conM.setUnify_groups(rs.getString("unify_groups"));
				 connectorModelList.add(conM);

	            
	         }
	         	
	         
	         rs.close();
	         stmt.close();
	         con.close();
	         //log.info("### : *** Connection Closed from getActiveModelList()");
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	     }
	return connectorModelList;
	}
    
    
//****************************** getLogViewerDateWise(String datasetID, java.util.Date sDate, java.util.Date eDate) *************
    
    public List<LogViewerDatewiseModel> getLogViewerDateWise(String datasetID, java.util.Date sDate, java.util.Date eDate)
    {
        System.out.println("Parameter Values : "+datasetID+"   "+sDate+"    "+eDate);
        StringBuffer query=new StringBuffer();
        query.append(Constants.SQL.QUERY_STRING.toString());
        
        if(datasetID!=null && !datasetID.isEmpty())
        {
            System.out.println("True : "+datasetID);
            query.append("and c.dataset_id="+datasetID+"");
        }
        if(sDate!=null)
        {
            query.append(" and a.created_on >= '"+sDate+"'");
        }
        if(eDate!=null)
        {
            query.append(" and a.created_on <= '"+eDate+"'");
        }
        query.append(" order by c.dataset_id,b.job_id,b.job_step_index;");    
        
        System.out.println("Query String : "+query);
        List<LogViewerDatewiseModel> logViewerDatewiseModelList = new ArrayList<>();
        try {
			Connection con = null;
			PreparedStatement pstmt = null;
                	con=ConnectionDAO.getConnection();
	                pstmt = con.prepareStatement(query.toString());
                        ResultSet rs = pstmt.executeQuery();
         		
	         while ( rs.next() ) {
	        	 LogViewerDatewiseModel logViewerDatewiseModel=new LogViewerDatewiseModel();
	           
	            logViewerDatewiseModel.setDatasetId(rs.getInt("dataset_id"));
                    logViewerDatewiseModel.setServerHost(rs.getString("server_host"));
                    logViewerDatewiseModel.setServerPort(rs.getString("server_port"));
                    logViewerDatewiseModel.setModelName(rs.getString("model_name"));
                    logViewerDatewiseModel.setDataFile(rs.getString("data_file"));
                    logViewerDatewiseModel.setCreatedOn(rs.getString("created_on"));
                    logViewerDatewiseModel.setCreatedBy(rs.getString("created_by"));
                    logViewerDatewiseModel.setJobName(rs.getString("job_name"));
                    logViewerDatewiseModel.setJobStatus(rs.getInt("job_status"));
                    logViewerDatewiseModel.setStepInformation(rs.getString("step_information"));
                    logViewerDatewiseModel.setStatus(rs.getString("status"));
                    logViewerDatewiseModel.setCreatedOnDate(rs.getDate("created_on"));
                    
                    
                    if(rs.getString("step_information").equals("Rebuild Dimension Hierarchies"))
                    {
                        //logViewerDatewiseModel.setPageName("/ui/exceptionPage.xhtml");
                        logViewerDatewiseModel.setPageName("exceptionPage");
                        
                    }
                    if(rs.getString("step_information").equals("Create or Update Model"))
                    {
                        //logViewerDatewiseModel.setPageName("/ui/exceptionPage.xhtml");
                    	logViewerDatewiseModel.setPageName("exceptionPage");
                    }
                    if(rs.getString("step_information").equals("Data Migration (Connector)"))
                    {
                        logViewerDatewiseModel.setPageName("dataMigration");
                    }
                    
                    if(rs.getString("step_information").equals("Delete connector model "))
                    {
                       
                        logViewerDatewiseModel.setPageName("deleteConnectorModel");
                    }
                    if(rs.getString("step_information").equals("Register Model in Unify"))
                    {
                        logViewerDatewiseModel.setPageName("registerModelInUnify");
                    }
  	        	                     
	         logViewerDatewiseModelList.add(logViewerDatewiseModel);
	         }
	         	
	         rs.close();
                 pstmt.close();
	         con.close();
	         //log.info("### : *** Connection Closed from getRegistEnviDetailS()");
	     } catch (Exception e) {
	    	 
	    	 //log.info("Connected failed from getRegistEnviDetailS() : d"+e);
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	     }
     
     
        
     return logViewerDatewiseModelList;
    }
    
//********************** getExceptionValue(long datasetid,java.util.Date createdOnDate) *********************
    
    public List<ExceptionValueModel> getExceptionValue(long datasetid,java.util.Date createdOnDate)
    {
  
        System.out.println(" DaoImp :"+datasetid);
        
        List<ExceptionValueModel> exceptionValueModelList = new ArrayList<>();
        try {
			Connection con = null;
			PreparedStatement pstmt = null;
		        StringBuilder sq_exception = new StringBuilder(Constants.SQL.SQL_EXCEPTION);
			con=ConnectionDAO.getConnectionNew();
                        
                        java.sql.Date createdOnDateval = new java.sql.Date(createdOnDate.getTime());
                   	pstmt = con.prepareStatement(sq_exception.toString()); 
                        pstmt.setLong(1, datasetid);
                        pstmt.setDate(2, createdOnDateval);
                        pstmt.setDate(3, createdOnDateval);
                        ResultSet rs = pstmt.executeQuery();
             		
	         while ( rs.next() ) {
	        	 ExceptionValueModel exceptionValueModel=new ExceptionValueModel();
	           
	            exceptionValueModel.setDatasetId(rs.getLong("dataset_id"));
                    exceptionValueModel.setDimensionName(rs.getString("dimension_name"));
                    exceptionValueModel.setIsHierarchyCreated(rs.getInt("is_hierarchy_created"));
                    exceptionValueModel.setIsHierarchyRebuilt(rs.getInt("is_hierarchy_rebuilt"));
                    exceptionValueModel.setException(rs.getString("exception"));
                    exceptionValueModel.setStatusCode(rs.getInt("status_code"));
                    exceptionValueModel.setLastupdated(rs.getString("last_updated_on"));
                    exceptionValueModel.setStatusId(rs.getLong("status_id"));
             
                    exceptionValueModelList.add(exceptionValueModel);
	         }
	         rs.close();
                 pstmt.close();
	         con.close();
	         //log.info("### : *** Connection Closed from getRegistEnviDetailS()");
	     } catch (Exception e) {
	    	 
	    	 //log.info("Connected failed from getRegistEnviDetailS() : d"+e);
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	     }
      return exceptionValueModelList;
    }
    
    
    
    public List<LayoutMode> getLayoutDetails(String priLocation, String secLocation,int pageSize, int currentPage)
	{
    	ConnectionDAO condao;
    	BasicDataSource bds=null;
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	
       System.out.println("********* Selected Location : "+priLocation+"   "+secLocation);
	
		List<LayoutMode> layoutModeList = new ArrayList<>();
		try {
			
			condao=new ConnectionDAO();
			bds=condao.getDataSource();
			con=bds.getConnection();
		
			StringBuilder sq_layout = new StringBuilder(Constants.SQL.SQL_LAYOUT);
			
							pstmt = con.prepareStatement(sq_layout.toString());
                            pstmt.setString(1, priLocation);
                            pstmt.setString(2, secLocation);
                            pstmt.setInt(3, pageSize);
				            pstmt.setInt(4, (currentPage - 1) * pageSize);
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
    
    
    
    
  //************************************AgriculturalDetails******************************************//

    public List<AgriculturalModel> getAgriculturalDetails(String priLocation, String secLocation,int pageSize, int currentPage)
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
			
			StringBuilder sql_agricultural = new StringBuilder(Constants.SQL.SQL_AGRICULTURAL);
			log.info("###: Query : "+sql_agricultural.toString());
			
	                    pstmt = con.prepareStatement(sql_agricultural.toString());
                            pstmt.setString(1, priLocation);
                            pstmt.setString(2, secLocation);
                            pstmt.setInt(3, pageSize);
				            pstmt.setInt(4, (currentPage - 1) * pageSize);
				            
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

    
    
 // ***************** update plot data entry **************
    public String updatePlotDataEntry(PlotsDataEntryModel plotsDataEntryModel, int userId,int rankId)
    {
    	
    	log.info(" Executing dao updatePlotDataEntry ");
    	String succVal="";
    	
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_layout_insert = new StringBuilder(Constants.SQL.SQL_LAYOUT_INSERT);
            
            pstmt = con.prepareStatement(sql_layout_insert.toString());
            
            pstmt.setString(1,plotsDataEntryModel.getName());
            pstmt.setString(2, plotsDataEntryModel.getLocation());
            pstmt.setDouble(3, plotsDataEntryModel.getPersqft());
            //pstmt.setInt(4, plotsDataEntryModel.getCost());
            pstmt.setString(4, plotsDataEntryModel.getContactOwner());
            pstmt.setString(5, plotsDataEntryModel.getOwnerName());
            pstmt.setString(6, plotsDataEntryModel.getWonership());
            pstmt.setInt(7,1);
            pstmt.setString(8, plotsDataEntryModel.getTransaction());
            pstmt.setString(9, plotsDataEntryModel.getComment());
            pstmt.setInt(10, plotsDataEntryModel.getLength());
            pstmt.setInt(11, plotsDataEntryModel.getWidth());
            pstmt.setString(12, plotsDataEntryModel.getPrimLocation());
            pstmt.setString(13, plotsDataEntryModel.getSecoLocation());
            pstmt.setString(14, plotsDataEntryModel.getSwimingPool());
            pstmt.setString(15, plotsDataEntryModel.getPlayground());
            pstmt.setString(16, plotsDataEntryModel.getPark());
            pstmt.setString(17, plotsDataEntryModel.getWall());
            pstmt.setString(18, plotsDataEntryModel.getCommunity());
            pstmt.setString(19, plotsDataEntryModel.getFacing());
            pstmt.setString(20, plotsDataEntryModel.getAgentName());
                    InputStream fin2=plotsDataEntryModel.getInputStream();
		            UploadedFile file=plotsDataEntryModel.getFile();
			pstmt.setBinaryStream(21, fin2, file.getSize());
			pstmt.setDouble(22, plotsDataEntryModel.getPersqft() * (plotsDataEntryModel.getLength() * plotsDataEntryModel.getWidth()));
			pstmt.setInt(23, userId);
			pstmt.setString(24, plotsDataEntryModel.getCornerBit());
			pstmt.setInt(25, rankId);


			int res = pstmt.executeUpdate();
			System.out.println("Result status  - >" + res);
			if (res > 0) {
				succVal = "Successful updated record";
				int layoutId=getLatestPropertyId();
				System.out.println("Layout Layout id : "+layoutId);
				uploadGalary(plotsDataEntryModel,
						userId,
						layoutId,
						GeneralConstants.PropertyType.layout );
			}
           
        } catch (Exception e) {
         
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.out.println("Error message  - >"+e.getMessage());
	    
	        succVal=e.getMessage();
	        log.error("An error occurred: {}", e.getMessage());
	        return succVal;
	       
	}
      

        return succVal;
    }

	//***************************getLatestPropertyid **************************

	public int getLatestPropertyId()
	{

		int lyoutId=0;
			try {
				Connection con = null;
				PreparedStatement pstmt = null;
				con=ConnectionDAO.getConnection();
				StringBuilder sq_latest_layout_id = new StringBuilder(Constants.SQL.SQL_LATEST_LAYOUT_ID);
				pstmt = con.prepareStatement(sq_latest_layout_id.toString());
				ResultSet rs = pstmt.executeQuery();
				while ( rs.next() ) {
					lyoutId=rs.getInt("layout_id");

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
		return lyoutId;
		}



    //*********************************************

	public void uploadGalary(PlotsDataEntryModel plotsDataEntryModel, int userId, int layoutId, int propType)
	{
		System.out.println("Database upload Image size :"+plotsDataEntryModel.getInputStreams().size());

		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = ConnectionDAO.getConnection();
			StringBuilder sql_image_upload_galary = new StringBuilder(Constants.SQL.SQL_IMAGE_UPLOAD_GALARY);
			PreparedStatement pstmtGalary = con.prepareStatement(sql_image_upload_galary.toString());
			List<InputStream> inputStream = plotsDataEntryModel.getInputStreams();
			for (int i = 0; i < plotsDataEntryModel.getInputStreams().size(); i++) {
				pstmtGalary.setBinaryStream(1, inputStream.get(i));
				pstmtGalary.setInt(2, userId);
				pstmtGalary.setInt(3, layoutId);
				pstmtGalary.setInt(4, propType);
				int x = pstmtGalary.executeUpdate();
			}

		} catch (Exception e) {

					e.printStackTrace();
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.out.println("Error message  - >"+e.getMessage());

					log.error("An error occurred: {}", e.getMessage());

				}

	}
    

  //************************************IndividualSiteDetails******************************************//

    public List<IndividualSiteModel> getIndividualSiteDetails(String priLocation, String secLocation,int pageSize, int currentPage)
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
    			
    			StringBuilder sql_IndividualSite = new StringBuilder(Constants.SQL.SQL_IndividualSite);
    			log.info("###: Query : "+sql_IndividualSite.toString());
    			                pstmt = con.prepareStatement(sql_IndividualSite.toString());
                                pstmt.setString(1, priLocation);
                                pstmt.setString(2, secLocation);
                                pstmt.setInt(3, pageSize);
    				            pstmt.setInt(4, (currentPage - 1) * pageSize);
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
    
    
  //***individual count**////
    public int getIndividualSiteDetailsCountTotalRecords(String priLocation, String secLocation )
    {
    	int totalRecords=0;
    	
    	ConnectionDAO condao;
    	BasicDataSource bds=null;
    	Connection con = null;
    	PreparedStatement pstmt = null;
    


   	
   	StringBuilder sql_agri_details = new StringBuilder(Constants.SQL.SQL_Individual_COUNT);
	
		
	 	try {
	 		condao=new ConnectionDAO();
	 		bds=condao.getDataSource();
	 		con=bds.getConnection();
            pstmt = con.prepareStatement(sql_agri_details.toString());
            pstmt.setString(1, priLocation);
         pstmt.setString(2, secLocation);
         ResultSet rs = pstmt.executeQuery();
	                  
	        	if (rs.next()) {
	                totalRecords = rs.getInt(1);
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
    	
    	return totalRecords;
    }
    
 // ***************** update Indi data entry **************
    public String updateIndiDataEntry(IndiSiteDataEntryModel indiSiteDataEntryModel,int userId, int rankId)
    {
    	String succVal="";
        try {
			            Connection con = null;
			            PreparedStatement pstmt = null;
			            con=ConnectionDAO.getConnection();
			            
			            StringBuilder sql_Indi_insert = new StringBuilder(Constants.SQL.SQL_INDI_INSERT);
			            
			            pstmt = con.prepareStatement(sql_Indi_insert.toString());
			            
			            pstmt.setString(1,indiSiteDataEntryModel.getOwnerName());
			            pstmt.setString(2, indiSiteDataEntryModel.getLocation());
			            pstmt.setString(3, indiSiteDataEntryModel.getContactNo());
			            pstmt.setString(4, indiSiteDataEntryModel.getSiteNo());
			            pstmt.setInt(5, indiSiteDataEntryModel.getPersqft());
			            pstmt.setInt(6, indiSiteDataEntryModel.getLength());
			            pstmt.setInt(7, indiSiteDataEntryModel.getWidth());
			            pstmt.setString(8, indiSiteDataEntryModel.getWonership());
			            pstmt.setString(9, indiSiteDataEntryModel.getTransaction());
			            pstmt.setString(10,indiSiteDataEntryModel.getPrimLocation());
			            pstmt.setString(11,indiSiteDataEntryModel.getSecoLocation());
			            pstmt.setString(12, indiSiteDataEntryModel.getComment());
			            pstmt.setString(13, indiSiteDataEntryModel.getFacing());
			            pstmt.setString(14, indiSiteDataEntryModel.getAgentName());
			            pstmt.setInt(15, indiSiteDataEntryModel.getPersqft() * (indiSiteDataEntryModel.getLength() * indiSiteDataEntryModel.getWidth())); // Cost
			        		InputStream fin2=indiSiteDataEntryModel.getInputStream();
			    			UploadedFile file=indiSiteDataEntryModel.getFile();
			            pstmt.setBinaryStream(16, fin2, file.getSize());
			            pstmt.setInt(17,userId);
			            pstmt.setString(18, indiSiteDataEntryModel.getCornerBit());
			            pstmt.setInt(19, rankId);



			// ... prepare pstmt earlier ...
			int res = pstmt.executeUpdate();
			System.out.println("Result status  - >" + res);
			if (res > 0) {
				succVal = "Successfully updated record";
				int indiId = getIndiPropertyId();
				System.out.println("Indi Property ID : " + indiId);

				// call the upload method (do NOT declare it here)
				uploadGalary(indiSiteDataEntryModel, userId, indiId, GeneralConstants.PropertyType.indi);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println("Error message  - >" + e.getMessage());
			succVal = e.getMessage();
			log.error("An error occurred: {}", e.getMessage());
			return succVal;
		}

		return succVal;
	}
//indiproperty

		public int getIndiPropertyId() {
		int indiId = 0;
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = ConnectionDAO.getConnection();
			StringBuilder sq_latest_indi_id = new StringBuilder(Constants.SQL.SQL_LATEST_INDI_ID);
			pstmt = con.prepareStatement(sq_latest_indi_id.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				indiId = rs.getInt("ind_id");
			}

			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			log.error("An error occurred: {}", e.getMessage());
		}
		return indiId;
	}

	//*********************************

	public void uploadGalary(IndiSiteDataEntryModel indiSiteDataEntryModel, int userId, int indiId, int propType)
	{
		System.out.println("Database upload Image size :"+indiSiteDataEntryModel.getInputStreams().size());

		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = ConnectionDAO.getConnection();
			StringBuilder sql_image_upload_galary = new StringBuilder(Constants.SQL.SQL_IMAGE_UPLOAD_GALARY);
			PreparedStatement pstmtGalary = con.prepareStatement(sql_image_upload_galary.toString());
			List<InputStream> inputStream = indiSiteDataEntryModel.getInputStreams();
			for (int i = 0; i < indiSiteDataEntryModel.getInputStreams().size(); i++) {
				pstmtGalary.setBinaryStream(1, inputStream.get(i));
				pstmtGalary.setInt(2, userId);
				pstmtGalary.setInt(3, indiId);
				pstmtGalary.setInt(4, propType);
				int x = pstmtGalary.executeUpdate();
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.out.println("Error message  - >"+e.getMessage());

			log.error("An error occurred: {}", e.getMessage());

		}

	}


		// ***************** update Agricultural data entry **************
    public String updateAgriDataEntry(AgriculturalDataEntryModel agriculturalDataEntryModel,int userId, int rankId)
    {
		log.info(" Executing dao Agricultural data entry ");
    	String succVal="";
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_agriDataEntry_insert = new StringBuilder(Constants.SQL.SQL_AGRIDATAENTRY_INSERT);
            
            pstmt = con.prepareStatement(sql_agriDataEntry_insert.toString());
            
            pstmt.setString(1,agriculturalDataEntryModel.getOwnerName());
            pstmt.setString(2, agriculturalDataEntryModel.getContactNo());
            pstmt.setString(3, agriculturalDataEntryModel.getSurveyNo());
            pstmt.setString(4, agriculturalDataEntryModel.getLocation());
            pstmt.setString(5, agriculturalDataEntryModel.getWonership());
            pstmt.setString(6, agriculturalDataEntryModel.getTransaction());
            pstmt.setInt(7, agriculturalDataEntryModel.getPerCent());
            pstmt.setInt(8, agriculturalDataEntryModel.getNumberCents());
            pstmt.setString(9, agriculturalDataEntryModel.getWaterSource());
            pstmt.setString(10, agriculturalDataEntryModel.getCrop());
            pstmt.setString(11, agriculturalDataEntryModel.getPrimLocation());
            pstmt.setString(12, agriculturalDataEntryModel.getSecoLocation());
            pstmt.setString(13, agriculturalDataEntryModel.getComment());
            pstmt.setString(14, agriculturalDataEntryModel.getAgentName());
            pstmt.setInt(15, agriculturalDataEntryModel.getPerCent() * agriculturalDataEntryModel.getNumberCents());
	            InputStream fin2=agriculturalDataEntryModel.getInputStream();
	            UploadedFile file=agriculturalDataEntryModel.getFile();
		    pstmt.setBinaryStream(16, fin2, file.getSize());  
		    pstmt.setInt(17, userId);
		    pstmt.setString(18, agriculturalDataEntryModel.getCornerBit());
		    pstmt.setInt(19, rankId);

			int res = pstmt.executeUpdate();
			System.out.println("Result status  - >" + res);
			if (res > 0) {
				succVal = "Successfully updated record";
				int agriId = getAgriPropertyId();
				System.out.println("Agri Property ID : " + agriId);
				uploadGalary(agriculturalDataEntryModel, userId, agriId, GeneralConstants.PropertyType.agri);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println("Error message  - >" + e.getMessage());
			succVal = e.getMessage();
			log.error("An error occurred: {}", e.getMessage());
			return succVal;
		}

		return succVal;
	}
	//******************leatest getAgriPropertyId************
	public int getAgriPropertyId() {
		int agriId = 0;
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = ConnectionDAO.getConnection();
			StringBuilder sq_latest_agri_id = new StringBuilder(Constants.SQL.SQL_LATEST_AGRI_ID);
			pstmt = con.prepareStatement(sq_latest_agri_id.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				agriId = rs.getInt("agri_id");
			}

			pstmt.close();
			rs.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			log.error("An error occurred: {}", e.getMessage());
		}
		return agriId;
	}


	//**************galary***************//

	public void uploadGalary(AgriculturalDataEntryModel agriculturalDataEntryModel,  int userId, int agriId, int propType)
	{
		System.out.println("Database upload Image size :"+agriculturalDataEntryModel.getInputStreams().size());

		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = ConnectionDAO.getConnection();
			StringBuilder sql_image_upload_galary = new StringBuilder(Constants.SQL.SQL_IMAGE_UPLOAD_GALARY);
			PreparedStatement pstmtGalary = con.prepareStatement(sql_image_upload_galary.toString());
			List<InputStream> inputStream = agriculturalDataEntryModel.getInputStreams();
			for (int i = 0; i < agriculturalDataEntryModel.getInputStreams().size(); i++) {
				pstmtGalary.setBinaryStream(1, inputStream.get(i));
				pstmtGalary.setInt(2, userId);
				pstmtGalary.setInt(3, agriId);
				pstmtGalary.setInt(4, propType);
				int x = pstmtGalary.executeUpdate();
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.out.println("Error message  - >"+e.getMessage());

			log.error("An error occurred: {}", e.getMessage());

		}

	}
    
    
    
    
    
 // ***************** update enquiry data entry **************
    public int updateEnquiryDataEntry(EnquiryDataEntryModel enquiryDataEntryModel)
    {
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_enqu_insert = new StringBuilder(Constants.SQL.SQL_ENQU_INSERT);
            
            pstmt = con.prepareStatement(sql_enqu_insert.toString());
            
            pstmt.setString(1,enquiryDataEntryModel.getName());
            pstmt.setString(2, enquiryDataEntryModel.getEmail());
            pstmt.setString(3, enquiryDataEntryModel.getPhone());
           
                 
           
            int res=pstmt.executeUpdate();
            if(res > 0)
            {
               return 1;
            }
            else
            {
                return 0;
            }
        
   
        
        } catch (Exception e) {
                
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	}
      
        
        return 1;
    }
    
    //***************************************** Question updated ***********************************
    
    public int updateHelpDataEntry(HelpModel helpModel)
    {
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_help_insert = new StringBuilder(Constants.SQL.SQL_HELP_INSERT);
            
            pstmt = con.prepareStatement(sql_help_insert.toString());
            
            pstmt.setString(1,helpModel.getQuery());
            pstmt.setString(2, helpModel.getPhone());
           
                 
           
            int res=pstmt.executeUpdate();
            if(res > 0)
            {
               return 1;
            }
            else
            {
                return 0;
            }
        
   
        
        } catch (Exception e) {
                
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	}
      
        
        return 1;
    }
    
    
    

    //***************************************** Upload Image ***********************************
    
    
    public String uploadImageDao(ImageUploadModel imageUploadModel)
    {
    	String succVal="";
        try {
        	
        	 
        	 
        	 StringBuilder sql_layout = new StringBuilder("update hansi_layout set image=? where img_path = ?");
        	 StringBuilder sql_agricultural = new StringBuilder("update hansi_agricultural set image=? where img_path = ?");
        	 StringBuilder sql_individual_site = new StringBuilder("update hansi_individual_site set image=? where img_path = ?");
        	 StringBuilder sql_property_image = new StringBuilder("insert into hansi_property_image values (?,?,?)");
        	 
        	 
        	 
        	 
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_image_upload = new StringBuilder(Constants.SQL.SQL_IMAGE_UPLOAD);
            pstmt = con.prepareStatement(sql_image_upload.toString());
            
            //pstmt.setInt(1, 102);
            pstmt.setString(1, imageUploadModel.getImageName());
		            InputStream fin2=imageUploadModel.getInputStream();
		            UploadedFile file=imageUploadModel.getFile();
            pstmt.setBinaryStream(2, fin2, file.getSize());
            
            
          
        	
        	int res=pstmt.executeUpdate();
        	System.out.println("*****Successful updated image*******");
            if(res > 0)
            {
            	succVal="Successful updated record";
            }
            
           
       
       
    
        
        } catch (Exception e) {
                
        	e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	        succVal=e.getMessage();
	        return succVal;
	}
      
        
        return succVal;
    }
    
    
 // ***************** update Rental data entry **************
 // ***************** update Rental data entry **************
    public String updateRentalDataEntry(RentalDataEntryModel rentalDataEntryModel)
    {
    	
    	String succVal="";
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_rentalDataEntry_insert = new StringBuilder(Constants.SQL.SQL_RENTAL_DATA_INSERT);
            
            pstmt = con.prepareStatement(sql_rentalDataEntry_insert.toString());
            
            pstmt.setString(1,rentalDataEntryModel.getOwn_name());
            pstmt.setString(2, rentalDataEntryModel.getAddress());
            pstmt.setString(3, rentalDataEntryModel.getOwn_con_no());
            pstmt.setString(4, rentalDataEntryModel.getPro_type());
            pstmt.setInt(5, rentalDataEntryModel.getTot_bed_rooms());
            pstmt.setInt(6, rentalDataEntryModel.getTot_floors());
            pstmt.setInt(7, rentalDataEntryModel.getTot_bath_rooms());
            pstmt.setString(8, rentalDataEntryModel.getFurniture());
            pstmt.setString(9, rentalDataEntryModel.getRent_pref());
            pstmt.setInt(10, rentalDataEntryModel.getSec_depo());
            pstmt.setInt(11, rentalDataEntryModel.getMon_rent());
            pstmt.setString(12, rentalDataEntryModel.getKitc_room());
            pstmt.setString(13, rentalDataEntryModel.getFacing());
            pstmt.setInt(14, rentalDataEntryModel.getTot_area_sqft());
            pstmt.setString(15, rentalDataEntryModel.getPrim_location());
            pstmt.setString(16, rentalDataEntryModel.getSeco_location());
            	InputStream fin2=rentalDataEntryModel.getInputStream();
            	UploadedFile file=rentalDataEntryModel.getFile();
		    pstmt.setBinaryStream(17, fin2, file.getSize()); 
		    pstmt.setString(18, (rentalDataEntryModel.getAvail_date().toString()!=null) ? rentalDataEntryModel.getAvail_date().toString() : "" );
		
		    int res=pstmt.executeUpdate();
		        if(res > 0)
		        {
		        	succVal="Successful updated record";
		        }
        
          
        
        } catch (Exception e) {
                
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        succVal=e.getMessage();
	        log.error("An error occurred: {}", e.getMessage());
	        return succVal;
	   }
      
        
    return succVal;
    }
    
    
//********************************************** Get Rental details *************************************************************
    
    public List<RentalDataEntryModel> getRentalDetails(String priLocation, String secLocation,String proType)
	{
            
       System.out.println("********* Rental database : "+priLocation+"   "+secLocation+"   ");
	
		List<RentalDataEntryModel> RentalDataEntryModelList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			StringBuilder sql_rental_details = new StringBuilder(Constants.SQL.SQL_RENTAL_DETAILS);
		
			if(proType.equals("All"))
			{
				sql_rental_details.append(" and pro_type in ('Villa','House','Plot','Land','Shop') order by create_date desc");
			}
			else
			{
				sql_rental_details.append(" and pro_type='"+proType+"' order by create_date desc");
			}
			
			con=ConnectionDAO.getConnection();
			
			System.out.println("Rental Query : "+sql_rental_details.toString());
							pstmt = con.prepareStatement(sql_rental_details.toString());
                            pstmt.setString(1, priLocation);
                            pstmt.setString(2, secLocation);
                            ResultSet rs = pstmt.executeQuery();
	         while ( rs.next() ) {
	        	 RentalDataEntryModel rentalDataEntyModel=new RentalDataEntryModel();
	        	 
	        	 rentalDataEntyModel.setOwn_name(rs.getString("own_name"));
	        	 rentalDataEntyModel.setAddress(rs.getString("address"));
	        	 rentalDataEntyModel.setOwn_con_no(rs.getString("own_con_no"));
	        	 rentalDataEntyModel.setPro_type(rs.getString("pro_type"));
	        	 rentalDataEntyModel.setTot_bed_rooms(rs.getInt("tot_bed_rooms"));
	        	 rentalDataEntyModel.setTot_floors(rs.getInt("tot_floors"));
	        	 rentalDataEntyModel.setTot_bath_rooms(rs.getInt("tot_bath_rooms"));
	        	 rentalDataEntyModel.setFurniture(rs.getString("furniture"));
	        	 rentalDataEntyModel.setRent_pref(rs.getString("rent_pref"));
	        	 rentalDataEntyModel.setSec_depo(rs.getInt("sec_depo"));
	        	 rentalDataEntyModel.setMon_rent(rs.getInt("mon_rent"));
	        	 rentalDataEntyModel.setKitc_room(rs.getString("kitc_room"));
	        	 rentalDataEntyModel.setFacing(rs.getString("facing"));
	        	 rentalDataEntyModel.setTot_area_sqft(rs.getInt("tot_area_sqft"));
	        	 
	        	 
	        	 rentalDataEntyModel.setPrim_location(rs.getString("prim_location"));
	        	 rentalDataEntyModel.setSeco_location(rs.getString("seco_location"));
	        	 rentalDataEntyModel.setCreate_date(rs.getDate("create_date"));
	        	 rentalDataEntyModel.setIs_active(rs.getInt("is_active"));
	        	 
	        	 			// below for Image
	        	 
	        	 System.out.println(" Villa image : "+rs.getString("own_name")+" --->"+rs.getBytes("image").length);
	        	 
					        	 if(rs.getBytes("image").length!=0)
				                 {
				                 byte[] bb=rs.getBytes("image");
				                 
				                 rentalDataEntyModel.setStreamedContent(DefaultStreamedContent.builder()
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
				                		      rentalDataEntyModel.setStreamedContent(DefaultStreamedContent.builder()
				                             .name("US_Piechart.jpg")
				                             .contentType("image/jpg")
				                             .stream(() -> new ByteArrayInputStream(def)).build());
				                			 }
				                	 
				                  }
	        	  
	                        
					        	 RentalDataEntryModelList.add(rentalDataEntyModel);
             
            
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
		

		
	return RentalDataEntryModelList; 	
	}
   
    
 // ***************** Save package enquiry **************
    public int savePackageEnquiry(String custName, String contactNumber, String email, int packageEnquiry)
    {
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_package_enquiry = new StringBuilder(Constants.SQL.SQL_PACKAGE_ENQUIRY);
            
            pstmt = con.prepareStatement(sql_package_enquiry.toString());
            
            pstmt.setString(1,custName);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, email);
            pstmt.setInt(4, packageEnquiry);
            
         int res=pstmt.executeUpdate();
            if(res > 0)
            {
               return 1;
            }
            else
            {
                return 0;
            }
        
   
        
        } catch (Exception e) {
                
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	}
      
        
        return 1;
    }
    
    
    public String promoImageUpload(PromoImageModel promoImageModel)
    {
    	String succVal="";
        try {
        	
            	 
        	 
            Connection con = null;
            PreparedStatement pstmt = null;
            con=ConnectionDAO.getConnection();
            
            StringBuilder sql_promo_image = new StringBuilder(Constants.SQL.SQL_PROMO_IMAGE);
            pstmt = con.prepareStatement(sql_promo_image.toString());
            
            //pstmt.setInt(1, 102);
            
		            InputStream fin2=promoImageModel.getInputStream();
		            UploadedFile file=promoImageModel.getFile();
            pstmt.setBinaryStream(1, fin2, file.getSize());
            pstmt.setString(2, promoImageModel.getComment());
            pstmt.setString(3, promoImageModel.getImageName());
            //pstmt.setInt(4, promoImageModel.getDisplayOrder());
			pstmt.setString(4,promoImageModel.getPrimLocation());
			pstmt.setString(5,promoImageModel.getDefaultImage());
        

        	int res=pstmt.executeUpdate();
        	System.out.println("*****Successful updated image*******");
            if(res > 0)
            {
            	succVal="Successful updated Image";
            }
            

        
        } catch (Exception e) {
                
        	e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        log.error("An error occurred: {}", e.getMessage());
	        succVal=e.getMessage();
	        return succVal;
	}
      
        
        return succVal;
    }
    
    
    //************** getPromoImageLayout*********************
    public List<PromoImageModel> getPromoImage(int pageSize, int currentPage, String prim_location)
   	{
    	System.out.println("*************************** primelocation*************** :"+prim_location);
    	ConnectionDAO condao;
    	BasicDataSource bds=null;
    	Connection con = null;
    	PreparedStatement pstmt = null;
   	
   		List<PromoImageModel> promoImageModelList = new ArrayList<>();
   		try {
   		
   			
   			
   			StringBuilder sql_promo_image = new StringBuilder(Constants.SQL.SQL_PROMO_IMAGE_LAYOUT);
   			
   			condao=new ConnectionDAO();
   			bds=condao.getDataSource();
   			con=bds.getConnection();
   			
			System.out.println(" Owner Properties Query : "+sql_promo_image .toString());
			log.info("owner properties : "+sql_promo_image .toString());
							pstmt = con.prepareStatement(sql_promo_image.toString());
							pstmt.setString(1, prim_location);
							pstmt.setInt(2, pageSize);
				            pstmt.setInt(3, (currentPage - 1) * pageSize);

						    ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				System.out.println("************************** True ************** primelocation*************** :"+prim_location);
									do {

										System.out.println("************************** Result ************** primelocation*************** :"+rs.getInt("promo_id"));
										PromoImageModel promoImageModel=new PromoImageModel();


										promoImageModel.setPromoId(rs.getInt("promo_id"));
										promoImageModel.setCreateDate(rs.getDate("create_date"));
										promoImageModel.setIs_active(rs.getInt("is_active"));
										promoImageModel.setComment(rs.getString("comment"));
										promoImageModel.setImageName(rs.getString("img_name"));

										InputStream imageStream = rs.getBinaryStream("image");
										if(rs.getBytes("image").length!=0) {
											BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

											promoImageModel.setStreamedContent(DefaultStreamedContent.builder()
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
												promoImageModel.setStreamedContent(DefaultStreamedContent.builder()
														.name("US_Piechart.jpg")
														.contentType("image/jpg")
														.stream(() -> new ByteArrayInputStream(def)).build());
											}

										}


										promoImageModelList.add(promoImageModel);
									}while (rs.next());
			} else {

				System.out.println("************************** False ************** ");
				PreparedStatement  pstmt1 = con.prepareStatement("select * from promo_img where default_dis ='Yes' order by promo_id desc LIMIT ? OFFSET ?");
									pstmt1.setInt(1, pageSize);
									pstmt1.setInt(2, (currentPage - 1) * pageSize);
									ResultSet rs1 = pstmt1.executeQuery();


										while ( rs1.next() ) {
											PromoImageModel promoImageModel = new PromoImageModel();


											promoImageModel.setPromoId(rs1.getInt("promo_id"));
											promoImageModel.setCreateDate(rs1.getDate("create_date"));
											promoImageModel.setIs_active(rs1.getInt("is_active"));
											promoImageModel.setComment(rs1.getString("comment"));
											promoImageModel.setImageName(rs1.getString("img_name"));

											InputStream imageStream = rs1.getBinaryStream("image");
											if (rs1.getBytes("image").length != 0) {
												BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);

												promoImageModel.setStreamedContent(DefaultStreamedContent.builder()
														.name("US_Piechart.jpg")
														.contentType("image/jpg")
														.stream(() -> bufferedStream) // Stream the content directly
														.build());
											}

											promoImageModelList.add(promoImageModel);
										}
				rs.close();
				pstmt1.close();

			}


   	     
   	         rs.close();
   	         log.info("### : *** Connection Closed from getPromoImage()");

   	     } catch (Exception e) {
   	        e.printStackTrace();
   	        System.err.println(e.getClass().getName()+": "+e.getMessage());
   	        log.error("An error occurred getPromoImage() : {}", e.getMessage());
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
   	return promoImageModelList;		
   	}
	
//////layout total count************************************************/////
  public int getLayoutCountTotalRecords(String priLocation, String secLocation)
  {
  	int totalRecords=0;
  	ConnectionDAO condao;
  	BasicDataSource bds=null;
  	Connection con = null;
  	PreparedStatement pstmt = null;

 	StringBuilder sql_layout_details = new StringBuilder(Constants.SQL.SQL_LAYOUT_COUNT);
	
	
	 	try {
			
	 		condao=new ConnectionDAO();
	 		bds=condao.getDataSource();
	 		con=bds.getConnection();
          pstmt = con.prepareStatement(sql_layout_details.toString());
          pstmt.setString(1, priLocation);
          pstmt.setString(2, secLocation);
          ResultSet rs = pstmt.executeQuery();
	                  
	        	if (rs.next()) {
	                totalRecords = rs.getInt(1);
	            }
 			pstmt.close();
	        rs.close();
	        con.close();
	       
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
  	
  	return totalRecords;
  }
  
//promo count 
			public int getPromoCountTotalRecords()
		    {
		    	int totalRecords=0;
		    
		    	ConnectionDAO condao;
		    	BasicDataSource bds=null;
		    	Connection con = null;
		    	PreparedStatement pstmt = null;
	   	
		   	
		   	StringBuilder sql_promo_count = new StringBuilder(Constants.SQL.SQL_PROMO_COUNT);
			

				
			 	try {
			 		condao=new ConnectionDAO();
			 		bds=condao.getDataSource();
			 		con=bds.getConnection();
		            pstmt = con.prepareStatement(sql_promo_count.toString());
		            ResultSet rs = pstmt.executeQuery();
			                  
			        	if (rs.next()) {
			                totalRecords = rs.getInt(1);
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
		    	
		    	return totalRecords;
		    }
			
			// **********************agriculture count **************************************
 			public int getAgriculturalCountTotalRecords(String priLocation, String secLocation)
 		    {
 		    	int totalRecords=0;
 		    
 		    	ConnectionDAO condao;
 		    	BasicDataSource bds=null;
 		    	Connection con = null;
 		    	PreparedStatement pstmt = null;
 	   	
 		   	
 		   	StringBuilder sql_agri_details = new StringBuilder(Constants.SQL.SQL_AGRI_COUNT);
 			
 				
 			 	try {
 			 		condao=new ConnectionDAO();
 			 		bds=condao.getDataSource();
 			 		con=bds.getConnection();
 		            pstmt = con.prepareStatement(sql_agri_details.toString());
 		            pstmt.setString(1, priLocation);
 	                pstmt.setString(2, secLocation);
 	                ResultSet rs = pstmt.executeQuery();
 			                  
 			        	if (rs.next()) {
 			                totalRecords = rs.getInt(1);
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
 		    	
 		    	return totalRecords;
 		    }
 			
 			
 			
 			
 		// ********************* Promotion image ************	
 	 		public List<PromoImageModel> getPromoImageVilla(int pageSize, int currentPage)
 	 	   	{
 	 	   	
 	 			ConnectionDAO condao;
 	 			BasicDataSource bds=null;
 	 			Connection con = null;
 	 			PreparedStatement pstmt = null;
 	 			
 	 	   		List<PromoImageModel> promoImageModelList = new ArrayList<>();
 	 	   		try {
 	 	   		condao=new ConnectionDAO();
 	 	   		bds=condao.getDataSource();
 	 	   		con=bds.getConnection();
 	 	   			
 	 	   			
 	 	   			StringBuilder sql_promo_image = new StringBuilder(Constants.SQL.SQL_PROMO_IMAGE_VILLA);
 	 	   		
 	 				System.out.println(" Owner Properties Query : "+sql_promo_image .toString());
 	 				
 	 				log.info("owner properties : "+sql_promo_image .toString());
 	 				
 	 								pstmt = con.prepareStatement(sql_promo_image.toString());
 	 								pstmt.setInt(1, pageSize);
 	 					            pstmt.setInt(2, (currentPage - 1) * pageSize);
 	 							    ResultSet rs = pstmt.executeQuery();
 	 	   	         while ( rs.next() ) {
 	 	   	        	PromoImageModel promoImageModel=new PromoImageModel();
 	 	   	        	 
 	 	   	        	 
 	 	   	        	promoImageModel.setPromoId(rs.getInt("promo_id"));
 	 	   	        	promoImageModel.setCreateDate(rs.getDate("create_date"));
 	 	   	        	promoImageModel.setIs_active(rs.getInt("is_active"));
 	 	   	        	promoImageModel.setComment(rs.getString("comment"));
 	 	   	        	promoImageModel.setImageName(rs.getString("img_name"));
 	 	   	        	
 	 	     	        	 		InputStream imageStream = rs.getBinaryStream("image");
 	 	   				        	 if(rs.getBytes("image").length!=0) {
 	 	   				        	     BufferedInputStream bufferedStream = new BufferedInputStream(imageStream);
 	 	   				        	     
 	 	   				        	promoImageModel.setStreamedContent(DefaultStreamedContent.builder()
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
 	 	   			                		 promoImageModel.setStreamedContent(DefaultStreamedContent.builder()
 	 	   			                             .name("US_Piechart.jpg")
 	 	   			                             .contentType("image/jpg")
 	 	   			                             .stream(() -> new ByteArrayInputStream(def)).build());
 	 	   			                			 }
 	 	   			                	 
 	 	   			                  }
 	 	   	        	  
 	 	   	                     
 	 	   					        	promoImageModelList.add(promoImageModel);
 	 	   	         }
 	 	   	         	
 	 	   	        
 	 	   	         rs.close();
 	 	   	         log.info("### : *** Connection Closed from getPromoImage()");
 	 	   	     } catch (Exception e) {
 	 	   	        e.printStackTrace();
 	 	   	        System.err.println(e.getClass().getName()+": "+e.getMessage());
 	 	   	        log.error("An error occurred getPromoImage() : {}", e.getMessage());
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
 	 	   		
 	 	   	return promoImageModelList;		
 	 	   	}
 	    
 			
 			 
    

}
