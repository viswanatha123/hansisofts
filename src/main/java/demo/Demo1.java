package demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import javax.xml.stream.events.Characters;

import com.restfb.AccessToken;
import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
//import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.Album;
import com.restfb.types.FacebookType;
import com.restfb.types.GraphResponse;
import com.restfb.types.Photo;
import com.restfb.types.Post;
import com.restfb.types.User;

public class Demo1 {
	
	public static void main(String args[]) throws FileNotFoundException 
	{
		
	
		    	
		 System.out.println("Facebook integraion...");
	        
	        String accessToken="EAAMjErJyzr4BO1oOQZBZBmalw9a2ZC7Xx6ZAEaHR7xQlU8IVcFZBXnru1O5la4NDdkgkgCqUiFZAn6UWh2IKj9nnFORKHjoW4x7FkyiFmZBtHVAny0ZC3za2LS3uI4TyDzPOxjDaIJAiaSxPzcqiQjsjXcRzxjZBepBvNYU6eUoEd3j2gFrtJfonDJAn80PKLo2ATW9FpRi2nX09hoTa444AiUrft2JgZD";
	          
	        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
	        //FacebookClient client = new DefaultFacebookClient(accessToken);
	        
	        AccessToken exAccessToken = fbClient.obtainExtendedAccessToken("882988140383934", "64eacd5152a5cca37c0d0aa14fa314ab");
	        
	        System.out.println("Ext Token :"+exAccessToken.getAccessToken());
	        System.out.println("Ext Expired :"+exAccessToken.getExpires());
	        
	        
	        try {
	        	
	               	
	        	/*
	        	FileInputStream fis=new FileInputStream(new File("D:/img.jpg"));
	        	FacebookType response=fbClient.publish("me/photos",FacebookType.class, BinaryAttachment.with("img.jpg",fis), Parameter.with("message", "Radixcode Logo Imgege"));
	            System.out.println("fb.com/"+response.getId());
	            */
	            
	          
	        	fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "Hello, world!"));

	        	
	        	
	        	/*
	        	
	        	Below permission required for posting
	        	
	        	publish_to_groups
	        	pages_read_engagement
	            pages_manage_posts
	    
	        	*/
	        	
	        	
	        
	        	//FacebookType response=fbClient.publish("me/feed",FacebookType.class,Parameter.with("message", "welcome to HansiSoft Solutions"));
	        	
	        	//System.out.println("fb.com/"+response.getId());
	        	
	        
	        	
	       
	        	/*
	        	int count=0;
	            Connection<Post> result=fbClient.fetchConnection("me/feed", Post.class);
	        
	        
		        for(List<Post> page: result)
		        {
		        	for(Post aPost : page)
		        	{
		        		System.out.println(aPost.getMessage());
		        		System.out.println("fb.com/"+aPost.getId());
		        		count++;
		        		
		        	}
		        }
	       
	       
	      	System.out.println("Number of count : "+count);
	          
	         */
	        	
	        	/*
	        	    FileInputStream fis= new FileInputStream(new File("D:\\img.jpg"));
	        	    FacebookType response =fbClient.publish("me/feed", FacebookType.class , BinaryAttachment.with("Event_Plaza_and_Rain_Garden.jpg", fis) ,Parameter.with("message", "RadixCode Logo"));
	        	    System.out.println("fb.com/"+response.getId());
	        	
	        	*/
	        	
	        	
	        	
	        	
	        	
	            
	        } catch (FacebookException e) {
	            e.printStackTrace();
	        }
	}

	
	
}
