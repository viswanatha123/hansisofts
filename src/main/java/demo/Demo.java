package demo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Demo {
	
	public static void main(String args[]) 
	{
		System.out.println("---------------welcome--------------------");
		
	
			/*
   
		Session session=godaddyService();
		
		if(session==null)
		{
			System.out.println("--------------Godaddy--------------------");
		}else
		{
			System.out.println("--------------Gmail--------------------");
		}
    
    */
		
		Properties props = new Properties();
        props.put("mail.smtp.host", "smtpout.secureserver.net"); // SMTP server address
        props.put("mail.smtp.port", "465"); // SSL port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.connectiontimeout", "10000"); // 10 seconds
        props.put("mail.smtp.timeout", "10000"); // 10 seconds
        props.put("mail.smtp.starttls.enable", "true");
        
    // Set up the session
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("hansisoft.hr@hansisofts.com", "HansiSofts@123");
        }
    });
    
    try {
        // Create a default MimeMessage object
        Message message = new MimeMessage(session);

        // Set From: header field
        message.setFrom(new InternetAddress("hansisoft.hr@hansisofts.com"));

        // Set To: header field
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("viswanatha.reddy2255@gmail.com"));

        // Set Subject: header field
        message.setSubject("HansiSoft Solutions");

        // Set the actual message
        message.setText("Hello, this is a test email sent from a Java program!");

        // Send message
        Transport.send(message);

        System.out.println("Email sent successfully!");

    } catch (javax.mail.MessagingException e) {
        System.out.println("Failed to send email: {}"+ e.getMessage());
        // Optionally: Notify users or take corrective action
        e.printStackTrace();
        
    } catch (Exception e) {
    	System.out.println("Failed to send email: {}"+ e.getMessage());
        // Optionally: Notify users or take corrective action
    	e.printStackTrace();
    	
    }
		
    
    /*

    try {
        // Create a default MimeMessage object
        Message message = new MimeMessage(session);

        // Set From: header field
        message.setFrom(new InternetAddress("hansisoft.hr@hansisofts.com"));

        // Set To: header field
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("viswanatha.reddy2255@gmail.com"));

        // Set Subject: header field
        message.setSubject("HansiSoft Solutions");

        // Set the actual message
        message.setText("Hello, this is a test email sent from a Java program!");

        // Send message
        Transport.send(message);

        System.out.println("Email sent successfully!");

    } catch (javax.mail.MessagingException e) {
        System.out.println("Failed to send email: {}"+ e.getMessage());
        // Optionally: Notify users or take corrective action
        e.printStackTrace();
    } catch (Exception e) {
    	System.out.println("Failed to send email: {}"+ e.getMessage());
        // Optionally: Notify users or take corrective action
    	e.printStackTrace();
    }

*/


	}
	
	public static Session godaddyService()
	{
			 
	   	 Properties props = new Properties();
	        props.put("mail.smtp.host", "smtpout.secureserver.net"); // SMTP server address
	        props.put("mail.smtp.port", "465"); // SSL port
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.connectiontimeout", "10000"); // 10 seconds
	        props.put("mail.smtp.timeout", "10000"); // 10 seconds
	        props.put("mail.smtp.starttls.enable", "true");
	        
	    // Set up the session
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("hansisoft.hr@hansisofts.com", "HansiSofts@123");
	        }
	    });
	    
	    try {
	        // Create a default MimeMessage object
	        Message message = new MimeMessage(session);

	        // Set From: header field
	        message.setFrom(new InternetAddress("hansisoft.hr@hansisofts.com"));

	        // Set To: header field
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("viswanatha.reddy2255@gmail.com"));

	        // Set Subject: header field
	        message.setSubject("HansiSoft Solutions");

	        // Set the actual message
	        message.setText("Hello, this is a test email sent from a Java program!");

	        // Send message
	        Transport.send(message);

	        System.out.println("Email sent successfully!");

	    } catch (javax.mail.MessagingException e) {
	        System.out.println("Failed to send email: {}"+ e.getMessage());
	        // Optionally: Notify users or take corrective action
	        e.printStackTrace();
	        return null;
	    } catch (Exception e) {
	    	System.out.println("Failed to send email: {}"+ e.getMessage());
	        // Optionally: Notify users or take corrective action
	    	e.printStackTrace();
	    	return null;
	    }
	    return session;
	}
	
	public static Session gmailService()
	{
		Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true"); // Use TLS
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    
	    // Set up the session
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("viswanatha.reddy2255@gmail.com", "ymuk xndr wpgd sxcj");
	        }
	    });
	    
	    try {
	        // Create a default MimeMessage object
	        Message message = new MimeMessage(session);

	        // Set From: header field
	        message.setFrom(new InternetAddress("hansisoft.hr@hansisofts.com"));

	        // Set To: header field
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("viswanatha.reddy2255@gmail.com"));

	        // Set Subject: header field
	        message.setSubject("HansiSoft Solutions");

	        // Set the actual message
	        message.setText("Hello, this is a test email sent from a Java program!");

	        // Send message
	        Transport.send(message);

	        System.out.println("Email sent successfully!");

	    } catch (javax.mail.MessagingException e) {
	        System.out.println("Failed to send email: {}"+ e.getMessage());
	        // Optionally: Notify users or take corrective action
	        e.printStackTrace();
	        return null;
	    } catch (Exception e) {
	    	System.out.println("Failed to send email: {}"+ e.getMessage());
	        // Optionally: Notify users or take corrective action
	    	e.printStackTrace();
	    	return null;
	    }
	    return session;
	}

}


