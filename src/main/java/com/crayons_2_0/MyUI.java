package com.crayons_2_0;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.crayons.view.dagred3.Dagre;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.ConnectorTracker;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

// @PreserveOnRefresh
@SuppressWarnings("serial")
@SpringUI
@SpringViewDisplay
@Theme("mytheme")
@PreserveOnRefresh
public class MyUI extends UI {

    private ApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

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

    private ConnectorTracker tracker;

    @Override
    public ConnectorTracker getConnectorTracker() {
        if (this.tracker == null) {
            this.tracker = new ConnectorTracker(this) {
                @Override
                public void registerConnector(ClientConnector connector) {
                    try {
                        super.registerConnector(connector);
                    } catch (RuntimeException e) {
                        Logger.getLogger(MyUI.class.getName()).log(Level.ERROR, connector.getClass().getSimpleName());
                        super.unregisterConnector(connector);
                        getPage().reload();
                        throw e;
                    }
                }
            };
        }
        return tracker;
    }
}
