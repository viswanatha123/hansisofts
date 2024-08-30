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
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("viswanathareddy120@gmail.com"));

        // Set Subject: header field
        message.setSubject("Test Email from Java");

        // Set the actual message
        message.setText("Hello, this is a test email sent from a Java program!");

        // Send message
        Transport.send(message);

        System.out.println("Email sent successfully!");

    } catch (javax.mail.MessagingException e) {
        //logger.error("Failed to send email: {}", e.getMessage(), e);
        // Optionally: Notify users or take corrective action
    } catch (Exception e) {
        //logger.error("Unexpected error occurred: {}", e.getMessage(), e);
        // Optionally: Notify users or take corrective action
    }




	}

}
