package com.crayons_2_0.view;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 * User library view represents the courses which are available for current
 * user.
 */
@SuppressWarnings("serial")
@SpringView(name = UserlibraryView.VIEW_NAME)
public class UserlibraryView extends VerticalLayout implements View {

	/**
     * 
     */

	public static final String VIEW_NAME = "Userlibrary";
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	private @Autowired
	CourseService courseService;

	private @Autowired
	UserService userService;

	private TabSheet coursesTabSheet;
	private Component filter;

	@PostConstruct
	void init() {
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

	public UserlibraryView() {

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
	 * Tab sheet getter.
	 * 
	 * @return tabSheet
	 */
	public TabSheet getTabSheet() {
		return this.coursesTabSheet;
	}

	/**
	 * Builds a tab sheet which includes all available for the user courses.
	 * 
	 * @return tab sheet
	 */
	private Component buildCoursesTabSheet() {
		this.coursesTabSheet = new TabSheet();
		coursesTabSheet.setSizeFull();
		coursesTabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		coursesTabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		for (Course tmp : courseService.findAllCoursesOfUser(userService
				.findByEMail(CurrentUser.getInstance().geteMail()))) {
			coursesTabSheet.addComponent(buildCourseTab(tmp));
		}
		return coursesTabSheet;
	}

	/**
	 * Builds a tab with a description of a course.
	 * 
	 * @param course
	 *            course which is represented in the tab
	 * @return course tab
	 */
	private Component buildCourseTab(Course course) {
		VerticalLayout tabContent = new VerticalLayout();
		tabContent.setCaption(course.getTitle());
		tabContent.setSpacing(true);
		tabContent.setMargin(true);

		Label courseAuthor = new Label();
		courseAuthor.setContentMode(ContentMode.HTML);
		courseAuthor.setValue("<h3>" + lang.getString("Author") + "</h3>\n"
				+ course.getAuthor().getFirstName() + " "
				+ course.getAuthor().getLastName());
		courseAuthor.setSizeFull();
		tabContent.addComponent(courseAuthor);
		
		Label courseDescription = new Label();
        courseDescription.setContentMode(ContentMode.HTML);
        courseDescription.setValue("<h3>" + lang.getString("CourseDescription") + "</h3>\n"
                + course.getDescription());
        courseDescription.setSizeFull();
        tabContent.addComponent(courseDescription);

        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        Component controlButtons = buildControlButtons(tabContent, course.getTitle());
        footer.addComponent(controlButtons);
        footer.setComponentAlignment(controlButtons, Alignment.BOTTOM_CENTER);
        tabContent.addComponent(footer);

		return tabContent;
	}

	/**
	 * Builds a layout with the control buttons which allows to learn or leave a
	 * course.
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

		Button leaveCourse = new Button(lang.getString("LeaveTheCourse"));
		controlButtons.addComponent(leaveCourse);
		controlButtons
				.setComponentAlignment(leaveCourse, Alignment.MIDDLE_LEFT);
		leaveCourse.addClickListener(new ClickListener() {

			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				courseService.removeStudent(title, CurrentUser.getInstance()
						.geteMail());
				coursesTabSheet.removeComponent(tab);
			}

		});

		Button study = new Button(lang.getString("Learn"));
		study.setStyleName(ValoTheme.BUTTON_PRIMARY);
		controlButtons.addComponent(study);
		controlButtons.setComponentAlignment(study, Alignment.MIDDLE_RIGHT);

		study.addClickListener(new ClickListener() {

			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				CurrentCourses.getInstance().setTitle(title);
				Set <UnitNode> unitSet = courseService.getCourseData(title).getStartUnit().getChildNodes();
				if (unitSet.size() == 1) {
					CurrentCourses.getInstance().setUnitTitle(title + "#" + unitSet.iterator().next().getUnitNodeTitle());
					UI.getCurrent().getNavigator()
							.navigateTo(UnitUserView.VIEW_NAME);
				} else {
					
				}
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
						if (comp.getCaption().toLowerCase()
								.contains(event.getText().toLowerCase())) {
							tabs.getTab(comp).setVisible(true);
						} else {
							tabs.getTab(comp).setVisible(false);
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
