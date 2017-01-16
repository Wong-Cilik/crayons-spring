package com.crayons_2_0.view;

import com.crayons_2_0.controller.UserBibManager;
import com.crayons_2_0.mockup.Benutzeransicht;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
@SpringUI
public class UserlibraryView extends VerticalLayout implements View {

	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "Userlibrary";

    
    private TabSheet tabSheet;
    
    public UserlibraryView() {

        // you can add Vaadin components in predefined slots in the custom
        // layout
      

        setSizeFull();
        setStyleName("about-view");
        setMargin(true);
        
        // ------- View 1 ---------------
        addComponent(new Benutzeransicht());
        
        // ------- View 2 ---------------
        /*
        CourseService courseService = new CourseService();
        this.tabSheet = UserBibManager.getCourseTabs(tabSheet);
        addComponent(this.tabSheet);
    	*/
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
