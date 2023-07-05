package com.DIC.DAO.Impl;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.SerializableSupplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.NumberFormat;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.Service.AgriculturalService;
import com.DIC.model.AgriculturalDataEntryModel;
import com.DIC.model.AgriculturalModel;
import com.DIC.model.ConnectorMode;
import com.DIC.model.EnquiryDataEntryModel;
import com.DIC.model.ExceptionValueModel;
import com.DIC.model.ImageUploadModel;
import com.DIC.model.IndiSiteDataEntryModel;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.LogViewerDatewiseModel;
import com.DIC.model.PlotsDataEntryModel;


import java.sql.PreparedStatement;
import static java.lang.Math.log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.util.SerializableSupplier;

@ManagedBean
@ApplicationScoped
public class ConnectionDAOImpl {
	
	private static final Logger log = Logger.getLogger(ConnectionDAOImpl.class.getName());
	
	interface Constants {
		// SQL
		interface SQL {
			
			String SQL_LAYOUT="select * from hansi_layout where prim_location = ? and seco_location = ? order by create_date desc";
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
			String SQL_AGRICULTURAL="select * from hansi_agricultural where prim_location = ? and seco_location = ? order by create_date desc";
			
			String SQL_LAYOUT_INSERT="insert into hansi_layout (layout_id,name,location,persqft,contact_owner,owner_name,wonership,is_active,transaction,comment,length,width,prim_location,seco_location,create_date,swimingpool,playground,park,wall,community,facing,agent_name,image,cost) \n" +
					"values (nextval('hansi_layout_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?,?,?);";
			
			String SQL_IndividualSite="select * from hansi_individual_site where prim_location = ? and seco_location = ? order by create_date desc";
			
			String SQL_INDI_INSERT="INSERT INTO hansi_individual_site (ind_id,owner_name, location, contact_no, site_no, persqft, length, width, wonership, transaction, prim_location, seco_location, create_date, is_active,comment,facing,agent_name,cost,image) VALUES(nextval('hansi_individual_site_seq'),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,current_timestamp, 1, ?,?,?,?,?)";
			
			String SQL_AGRIDATAENTRY_INSERT = "INSERT INTO hansi_agricultural (agri_id,owner_name, contact_no, survey_no, location, wonership, transaction, per_cent, number_cents, water_source, crop, prim_location, seco_location, create_date, is_active, comment,agent_name,cost,image) VALUES(nextval('hansi_agricultural_seq'),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp, 1, ?,?,?,?)";
			String SQL_ENQU_INSERT="INSERT INTO hansi_enquiry (enqi_id, name, email, phone, create_date, is_active) VALUES(nextval('hansi_enquiry_seq'),?, ?, ?, current_timestamp, 1)";
			String SQL_IMAGE_UPLOAD="insert into hansi_property_image (prop_img_id,img_name,image) values (nextval('hansi_imageUpload_seq'),?,?)";
			
			String SQL_IMAGE_DEFAULT="select image from hansi_property_image where prop_img_id=6";
			
  			}
                
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
	        System.exit(0);
	     }
	return count;		
	}
	
	
 // ************************** getActiveModelList()*******************//
    
    public List<ConnectorMode> getActiveModelList()
	{
    	
    	System.out.println("############## Started working getActiveModelList() #####################");
		//log.info("### : get started :: getActiveModelList() ");
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
	        
	        	 //System.out.println("################################### : "+rs.getString("server_host"));
	            
	         }
	         	
	         
	         rs.close();
	         stmt.close();
	         con.close();
	         //log.info("### : *** Connection Closed from getActiveModelList()");
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
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
	        //System.exit(0);
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
	        //System.exit(0);
	     }
      return exceptionValueModelList;
    }
    
    
    
    public List<LayoutMode> getLayoutDetails(String priLocation, String secLocation)
	{
            
       System.out.println("********* Selected Location : "+priLocation+"   "+secLocation);
	
		List<LayoutMode> layoutModeList = new ArrayList<>();
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
		
			
			con=ConnectionDAO.getConnection();
			StringBuilder sq_layout = new StringBuilder(Constants.SQL.SQL_LAYOUT);
			
							pstmt = con.prepareStatement(sq_layout.toString());
                            pstmt.setString(1, priLocation);
                            pstmt.setString(2, secLocation);
                            ResultSet rs = pstmt.executeQuery();
	         while ( rs.next() ) {
	        	 LayoutMode layoutMode=new LayoutMode();
	        	 
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
                         
                         
                         if(rs.getBytes("image")!=null)
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
                        	 System.out.println("********** null *********");
                        	 StringBuilder sq_image_default = new StringBuilder(Constants.SQL.SQL_IMAGE_DEFAULT);
                        	 
                 			PreparedStatement pstmtDefault1 = null;
                        	pstmtDefault1 = con.prepareStatement(sq_image_default.toString());
                        	 ResultSet rsDef = pstmtDefault1.executeQuery();
                        	
                        		 byte[] def=rsDef.getBytes("image");
                        		 layoutMode.setStreamedContent(DefaultStreamedContent.builder()
                                     .name("US_Piechart.jpg")
                                     .contentType("image/jpg")
                                     .stream(() -> new ByteArrayInputStream(def)).build());
                        		
                        	 
                         }
                         
                  
                       
                        
                         /*
                        File file=new File("D:\\image1.jpg");
                        FileOutputStream fos=new FileOutputStream(file);
                        fos.write(bb);
                       */
                         
                         
                  
                         
                         /*
                         StreamedContent streamedContent = DefaultStreamedContent.builder()
                                 .name("US_Piechart.jpg")
                                 .contentType("image/jpg")
                                 .stream(() -> new ByteArrayInputStream(bb)).build();
                         
                         layoutMode.setStreamedContent(streamedContent);
                         */
                       
                         
                        
                    
                           
                         
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
    
    
    
    
  //************************************AgriculturalDetails******************************************//

    public List<AgriculturalModel> getAgriculturalDetails(String priLocation, String secLocation)
    	{
                
            
    		log.info("### : get started :: getAgriculturalDetails() ");
    		List<AgriculturalModel> agriculturalModelList = new ArrayList<>();
    		try {
    			Connection con = null;
    			PreparedStatement pstmt = null;
    			
    			StringBuilder sql_agricultural = new StringBuilder(Constants.SQL.SQL_AGRICULTURAL);
    			log.info("###: Query : "+sql_agricultural.toString());
    			
    			con=ConnectionDAO.getConnection();
    	                    pstmt = con.prepareStatement(sql_agricultural.toString());
                                pstmt.setString(1, priLocation);
                                pstmt.setString(2, secLocation);
                                ResultSet rs = pstmt.executeQuery();
    	         while ( rs.next() ) {
    	        	 AgriculturalModel agriculturalModel=new AgriculturalModel();
    	        	 
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
                             
                             
                             
                             /*
                             byte[] bb=rs.getBytes("image");
                             agriculturalModel.setStreamedContent(DefaultStreamedContent.builder()
                                     .name("US_Piechart.jpg")
                                     .contentType("image/jpg")
                                     .stream(() -> new ByteArrayInputStream(bb)).build());
                             
                             */
                          
                             
                             
                             if(rs.getBytes("image")!=null)
                             {
                             byte[] bb=rs.getBytes("image");
                             
                             agriculturalModel.setStreamedContent(DefaultStreamedContent.builder()
                                     .name("US_Piechart.jpg")
                                     .contentType("image/jpg")
                                     .stream(() -> new ByteArrayInputStream(bb)).build());
                             }
                             else
                             {
                            	// Defalut Image
                            	 PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =6");
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
    	         	
    	         pstmt.close();
    	         rs.close();
    	         con.close();
    	         //log.info("### : *** Connection Closed from getActiveModelList()");
    	     } catch (Exception e) {
    	        e.printStackTrace();
    	        System.err.println(e.getClass().getName()+": "+e.getMessage());
    	        System.exit(0);
    	     }
    	return agriculturalModelList;		
    	}

    
    
    // ***************** update plot data entry **************
    public String updatePlotDataEntry(PlotsDataEntryModel plotsDataEntryModel)
    {
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
    
    
    
    
    //************************************IndividualSiteDetails******************************************//

    public List<IndividualSiteModel> getIndividualSiteDetails(String priLocation, String secLocation)
    	{
                
            
    		log.info("### : get started :: getIndividualSiteDetails() ");
    		List<IndividualSiteModel> individualSiteModelList = new ArrayList<>();
    		try {
    			Connection con = null;
    			PreparedStatement pstmt = null;
    			
    			StringBuilder sql_IndividualSite = new StringBuilder(Constants.SQL.SQL_IndividualSite);
    			log.info("###: Query : "+sql_IndividualSite.toString());
    			
    			con=ConnectionDAO.getConnection();
    	                    pstmt = con.prepareStatement(sql_IndividualSite.toString());
                                pstmt.setString(1, priLocation);
                                pstmt.setString(2, secLocation);
                                ResultSet rs = pstmt.executeQuery();
    	         while ( rs.next() ) {
    	        	 IndividualSiteModel individualSiteModel=new IndividualSiteModel();
    	        
    	        		
    	        		
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
        	        	  
        	        	 
        	        	 /*
        	        	 byte[] bb=rs.getBytes("image");


        	        	  individualSiteModel.setStreamedContent(DefaultStreamedContent.builder()
        	        	                                  .name("US_Piechart.jpg")
        	        	                                  .contentType("image/jpg")
        	        	                                  .stream(() -> new ByteArrayInputStream(bb)).build());
        	        	  
        	        	  */
        	        	  if(rs.getBytes("image")!=null)
                          {
                          byte[] bb=rs.getBytes("image");
                          
                          individualSiteModel.setStreamedContent(DefaultStreamedContent.builder()
                                  .name("US_Piechart.jpg")
                                  .contentType("image/jpg")
                                  .stream(() -> new ByteArrayInputStream(bb)).build());
                          }
                          else
                          {
                         	// Defalut Image
                         	 PreparedStatement pstmtDefault = con.prepareStatement("select image from hansi_property_image where prop_img_id =100");
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
    
    
    
    // ***************** update Indi data entry **************
    public String updateIndiDataEntry(IndiSiteDataEntryModel indiSiteDataEntryModel)
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
    
    
    
    
    
    // ***************** update Agricultural data entry **************
    public String updateAgriDataEntry(AgriculturalDataEntryModel agriculturalDataEntryModel)
    {
    	
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
	      //System.exit(0);
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
	    
	        succVal=e.getMessage();
	        return succVal;
	}
      
        
        return succVal;
    }
    
    
    
    
    

}
