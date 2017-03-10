package com.crayons_2_0.view.login;

import java.util.ResourceBundle; 

import org.springframework.context.ApplicationContext;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.controller.RegisterFormListener;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginForm extends VerticalLayout {

	private ResourceBundle lang = LanguageService.getInstance().getRes();
	private TextField txtLogin = new TextField(lang.getString("Login") + ": ");
	private PasswordField txtPassword = new PasswordField(
			lang.getString("Password") + ": ");
	private Button btnLogin = new Button(lang.getString("Login"));
	private Button btnRegistrate = new Button(lang.getString("Register"));
	private TextField textFieldPassoword = new TextField();
	
	
	
	public LoginForm() {
	    addStyleName("login-panel");
	    setSpacing(true);
	    addComponent(buildLabels());
	    txtLogin.setIcon(FontAwesome.USER);
        txtLogin.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        txtPassword.setIcon(FontAwesome.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnLogin.setClickShortcut(KeyCode.ENTER);
        btnLogin.focus();
        btnLogin.addClickListener(getLoginFormListener());
        
        btnRegistrate.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnRegistrate.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("registerView");
            }
        });
		addComponents(txtLogin,txtPassword,btnLogin,btnRegistrate);
		//LoginFormListener loginFormListener = getLoginFormListener();
		

		getRegisterFormListener();
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
	private Component buildLabels() {
        HorizontalLayout labels = new HorizontalLayout();
        labels.addStyleName("labels");
        labels.setSpacing(true);

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("Crayons 2.0");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
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
