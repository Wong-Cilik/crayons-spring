package com.crayons_2_0;



import javax.servlet.ServletContext; 
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.annotations.Theme;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;

import com.vaadin.ui.UI;


@SpringUI
@SpringViewDisplay
@Theme("mytheme")
public class MyUI extends UI {


    private static final long serialVersionUID = 1L;

    private ApplicationContext applicationContext;
    
    
    @Override
    protected void init(VaadinRequest request) {

        httpSession(request);
        getPage().setTitle("Crayons");

        
        getUI().getNavigator().navigateTo("");

    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public void showMainView() {

        getUI().getNavigator().navigateTo("mainScreen");
    }
    
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private void httpSession(VaadinRequest request) {
        WrappedSession session = request.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
        ServletContext servletContext = httpSession.getServletContext();
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }
}
