package com.crayons_2_0;

import javax.servlet.annotation.WebServlet;

import com.crayons_2_0.component.MainScreen;
import com.crayons_2_0.dashboard.dummy.authentification.AccessControl;
import com.crayons_2_0.dashboard.dummy.authentification.BasicAccessControl;
import com.crayons_2_0.dashboard.dummy.authentification.LoginScreen;
import com.crayons_2_0.dashboard.dummy.authentification.LoginScreen.LoginListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
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
@SpringUI
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("valo")
public class MyUI extends UI {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
    }
}
