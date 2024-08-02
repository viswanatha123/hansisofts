package com.DIC.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;

@ManagedBean(name="queryToolService")
@ViewScoped
public class QueryToolService {
	
	
	private String queryString;
	private String dataResult;
	private String result;
	private int errorMessage=1;
	
	GeneralDAOImpl gDao;
	
	@PostConstruct 
    public void init()
    {
		
		gDao=new GeneralDAOImpl();
    }
	
	public void execute() 
	{
		dataResult="";
		result="";
			System.out.println("*********** Query executed successful **************");
			System.out.println("Query  : "+queryString);
			
			
			Object data1=gDao.executeQuery(queryString);
			
			
			
			 if (data1==null) {
		            System.out.println("No data to display.");
		            result="No data to display.";
		            
		     } else
		     {
			
							 if (data1 instanceof ArrayList) {
								 List<Map<String, Object>> data = (List<Map<String, Object>>) data1;
								 		
								 	if (!data.isEmpty() && data.size() > 0) {
								 
														 Map<String, Object> firstRow = data.get(0);
													     System.out.println(String.join(", ", firstRow.keySet()));
													     result+=String.join(", ", firstRow.keySet()+"\n");
													     result+="===============================================================================================\n";
													     System.out.println("---------------headers-------------"+result);
														 int rowNum=0;
														 for (Map<String, Object> row : data) {
															 String rowdata="";
													            
													            for (Map.Entry<String, Object> entry : row.entrySet()) {
													               
													            	rowdata=rowdata+entry.getValue()+" , ";
													            	
													            	
													            }
													            
													            System.out.println(rowNum+" "+rowdata);
													            result+=rowNum+" "+rowdata+"\n";
													            System.out.println(); // Add an empty line between rows
													            rowNum+=1;
													            
													      }
														 
								 	}
								 	else
								 	{
								 		result="No data to display.";
								 	}
														 
														 
								errorMessage=1;   
						        } else if (data1 instanceof String) {
						        	
						        	result=data1.toString();
						        	errorMessage=0;
						            
						        }
			
		     } 
			 
	dataResult=result;
	}
	
	public void clearQuery() 
	{
		
		queryString="";
	}
	
	public void clearResult() 
	{
		result="";
	}
	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}



	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDataResult() {
		return dataResult;
	}

	public void setDataResult(String dataResult) {
		this.dataResult = dataResult;
	}

	public int getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(int errorMessage) {
		this.errorMessage = errorMessage;
	}

	



	
	

}
