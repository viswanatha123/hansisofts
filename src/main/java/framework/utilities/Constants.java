package framework.utilities;

public class  Constants {
	
	
	public interface SMTPServer
	{
		// Godday SMTP Server
		
		public static final String SMTPServer="smtpout.secureserver.net";
		public static final String PORT="465";
		public static final String AUTH="true";
    	public static final String USER_NAME="hansisoft.hr@hansisofts.com";
    	public static final String PASSWORD="HansiSofts@123";
    	public static final String FROMEMAIL="hansisoft.hr@hansisofts.com";
    	public static final String SUBJECT="HansiSoft Solutions,,. New Account created successfull..";
    	/*
    	public static final String[] ADMIN_GROUP_EMAIL ={"viswanathareddy120@gmail.com"
    			,"sukumar.a@hansisofts.com"
    			,"swathi.sreeram@hansisofts.com"
    			,"nagalakshmi.m@hansisofts.com"
    			,"sivakumar.m@hansisofts.com"};
    	*/
    	public static final String[] ADMIN_GROUP_EMAIL ={"viswanathareddy120@gmail.com"
    			};
    	
		
	}
	
	public interface PropertyConstants
	{
		public static final String LAYOUT_SUBJECT="HansiSoft Solutions,,. New Layout listed..";
		public static final String AGRI_SUBJECT="HansiSoft Solutions,,. New Agiculture listed..";
		public static final String INDI_SUBJECT="HansiSoft Solutions,,. New Individual listed..";
		public static final String VILLA_SUBJECT="HansiSoft Solutions,,. New Villa listed..";
		public static final String LEAD_SUBJECT="HansiSoft Solutions,,. Lead Generated..";
	}
	
	public interface SMSServer
	{
		/* Twilio Service */
		
		public static final String[] ADMIN_GROUP_CONTACT ={
				"+919632152255"
    		   ,"+917353747006" // raji
    		   ,"+918328518573" //sukumar
    		   ,"+918096444763" //swathi
    		   ,"+917093637102" //kumari
    		   ,"+919390796667" // rajashakar
    		   ,"+919502653507" // jagadesh
    			
    	};
		
		public static final String ACCOUNT_SID = "AC3116f795624b49e44baf5cfc888fe220";
	    public static final String AUTH_TOKEN = "d0153bb27044871fd3126aecc7b21be7";
	    public static final String TWILIO_NUMBER = "+14253097706";
	    
	    
	  // Belwo original api url 
	  // Text2SMS Service
	  // String urlString = "https://www.fast2sms.com/dev/bulkV2?authorization=FtyePe5DffirXSPYmYRGldgAQb7x7CuyoO1o2fiqJdlPjXBFyy0JEu8HM5Nd&route=q&message=khkhkhkjhkj&flash=0&numbers=9632152255";
		
	    /*
	    public static String baseUrl="https://www.fast2sms.com/dev/bulkV2?authorization=";
	    public static final String apiKey = "FtyePe5DffirXSPYmYRGldgAQb7x7CuyoO1o2fiqJdlPjXBFyy0JEu8HM5Nd";
	    public static final String route = "q";
	    public static final String flash = "0";
	    */
	    public static String baseUrl="https://www.fast2sms.com/dev/bulkV2?authorization=";
	    public static String apiKey = "FtyePe5DffirXSPYmYRGldgAQb7x7CuyoO1o2fiqJdlPjXBFyy0JEu8HM5Nd";
	    public static String route = "q";
	    public static String flash = "1";
	    
	    
	 	
	
	}
	
	
	

}
