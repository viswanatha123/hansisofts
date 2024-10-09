package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import org.primefaces.shaded.json.JSONObject;

public class Demo1 {
	
	public static void main(String args[])
	{
		
	      try
	      {
	    	  //String ipAddress = "8.8.8.8"; // Change to the desired IP address
	    	  //String ipAddress = "192.168.0.102";
	    	  String ipAddress = "10.218.22.198";
	           
	            
	            String urlString = "http://ip-api.com/json/" + ipAddress;
	            URL url = new URL(urlString);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");
	            
	            
	            
	            if (conn.getResponseCode() == 200) {
	                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	                StringBuilder response = new StringBuilder();
	                String line;

	                while ((line = reader.readLine()) != null) {
	                    response.append(line);
	                }
	                reader.close();
	                
	             // Parse the JSON response
	                JSONObject jsonResponse = new JSONObject(response.toString());
	                String city = jsonResponse.getString("city");
	                String region = jsonResponse.getString("regionName");
	                String country = jsonResponse.getString("country");
	                double lat = jsonResponse.getDouble("lat");
	                double lon = jsonResponse.getDouble("lon");

	                System.out.printf("IP: %s, City: %s, Region: %s, Country: %s, Latitude: %.6f, Longitude: %.6f%n", ipAddress, city, region, country, lat, lon);
	            } else {
	                System.out.println("Error: Unable to fetch location data.");
	            }


		    	  
	      }catch(Exception e)
	      {
	    	  e.printStackTrace();
	      }
        

	}

	
}
