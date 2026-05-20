package com.DIC.Service;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.UserDetails;
import framework.utilities.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
@ManagedBean(name="templateService")
@SessionScoped
public class TemplateService implements Serializable {
    private static final Logger log = LogManager.getLogger(TemplateService.class);

    private UserDetails userDetails;

    UserDAOImpl uDao;
    public TemplateService() {
        uDao=new UserDAOImpl();
        HttpSession session = SessionUtils.getSession();

        if (session != null) {
            if (session.getAttribute("userId") != null) {
                int userId = Integer.parseInt(session.getAttribute("userId").toString());
                if (userId > 0) {
                    userDetails = uDao.getUser(userId);
                }
            }
        }
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
