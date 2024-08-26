package SMTPService;

import javax.mail.*;
import javax.mail.internet.*;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AgriculturalDataEntryModel;
import com.DIC.model.AgriculturalModel;
import com.DIC.model.IndiSiteDataEntryModel;
import com.DIC.model.IndividualSiteModel;
import com.DIC.model.LayoutMode;
import com.DIC.model.PlotsDataEntryModel;
import com.DIC.model.UserDetails;
import com.DIC.model.VillaModel;

import framework.utilities.Constants;

import java.time.LocalDate;
import java.util.Properties;


public class SMTPService {
	
	

	private static Session session;
	private static Message message;
	private static InternetAddress[] recipientAddresses = new InternetAddress[Constants.SMTPServer.ADMIN_GROUP_EMAIL.length+1];
	
	
	public static UserDAOImpl uDao;
	
	static
	{
		uDao=new UserDAOImpl();
		
				Properties props = new Properties();
		        props.put("mail.smtp.host", Constants.SMTPServer.SMTPServer); // SMTP server address
		        props.put("mail.smtp.port", Constants.SMTPServer.PORT); // SSL port
		        props.put("mail.smtp.auth", Constants.SMTPServer.AUTH);
		        props.put("mail.smtp.socketFactory.port", Constants.SMTPServer.PORT);
		        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		        
		       // Set up the session
		       session = Session.getInstance(props, new javax.mail.Authenticator() {
			        protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(Constants.SMTPServer.USER_NAME , Constants.SMTPServer.PASSWORD);
			        }
		        });
		    
			    try {
			      message  = new MimeMessage(session);
	              message.setFrom(new InternetAddress(Constants.SMTPServer.FROMEMAIL));
	              //InternetAddress[] recipientAddresses = new InternetAddress[Constants.SMTPServer.ADMIN_GROUP_EMAIL.length+1];
  	              for (int i = 0; i < Constants.SMTPServer.ADMIN_GROUP_EMAIL.length; i++) {
  	                recipientAddresses[i] = new InternetAddress(Constants.SMTPServer.ADMIN_GROUP_EMAIL[i]);
  	              }
			    } catch (MessagingException e) {
		             throw new RuntimeException(e);
		        }
    
	} 
	
	

    
	public static void sendRegiEmail(String toEmail, String subject, String body)
	{
	    	
			    	 try {
			    		 
		  	             recipientAddresses[recipientAddresses.length-1]=new InternetAddress(toEmail);
		  	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
		  	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			             message.setSubject(subject);
			             message.setText(body);
			             Transport.send(message);
		
			             System.out.println("Email sent successfully!");
			             
		
			         } catch (MessagingException e) {
			             throw new RuntimeException(e);
			         }
	
	}
	
	
	public static void sendLayoutEmail(PlotsDataEntryModel plotsDataEntryModel,int userId)
	{
	    	
			    	 try {
			    		 
			    		 UserDetails userDetails=uDao.getUser(userId);
			    		 System.out.println("User email :"+userDetails.getEmail());
			    		 
			    		 
			    		 String agentDetails="Agent : "+userDetails.getfName()+" "+userDetails.getlName()+",\n\n Congratulations...\n Your Property has been listed successfully.\n\n"
									+ "Agent Details \n\n First Name : "+userDetails.getfName()+";\n Last Name : "+userDetails.getlName()+";\n Contact Number : "+userDetails.getPhone()+" ;\n Email : "+userDetails.getEmail()+"; \n Address : "+userDetails.getAddress()+";\n Date :  "+LocalDate.now().toString()+".";
							
			    		
			    		 int cost=(plotsDataEntryModel.getLength()*plotsDataEntryModel.getWidth())*plotsDataEntryModel.getPersqft();
			    		 String proDetals="\n\n\nLayout Details.\n\n Layout Name : "+plotsDataEntryModel.getName()+";"
			    		 		+ "\n Cost : "+cost+";\n Primary location : "+plotsDataEntryModel.getPrimLocation()+"; "
			    		 		+ "\n Secondry Location : "+plotsDataEntryModel.getSecoLocation()+"\n\n\n";
			    		 
			    		 String thanks="\n\n Thank you\n HansiSoft Solutions..";
			    		 
			    		 String body=agentDetails+""+proDetals+""+thanks;
			    		 
			    		 
			    		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
			  	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
			  	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
				             message.setSubject(Constants.PropertyConstants.LAYOUT_SUBJECT);
				             message.setText(body);
				             Transport.send(message);
			
				             System.out.println("Email sent successfully!");
			    		 
			    	 } catch (MessagingException e) {
			             throw new RuntimeException(e);
			         }
	}
	
	
	public static void sendAgeriEmail(AgriculturalDataEntryModel agriculturalDataEntryModel,int userId)
	{
	    	
			    	 try {
			    		 
			    		 UserDetails userDetails=uDao.getUser(userId);
			    		 System.out.println("User email :"+userDetails.getEmail());
			    		 
			    		 
			    		 String agentDetails="Agent : "+userDetails.getfName()+" "+userDetails.getlName()+",\n\n Congratulations...\n Your Property has been listed successfully.\n\n"
									+ "Agent Details \n\n First Name : "+userDetails.getfName()+";\n Last Name : "+userDetails.getlName()+";\n Contact Number : "+userDetails.getPhone()+" ;\n Email : "+userDetails.getEmail()+"; \n Address : "+userDetails.getAddress()+";\n Date :  "+LocalDate.now().toString()+".";
							
			    		 
			    		 int cost=agriculturalDataEntryModel.getNumberCents() * agriculturalDataEntryModel.getPerCent();
			    		 
			    		 String proDetals="\n\n\nAgriculture Details.\n\n Owner Name : "+agriculturalDataEntryModel.getOwnerName()+";"
			    		 		+ "\n Cost : "+cost+";\n Primary location : "+agriculturalDataEntryModel.getPrimLocation()+"; "
			    		 		+ "\n Secondry Location : "+agriculturalDataEntryModel.getSecoLocation()+"\n\n\n";
			    		 
			    		 String thanks="\n\n Thank you\n HansiSoft Solutions..";
			    		 
			    		 String body=agentDetails+""+proDetals+""+thanks;
			    		 
			    		 
			    		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
			  	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
			  	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
				             message.setSubject(Constants.PropertyConstants.AGRI_SUBJECT);
				             message.setText(body);
				             Transport.send(message);
			
				             System.out.println("Email sent successfully!");
			    		 
			    	 } catch (MessagingException e) {
			             throw new RuntimeException(e);
			         }
	}
	
	public static void sendIndiEmail(IndiSiteDataEntryModel ndiSiteDataEntryModel,int userId)
	{
	    	
			    	 try {
			    		 
			    		 UserDetails userDetails=uDao.getUser(userId);
			    		 System.out.println("User email :"+userDetails.getEmail());
			    		 
			    		 
			    		 String agentDetails="Agent : "+userDetails.getfName()+" "+userDetails.getlName()+",\n\n Congratulations...\n Your Property has been listed successfully.\n\n"
									+ "Agent Details \n\n First Name : "+userDetails.getfName()+";\n Last Name : "+userDetails.getlName()+";\n Contact Number : "+userDetails.getPhone()+" ;\n Email : "+userDetails.getEmail()+"; \n Address : "+userDetails.getAddress()+";\n Date :  "+LocalDate.now().toString()+".";
							
			    		 
			    		 int cost=(ndiSiteDataEntryModel.getLength() * ndiSiteDataEntryModel.getWidth()) * ndiSiteDataEntryModel.getPersqft();
			    		 
			    		 String proDetals="\n\n\nIndividual Site Details.\n\n Owner Name : "+ndiSiteDataEntryModel.getOwnerName()+";"
			    		 		+ "\n Cost : "+cost+";\n Primary location : "+ndiSiteDataEntryModel.getPrimLocation()+"; "
			    		 		+ "\n Secondry Location : "+ndiSiteDataEntryModel.getSecoLocation()+"\n\n\n";
			    		 
			    		 String thanks="\n\n Thank you\n HansiSoft Solutions..";
			    		 
			    		 String body=agentDetails+""+proDetals+""+thanks;
			    		 
			    		 
			    		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
			  	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
			  	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
				             message.setSubject(Constants.PropertyConstants.INDI_SUBJECT);
				             message.setText(body);
				             Transport.send(message);
			
				             System.out.println("Email sent successfully!");
			    		 
			    	 } catch (MessagingException e) {
			             throw new RuntimeException(e);
			         }
	}
	
	
	public static void sendVillaEmail(VillaModel villaModel,int userId)
	{
	    	
			    	 try {
			    		 
			    		 UserDetails userDetails=uDao.getUser(userId);
			    		 System.out.println("User email :"+userDetails.getEmail());
			    		 
			    		 
			    		 String agentDetails="Agent : "+userDetails.getfName()+" "+userDetails.getlName()+",\n\n Congratulations...\n Your Property has been listed successfully.\n\n"
									+ "Agent Details \n\n First Name : "+userDetails.getfName()+";\n Last Name : "+userDetails.getlName()+";\n Contact Number : "+userDetails.getPhone()+" ;\n Email : "+userDetails.getEmail()+"; \n Address : "+userDetails.getAddress()+".";
							
			    		 
			    		 int cost=(villaModel.getPlot_area() + villaModel.getS_build_are()) * villaModel.getPersqft();
			    		 
			    		 String proDetals="\n\n\n Villa Details.\n\n Owner Name : "+villaModel.getOwner_name()+";"
			    		 		+ "\n Cost : "+cost+";\n Primary location : "+villaModel.getPrim_location()+"; "
			    		 		+ "\n Secondry Location : "+villaModel.getSeco_location()+"\n\n\n";
			    		 
			    		 String thanks="\n\n Thank you\n HansiSoft Solutions..";
			    		 
			    		 String body=agentDetails+""+proDetals+""+thanks;
			    		 
			    		 
			    		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
			  	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
			  	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
				             message.setSubject(Constants.PropertyConstants.VILLA_SUBJECT);
				             message.setText(body);
				             Transport.send(message);
			
				             System.out.println("Email sent successfully!");
			    		 
			    	 } catch (MessagingException e) {
			             throw new RuntimeException(e);
			         }
	}
	
	
	public static void sendLayoutLeadEmail(String custName,String contactNumber,String email,LayoutMode selectedProperty )
	{
		try {
   		 
   		 UserDetails userDetails=uDao.getUser(selectedProperty.getUserId());
   		 System.out.println("User email :"+userDetails.getEmail());
   		 
   		 
   		String customerDetails="Customer Details..\n\n Customer Name : "+custName+"\n Contact No : "+contactNumber+",\n Email ID : "+email+"\n\n\n";
   		String propertySetils="Layoyt Details..\n\n Layout Name : "+selectedProperty.getName()+" \n Primary location : "+selectedProperty.getPrimLocation()+"\n Secondry Location : "+selectedProperty.getSecoLocation()+"\n Date : "+LocalDate.now().toString()+".";
   	    String thanks="\n\n Thank you\n HansiSoft Solutions..";
   		 
   		 String body=customerDetails+""+propertySetils+""+thanks;
   		 
   		 
   		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
 	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
 	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	             message.setSubject(Constants.PropertyConstants.LEAD_SUBJECT);
	             message.setText(body);
	             Transport.send(message);

	             System.out.println("Email sent successfully!");
   		 
   	 } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public static void sendAgriLeadEmail(String custName,String contactNumber,String email,AgriculturalModel selectedProperty )
	{
		try {
   		 
   		 UserDetails userDetails=uDao.getUser(selectedProperty.getUserId());
   		 System.out.println("User email :"+userDetails.getEmail());
   		 
   		 
   		String customerDetails="Customer Details..\n\n Customer Name : "+custName+"\n Contact No : "+contactNumber+",\n Email ID : "+email+"\n\n\n";
   		String propertySetils="Agricultural Details..\n\n Owner Name : "+selectedProperty.getOwnerName()+" \n Primary location : "+selectedProperty.getPrimLocation()+"\n Secondry Location : "+selectedProperty.getSecoLocation()+"\n Date : "+LocalDate.now().toString()+".";
   	    String thanks="\n\n Thank you\n HansiSoft Solutions..";
   		 
   		 String body=customerDetails+""+propertySetils+""+thanks;
   		 
   		 
   		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
 	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
 	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	             message.setSubject(Constants.PropertyConstants.LEAD_SUBJECT);
	             message.setText(body);
	             Transport.send(message);

	             System.out.println("Email sent successfully!");
   		 
   	 } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public static void sendIndiLeadEmail(String custName,String contactNumber,String email,IndividualSiteModel selectedProperty )
	{
		try {
   		 
   		 UserDetails userDetails=uDao.getUser(selectedProperty.getUserId());
   		 System.out.println("User email :"+userDetails.getEmail());
   		 
   		 
   		String customerDetails="Customer Details..\n\n Customer Name : "+custName+"\n Contact No : "+contactNumber+",\n Email ID : "+email+"\n\n\n";
   		String propertySetils="Individual Site Details..\n\n Owner Name : "+selectedProperty.getOwnerName()+" \n Primary location : "+selectedProperty.getPrimLocation()+"\n Secondry Location : "+selectedProperty.getSecoLocation()+"\n Date : "+LocalDate.now().toString()+".";
   	    String thanks="\n\n Thank you\n HansiSoft Solutions..";
   		 
   		 String body=customerDetails+""+propertySetils+""+thanks;
   		 
   		 
   		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
 	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
 	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	             message.setSubject(Constants.PropertyConstants.LEAD_SUBJECT);
	             message.setText(body);
	             Transport.send(message);

	             System.out.println("Email sent successfully!");
   		 
   	 } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public static void sendVillaLeadEmail(String custName,String contactNumber,String email,VillaModel selectedProperty )
	{
		try {
   		 
   		 UserDetails userDetails=uDao.getUser(selectedProperty.getUserId());
   		 System.out.println("User email :"+userDetails.getEmail());
   		 
   		 
   		String customerDetails="Customer Details..\n\n Customer Name : "+custName+"\n Contact No : "+contactNumber+",\n Email ID : "+email+"\n\n\n";
   		String propertySetils="Villa Details..\n\n Owner Name : "+selectedProperty.getOwner_name()+" \n Primary location : "+selectedProperty.getPrim_location()+"\n Secondry Location : "+selectedProperty.getSeco_location()+"\n Date : "+LocalDate.now().toString()+".";
   	    String thanks="\n\n Thank you\n HansiSoft Solutions..";
   		 
   		 String body=customerDetails+""+propertySetils+""+thanks;
   		 
   		 
   		  recipientAddresses[recipientAddresses.length-1]=new InternetAddress(userDetails.getEmail() == null ? "viswanatha.reddy@hansisofts.com" : userDetails.getEmail());
 	             message.addRecipients(Message.RecipientType.TO, recipientAddresses);
 	             //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	             message.setSubject(Constants.PropertyConstants.LEAD_SUBJECT);
	             message.setText(body);
	             Transport.send(message);

	             System.out.println("Email sent successfully!");
   		 
   	 } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
		
	}
 

}
