package beans;

import framework.EventHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "test", eager = true)
@RequestScoped
public class TestController {

    public void testFunction1() {
        EventHandler.alertUserInfo("Function 1", "This is what info notification looks like.");
    }

    public void testFunction2() {
        EventHandler.alertUserError("Function 2", "Some error happened (just an example of a notification)!");
    }

    public void testFunction3() {
        EventHandler.alertUserFatal("Function 3", "Fatal, your webapp is about to crash! (just kidding)");
    }

}
