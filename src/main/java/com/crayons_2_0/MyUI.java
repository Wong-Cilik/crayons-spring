package com.crayons_2_0;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
//import org.apache.catalina.core.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.view.MainScreen;
import com.crayons_2_0.view.login.LoginScreen;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@SuppressWarnings("serial")
@SpringUI

@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")

public class MyUI extends UI {

    /**
     * 
     */
    private ApplicationContext applicationContext;
    

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        //setLocale(vaadinRequest.getLocale());
       
        //showMainView();
        
        /*
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                private static final long serialVersionUID = 1L;

                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }*/
         
        WrappedSession session = vaadinRequest.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
        ServletContext servletContext = httpSession.getServletContext();
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        getPage().setTitle("Crayons");
        
        
       
        if (!AuthManager.isHasAuthority()){
            setContent(new LoginScreen());
        }
        
        /*
        Navigator navigator = new Navigator(this, this);
        navigator.setErrorView(ErrorView.class);
        


        navigator.addView("login", LoginScreen.class);
        navigator.navigateTo("login");
        setNavigator(navigator);
        */
        
        
        
    }
    
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    public  void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }
    /*
    public AccessControl getAccessControl() {
        return accessControl;
    }
    /*
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

        private static final long serialVersionUID = 1L;
    }*/
}
