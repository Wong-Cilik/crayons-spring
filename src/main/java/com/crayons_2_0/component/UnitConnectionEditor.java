package com.crayons_2_0.component;

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

        Button ok = new Button("Connect");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
                Notification success = new Notification(
                        "Units are connected successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_CENTER);
        return footer;
    }

    /*private Component buildUnitChoice(String label) {
        VerticalLayout unitTypeChoice = new VerticalLayout();
        return null;
    }*/

    private Component buildTitle() {
        Label title = new Label("Connect units");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    /*private Component buildDescription() {
        return null;
    }*/
}
