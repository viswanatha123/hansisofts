package com.DIC.Service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ManagedBean(name="appLogService")
@SessionScoped
public class AppLogService implements Serializable {
	
	private static final Logger log = LogManager.getLogger(AppLogService.class);
	
	
	
	@PostConstruct 
    public void init()
    {
		
    }
	
	public StreamedContent download(){
		String filePath="";
		
		//String catalinaHome = System.getProperty("catalina.home");
        String catalinaBase = System.getProperty("catalina.base");

        //System.out.println("Catalina Home: " + catalinaHome);
        System.out.println("Catalina Base: " + catalinaBase);
        
        if (catalinaBase != null) {
            File logsDir = new File(catalinaBase, "logs/applog.log");
            System.out.println("Logs Directory: " + logsDir.getAbsolutePath());
             filePath = logsDir.getAbsolutePath();
             log.info("Tomcat path :"+logsDir.getAbsolutePath());
        }
		
		
		Path path = Paths.get(filePath);
      		
		return DefaultStreamedContent.builder() 
				.name("applog.txt")
				.contentType("text/plain")
				.stream(() -> {
					try {
						return new ByteArrayInputStream(Files.readAllBytes(path));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Logger issue occured : "+e.getMessage());
					}
					return null;
				})
				.build();
		
		
	}
	
	   

}
