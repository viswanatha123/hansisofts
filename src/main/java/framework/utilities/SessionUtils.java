package framework.utilities;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.DIC.model.PackageModel;
import com.DIC.model.UserDetails;

public class SessionUtils {
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static int getUserId() {
		HttpSession session = getSession();
		if (session != null)
			
			
			return Integer.parseInt(session.getAttribute("userId").toString());
			
		else
			return 0;
	}
	
	public static String getUserDisName() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("disName");
		else
			return null;
	}
	
	public static String getUserFullName() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("fullName");
		else
			return null;
	}

	
	
	
	
	public static void setUserDetails(UserDetails UserDetails, PackageModel packageModel,long remainDays) {
		
		if(UserDetails!=null)
		{
					HttpSession session = getSession();
					if (session != null)
					{
						session.setAttribute("userId", UserDetails.getUserId());
						session.setAttribute("userName", UserDetails.getUserName());
						session.setAttribute("fname", UserDetails.getfName());
						session.setAttribute("lname", UserDetails.getlName());
						session.setAttribute("address", UserDetails.getAddress());
						session.setAttribute("disName", SessionUtils.displayName(UserDetails.getfName(),UserDetails.getlName()));
						session.setAttribute("fullName", SessionUtils.displayFullName(UserDetails.getfName(),UserDetails.getlName()));
						session.setAttribute("listLimit",packageModel.getListLimit());
						session.setAttribute("isEnable",packageModel.getIsEnable());
						session.setAttribute("remainDays",remainDays);
						
						
						
						
					}
					
		}
	  }
	
		public static String displayName(String fName,String lName)
		{
			return fName.substring(0,1).toUpperCase()+""+lName.substring(0,1).toUpperCase();
		}
		
		public static String displayFullName(String fName,String lName)
		{
			return fName+" "+lName;
		}
	
	
	
	
}
