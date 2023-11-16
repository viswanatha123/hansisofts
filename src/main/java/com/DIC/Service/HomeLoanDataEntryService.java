package com.DIC.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.file.UploadedFile;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.HomeLoanDataEntryModel;
@ManagedBean(name="HomeLoanDataEntryService")
@ViewScoped
public class HomeLoanDataEntryService implements Serializable{

	 private static final Logger log = Logger.getLogger(HomeLoanDataEntryService.class.getName());
	
	private String agentName;
	 private int age;
	 private String gender;
	 private String contactNo;
	 private String email;
	 private int loanAmt;
	 private int monthlyInc;
	 private String empType;
	//private UploadedFile file;
	 
	 
	 
	 
	 private String updateResult;
		
	 ConnectionDAOImpl dao;
	 GeneralDAOImpl gdao;
	
	public HomeLoanDataEntryService()
	{
		
	}
	
	@PostConstruct 
	   public void init()
	      {
	          log.log(Level.INFO, "Loading HomeLoanDataEntryService init()");
	          dao=new ConnectionDAOImpl();
	          gdao=new GeneralDAOImpl();
	           
	           
	           
	      }
		          
		    
		
	           public void upload() {
	         try {
	        	 this.updateResult="";
	             
	            
	
	
	         
             
	             HomeLoanDataEntryModel homeLoanDataEntryModel=new HomeLoanDataEntryModel();
             
             homeLoanDataEntryModel.setAgentName(agentName);
             homeLoanDataEntryModel.setAge(age);
             homeLoanDataEntryModel.setGender(gender);
             homeLoanDataEntryModel.setContactNo(contactNo);
             homeLoanDataEntryModel.setEmail(email);
             homeLoanDataEntryModel.setLoanAmt(loanAmt);
             homeLoanDataEntryModel.setMonthlyInc(monthlyInc);
             homeLoanDataEntryModel.setEmpType(empType);
             
	         updateResult=gdao.updateHomeLoanDataEntry(homeLoanDataEntryModel);
	              
	        
	     	this.agentName="";
	     	this.age=0;
	     	this.gender="";
	     	this.contactNo="";
	     	this.email="";
	     	this.loanAmt=0;
	     	this.monthlyInc=0;
	     	this.empType="";
	     	
	     	
	         } catch (Exception e) {
	             System.out.println("Exception-File Upload." + e.getMessage());
	         }
	     }
	     
	 
		
	 
	 
	       
	       	
	  
	
	     	
	         
	        
	  
        public void clear()
   {

	     	this.agentName="";
	     	this.age=0;
	     	this.gender="";
	     	this.contactNo="";
	     	this.email="";
	     	this.loanAmt=0;
	     	this.monthlyInc=0;
	     	this.empType="";
	     	
	     	
	     	this.updateResult="";
       System.out.println("****** Clicked on Clear button*****");
   }
        
       public String getAgentName() {
   		return agentName;
   	}
   	public void setAgentName(String agentName) {
   		this.agentName = agentName;
   	}
   	public int getAge() {
   		return age;
   	}
   	public void setAge(int age) {
   		this.age = age;
   	}
   	public String getGender() {
   		return gender;
   	}
   	public void setGender(String gender) {
   		this.gender = gender;
   	}
   	public String getContactNo() {
   		return contactNo;
   	}
   	public void setContactNo(String contactNo) {
   		this.contactNo = contactNo;
   	}
   	public String getEmail() {
   		return email;
   	}
   	public void setEmail(String email) {
   		this.email = email;
   	}
   	public int getLoanAmt() {
   		return loanAmt;
   	}
   	public void setLoanAmt(int loanAmt) {
   		this.loanAmt = loanAmt;
   	}
   	public int getMonthlyInc() {
   		return monthlyInc;
   	}
   	public void setMonthlyInc(int monthlyInc) {
   		this.monthlyInc = monthlyInc;
   	}
   	public String getEmpType() {
   		return empType;
   	}
   	public void setEmpType(String empType) {
   		this.empType = empType;
   	}
   		public String getUpdateResult() {
   			return updateResult;
   		}

   		public void setUpdateResult(String updateResult) {
   			this.updateResult = updateResult;
   		}
   			
   			}