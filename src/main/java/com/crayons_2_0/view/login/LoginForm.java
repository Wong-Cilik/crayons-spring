package com.crayons_2_0.view.login;

import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.controller.RegisterFormListener;
import com.crayons_2_0.controller.RegisterFormListener2;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginForm extends VerticalLayout {

    /**
     * 
     */
	
	
    private ResourceBundle lang = LanguageService.getInstance().getRes();
    private TextField txtLogin = new TextField(lang.getString("Login") + ": ");
    private PasswordField txtPassword = new PasswordField(lang.getString("Password") + ": ");
    private Button btnLogin = new Button(lang.getString("Login"));
    // Registrate
    private Button btnRegistrate = new Button("Register");  //Todo Sprache
    private Button btnRegistrate2 = new Button("Registrieren 2!");
   //Todo Sprache
   
	private TextField textFieldPassoword = new TextField();

    public LoginForm() {
        addComponent(txtLogin);
        addComponent(txtPassword);
        addComponent(btnLogin);
        addComponent(btnRegistrate);
        addComponent(btnRegistrate2);

        LoginFormListener loginFormListener = getLoginFormListener();
        btnLogin.addClickListener(loginFormListener);
        btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        RegisterFormListener registerFormListener = getRegisterFormListener();
        createRegisterButton();
        
        RegisterFormListener2 registerFormListener2 = getRegisterFormListener2();
        btnRegistrate2.addClickListener(registerFormListener2);
        
        
    }
    
    

	/**
     * Create Button for registrate new User
     */
    private void createRegisterButton() {
		btnRegistrate.addClickListener(new ClickListener() {
		private RegisterWindow window = new RegisterWindow();
			@Override
			public void buttonClick(ClickEvent event) {	
				UI.getCurrent().addWindow(window);
				
			}
		});
		
		
	}

	public LoginFormListener getLoginFormListener() {
        MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(LoginFormListener.class);
    }
	
	private RegisterFormListener getRegisterFormListener() {
		MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(RegisterFormListener.class);
	}
	
	private RegisterFormListener2 getRegisterFormListener2() {
		MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(RegisterFormListener2.class);
	}

    public TextField getTxtLogin() {
        return txtLogin;
    }

    public PasswordField getTxtPassword() {
        return txtPassword;
    }
    
    private TextField textFieldEMail = new TextField();
    public TextField getTextFieldEMail() {
		return textFieldEMail;
	}



	public void setTextFieldEMail(TextField textFieldEMail) {
		this.textFieldEMail = textFieldEMail;
	}



	public TextField getTextFieldPassoword() {
		return textFieldPassoword;
	}



	public void setTextFieldPassoword(TextField textFieldPassoword) {
		this.textFieldPassoword = textFieldPassoword;
	}
}
