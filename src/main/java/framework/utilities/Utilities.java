package framework.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.Service.PlotsDataEntryService;

public class Utilities {
	
	private static final Logger log = Logger.getLogger(Utilities.class.getName());

	
	
    private Utilities() {
    	
    }

    // Get object from Context using binding spring, just like in xhtml el expression #{object}
    public static Object getObject(String bindingString) {
        if (bindingString.indexOf("{") == -1) {
            bindingString = "#{" + bindingString + "}";
        }
        FacesContext ctx = FacesContext.getCurrentInstance();
        Application app = ctx.getApplication();
        ExpressionFactory ef = app.getExpressionFactory();
        return ef.createValueExpression(ctx.getELContext(), bindingString, Object.class).getValue(
                ctx.getELContext());
    }
    
    
    

    
    
    
}
