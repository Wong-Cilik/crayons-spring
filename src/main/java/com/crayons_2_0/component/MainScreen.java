package com.crayons_2_0.component;

import java.util.ResourceBundle;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.service.LanguageControl;
import com.crayons_2_0.view.AboutView;
import com.crayons_2_0.view.Authorlibrary;
import com.crayons_2_0.view.LearningGraph;
import com.crayons_2_0.view.Preferences;
import com.crayons_2_0.view.Userlibrary;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
@SpringUI
public class MainScreen extends HorizontalLayout {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Menu menu;
    
    ResourceBundle lang = LanguageControl.getInstance().getRes();

    public MainScreen(MyUI ui) {

        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);
        menu.addView(new AboutView(), AboutView.VIEW_NAME, lang.getString(AboutView.VIEW_NAME),
                FontAwesome.INFO_CIRCLE);
        menu.addView(new Authorlibrary(), Authorlibrary.VIEW_NAME, lang.getString(Authorlibrary.VIEW_NAME),
                FontAwesome.BOOK);
        menu.addView(new Userlibrary(), Userlibrary.VIEW_NAME, lang.getString(Userlibrary.VIEW_NAME),
                FontAwesome.PENCIL);
        menu.addView(new Preferences(),Preferences.VIEW_NAME, lang.getString(Preferences.VIEW_NAME),
                FontAwesome.GEAR);
        menu.addView(new LearningGraph(), LearningGraph.VIEW_NAME, LearningGraph.VIEW_NAME, FontAwesome.PLUS);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
}
