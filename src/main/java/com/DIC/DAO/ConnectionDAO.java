package com.DIC.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ConnectionDAO {
	
	  //Hani Database
		private final static String url="jdbc:postgresql://localhost:5432/postgres";
		private final static String username="postgres";
		private final static String password="admin";
		
		
		//New prod
	     private final static String urlnew="jdbc:postgresql://lnx1540.ch3.prod.i.com:5432/db_internalapp_intgtncloud_prod_01";
	     private final static String usernamenew="vis_rep_connectors_p1";
	     private final static String passwordnew="Passw0rd";
		
		public ConnectionDAO() {
	    }
		
		public static Connection getConnection()
		{
			//log.info(" Started connection..");
			Connection con = null;
						
			try {
		        Class.forName("org.postgresql.Driver");
		        con = DriverManager.getConnection(url,username,password);
		   	//log.info("Connected database Successful..");
		        
		        
		        //log.info("### : Connected database Successful..");	
		        
		     } catch (Exception e) {
		    	 
		    	 //log.info("-###: Connection failed ...."+e);
		       e.printStackTrace();
		       //log.info("#### Database Connection : "+e.getClass().getName()+": "+e.getMessage());
		        
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

}
