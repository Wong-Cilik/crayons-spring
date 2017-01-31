package com.crayons_2_0.component;

import com.crayons_2_0.view.Uniteditor;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class SelectUnitForEditWindow extends Window {
    public SelectUnitForEditWindow() {
        setSizeFull();
        setModal(true);
        setResizable(false);
        setClosable(true); 
        setHeight(40.0f, Unit.PERCENTAGE);
        setWidth(40.0f, Unit.PERCENTAGE);
        
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(true);
        setContent(content);
        
        Component title = buildTitle();
        content.addComponent(title);
        content.setComponentAlignment(title, Alignment.TOP_CENTER);
        
        //content.addComponent(buildDescription());
        
        Component unitChoise = buildUnitChoice();
        content.addComponent(unitChoise);
        content.setComponentAlignment(unitChoise, Alignment.MIDDLE_LEFT);
        
        Component footer = buildFooter();
        content.addComponent(footer);
        content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
    }
    
    private Component buildUnitChoice() {
        ComboBox selectUnit = new ComboBox("Select the unit for edit");
        selectUnit.addItem("Node 1");
        selectUnit.addItem("Node 2");
        return selectUnit;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Edit");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
                UI.getCurrent().getNavigator().navigateTo(Uniteditor.VIEW_NAME);

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_CENTER);
        return footer;
    }

    private Component buildTitle() {
        Label title = new Label("Open the unit editor");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    /*private Component buildDescription() {
        return null;
    }*/
}
