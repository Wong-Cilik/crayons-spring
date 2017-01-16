package com.crayons_2_0.component;

import com.crayons_2_0.controller.graph.ConnectUnitsListener;
import com.crayons_2_0.controller.graph.DeleteConnectionListener;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class ConnectWindow extends Window {
    public ConnectWindow() {
        setSizeFull();
        setModal(true);
        setResizable(false);
        setClosable(true); 
        setHeight(40.0f, Unit.PERCENTAGE);
        setWidth(40.0f, Unit.PERCENTAGE);
        
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);
        Component title = buildTitle();
        content.addComponent(title);
        content.setComponentAlignment(title, Alignment.TOP_CENTER);
        
        /*content.addComponent(buildDescription());
        
        Component unitChoiseFrom = buildUnitChoice("From");
        content.addComponent(unitChoise);
        content.setComponentAlignment(unitChoise, Alignment.MIDDLE_LEFT);
        
        Component unitChoiseTo = buildUnitChoice("To");
        content.addComponent(unitChoise);
        content.setComponentAlignment(unitChoise, Alignment.MIDDLE_LEFT);*/
        
        content.addComponent(buildFooter());
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button connect = new Button("Connect");
        connect.addStyleName(ValoTheme.BUTTON_PRIMARY);
        connect.addClickListener(new ConnectUnitsListener());
        connect.focus();
        footer.addComponent(connect);
        footer.setComponentAlignment(connect, Alignment.TOP_CENTER);
        
        Button disconnect = new Button("Delete connection");
        disconnect.addStyleName(ValoTheme.BUTTON_PRIMARY);
        disconnect.addClickListener(new DeleteConnectionListener());
        disconnect.focus();
        footer.addComponent(disconnect);
        footer.setComponentAlignment(disconnect, Alignment.TOP_CENTER);
        
        return footer;
    }

    /*private Component buildUnitChoice(String label) {
        VerticalLayout unitTypeChoice = new VerticalLayout();
        return null;
    }*/

    private Component buildTitle() {
        Label title = new Label("Manage unit connection");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    /*private Component buildDescription() {
        return null;
    }*/
}
