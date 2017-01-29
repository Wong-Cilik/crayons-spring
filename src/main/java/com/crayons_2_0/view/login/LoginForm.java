package com.crayons_2_0.view.login;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.AddNewUserListener;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;

/**
 * @author Ondrej Kvasnovsky
 */
public class LoginForm extends VerticalLayout {

    /**
     * 
     */
	
    private ResourceBundle lang = LanguageService.getInstance().getRes();
    private TextField txtLogin = new TextField(lang.getString("Login") + ": ");
    private PasswordField txtPassword = new PasswordField(lang.getString("Password") + ": ");
    private Button btnLogin = new Button(lang.getString("Login"));
    private Button btnRegistrate = new Button("Registrieren!!");  //Todo Sprache

    public LoginForm() {
        addComponent(txtLogin);
        addComponent(txtPassword);
        addComponent(btnLogin);
        addComponent(btnRegistrate);

        LoginFormListener loginFormListener = getLoginFormListener();
        btnLogin.addClickListener(loginFormListener);
        
        createRegisterButton();
    }
    
    /**
     * Create Button for registrate new User
     */
    private void createRegisterButton() {
		btnRegistrate.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Window window = new Window("Registrate User");
				VerticalLayout content = new VerticalLayout();
				
				content.addComponent(new Label("eMail:"));
				TextField textFieldEMail = new TextField(); 
				content.addComponent(textFieldEMail);
				
				content.addComponent(new Label(""));
				
				content.addComponent(new Label("passowrd"));
				TextField textFieldPassoword = new TextField();
				content.addComponent(textFieldPassoword);
				
				
				
				Button btnInsertUser = new Button("Insert/Save User");
				btnInsertUser.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String eMail = textFieldEMail.getValue();
						String password = textFieldPassoword.getValue();
				        String firstName = "firstName";
				        String lastName = "lastName";
				        
				        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				        authorities.add(new SimpleGrantedAuthority("CLIENT"));
				        CrayonsUser user = new CrayonsUser(firstName, lastName, eMail, password, true, true, false, false, authorities);
				        
				        UserService userService = new UserService();
				        userService.insertUser(user);
						
					}
				});
				content.addComponent(btnInsertUser);
				
				window.setContent(content);
				window.setSizeFull();
				
				UI.getCurrent().addWindow(window);
				
				
			}
			
			
		});
		
	}

	public LoginFormListener getLoginFormListener() {
        MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(LoginFormListener.class);
    }

    public TextField getTxtLogin() {
        return txtLogin;
    }

    public PasswordField getTxtPassword() {
        return txtPassword;
    }
}
