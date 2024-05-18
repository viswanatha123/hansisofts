package com.DIC.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
//import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.DIC.DAO.Impl.GeneralDAOImpl;

import framework.utilities.SessionUtils;

@ManagedBean(name="userRoleService")
@ViewScoped
public class UserRoleService implements Serializable{
	
	private static final Logger log = Logger.getLogger(VillaDetailsService.class.getName());
	


	private int userId;
	private String userName;
	private String disName;
	private List<String> userRole; 

	 
	GeneralDAOImpl gDao;
	
	
	@PostConstruct 
    public void init()
    {
		int default_userid=1;
		log.log(Level.INFO, "Loading UserRoleService init()");
			
    	gDao=new GeneralDAOImpl();
    	userRole=gDao.getUserRole(default_userid);
    	
    	HttpSession session = SessionUtils.getSession();
      	
    	if (session != null)
    	{
    		if((String)session.getAttribute("userName")!=null)
    		{
    		userId= Integer.parseInt(session.getAttribute("userId").toString());
    		userName = (String)session.getAttribute("userName");
    		disName = (String)session.getAttribute("userName");
    		
    		
    		
    		System.out.println(" ************ logged in user Details : "+userId+"    "+userName+"   "+disName);
    		userRole=gDao.getUserRole(userId);
    		}
    	}
    	
    	
    	
	
    }
	
	
	
	public List<String> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<String> userRole) {
		this.userRole = userRole;
	}




	

}
