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
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AdminView.VIEW_NAME)
@ViewScope
@SpringComponent
public final class AdminView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	UserService userService;

	@Autowired
	CourseService courseService;

	ResourceBundle lang = LanguageService.getInstance().getRes();
	List<UserDisplay> collection = new ArrayList<UserDisplay>();
	public static final String VIEW_NAME = "AdminView";
	HorizontalLayout root;
	private Table table;
	@PropertyId("name")
	private TextField nameField = new TextField(lang.getString("Name"));
	@PropertyId("title")
	private TextField title = new TextField(lang.getString("Title"));
	@PropertyId("rights")
	private TextField rights = new TextField(lang.getString("Rights"));
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
		addComponent(buildToolbar());

		table = buildTable();
		addComponent(table);
		Responsive.makeResponsive(this);
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		addComponent(content);
		content.addComponent(buildProfileTab(null));
		setComponentAlignment(content, Alignment.BOTTOM_LEFT);

	}

	private Component buildToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);
		return header;
	}

	private Table buildTable() {
		final Table table = new Table();
		table.setSizeFull();
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
			collection.add(new UserDisplay(tmpUser.getEmail(), tmpUser
					.getFirstName() + " " + tmpUser.getLastName(), permission,
					courseService.findAllAuthorCoursesOfUser(tmpUser).size(),
					courseService.findAllCoursesOfUser(tmpUser).size()));
		}
		table.setSortContainerPropertyId("email");
		table.setSortAscending(false);

		table.setContainerDataSource(new TempContainer(collection));
		table.setVisibleColumns("email", "name", "role", "createdCourses",
				"visitedCourses");
		table.setColumnHeaders("eMail", "Name", "Rechte", "Erstellte Kurse",
				"Besuchte Kurse");
		table.setEditable(false);
		table.addItemClickListener(new ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				UserDisplay userDisplay = (UserDisplay) event.getItemId();
				AdminView ad = (AdminView) event.getComponent().getParent();

				ad.nameField.setReadOnly(false);
				ad.nameField.setValue(userDisplay.getName());
				ad.nameField.setReadOnly(true);

				ad.rights.setReadOnly(false);
				ad.rights.setValue(userDisplay.getRole());
				ad.rights.setReadOnly(true);

				ad.emailField.setReadOnly(false);
				ad.emailField.setValue(userDisplay.getEmail());
				ad.emailField.setReadOnly(true);

				ad.locationField.setReadOnly(false);
				ad.locationField.setValue("");
				ad.locationField.setReadOnly(true);

				ad.phoneField.setReadOnly(false);
				ad.phoneField.setValue("");
				ad.phoneField.setReadOnly(true);

			}

		});
		return table;
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

	private class TempContainer extends ListContainer<UserDisplay> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		nameField.setValue("");
		nameField.setWidth("100%");
		nameField.setReadOnly(true);
		details.addComponent(nameField);

		title.setValue("");
		title.setWidth("100%");
		title.setReadOnly(true);
		details.addComponent(title);

		rights.setValue("");
		rights.setWidth("100%");
		rights.setReadOnly(true);
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

		return root;
	}
}