package com.DIC.DAO.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.DIC.DAO.ConnectionDAO;
import com.DIC.DAO.Impl.ConnectionDAOImpl.Constants;

@ManagedBean
@ApplicationScoped
public class LocationDAOImpl {
	
	private static final Logger log = Logger.getLogger(LocationDAOImpl.class.getName());
	
	
	interface Constants {
		// SQL
		interface SQL {
			
			String SQL_LAYOUT_PRIMERY_LOCATION="select distinct prim_location from hansi_layout order by prim_location";
			String SQL_LAYOUT_SECONDRY_LOCATION="select distinct seco_location from hansi_layout where prim_location =? order by seco_location desc";
			
			String SQL_AGRI_PRIMERY_LOCATION="select distinct prim_location from hansi_agricultural order by prim_location";
			String SQL_AGRI_SECONDRY_LOCATION="select distinct seco_location from hansi_agricultural where prim_location =? order by seco_location desc";
			
			String SQL_INDIV_PRIMERY_LOCATION="select distinct prim_location from hansi_individual_site order by prim_location";
			String SQL_INDIV_SECONDRY_LOCATION="select distinct seco_location from hansi_individual_site where prim_location =? order by seco_location desc";
			
			String SQL_VILLA_PRIMERY_LOCATION="select distinct prim_location from villa_plot  order by prim_location";
			String SQL_VILLA_SECONDRY_LOCATION="select distinct seco_location from villa_plot where prim_location = ? order by seco_location desc";
			
			String SQL_RENTAL_PRIMERY_LOCATION="select distinct prim_location from rental_plot  order by prim_location";
			String SQL_RENTAL_SECONDRY_LOCATION="select distinct seco_location from rental_plot where prim_location = ? order by seco_location desc";
		}
	}
	
	
	public Map<String, String> getLayoutPrimaryLocation()
	{	
		
		
		Map<String, String> primaryModelList = new java.util.HashMap();
			try {
			Connection con = null;
			Statement stmt= null;
			
			StringBuilder sql_layout_primary_data = new StringBuilder(Constants.SQL.SQL_LAYOUT_PRIMERY_LOCATION);
			con=ConnectionDAO.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql_layout_primary_data.toString());
			log.info("****************** log x***************** :"+rs.getFetchSize());
					while(rs.next())
					{ 
			  		 primaryModelList.put(rs.getString("prim_location"), rs.getString("prim_location"));
	
					}
	         rs.close();
	         stmt.close();
	         con.close();
			}catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
	     }
	return primaryModelList;		
	}
	
	
			
			
		//****************************************** Secondry Locaton ****************************************
			
		public List<String> getLayoutSecondryLocation(String primCode)
			{		
				
				
				log.log(Level.INFO, "Primary value  in db--------->:"+primCode);
				List<String> secondryModelList = new ArrayList<>();
					try {
					Connection con = null;
					PreparedStatement pstmt = null;
					StringBuilder sql_layout_secondry_data = new StringBuilder(Constants.SQL.SQL_LAYOUT_SECONDRY_LOCATION);
					log.info("###: Secondry location query : "+sql_layout_secondry_data.toString());
					
					
					con=ConnectionDAO.getConnection();
					pstmt = con.prepareStatement(sql_layout_secondry_data.toString());
	                pstmt.setString(1, primCode);
	                ResultSet rs = pstmt.executeQuery();
	                      	while ( rs.next() ) {
	    	            		secondryModelList.add(rs.getString("seco_location"));
	    	            		
	    	            		log.log(Level.INFO, "debug x----->"+rs.getString("seco_location"));
				          	 }	
	                
	                 rs.close();
			         con.close();
			         pstmt.close();
			         log.info("### : *** Connection Closed from getSecondryLocation");
			     } catch (Exception e) {
			        e.printStackTrace();
			        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
			        //System.exit(0);
			     }
			return secondryModelList;		
			}
		
		//*********************************************************************************************************************************
										// Agricultural
		//*********************************************************************************************************************************
		
		public Map<String, String> getAgriPrimaryLocation()
		{	
			
			
			Map<String, String> primaryModelList = new java.util.HashMap();
				try {
				Connection con = null;
				Statement stmt= null;
				
				StringBuilder sql_agri_primary_data = new StringBuilder(Constants.SQL.SQL_AGRI_PRIMERY_LOCATION);
				con=ConnectionDAO.getConnection();
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql_agri_primary_data.toString());
				log.info("****************** log x***************** :"+rs.getFetchSize());
						while(rs.next())
						{ 
				  		 primaryModelList.put(rs.getString("prim_location"), rs.getString("prim_location"));
		
						}
		         rs.close();
		         stmt.close();
		         con.close();
				}catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
		     }
		return primaryModelList;		
		}
		
		
				
				
			//****************************************** Secondry Locaton ****************************************
				
			public List<String> getAgriSecondryLocation(String primCode)
				{		
					
					
					log.log(Level.INFO, "Primary value  in db--------->:"+primCode);
					List<String> secondryModelList = new ArrayList<>();
						try {
						Connection con = null;
						PreparedStatement pstmt = null;
						StringBuilder sql_agri_secondry_data = new StringBuilder(Constants.SQL.SQL_AGRI_SECONDRY_LOCATION);
						log.info("###: Secondry location query : "+sql_agri_secondry_data.toString());
						
						
						con=ConnectionDAO.getConnection();
						pstmt = con.prepareStatement(sql_agri_secondry_data.toString());
		                pstmt.setString(1, primCode);
		                ResultSet rs = pstmt.executeQuery();
		                      	while ( rs.next() ) {
		    	            		secondryModelList.add(rs.getString("seco_location"));
		    	            		
		    	            		log.log(Level.INFO, "debug x----->"+rs.getString("seco_location"));
					          	 }	
		                
		                 rs.close();
				         con.close();
				         pstmt.close();
				         log.info("### : *** Connection Closed from getSecondryLocation");
				     } catch (Exception e) {
				        e.printStackTrace();
				        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
				        //System.exit(0);
				     }
				return secondryModelList;		
				}
			
			
				//*********************************************************************************************************************************
													// Individual
				//*********************************************************************************************************************************
				
				public Map<String, String> getIndivPrimaryLocation()
				{	
				
				
				Map<String, String> primaryModelList = new java.util.HashMap();
				try {
				Connection con = null;
				Statement stmt= null;
				
				StringBuilder sql_indiv_primary_data = new StringBuilder(Constants.SQL.SQL_INDIV_PRIMERY_LOCATION);
				con=ConnectionDAO.getConnection();
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql_indiv_primary_data.toString());
				log.info("****************** log x***************** :"+rs.getFetchSize());
				while(rs.next())
				{ 
				primaryModelList.put(rs.getString("prim_location"), rs.getString("prim_location"));
				
				}
				rs.close();
				stmt.close();
				con.close();
				}catch (Exception e) {
				e.printStackTrace();
				System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
				}
				return primaryModelList;		
				}
				
				
				
				
				//****************************************** Secondry Locaton ****************************************
				
				public List<String> getIndivSecondryLocation(String primCode)
				{		
				
				
				log.log(Level.INFO, "Primary value  in db--------->:"+primCode);
				List<String> secondryModelList = new ArrayList<>();
				try {
				Connection con = null;
				PreparedStatement pstmt = null;
				StringBuilder sql_indiv_secondry_data = new StringBuilder(Constants.SQL.SQL_INDIV_SECONDRY_LOCATION);
				log.info("###: Secondry location query : "+sql_indiv_secondry_data.toString());
				
				
				con=ConnectionDAO.getConnection();
				pstmt = con.prepareStatement(sql_indiv_secondry_data.toString());
				pstmt.setString(1, primCode);
				ResultSet rs = pstmt.executeQuery();
				  	while ( rs.next() ) {
						secondryModelList.add(rs.getString("seco_location"));
						
						log.log(Level.INFO, "debug x----->"+rs.getString("seco_location"));
				  	 }	
				
				rs.close();
				con.close();
				pstmt.close();
				log.info("### : *** Connection Closed from getSecondryLocation");
				} catch (Exception e) {
				e.printStackTrace();
				System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
				//System.exit(0);
				}
				return secondryModelList;		
				}
				
				//*********************************************************************************************************************************
				// Villa/Plot
				//*********************************************************************************************************************************
			
			public Map<String, String> getVillaPrimaryLocation()
			{	
				
				
				Map<String, String> primaryModelList = new java.util.HashMap();
					try {
					Connection con = null;
					Statement stmt= null;
					
					StringBuilder sql_villa_primary_data = new StringBuilder(Constants.SQL.SQL_VILLA_PRIMERY_LOCATION);
					con=ConnectionDAO.getConnection();
					stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sql_villa_primary_data.toString());
					log.info("****************** log x***************** :"+rs.getFetchSize());
							while(rs.next())
							{ 
					  		 primaryModelList.put(rs.getString("prim_location"), rs.getString("prim_location"));
			
							}
			         rs.close();
			         stmt.close();
			         con.close();
					}catch (Exception e) {
			        e.printStackTrace();
			        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
			     }
			return primaryModelList;		
			}
			
			//****************************************** Secondry Locaton ****************************************
			
			public List<String> getVillaSecondryLocation(String primCode)
				{		
					
					
					log.log(Level.INFO, "Primary value  in db--------->:"+primCode);
					List<String> secondryModelList = new ArrayList<>();
						try {
						Connection con = null;
						PreparedStatement pstmt = null;
						StringBuilder sql_villa_secondry_data = new StringBuilder(Constants.SQL.SQL_VILLA_SECONDRY_LOCATION);
						log.info("###: Secondry location query : "+sql_villa_secondry_data.toString());
						
						
						con=ConnectionDAO.getConnection();
						pstmt = con.prepareStatement(sql_villa_secondry_data.toString());
		                pstmt.setString(1, primCode);
		                ResultSet rs = pstmt.executeQuery();
		                      	while ( rs.next() ) {
		    	            		secondryModelList.add(rs.getString("seco_location"));
		    	            		
		    	            		log.log(Level.INFO, "debug x----->"+rs.getString("seco_location"));
					          	 }	
		                
		                 rs.close();
				         con.close();
				         pstmt.close();
				         log.info("### : *** Connection Closed from getSecondryLocation");
				     } catch (Exception e) {
				        e.printStackTrace();
				        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
				        //System.exit(0);
				     }
				return secondryModelList;		
				}
			
			//*********************************************************************************************************************************
			// Rental
			//*********************************************************************************************************************************
		
		public Map<String, String> getRentalPrimaryLocation()
		{	
			
			
			Map<String, String> primaryModelList = new java.util.HashMap();
				try {
				Connection con = null;
				Statement stmt= null;
				
				StringBuilder sql_rental_primary_data = new StringBuilder(Constants.SQL.SQL_RENTAL_PRIMERY_LOCATION);
				con=ConnectionDAO.getConnection();
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql_rental_primary_data.toString());
				log.info("****************** log x***************** :"+rs.getFetchSize());
						while(rs.next())
						{ 
				  		 primaryModelList.put(rs.getString("prim_location"), rs.getString("prim_location"));
		
						}
		         rs.close();
		         stmt.close();
		         con.close();
				}catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
		     }
		return primaryModelList;		
		}

		//****************************************** Secondry Locaton ****************************************
		
		public List<String> getRentalSecondryLocation(String primCode)
			{		
				
				
				log.log(Level.INFO, "Primary value  in db--------->:"+primCode);
				List<String> secondryModelList = new ArrayList<>();
					try {
					Connection con = null;
					PreparedStatement pstmt = null;
					StringBuilder sql_rental_secondry_data = new StringBuilder(Constants.SQL.SQL_RENTAL_SECONDRY_LOCATION);
					log.info("###: Secondry location query : "+sql_rental_secondry_data.toString());
					
					
					con=ConnectionDAO.getConnection();
					pstmt = con.prepareStatement(sql_rental_secondry_data.toString());
	                pstmt.setString(1, primCode);
	                ResultSet rs = pstmt.executeQuery();
	                      	while ( rs.next() ) {
	    	            		secondryModelList.add(rs.getString("seco_location"));
	    	            		
	    	            		log.log(Level.INFO, "debug x----->"+rs.getString("seco_location"));
				          	 }	
	                
	                 rs.close();
			         con.close();
			         pstmt.close();
			         log.info("### : *** Connection Closed from getSecondryLocation");
			     } catch (Exception e) {
			        e.printStackTrace();
			        System.err.println("@@@@@@@@@@@@Primary data @@@@@@@@@@@@@@@@@@@@@@@@@@ :"+e.getClass().getName()+": "+e.getMessage());
			        //System.exit(0);
			     }
			return secondryModelList;		
			}
			

}
