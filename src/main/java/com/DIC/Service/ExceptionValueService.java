package com.DIC.Service;


import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.ExceptionValueModel;

@ManagedBean(name="exceptionValueService")
@SessionScoped
public class ExceptionValueService {
	

	   long datasetid=0;
	   String step_informationVal;
	   Date createdOnDate;
	   String modelName;
	    
	  
	    
	    List<ExceptionValueModel> exceptionValueModel;
	    
	
		private ConnectionDAOImpl dao;
	      public ExceptionValueService() {
	          FacesContext fctx = FacesContext.getCurrentInstance();
	          Map<String, Object> sessionMap = fctx.getExternalContext().getSessionMap();
	          if (sessionMap.get("dataSetId") == null) {
	              datasetid = 0;
	          } else {
	              try {
	                  datasetid = (Long) (sessionMap.get("dataSetId"));
	              } catch (Exception e) {
	                  try {
	                      datasetid = Long.parseLong((String) (sessionMap.get("dataSetId")));
	                  } catch (Exception ex) {
	                  }
	              }
	          }
	          
	          if (sessionMap.get("step_information") == null) {
	              step_informationVal = null;
	          } else {
	              try {
	                  step_informationVal = (String) (sessionMap.get("step_information"));
	              } catch (Exception e) {
	                  try {
	                     // step_informationVal = (String) (sessionMap.get("step_information")));
	                  } catch (Exception ex) {
	                  }
	              }
	          }
	          
	          if (sessionMap.get("createdOnDateVal") == null) {
	              createdOnDate = null;
	          } else {
	              try {
	                  createdOnDate = (Date) (sessionMap.get("createdOnDateVal"));
	              } catch (Exception e) {
	                  try {
	                     // step_informationVal = (String) (sessionMap.get("step_information")));
	                  } catch (Exception ex) {
	                  }
	              }
	          }
	          
	          //  Model Name
	          if (sessionMap.get("modelname") == null) {
	              modelName = null;
	          } else {
	              try {
	                  modelName = (String) (sessionMap.get("modelname"));
	              } catch (Exception e) {
	                  try {
	                     // step_informationVal = (String) (sessionMap.get("step_information")));
	                  } catch (Exception ex) {
	                  }
	              }
	          }
	          
	          
	          
	          dao = new ConnectionDAOImpl();
	          exceptionValueModel = dao.getExceptionValue(datasetid,createdOnDate);

	          System.out.println("*****Dataset Id ******* : "+datasetid);
	          System.out.println("*****Step information ******* : "+step_informationVal);
	          

	      }
	      
	      public List<ExceptionValueModel> getExceptionValueModel() {
				return exceptionValueModel;
			}
			public void setExceptionValueModel(List<ExceptionValueModel> exceptionValueModel) {
				this.exceptionValueModel = exceptionValueModel;
			}
			
			
			   public long getDatasetid() {
					return datasetid;
				}

				public void setDatasetid(long datasetid) {
					this.datasetid = datasetid;
				}

				public String getStep_informationVal() {
					return step_informationVal;
				}

				public void setStep_informationVal(String step_informationVal) {
					this.step_informationVal = step_informationVal;
				}

				public Date getCreatedOnDate() {
					return createdOnDate;
				}

				public void setCreatedOnDate(Date createdOnDate) {
					this.createdOnDate = createdOnDate;
				}

				public String getModelName() {
					return modelName;
				}

				public void setModelName(String modelName) {
					this.modelName = modelName;
				}

}
