package com.crayons_2_0.component;

import java.util.ResourceBundle;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class ImportEditor  {

    public ImportEditor() {
        // TODO Auto-generated constructor stub
    }

    
public Component ImportButton(){
        
        Button import_ = new Button("Import");
        import_.addStyleName(ValoTheme.BUTTON_PRIMARY);
        import_.addClickListener(new ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                for (Window window : UI.getCurrent().getWindows())
                    window.close();
                Notification success = new Notification("file is imported successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
                
            }
        });
        return import_;
        
    }
}
