package com.DIC.model;

import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.file.UploadedFile;

public class ImageUploadModel implements Serializable {
	
	 private InputStream inputStream;
	 private UploadedFile file;
     private String imageName;
	    
	    
	   public UploadedFile getFile() {
			return file;
		}

		public void setFile(UploadedFile file) {
			this.file = file;
		}
		
		
	  	
	  	public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		
		 public String getImageName() {
				return imageName;
			}

			public void setImageName(String imageName) {
				this.imageName = imageName;
			}
		

}
