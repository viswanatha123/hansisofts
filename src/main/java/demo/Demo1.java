package demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.Desktop;
import java.net.URI;

public class Demo1 {
	
	public static void main(String args[])
	{
		
	
		    	
		        String ip = "192.168.0.102"; // Replace with the public IP address
		        String apiKey = "ce3576e8f7e348b5ae2545b9fa0a4db8"; // Replace with your actual API key
		        String url = "https://api.ipgeolocation.io/ipgeo?apiKey=" + apiKey + "&ip=" + ip;

		        try (CloseableHttpClient client = HttpClients.createDefault()) {
		            HttpGet request = new HttpGet(url);
		            HttpResponse response = client.execute(request);
		            String jsonResponse = EntityUtils.toString(response.getEntity());

		            // Extract latitude and longitude from the JSON response
		            String latitude = extractValue(jsonResponse, "latitude");
		            String longitude = extractValue(jsonResponse, "longitude");

		            // Create a Google Maps URL
		            String googleMapsUrl = "https://www.google.com/maps/@?api=1&map_action=map&center=" + latitude + "," + longitude;

		            // Open the URL in the default web browser
		            if (Desktop.isDesktopSupported()) {
		                Desktop.getDesktop().browse(new URI(googleMapsUrl));
		            } else {
		                System.out.println("Desktop is not supported.");
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }

		    private static String extractValue(String jsonResponse, String key) {
		        // A simple method to extract a value from JSON (for demo purposes only)
		        String prefix = "\"" + key + "\":\"";
		        int startIndex = jsonResponse.indexOf(prefix) + prefix.length();
		        int endIndex = jsonResponse.indexOf("\"", startIndex);
		        return jsonResponse.substring(startIndex, endIndex);
		    }
		

	
	
}
