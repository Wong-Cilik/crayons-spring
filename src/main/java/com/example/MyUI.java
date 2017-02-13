package com.example;

import javax.servlet.ServletContext;  
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;


@SpringUI
@SpringViewDisplay
@Theme("valo")
public class MyUI extends UI {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ApplicationContext applicationContext;
    
//    @Autowired
//    SpringViewProvider viewProvider;
    
//    @Autowired
//    ErrorView errorView;

    @Override
    protected void init(VaadinRequest request) {

        httpSession(request);
        getPage().setTitle("Demo");

        getUI().getNavigator().navigateTo("");
        

    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public void showMainView() {
        
        getUI().getNavigator().navigateTo("mainView");
        
  
    }
    
    

    
    public void showRegisterView(){
        
       getUI().getNavigator().navigateTo("registerView");
    }
    
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
   
//    private void handleError(com.vaadin.server.ErrorEvent event) {
//        Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
//        if (t instanceof AccessDeniedException) {
//            Notification.show("You do not have permission to perform this operation",
//                Notification.Type.WARNING_MESSAGE);
//        } else {
//            DefaultErrorHandler.doDefault(event);
//        }
//    }
    
    private void httpSession(VaadinRequest request) {
        WrappedSession session = request.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
        ServletContext servletContext = httpSession.getServletContext();
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

   
}
