package com.example.view;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.MyUI;
import com.example.db.UserService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
@SpringUI
public class MainView extends VerticalLayout  {

   
    @Resource
    UserService userService2;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MainView(MyUI ui) {
     
        this.mainViewBuilder();
    }
    
    private void mainViewBuilder() {
      TextField field = new TextField();
      final TextField field2 = new TextField();
      field.setCaption("vField");
      field2.setCaption(userService2.testService());
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      Button button = new Button("vClick");
      Button button2 = new Button("hClick2");

      button.addClickListener(e -> Notification.show("Hallo lambda"));

      HorizontalLayout hlayout = new HorizontalLayout();
      hlayout.setMargin(true);
      hlayout.addComponents(field2, button2);
      hlayout.setComponentAlignment(button2, Alignment.BOTTOM_RIGHT);

      addComponents(hlayout);
    }
}
