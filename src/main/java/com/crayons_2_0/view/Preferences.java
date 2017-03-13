package com.crayons_2_0.view;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

// @PreserveOnRefresh
@SuppressWarnings("serial")
@SpringView(name = Preferences.VIEW_NAME)
@ViewScope
@SpringComponent
public class Preferences extends VerticalLayout implements View {

	/**
	 * 
	 */

	// public static final String ID = "profilepreferenceswindow";
	public static final String VIEW_NAME = "Preferences";
	private ResourceBundle lang = LanguageService.getInstance().getRes();
	private @Autowired
	UserService userService;

	// private final BeanFieldGroup<User> fieldGroup;
	/*
	 * Fields for editing the User object are defined here as class members.
	 * They are later bound to a FieldGroup by calling
	 * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
	 * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
	 * the fields with the user object.
	 */
	@PropertyId("firstName")
	private TextField firstNameField;
	@PropertyId("lastName")
	private TextField lastNameField;
	@PropertyId("title")
	private ComboBox titleField;
	@PropertyId("male")
	private OptionGroup sexField;
	@PropertyId("email")
	private TextField emailField;
	@PropertyId("location")
	private TextField locationField;
	@PropertyId("phone")
	private TextField phoneField;
	/*
	 * @PropertyId("newsletterSubscription") private OptionalSelect<Integer>
	 * newsletterField;
	 */
	@PropertyId("newpassword")
	private TextField newPassword;
	@PropertyId("newpasswordconfirmation")
	private TextField newPasswordConfirmation;

	public Preferences() {

	}

	@PostConstruct
	void init() {

		// addStyleName("profile-window");
		// setId(ID);
		Responsive.makeResponsive(this);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		addComponent(content);

		TabSheet detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		detailsWrapper.addComponent(buildProfileTab());
		detailsWrapper.addComponent(buildPreferencesTab());

		content.addComponent(buildFooter());
		/*
		 * fieldGroup = new BeanFieldGroup<User>(User.class);
		 * fieldGroup.bindMemberFields(this);
		 * fieldGroup.setItemDataSource(user);
		 */
	}
	
	private Component buildPreferencesTab() {
		VerticalLayout root = new VerticalLayout();
		root.setCaption(lang.getString("Preferences"));
		root.setIcon(FontAwesome.COGS);
		root.setSpacing(true);
		root.setMargin(true);
		root.setSizeFull();

		ComboBox selectLanguage = new ComboBox(
				lang.getString("SelectYourLanguage"));
		selectLanguage.setNullSelectionAllowed(false);
		selectLanguage.addItem(lang.getString("German"));
		selectLanguage.addItem(lang.getString("English"));
		selectLanguage.setValue(lang.getString(LanguageService.getInstance()
                .getLanguage().toString()));
		selectLanguage.addValueChangeListener(new ValueChangeListener() {

			// ToDO Makeup Hardcoded.

			/**
			 * 
			 */

			@Override
			public void valueChange(ValueChangeEvent event) {
				Language newLanguage;
				String value = selectLanguage.getValue().toString();

				if (value.equals(lang.getString("German"))) {
					newLanguage = Language.German;
				} else if (value.equals(lang.getString("English"))) {
					newLanguage = Language.English;
				} else {
					// newLanguage = null;
					throw new IllegalArgumentException(
							"Language not Implemented");
				}

				CurrentUser.getInstance().getUser().setLanguage(newLanguage); // ??

				if (userService.updateUserLanguage(CurrentUser.getInstance()
						.getUser(), newLanguage)) {
					Notification success = new Notification(lang
							.getString("ProfileUpdatedSuccessfully"));
					success.setDelayMsec(2000);
					success.setStyleName("barSuccessSmall");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());

					LanguageService.getInstance().setCurrentLocale(newLanguage);

					Page.getCurrent().reload();

					// Problem: Logout - Soloution maybe:
					// https://vaadin.com/forum#!/thread/11317960
					// http://stackoverflow.com/questions/23612615/preserveonrefresh-purpose-and-need
					// https://vaadin.com/tutorial/declarative

					Notification.show(lang.getString("LanguageChangedTo")
							+ ": " + value);

				}

			}
		});
		root.addComponent(selectLanguage);
		return root;
	}

	private Component buildProfileTab() {
		CurrentUser.getInstance().setUser(
				userService.findByEMail(CurrentUser.getInstance().geteMail()));
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption(lang.getString("Profile"));
		root.setIcon(FontAwesome.USER);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addStyleName("profile-form");

		VerticalLayout pic = new VerticalLayout();
		pic.setSizeUndefined();
		pic.setSpacing(true);
		Image profilePic = new Image(null, new ThemeResource(
				"img/profile-pic-300px.jpg"));
		profilePic.setWidth(100.0f, Unit.PIXELS);
		pic.addComponent(profilePic);
		root.addComponent(pic);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		firstNameField = new TextField(lang.getString("FirstName"));
		firstNameField.setValue(CurrentUser.getInstance().getUser()
				.getFirstName());
		details.addComponent(firstNameField);
		lastNameField = new TextField(lang.getString("LastName"));
		lastNameField.setValue(CurrentUser.getInstance().getUser()
				.getLastName());
		details.addComponent(lastNameField);
		titleField = new ComboBox(lang.getString("Title"));
		titleField.setInputPrompt(lang.getString("PleaseSpecify"));
		titleField.addItem(lang.getString("Mr."));
		titleField.addItem(lang.getString("Mrs."));
		titleField.addItem(lang.getString("Ms."));
		titleField.setNewItemsAllowed(true);
		details.addComponent(titleField);

		sexField = new OptionGroup(lang.getString("Sex"));
		sexField.addItem(Boolean.FALSE);
		sexField.setItemCaption(Boolean.FALSE, lang.getString("Female"));
		sexField.addItem(Boolean.TRUE);
		sexField.setItemCaption(Boolean.TRUE, lang.getString("Male"));
		sexField.addStyleName("horizontal");
		details.addComponent(sexField);

		Label section = new Label(lang.getString("ContactInfo"));
		section.addStyleName(ValoTheme.LABEL_H4);
		section.addStyleName(ValoTheme.LABEL_COLORED);
		details.addComponent(section);

		emailField = new TextField(lang.getString("Email"));
		emailField.setValue(CurrentUser.getInstance().getUser().getEmail());
		emailField.setReadOnly(true);

		emailField.setWidth("100%");
		emailField.setNullRepresentation("");
		details.addComponent(emailField);

		locationField = new TextField(lang.getString("Location"));
		locationField.setWidth("100%");
		locationField.setNullRepresentation("");
		details.addComponent(locationField);

		phoneField = new TextField(lang.getString("Phone"));
		phoneField.setWidth("100%");
		phoneField.setNullRepresentation("");
		details.addComponent(phoneField);
		/*
		 * newsletterField = new OptionalSelect<Integer>();
		 * newsletterField.addOption(0, "Daily"); newsletterField.addOption(1,
		 * "Weekly"); newsletterField.addOption(2, "Monthly");
		 * details.addComponent(newsletterField);
		 */

		section = new Label(lang.getString("PWChange"));
		section.addStyleName(ValoTheme.LABEL_H4);
		section.addStyleName(ValoTheme.LABEL_COLORED);
		details.addComponent(section);

		newPassword = new TextField(lang.getString("NewPassword"));
		newPassword.setWidth("100%");
		newPassword.setNullRepresentation("");
		details.addComponent(newPassword);
		
		newPasswordConfirmation = new TextField(lang.getString("NewPasswordConfirmation"));
		newPasswordConfirmation.setWidth("100%");
		newPasswordConfirmation.setNullRepresentation("");
		details.addComponent(newPasswordConfirmation);

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button(lang.getString("Save"));
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				if(newPassword.getValue().length() != 0 && (!newPassword.getValue().equals(newPasswordConfirmation.getValue()) 
						|| newPassword.getValue().length() < 8)) {
					notifPWMatchesNot();
					System.out.println("im 1");
						
				} else if (CurrentUser.getInstance().getUser().getUsername() != emailField
						.getValue()
						|| CurrentUser.getInstance().getUser().getFirstName() != firstNameField
								.getValue()
						|| CurrentUser.getInstance().getUser().getLastName() != lastNameField
								.getValue()
						|| newPassword.getValue().length() == 0) {
					System.out.println("im richtigem ändern");
					if (userService.updateUser(CurrentUser.getInstance()
							.getUser(), emailField.getValue(), firstNameField
							.getValue(), lastNameField.getValue(), CurrentUser.getInstance().getUser().getPassword()
							)) {
						notifSuccesProfileChange();
				} else if ((CurrentUser.getInstance().getUser().getUsername() != emailField
						.getValue()
						|| CurrentUser.getInstance().getUser().getFirstName() != firstNameField
								.getValue()
						|| CurrentUser.getInstance().getUser().getLastName() != lastNameField
								.getValue())) {
					System.out.println("im 3");
					if (userService.updateUser(CurrentUser.getInstance()
							.getUser(), emailField.getValue(), firstNameField
							.getValue(), lastNameField.getValue(), newPassword.getValue()
							)) {
						notifSuccesProfileChange();
					}
				}
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		return footer;
	}
	
	public void notifSuccesProfileChange() {
		Notification success = new Notification(lang
				.getString("ProfileUpdatedSuccessfully"));
		success.setDelayMsec(2000);
		success.setStyleName("barSuccessSmall");
		success.setPosition(Position.BOTTOM_CENTER);
		success.show(Page.getCurrent());
	}
	
	public void notifPWMatchesNot() {
		Notification failure = new Notification(lang
				.getString("PWsNeedsToBeSameOrGreater8"));
		failure.setDelayMsec(5000);
		failure.setPosition(Position.BOTTOM_CENTER);
		failure.show(Page.getCurrent());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		newPassword.setValue("");
		newPasswordConfirmation.setValue("");
	}

}
