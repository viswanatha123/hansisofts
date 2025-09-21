package com.DIC.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.AllPropertyList;
import com.DIC.model.LayoutMode;
import com.DIC.model.LeadModel;
import com.DIC.model.PackageModel;
import com.DIC.model.UserDetails;
import com.DIC.model.UserProfileRoleModel;

import framework.utilities.SessionUtils;

@ManagedBean(name="userProfileService")
@ViewScoped
public class UserProfileService {


	private static final Logger log = Logger.getLogger(UserProfileService.class.getName());

	 private UserDetails userDetails;
	 private List<UserProfileRoleModel> userProfileRoleModel;
	 private int listedCount;
	 private PackageModel packageModel;
	private List<AllPropertyList> allPropertyListVal;

	String defaultLastUpdateDate;

	 UserDAOImpl uDao;


	 private String firstname;
	 private String lastname;
	 private String lastWorkDay;
	 private int packDuration;


  	 private int disLeadFlag=0;

	 @PostConstruct
	    public void init()
	    {

	    	log.log(Level.INFO, "Loading UserProfileService init()");
	    	uDao=new UserDAOImpl();
			findLastUpdatedDate();
			profileDetails();
	    }

	public void findLastUpdatedDate()
	{
		defaultLastUpdateDate=uDao.findLastUpdatedDate(SessionUtils.getUserId());
		System.out.println("Last updated Date : "+defaultLastUpdateDate);
	}

	 public void profileDetails()
	 {
		 HttpSession session = SessionUtils.getSession();

		 if (session != null)
		 {
			 if(session.getAttribute("userId")!=null)
			 {
				 int userId= Integer.parseInt(session.getAttribute("userId").toString());
				 if(userId > 0)
				 {

					 packageModel=uDao.getPackageDetails(userId);


					 userDetails=uDao.getUser(userId);
					 userDetails.setListLimit(packageModel.getListLimit());
					 userDetails.setPackName(packageModel.getPackName());
					 userDetails.setIsEnable(packageModel.getIsEnable());
					 userProfileRoleModel=uDao.getUserProfileRoles(userId);
					 listedCount=uDao.getAllPropByUserId(Integer.parseInt(session.getAttribute("userId").toString())).size();
					 int	remainDays=packageModel.getPackDuration()-Integer.parseInt(session.getAttribute("remainDays").toString());

					 LocalDate currentDate = LocalDate.now();
					 LocalDate futureDate = currentDate.plusDays(remainDays);
					 System.out.println("Future date : "+futureDate);
					 lastWorkDay=futureDate.toString();
					 packDuration=packageModel.getPackDuration();

				 }
			 }
		 }
	 }


	public List<UserProfileRoleModel> getUserProfileRoleModel() {
		return userProfileRoleModel;
	}


	public void setUserProfileRoleModel(List<UserProfileRoleModel> userProfileRoleModel) {
		this.userProfileRoleModel = userProfileRoleModel;
	}

	public void refresh() {
		System.out.println("****************** welcome to refresh **************");
		profileDetails();

		if(SessionUtils.getUserId() > 0)
		{
			allPropertyListVal=uDao.getAllPropByUserId(SessionUtils.getUserId());
			System.out.println("****************** Size ************** :"+allPropertyListVal.size());
			int succReco=uDao.updateRefresh(SessionUtils.getUserId());
			System.out.println("****************** Total records updated ************** :"+succReco);
			findLastUpdatedDate();
		}
	}



    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }



	public int getDisLeadFlag() {
		return disLeadFlag;
	}


	public void setDisLeadFlag(int disLeadFlag) {
		this.disLeadFlag = disLeadFlag;
	}



	public int getListedCount() {
		return listedCount;
	}


	public void setListedCount(int listedCount) {
		this.listedCount = listedCount;
	}


	public PackageModel getPackageModel() {
		return packageModel;
	}


	public void setPackageModel(PackageModel packageModel) {
		this.packageModel = packageModel;
	}


	public UserDetails getUserDetails() {
		return userDetails;
	}


	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}


	public String getLastWorkDay() {
		return lastWorkDay;
	}


	public void setLastWorkDay(String lastWorkDay) {
		this.lastWorkDay = lastWorkDay;
	}




	public int getPackDuration() {
		return packDuration;
	}




	public void setPackDuration(int packDuration) {
		this.packDuration = packDuration;
	}


	public List<AllPropertyList> getAllPropertyListVal() {
		return allPropertyListVal;
	}

	public void setAllPropertyListVal(List<AllPropertyList> allPropertyListVal) {
		this.allPropertyListVal = allPropertyListVal;
	}

	public String getDefaultLastUpdateDate() {
		return defaultLastUpdateDate;
	}

	public void setDefaultLastUpdateDate(String defaultLastUpdateDate) {
		this.defaultLastUpdateDate = defaultLastUpdateDate;
	}
}
