package com.crayons_2_0.service.database;



import com.crayons_2_0.MyUI;
import com.crayons_2_0.view.AboutView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AddNewUserListener implements Button.ClickListener {

	@Override
	public void buttonClick(ClickEvent event) {
		AboutView view = (AboutView) event.getButton().getParent();

        MyUI current = (MyUI) (UI.getCurrent());
        ApplicationContext context = current.getApplicationContext();

        UserDAO userDAO = context.getBean(UserDAO.class);

        TextField txtUserEMail = view.getTxtUserLabel();
        String value = txtUserEMail.getValue();
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("CLIENT"));
        User user = new User(value, "pass", true, true, false, false, authorities);;
        
        userDAO.save(user);

        current.getNavigator().navigateTo("users"); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
		


}
