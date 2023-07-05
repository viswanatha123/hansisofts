package framework;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class EventHandler {

    private EventHandler() {
    }

    private static void alert(FacesMessage.Severity level, String message, String details) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(level, message, details));
        // Uncomment this line if you want to use Growl after synchronous requests
        //FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    //Alerts
    public static void alertUserInfo(String message, String details) {
        alert(FacesMessage.SEVERITY_INFO, message, details);
    }

    public static void alertUserFatal(String message, String details) {
        alert(FacesMessage.SEVERITY_FATAL, message, details);
    }

    public static void alertUserError(String message, String details) {
        alert(FacesMessage.SEVERITY_ERROR, message, details);
    }

}
