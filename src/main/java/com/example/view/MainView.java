package com.example.view;

import javax.annotation.PostConstruct; 
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.MyUI;
import com.example.db.UserService;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
@ViewScope
@SpringView(name = MainView.VIEW_NAME)

public class MainView extends VerticalLayout  implements View{

   
    public static final String VIEW_NAME = "mainView";
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private UserService userService2;
    @PostConstruct
    void init() {
        this.mainViewBuilder(); 
        
    }
    private void mainViewBuilder() {
        
        addComponent(new Button("Navigate to user view", event -> {
            MyUI.getCurrent().getNavigator().navigateTo("userView");
        })); 
        
        
      TextField field = new TextField();
      final TextField field2 = new TextField();
      field.setCaption("vField");
      field2.setCaption(userService2.findUserByMail("user").getRole());
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      Button button = new Button("vClick");
      Button button2 = new Button("hClick2");

      button.addClickListener(e -> Notification.show("Hallo lambda"));

      HorizontalLayout hlayout = new HorizontalLayout();
      hlayout.setMargin(true);
      hlayout.addComponents(field2, button2);
      hlayout.setComponentAlignment(button2, Alignment.BOTTOM_RIGHT);
      addComponents(hlayout,new RegisterButton(),(new Button("Logout", event -> logout())));
    }

    private void logout() {
        MyUI.get().getPage().reload();
        MyUI.get().getSession().close();
        
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}
