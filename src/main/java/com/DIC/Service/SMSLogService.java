package com.DIC.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.CommonDAOImpl;
import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.SMSModel;
import com.DIC.model.UserDetails;

@ManagedBean(name="sMSLogService")
@ViewScoped
public class SMSLogService {
	private static final Logger log = Logger.getLogger(SMSLogService.class.getName());
	
	private List<SMSModel> smsModel;
	
	
	CommonDAOImpl cDao;
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading SMS ");
    	cDao=new CommonDAOImpl();
    	smsModel=cDao.getSMSLog();
          
    }

	public List<SMSModel> getSmsModel() {
		return smsModel;
	}

	public void setSmsModel(List<SMSModel> smsModel) {
		this.smsModel = smsModel;
	}
	
	
	

}
