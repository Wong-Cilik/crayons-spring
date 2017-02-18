package com.crayons_2_0.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.component.CourseModificationWindow;
import com.crayons_2_0.component.UnitEditor;
import com.crayons_2_0.component.UnitEditor.CourseEditorListener;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Author library view represents the courses which were created by current
 * user, allows to modify existing and create new courses.
 */
@SuppressWarnings("serial")
@SpringView(name = Authorlibrary.VIEW_NAME)
@SpringComponent
public class Authorlibrary extends VerticalLayout implements View,
		CourseEditorListener {
	@Autowired
	CourseService courseService;

	@Autowired
	CurrentUser c;

	@Autowired
	CurrentCourses currentCourse;

	@Autowired
	UserService userService;

	public static final String VIEW_NAME = "Authorlibrary";
	ResourceBundle lang = LanguageService.getInstance().getRes();
	/*
	 * public Authorlibrary() { VerticalLayout aboutContent = new
	 * VerticalLayout(); //aboutContent.setStyleName("about-content");
	 * 
	 * // you can add Vaadin components in predefined slots in the custom //
	 * layout
	 * 
	 * setSizeFull(); setStyleName("about-view"); setSpacing(true);
	 * setMargin(true);
	 * 
	 * // addComponent(aboutContent); //setComponentAlignment(aboutContent,
	 * Alignment.MIDDLE_CENTER); autorenbereich a = new autorenbereich();
	 * addComponent(a);
	 * 
	 * setExpandRatio(a, 1f); //addComponent(buildFooter()); }
	 */
	private TabSheet tabSheet;
	private Component filter;
	private List<Course> authorCoursesList;

	// TODO bug: author got no course, gets a course he doesnt have

	@PostConstruct
	void init() {
		authorCoursesList = courseService.findAllAuthorCoursesOfUser(c.get());

		VerticalLayout content = new VerticalLayout();
		HorizontalLayout header = new HorizontalLayout();

		setSpacing(true);
		setMargin(false);

		if (Page.getCurrent().getBrowserWindowWidth() > 1024) {
			this.filter = buildFilter();
			header.setSizeFull();
			header.setWidth("100%");
			header.setSpacing(false);
			header.addComponent(buildTitle());
			header.addComponent(this.filter);
			header.setComponentAlignment(this.filter, Alignment.MIDDLE_RIGHT);
			header.setMargin(true);

			content.addComponent(buildCoursesTabSheet());
			addComponent(header);
			addComponent(content);
		} else {
			Panel panel = new Panel();
			panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
			panel.setSizeFull();
			panel.setContent(new Label(lang
					.getString("AuthorLibraryUnavailable")));
			content.addComponent(panel);
			content.setMargin(true);
			addComponent(content);
		}
	}

	public Authorlibrary() {

	}

	// NEU NEU NEU NEU
	/*
	 * AuthorlibraryForm content = new AuthorlibraryForm();
	 * addComponent(content);
	 * 
	 * setSizeFull(); setStyleName("about-view"); setSpacing(true);
	 * setMargin(true);
	 */

	/**
	 * Tab sheet getter.
	 * 
	 * @return tabSheet
	 */
	private TabSheet getTabSheet() {
		return this.tabSheet;
	}

	/**
	 * Builds a title.
	 * 
	 * @return title of the author library
	 */
	private Component buildTitle() {
		Label title = new Label(lang.getString("Overview"));
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	/**
	 * Builds a tab sheet which includes all existing courses and a tab for
	 * creating a new course.
	 * 
	 * @return tab sheet
	 */
	private TabSheet buildCoursesTabSheet() {

		this.tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.setHeight("100%");
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		tabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		tabSheet.addTab(buildAddNewCourseTab());
		for (Course tmpCourse : authorCoursesList) {
			tabSheet.addComponent(buildCourseTab(tmpCourse.getTitle()));
		}
		return tabSheet;
	}

	/**
	 * Builds a tab for creating a new course.
	 * 
	 * @return tab for creating a new course
	 */
	private Component buildAddNewCourseTab() {
		VerticalLayout tabContent = new VerticalLayout();
		tabContent.setIcon(FontAwesome.PLUS);
		tabContent.setSpacing(true);
		tabContent.setMargin(true);
		tabContent.setSizeFull();

		HorizontalLayout courseTitle = new HorizontalLayout();
		courseTitle.setSpacing(true);
		Label courseTitleLabel = new Label();
		courseTitleLabel.addStyleName(ValoTheme.LABEL_H3);
		courseTitleLabel.setValue(lang.getString("CourseTitle"));
		TextField courseTitleField = new TextField();
		courseTitle.addComponents(courseTitleLabel, courseTitleField);
		courseTitle.setComponentAlignment(courseTitleLabel,
				Alignment.MIDDLE_LEFT);
		courseTitle.setComponentAlignment(courseTitleField,
				Alignment.MIDDLE_LEFT);
		// courseTitleField.addValueChangeListener();
		tabContent.addComponent(courseTitle);
		// courseTitleField.setImmediate(true);

		VerticalLayout couseDescription = new VerticalLayout();
		couseDescription.setSizeFull();
		Label couseDescriptionLabel = new Label();
		couseDescriptionLabel.addStyleName(ValoTheme.LABEL_H3);
		couseDescriptionLabel.setValue(lang.getString("CourseDescription"));
		TextField couseDescriptionField = new TextField();
		couseDescriptionField.setSizeFull();
		couseDescription.addComponents(couseDescriptionLabel,
				couseDescriptionField);
		couseDescription.setSizeFull();
		// couseDescriptionField.addValueChangeListener();
		tabContent.addComponent(couseDescription);

		Button createCourse = new Button(lang.getString("CreateCourse"));
		tabContent.addComponent(createCourse);
		tabContent.setComponentAlignment(createCourse, Alignment.MIDDLE_CENTER);

		createCourse.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				courseService.insertCourse(new Course(courseTitleField
						.getValue(), couseDescriptionField.getValue(), c.get(),
						""));
				courseService.saveDummyGraph(courseTitleField.getValue());
				String title = (String) courseTitleField.getValue();
				Component newTab = buildCourseTab(title);
				getTabSheet().addComponent(newTab);
				getTabSheet().setSelectedTab(newTab);
				courseTitleField.clear();
				couseDescriptionField.clear();
			}
		});

		return tabContent;
	}

	/**
	 * Builds a tab of a specific course.
	 * 
	 * @param title
	 *            title of the course
	 * @return course tab
	 */
	private Component buildCourseTab(String title) {
		VerticalLayout tabContent = new VerticalLayout();
		tabContent.setCaption(title);
		tabContent.setSpacing(true);
		tabContent.setMargin(true);

		TwinColSelect selectStudents = new TwinColSelect();
		selectStudents.setMultiSelect(true);
		selectStudents.setCaptionAsHtml(true);
		selectStudents.setCaption("<h3>" + lang.getString("SelectParticipants")
				+ "</h3>");

		selectStudents.setRows(10);
		selectStudents.setSizeFull();
		selectStudents
				.setLeftColumnCaption(lang.getString("ListOfAllStudents"));
		selectStudents.setRightColumnCaption(lang.getString("Participants"));
		// adding all users to the select Student Table
		List<CrayonsUser> allUsers = userService.findAll();
		String[] emailOfStudentsInCourse = courseService.getStudents(title);
		List<String> rightColumn = new ArrayList<String>();
		if (emailOfStudentsInCourse != null) {
			for (int i = 0; i < allUsers.size(); i++) {
				for (int j = 1; j < emailOfStudentsInCourse.length; j++) {
					if (!emailOfStudentsInCourse[j].equals(allUsers.get(i)
							.getEmail())) {
						selectStudents.addItems(allUsers.get(i).getEmail());
					} else {
						rightColumn.add(allUsers.get(i).getEmail());
					}
				}
			}
		} else {
			for (int i = 0; i < allUsers.size(); i++) {
				selectStudents.addItems(allUsers.get(i).getEmail());
			}
		}
		selectStudents.setValue(rightColumn);

		Button saveStudents = new Button(lang.getString("SaveStudents"));
		saveStudents.addClickListener(new ClickListener() {

			// TODO known bugg: after adding yourself to a course need to update
			// the userlibrary
			@Override
			public void buttonClick(ClickEvent event) {
				@SuppressWarnings("unchecked")
				Collection<String> tmp = (Collection<String>) selectStudents
						.getValue();
				courseService.insertStudent(tmp.toArray(new String[0]), title);
			}
		});

		selectStudents.setImmediate(true);
		tabContent.addComponent(selectStudents);
		tabContent.addComponent(saveStudents);
		Component controlButtons = buildControlButtons(tabContent, title);
		tabContent.addComponent(controlButtons);
		tabContent.setComponentAlignment(controlButtons,
				Alignment.BOTTOM_CENTER);

		return tabContent;
	}

	/**
	 * Builds a layout with the control buttons which allows modify a course.
	 * 
	 * @param tab
	 *            course tab
	 * @param title
	 *            course title
	 * @return layout with the control buttons
	 */
	private Component buildControlButtons(Component tab, String title) {
		HorizontalLayout controlButtons = new HorizontalLayout();
		controlButtons.setMargin(true);
		controlButtons.setSpacing(true);

		Button deleteCourse = new Button(lang.getString("DeleteCourse"));
		controlButtons.addComponent(deleteCourse);
		controlButtons.setComponentAlignment(deleteCourse,
				Alignment.BOTTOM_RIGHT);
		deleteCourse.addClickListener(new ClickListener() {

			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				courseService.removeCourse(courseService
						.findCourseByTitle(title));
				getTabSheet().removeTab(
						getTabSheet().getTab(getTabSheet().getSelectedTab()));
				// TODO notification
			}

		});

		Button modifyCourse = new Button(lang.getString("ModifyCourse"));
		controlButtons.addComponent(modifyCourse);
		modifyCourse.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(
						new CourseModificationWindow(courseService
								.findCourseByTitle(title), tab, tabSheet));
			}
		});

		Button graphEditor = new Button(lang.getString("GraphEditor"));
		graphEditor.setStyleName(ValoTheme.BUTTON_PRIMARY);
		controlButtons.addComponent(graphEditor);
		graphEditor.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				currentCourse.setTitle(title);
				CourseEditorView.refreshGraph(courseService
						.getCourseData(title));
				UI.getCurrent().getNavigator()
						.navigateTo(CourseEditorView.VIEW_NAME);
			}
		});

		return controlButtons;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@Override
	public void titleChanged(String newTitle, UnitEditor editor) {
		// TODO auto generated
	}

	/**
	 * Builds a filter to search for a course by name.
	 * 
	 * @return layout with search field
	 */
	public Component buildFilter() {
		VerticalLayout search = new VerticalLayout();
		search.setStyleName("search");
		search.setMargin(false);
		search.setSizeUndefined();

		final TextField filter = new TextField();
		filter.addTextChangeListener(new TextChangeListener() {
			/**
			 * 
			 */

			@Override
			public void textChange(final TextChangeEvent event) {
				TabSheet tabs = getTabSheet();
				Iterator<Component> it = tabs.iterator();
				Component comp;
				if (event.getText().equals("")) {
					while (it.hasNext()) {
						comp = it.next();
						tabs.getTab(comp).setVisible(true);
					}
				} else {
					while (it.hasNext()) {
						comp = it.next();
						if (comp.getCaption() == null) {
							tabs.getTab(comp).setVisible(true);
						} else {
							if (comp.getCaption().toLowerCase()
									.contains(event.getText().toLowerCase())) {
								tabs.getTab(comp).setVisible(true);
							} else {
								tabs.getTab(comp).setVisible(false);
							}
						}
					}
				}
			}
		});
		filter.setInputPrompt(lang.getString("Search"));
		filter.setIcon(FontAwesome.SEARCH);
		filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filter.setSizeUndefined();
		search.addComponent(filter);
		search.setComponentAlignment(filter, Alignment.MIDDLE_RIGHT);
		return search;
	}
}
