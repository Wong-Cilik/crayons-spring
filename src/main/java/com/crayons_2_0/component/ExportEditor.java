package com.crayons_2_0.component;

import java.util.ResourceBundle;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Component;

public class ExportEditor   {

    public ExportEditor() {
        // TODO Auto-generated constructor stub
    }

    public Component ExportButton(){
        
        Button export = new Button("Export");
        export.addStyleName(ValoTheme.BUTTON_PRIMARY);
        export.addClickListener(new ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                for (Window window : UI.getCurrent().getWindows())
                    window.close();
                Notification success = new Notification("file is exported successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
                
            }
        });
        return export;
        
    }
    
}
