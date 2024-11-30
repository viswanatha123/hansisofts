package com.DIC.DAO.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
*/
import framework.utilities.Constants;

@ManagedBean
@ApplicationScoped
public class SMSService {
	
	private static final Logger log = LogManager.getLogger(SMSService.class);
	
	    //public static final String ACCOUNT_SID = "AC3116f795624b49e44baf5cfc888fe220";
	    //public static final String AUTH_TOKEN = "d0153bb27044871fd3126aecc7b21be7";
	    //public static final String TWILIO_NUMBER = "+14253097706";
	

	UserDAOImpl udo;
	
	public SMSService()
	{
		
		udo=new UserDAOImpl();
		
	}
	
	
	// Fast2sms Service
	public void sendSMSLeadText2sms(String toNum, String agentName,String custName,String contactNumber)
	{
		
		
		int responseCode=0;
		//String message = "Hello, this is a test message from Fast2SMS zzzz!";
		
		String message = "HansiSoft Solution, Congratulation..,Lead generated., Cust Name : "+custName+", Cust Num :"+contactNumber;
		
		String urlString = Constants.SMSServer.baseUrl + Constants.SMSServer.apiKey +
                "&route=" + Constants.SMSServer.route +
                "&message=" + message +
                "&flash=" + Constants.SMSServer.flash +
                "&numbers=" + toNum;
		
		        
		        try {
		        	
		        	URL url = new URL(urlString);
		        	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        	connection.setRequestMethod("GET");
		        	connection.setConnectTimeout(5000); // 5 seconds
		            connection.setReadTimeout(5000); // 5 seconds
		            responseCode = connection.getResponseCode();
		            System.out.println("Response Code: " + responseCode);
			             if (responseCode == HttpURLConnection.HTTP_OK) {
			                 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			                String inputLine;
			                StringBuilder response = new StringBuilder();
					                 while ((inputLine = in.readLine()) != null) {
					                    response.append(inputLine);
					                    
					                 }
			                
			                   in.close();
			                   
			                   String[] responseMessage=response.toString().split(",");
			                   System.out.println(" Response0 : "+responseMessage[0]);
			                   System.out.println(" Response1 : "+responseMessage[1]);
			                   System.out.println(" Response2 : "+responseMessage[2]);
			                   
			                   String result=udo.updateSMSLog(responseCode,responseMessage[0],responseMessage[1], responseMessage[2], toNum,  agentName,  custName,  contactNumber, "");
			                    
			           
			                log.info("SMS Response: " + response.toString());
			            } else {
			                log.error("SMS Error: Unable to send SMS. Response Code: " + responseCode);
			                String result=udo.updateSMSLog(responseCode,"Failed","Apikey wrong", "Unable to send SMS", toNum,  agentName,  custName,  contactNumber, "");
				               
			                
			            }
		            
		  
		            connection.disconnect();
		        	
		        
		        } catch (IOException e) {
		        	
		        	String result=udo.updateSMSLog(responseCode,"Error","exception", "Illegal character in URL", toNum,  agentName,  custName,  contactNumber, "");
		        	log.error("SMS Error: Unable to send SMS. Response Code: " + responseCode);
		            e.printStackTrace();
		        }
	
	}
	
}
