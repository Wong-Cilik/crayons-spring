package com.crayons_2_0.view;


import java.util.ResourceBundle;

import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageControl;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

//import org.apache.catalina.realm.JNDIRealm.User;

//import com.vaadin.data.fieldgroup.BeanFieldGroup;
//import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
//import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
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
//import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringUI
public class Preferences extends VerticalLayout implements View {
	

    //public static final String ID = "profilepreferenceswindow";
    public static final String VIEW_NAME = "Preferences";
    ResourceBundle lang = LanguageControl.getInstance().getRes();

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
    /*@PropertyId("newsletterSubscription")
    private OptionalSelect<Integer> newsletterField;
    @PropertyId("website")*/
    private TextField websiteField;
    @PropertyId("bio")
    private TextArea bioField;
    
    //public Preferences(final User user)
    public Preferences() {
        //addStyleName("profile-window");
        //setId(ID);
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
        fieldGroup = new BeanFieldGroup<User>(User.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);
        */
    }

    private Component buildPreferencesTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption(lang.getString("Preferences"));
        root.setIcon(FontAwesome.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();
        
        ComboBox selectLanguage = new ComboBox(lang.getString("SelectYourLanguage"));
        selectLanguage.setInputPrompt(LanguageControl.getInstance().getLanguage().toString());
        selectLanguage.addItem(Language.German.toString());
        selectLanguage.addItem(Language.English.toString());
        selectLanguage.addValueChangeListener(new ValueChangeListener() {
			
        	// ToDO Makeup Hardcoded. 
        	
			@Override
			public void valueChange(ValueChangeEvent event) {
				Language newLanguage;
				String value = selectLanguage.getValue().toString();
				if (value.equals(Language.German.toString())) {
					newLanguage = Language.German;
				} else if (value.equals(Language.English.toString())) {
					newLanguage = Language.English;
				} else {
					newLanguage = null;
				}
				LanguageControl.getInstance().setCurrentLocale(newLanguage);
				Page.getCurrent().reload();
				Notification.show(lang.getString("LanguageChangedTo") + ": " + value);
				
			}
		});
        root.addComponent(selectLanguage);

        Label message = new Label(lang.getString("NotImplementedInThisDemo"));
        message.setSizeUndefined();
        message.addStyleName(ValoTheme.LABEL_LIGHT);
        root.addComponent(message);
        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

        return root;
    }

    private Component buildProfileTab() {
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

        Button upload = new Button(lang.getString("Change…"), new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show(lang.getString("NotImplementedInThisDemo"));
            }
        });
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        pic.addComponent(upload);

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        firstNameField = new TextField(lang.getString("FirstName"));
        details.addComponent(firstNameField);
        lastNameField = new TextField(lang.getString("LastName"));
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
        emailField.setWidth("100%");
        emailField.setRequired(true);
        emailField.setNullRepresentation("");
        details.addComponent(emailField);

        locationField = new TextField(lang.getString("Location"));
        locationField.setWidth("100%");
        locationField.setNullRepresentation("");
        locationField.setComponentError(new UserError(
                lang.getString("ThisAddressDoesn'tExist")));
        details.addComponent(locationField);

        phoneField = new TextField(lang.getString("Phone"));
        phoneField.setWidth("100%");
        phoneField.setNullRepresentation("");
        details.addComponent(phoneField);
        /*
        newsletterField = new OptionalSelect<Integer>();
        newsletterField.addOption(0, "Daily");
        newsletterField.addOption(1, "Weekly");
        newsletterField.addOption(2, "Monthly");
        details.addComponent(newsletterField);
        */

        section = new Label(lang.getString("AdditionalInfo"));
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        websiteField = new TextField(lang.getString("Website"));
        websiteField.setInputPrompt("http://");
        websiteField.setWidth("100%");
        websiteField.setNullRepresentation("");
        details.addComponent(websiteField);

        bioField = new TextArea(lang.getString("Bio"));
        bioField.setWidth("100%");
        bioField.setRows(4);
        bioField.setNullRepresentation("");
        details.addComponent(bioField);

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button(lang.getString("OK"));
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                
                    //fieldGroup.commit();
                    // Updated user should also be persisted to database. But
                    // not in this demo.

                    Notification success = new Notification(
                            lang.getString("ProfileUpdatedSuccessfully"));
                    success.setDelayMsec(2000);
                    success.setStyleName("barSuccessSmall");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());

                    //DashboardEventBus.post(new ProfileUpdatedEvent());
               
                /*try {
                    //fieldGroup.commit();
                    // Updated user should also be persisted to database. But
                    // not in this demo.

                    Notification success = new Notification(
                            "Profile updated successfully");
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());

                    //DashboardEventBus.post(new ProfileUpdatedEvent());
                    close();
                } catch (CommitException e) {
                    Notification.show("Error while updating profile",
                            Type.ERROR_MESSAGE);
                }*/

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }
    @Override
    public void enter(ViewChangeEvent event) {
    }


}
