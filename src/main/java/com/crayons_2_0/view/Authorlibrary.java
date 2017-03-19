package com.crayons_2_0.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.authentication.CurrentGraph;
import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.component.CourseModificationWindow;
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
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
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
public class Authorlibrary extends VerticalLayout implements View {
	private @Autowired CourseService courseService;
	private @Autowired UserService userService;

	public static final String VIEW_NAME = "Authorlibrary";
	private ResourceBundle lang = LanguageService.getInstance().getRes();
	private TabSheet tabSheet;
	private Component filter;
	private List<Course> authorCoursesList;

	@PostConstruct
	void init() {
		authorCoursesList = courseService
				.findAllAuthorCoursesOfUser(CurrentUser.getInstance()
						.geteMail());

		VerticalLayout content = new VerticalLayout();
		HorizontalLayout header = new HorizontalLayout();

		setSpacing(true);
		setMargin(false);

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
	}

	public Authorlibrary() {

	}

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
		tabContent.addComponent(courseTitle);

		VerticalLayout couseDescription = new VerticalLayout();
		couseDescription.setSizeFull();
		Label couseDescriptionLabel = new Label();
		couseDescriptionLabel.addStyleName(ValoTheme.LABEL_H3);
		couseDescriptionLabel.setValue(lang.getString("CourseDescription"));
		TextArea couseDescriptionField = new TextArea();
		couseDescriptionField.setWordwrap(true);
		couseDescriptionField.setRows(5);
		couseDescriptionField.setSizeFull();
		couseDescription.addComponents(couseDescriptionLabel,
				couseDescriptionField);
		couseDescription.setSizeFull();
		tabContent.addComponent(couseDescription);

		HorizontalLayout footer = new HorizontalLayout();
		footer.setWidth("100%");
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		Button createCourse = new Button(lang.getString("CreateCourse"));
		createCourse.setStyleName(ValoTheme.BUTTON_PRIMARY);
		footer.addComponent(createCourse);
		footer.setComponentAlignment(createCourse, Alignment.BOTTOM_CENTER);
		tabContent.addComponent(footer);

		createCourse.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					boolean courseInserted = courseService
							.insertCourse(new Course(courseTitleField
									.getValue(), couseDescriptionField
									.getValue(), userService
									.findByEMail(CurrentUser.getInstance()
											.geteMail()), ""));
					if (courseInserted) {
						courseService.saveDummyGraph(courseTitleField
								.getValue());
						String title = (String) courseTitleField.getValue();
						Component newTab = buildCourseTab(title);
						getTabSheet().addComponent(newTab);
						getTabSheet().setSelectedTab(newTab);
						courseTitleField.clear();
						couseDescriptionField.clear();
					} else {
						Notification.show(
								lang.getString("CourseAlreadyExists"),
								Notification.Type.WARNING_MESSAGE);
					}
				} catch (IllegalArgumentException iae) {
					Notification.show(iae.getMessage());
				}
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
		selectStudents.setCaption(lang.getString("SelectParticipants"));

		selectStudents.setRows(10);
		selectStudents.setSizeFull();
		selectStudents
				.setLeftColumnCaption(lang.getString("ListOfAllStudents"));
		selectStudents.setRightColumnCaption(lang.getString("Participants"));

		List<CrayonsUser> allUsers = userService.findAll();
		for (int i = 0; i < allUsers.size(); i++) {
			selectStudents.addItem(allUsers.get(i).getEmail());
		}

		String[] emailOfStudentsInCourse = courseService
				.getStudentsWithAuthor(title);
		if (emailOfStudentsInCourse != null) {
			for (int i = 1; i < emailOfStudentsInCourse.length; i++) {
				selectStudents.select(emailOfStudentsInCourse[i]);
			}
		}

		Button saveStudents = new Button(lang.getString("SaveStudents"));
		saveStudents.addClickListener(new ClickListener() {

			// TODO known bugg: after adding yourself to a course need to update
			// the userlibrary
			@Override
			public void buttonClick(ClickEvent event) {
				@SuppressWarnings("unchecked")
				Collection<String> tmp = (Collection<String>) selectStudents
						.getValue();
				if (courseService.insertStudent(tmp.toArray(new String[0]),
						tabContent.getCaption())) {
					Notification success = new Notification(lang
							.getString("StudentListUpdatedSuccessfully"));
					success.setDelayMsec(4000);
					success.setStyleName("barSuccessSmall");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());
				} else {
					Notification failure = new Notification("FAIL");
					failure.setDelayMsec(4000);
					failure.setPosition(Position.BOTTOM_CENTER);
					failure.show(Page.getCurrent());
				}
			}
		});

		selectStudents.setImmediate(true);
		tabContent.addComponent(selectStudents);
		tabContent.addComponent(saveStudents);
		HorizontalLayout footer = new HorizontalLayout();
		footer.setWidth("100%");
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		Component controlButtons = buildControlButtons(tabContent, title);
		footer.addComponent(controlButtons);
		footer.setComponentAlignment(controlButtons, Alignment.BOTTOM_CENTER);
		tabContent.addComponent(footer);

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
		controlButtons.setMargin(false);
		controlButtons.setSpacing(true);

		Button deleteCourse = new Button(lang.getString("DeleteCourse"));
		controlButtons.addComponent(deleteCourse);
		deleteCourse.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteCourse.addClickListener(new ClickListener() {

			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				courseService.removeCourse(courseService
						.findCourseByTitleAndAuthor(tab.getCaption()));
				getTabSheet().removeTab(
						getTabSheet().getTab(getTabSheet().getSelectedTab()));
				Notification success = new Notification(lang
						.getString("CourseIsDeletedSuccessfully"));
				success.setDelayMsec(2000);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
			}

		});

		Button modifyCourse = new Button(lang.getString("ModifyCourse"));
		controlButtons.addComponent(modifyCourse);
		modifyCourse.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(
						new CourseModificationWindow(courseService,
								courseService.findCourseByTitleAndAuthor(tab
										.getCaption()), tab, tabSheet));
			}
		});

		Button graphEditor = new Button(lang.getString("GraphEditor"));
		graphEditor.setStyleName(ValoTheme.BUTTON_PRIMARY);
		controlButtons.addComponent(graphEditor);
		graphEditor.addClickListener(new ClickListener() {
			// TODO: replace title with tab.getCaption() ?
			@Override
			public void buttonClick(ClickEvent event) {
				CurrentGraph.getInstance().setCourseTitle(title);
				CurrentGraph.getInstance().setGraph(
						courseService.getCourseData(title));
				CurrentCourses.getInstance().setTitle(title);
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
