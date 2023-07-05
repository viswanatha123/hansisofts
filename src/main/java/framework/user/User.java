package framework.user;

import framework.EventHandler;
import framework.utilities.Utilities;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "user", eager = true)
@SessionScoped
public class User implements Serializable {

    String username;
    String password;
    String name;
    boolean authenticated;

    public String login() {
        //TODO: User-> implement login function

        //sample dummy code
        authenticated = true;
        name = "Restless Devil";
        EventHandler.alertUserInfo("User authenticated.", "Welcome!");
        //Dummy code end

        return "index?faces-redirect=true";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        EventHandler.alertUserInfo("Session terminated by user.", "Bye!");
        return "index?faces-redirect=true";
    }

    //Getteri i setteri
    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Context-depentend static getters
    public static String getContextUsername() {
        User current_user = (User) Utilities.getObject("#{user}");
        return current_user.getUsername();
    }

    public static boolean isContextAuthenticated() {
        User current_user = (User) Utilities.getObject("#{user}");
        return current_user.isAuthenticated();
    }

}
