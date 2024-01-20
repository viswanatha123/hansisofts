package com.DIC.Service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.LocationDAOImpl;
import com.DIC.model.UserDetails;
import com.DIC.model.UserRoleModel;
import com.DIC.model.VillaModel;

@ManagedBean(name="userDetailsService")
@ViewScoped
public class UserDetailsService implements Serializable {
	
	private static final Logger log = Logger.getLogger(UserDetailsService.class.getName());
	
	
	
	 private List<UserDetails> userDetailsList;
	    GeneralDAOImpl gDao;
	    
	
	@PostConstruct 
    public void init()
    {
    	log.log(Level.INFO, "Loading UserDetailsService init()");
    	gDao=new GeneralDAOImpl();
          
    	userDetailsList=gDao.getAllUsers();
          
    }


	public List<UserDetails> getUserDetailsList() {
		return userDetailsList;
	}


	public void setUserDetailsList(List<UserDetails> userDetailsList) {
		this.userDetailsList = userDetailsList;
	}


	
	
	
	
}
