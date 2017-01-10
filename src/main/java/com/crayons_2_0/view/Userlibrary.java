package com.crayons_2_0.view;

import com.crayons_2_0.mockup.Benutzeransicht;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
@SpringUI
public class Userlibrary extends VerticalLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "Userlibrary";

    public Userlibrary() {

        // you can add Vaadin components in predefined slots in the custom
        // layout
      

        setSizeFull();
        setStyleName("about-view");
        setMargin(true);
        addComponent(new Benutzeransicht());
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
