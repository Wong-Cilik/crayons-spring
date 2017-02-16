package com.crayons_2_0.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.maddon.ListContainer;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.UserDisplay;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AdminView.VIEW_NAME)
@ViewScope
@SpringComponent
public final class AdminView extends VerticalLayout implements View {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CourseService courseService;
	
	ResourceBundle lang = LanguageService.getInstance().getRes();
    List<UserDisplay> collection = new ArrayList<UserDisplay>();
    public static final String VIEW_NAME = "AdminView";
    private Table table;
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
    
    
    public AdminView() {

    }

    @PostConstruct
    void init(){
        addStyleName("courseDisplay");
        addComponent(buildToolbar());

        table = buildTable();
        addComponent(table);
        table.setCaption("select a student");
        setComponentAlignment(table, Alignment.TOP_LEFT);
        Responsive.makeResponsive(this);
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        addComponent(content);
        content.addComponent(buildProfileTab());
        setComponentAlignment(content, Alignment.BOTTOM_LEFT);
    }
    
    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);
        return header;
    }

	@SuppressWarnings("deprecation")
	private Table buildTable() {
		setSizeFull();
        final Table table = new Table();
        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);

        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        
        for (CrayonsUser tmpUser : userService.findAll()) {
        	String permission = "Fehler beim ermitteln der Rechte";
        	if (tmpUser.getPermission() == 2) {
        		permission = "Admin";
        	} else if (tmpUser.getPermission() == 1) {
        		permission = "Autor";
        	} else if (tmpUser.getPermission() == 0) {
        		permission = "Schüler";
        	}
        	collection.add(new UserDisplay(tmpUser.getEmail(), tmpUser.getFirstName() + " " + tmpUser.getLastName(), permission, courseService.findAllAuthorCoursesOfUser(tmpUser).size(), courseService.findAllCoursesOfUser(tmpUser).size()));
        }
        table.setSortContainerPropertyId("email");
        table.setSortAscending(false);

        table.setContainerDataSource(new TempContainer(collection));
        table.setVisibleColumns("email", "name", "role", "createdCourses", "visitedCourses");
        table.setColumnHeaders("eMail", "Name", "Rolle", "Erstellte Kurse", "Besuchte Kurse");
        table.addItemClickListener(new ItemClickListener(){
			@Override
			public void itemClick(ItemClickEvent event) {
				UserDisplay userDisplay = (UserDisplay)event.getItemId();
				System.out.println(userDisplay.getName());
				System.out.println(event.getComponent());
				System.out.println(event.getButton().getName());
			}

		});
		return table;
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

	private class TempContainer extends ListContainer<UserDisplay> {

		public TempContainer(final Collection<UserDisplay> collection) {
			super(collection);
		}

	}
	
	    private Component buildProfileTab() {
	        HorizontalLayout root = new HorizontalLayout();
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
	        firstNameField.setValue("ghsrdhs");
	        details.addComponent(firstNameField);
	        lastNameField = new TextField(lang.getString("LastName"));
	        lastNameField.setValue("dgtjfdzj");
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
	        emailField.setValue("fzhjkfzjk");
	        
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
}