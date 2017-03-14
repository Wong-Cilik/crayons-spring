package com.crayons_2_0.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.maddon.ListContainer;

import com.crayons_2_0.model.Course;
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
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringView(name = AdminView.VIEW_NAME)
@ViewScope
@SpringComponent
public final class AdminView extends VerticalLayout implements View {

	/**
	 * 
	 */

	private @Autowired
	UserService userService;

	private @Autowired
	CourseService courseService;
	
	public static final String VIEW_NAME = "AdminView";
	private ResourceBundle lang = LanguageService.getInstance().getRes();
	private List<UserDisplay> collection;
	private HorizontalLayout root;
	private Table table = new Table();;
	private TempContainer container;

	@PropertyId("name")
	private TextField nameField = new TextField(lang.getString("Name"));
	@PropertyId("title")
	private TextField title = new TextField(lang.getString("Title"));
	@PropertyId("rights")
	private NativeSelect rights = new NativeSelect(lang.getString("Rights"));
	@PropertyId("email")
	private TextField emailField = new TextField(lang.getString("Email"));
	@PropertyId("location")
	private TextField locationField = new TextField(lang.getString("Location"));
	@PropertyId("phone")
	private TextField phoneField = new TextField(lang.getString("Phone"));

	public AdminView() {

	}

	@PostConstruct
	void init() {
		addStyleName("courseDisplay");
		Responsive.makeResponsive(this);
		
		Component table = buildTable();
		Component profileTab = buildProfileTab(null);
		this.setSizeFull();
		this.addComponents(table, profileTab);
		this.setComponentAlignment(table, Alignment.TOP_CENTER);
		this.setComponentAlignment(profileTab, Alignment.BOTTOM_CENTER);
		this.setExpandRatio(table, 1);
		this.setExpandRatio(profileTab, 1);
	}

	private List<UserDisplay> getTableContents() {
		collection = new ArrayList<UserDisplay>();
		for (CrayonsUser tmpUser : userService.findAll()) {
			String permission = "";
			if (tmpUser.getPermission() == 0) {
				permission = lang.getString("admin");
			} else if (tmpUser.getPermission() == 1) {
				permission = lang.getString("Author");
			} else if (tmpUser.getPermission() == 2) {
				permission = lang.getString("Student");
			}
			collection.add(new UserDisplay(tmpUser.getEmail(), tmpUser
					.getFirstName() + " " + tmpUser.getLastName(), permission,
					courseService
							.findAllAuthorCoursesOfUser(tmpUser.getEmail())
							.size(), courseService
							.findAllCoursesOfUser(tmpUser).size()));
		}
		return collection;
	}

	private Table buildTable() {
		container = new TempContainer(getTableContents());

		table.setColumnReorderingAllowed(false);
		table.setWidth("100%");
		table.setPageLength(8);
		table.addStyleName(ValoTheme.TABLE_BORDERLESS);
		table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		table.addStyleName(ValoTheme.TABLE_COMPACT);
		table.setColumnCollapsingAllowed(true);
		table.setContainerDataSource(container);
		table.setVisibleColumns("email", "name", "role", "createdCourses",
				"visitedCourses");
		table.setColumnHeaders(lang.getString("Email"), lang.getString("Name"), 
		        lang.getString("Rights"), lang.getString("CreatedCourses"),
		        lang.getString("AttendedCourses"));
		table.setSortContainerPropertyId("email");
        table.sort();
		table.addItemClickListener(new ItemClickListener() {

			/**
			 * 
			 */

			@Override
			public void itemClick(ItemClickEvent event) {
				UserDisplay userDisplay = (UserDisplay) event.getItemId();
				AdminView ad = (AdminView) event.getComponent().getParent();

				ad.nameField.setReadOnly(false);
				ad.nameField.setValue(userDisplay.getName());
				ad.nameField.setReadOnly(true);

				ad.emailField.setReadOnly(false);
				ad.emailField.setValue(userDisplay.getEmail());
				ad.emailField.setReadOnly(true);

				ad.locationField.setReadOnly(false);
				ad.locationField.setValue("");
				ad.locationField.setReadOnly(true);

				ad.phoneField.setReadOnly(false);
				ad.phoneField.setValue("");
				ad.phoneField.setReadOnly(true);

				ad.rights.setValue(userDisplay.getRole());
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

	private HorizontalLayout buildProfileTab(UserDisplay uD) {
		root = new HorizontalLayout();
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
		root.setExpandRatio(pic, 1);

		VerticalLayout detailsWithButtons = new VerticalLayout();
		root.addComponent(detailsWithButtons);
		root.setExpandRatio(detailsWithButtons, 7);
		
		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		detailsWithButtons.addComponent(details);

		nameField.setValue("");
		nameField.setWidth("100%");
		nameField.setReadOnly(true);
		details.addComponent(nameField);

		title.setValue("");
		title.setWidth("100%");
		title.setReadOnly(true);
		details.addComponent(title);

		rights.addItems(lang.getString("admin"), lang.getString("Author"),
		        lang.getString("Student"));
		rights.setNullSelectionAllowed(false);
		details.addComponent(rights);

		Label section = new Label(lang.getString("ContactInfo"));
		section.addStyleName(ValoTheme.LABEL_H4);
		section.addStyleName(ValoTheme.LABEL_COLORED);
		details.addComponent(section);

		emailField.setValue("");
		emailField.setWidth("100%");
		emailField.setReadOnly(true);
		details.addComponent(emailField);

		locationField.setValue("");
		locationField.setWidth("100%");
		locationField.setReadOnly(true);
		details.addComponent(locationField);

		phoneField.setValue("");
		phoneField.setWidth("100%");
		phoneField.setReadOnly(true);
		details.addComponent(phoneField);
		
		HorizontalLayout controlButtons = new HorizontalLayout();
		controlButtons.setSizeUndefined();
		controlButtons.setSpacing(true);
		detailsWithButtons.addComponent(controlButtons);
		detailsWithButtons.setComponentAlignment(controlButtons, Alignment.MIDDLE_RIGHT);

		Button deleteUser = new Button(lang.getString("DeleteUser"));
		deleteUser.setStyleName(ValoTheme.BUTTON_DANGER);
		controlButtons.addComponent(deleteUser);
		controlButtons.setComponentAlignment(deleteUser, Alignment.MIDDLE_RIGHT);

		deleteUser.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				for (Course tmp : courseService
						.findAllAuthorCoursesOfUser(emailField.getValue())) {
					courseService.removeCourse(tmp);
				}
				for (Course tmp : courseService.findAll()) {
					courseService.removeStudentFromCourse(emailField.getValue(),
					        tmp);
				}
				userService.removeUser(emailField.getValue());
				updateTable();
			}
		});
		
		Button save = new Button(lang.getString("Save"));
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        controlButtons.addComponent(save);
        controlButtons.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
        
        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
                String newRights = rights.getValue().toString();
                switch (newRights) {
                    case "Administrator": newRights = "Admin";
                                          break;
                    case "Schüler": newRights = "Student";
                                    break;
                    case "Autor": newRights = "Author";
                                  break;
                }
                userService.updateRights(emailField.getValue(), newRights);
                updateTable();
            }
        });

		return root;
	}
	
	private void updateTable() {
	    java.lang.Object[] visibleColumns = table.getVisibleColumns();
        container = new TempContainer(getTableContents());
        table.setContainerDataSource(container);
        table.setVisibleColumns(visibleColumns);
        table.sort();
	}
}
