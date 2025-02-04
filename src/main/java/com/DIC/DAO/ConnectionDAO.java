package com.DIC.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.dbcp.BasicDataSource;

@ManagedBean
@SessionScoped
public class ConnectionDAO {
	//Connection
	
	private static BasicDataSource dataSource;
	
	  //Hani Prod Database
		//private final static String url="jdbc:postgresql://localhost:5432/hansi-prod";
		//private final static String username="postgres";
		//private final static String password="admin";
			
	  //Hani QA Database
		private final static String url="jdbc:postgresql://localhost:5432/hansi-qa";
		private final static String username="postgres";
		private final static String password="admin";
		
		
		//New prod
		
	     private final static String urlnew="jdbc:postgresql://lnx1540.ch3.prod.i.com:5432/db_internalapp_intgtncloud_prod_01";
	     private final static String usernamenew="vis_rep_connectors_p1";
	     private final static String passwordnew="Passw0rd";
	     
	     
	     //*************** test ************//
	     
		
	     public void ConnectionDAO() {
	    	    
	     }
	     
	     
	     

	     public BasicDataSource getDataSource() {
	    	 
	    	    dataSource = new BasicDataSource();
		        dataSource.setDriverClassName("org.postgresql.Driver");
		        dataSource.setUrl(url);
		        dataSource.setUsername("postgres");
		        dataSource.setPassword("admin");
		        dataSource.setInitialSize(5);
		        //dataSource.setMaxTotal(20);
		        dataSource.setMaxActive(30);
		        dataSource.setMaxIdle(10);
		        dataSource.setMinIdle(5);
	    	 
	         return dataSource;
	     }
		
		public static Connection getConnection()
		{
			Connection con = null;
						
			try {
		        Class.forName("org.postgresql.Driver");
		        con = DriverManager.getConnection(url,username,password);
		     } catch (Exception e) {
		       e.printStackTrace();
		     }
			return con;
		}
                
                
                // get New database connection
		public static Connection getConnectionNew()
		{
			//log.info(" Started connection..");
			Connection con = null;
						
			try {
		        Class.forName("org.postgresql.Driver");
		        con = DriverManager.getConnection(urlnew,usernamenew,passwordnew);
		   	//log.info("Connected database Successful..");
		        
		        
		        //log.info("### : Connected database Successful..");	
		        
		     } catch (Exception e) {
		    	 
		    	 //log.info("-###: Connection failed ...."+e);
		       e.printStackTrace();
		       //log.info("#### Database Connection : "+e.getClass().getName()+": "+e.getMessage());
		        
		     }
			return con;
		}
		
		
		@PreDestroy
	     public void close() {
	         try {
	        	 System.out.println("******************** DataSource closed **********************");
	             dataSource.close();
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	     }
		
	
}
