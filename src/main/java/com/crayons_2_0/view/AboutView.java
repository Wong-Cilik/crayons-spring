package com.crayons_2_0.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class AboutView extends VerticalLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "About";

    public AboutView() {
        VerticalLayout aboutContent = new VerticalLayout();
        aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.addComponent(
                new Label(FontAwesome.INFO_CIRCLE.getHtml()
                        + " This application is using Vaadin "
                        + Version.getFullVersion(), ContentMode.HTML));

        setSizeFull();
        setStyleName("about-view");
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
        TwinColSelect sample = new TwinColSelect();
        for (int i = 0; i < 6; i++) {
            sample.addItem(i);
            sample.setItemCaption(i, "Option " + i);
        }
        sample.setRows(6);
        sample.setNullSelectionAllowed(true);
        sample.setMultiSelect(true);
        sample.setImmediate(true);
        sample.setLeftColumnCaption("Available options");
        sample.setRightColumnCaption("Selected options");
 
 
        sample.addValueChangeListener(e -> Notification.show("Value changed:"));
        aboutContent.addComponent(sample);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
