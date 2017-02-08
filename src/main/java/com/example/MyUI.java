package com.example;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.example.view.Login;
import com.example.view.MainView;
import com.example.view.RegisterView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;


@SpringUI
@Theme("valo")
public class MyUI extends UI {

    private ApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {

        httpSession(request);
        getPage().setTitle("Demo");

        setContent(new Login());

    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public void showMainView() {
        
        setContent(new MainView(MyUI.this));
    }
    
    

    public void showRegisterView(){
        
        setContent(new RegisterView(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
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
