package com.crayons_2_0.view;

import java.util.Iterator;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 * User library view represents the courses which are available for current user.
 */
@SpringView(name = UserlibraryView.VIEW_NAME)
public class UserlibraryView extends VerticalLayout implements View {

	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "Userlibrary";
    ResourceBundle lang = LanguageService.getInstance().getRes();
    
    @Autowired
    CourseService courseService;
    @Autowired
    CurrentUser c;
    
    private TabSheet coursesTabSheet;
	private Component filter;
    
    @PostConstruct
    void init(){
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
    * @return title of the author library
    */
    private Component buildTitle() {
        Label title = new Label("Kursübersicht");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    /**
     * Tab sheet getter.
     * @return tabSheet
     */
    public TabSheet getTabSheet(){
    	return this.coursesTabSheet;
    }
    
    /**
     * Builds a tab sheet which includes all available for the user courses. 
     * @return tab sheet 
     */
    private Component buildCoursesTabSheet() {
        this.coursesTabSheet = new TabSheet();
        coursesTabSheet.setSizeFull();
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        for (Course tmp: courseService.findAllCoursesOfUser(c.get())) {
            coursesTabSheet.addComponent(buildCourseTab(tmp));
        }
        return coursesTabSheet;
    }
  
    /**
     * Builds a tab with a description of a course.
     * @param course course which is represented in the tab
     * @return course tab
     */
    private Component buildCourseTab(Course course) {
        VerticalLayout tabContent = new VerticalLayout();
        tabContent.setCaption(course.getTitle());
        tabContent.setSpacing(true);
        tabContent.setMargin(true);
        
        Label courseDescription = new Label();
        courseDescription.setContentMode(ContentMode.HTML);
        courseDescription.setValue("<h3>Course description</h3>\n" + course.getDescription());
        courseDescription.setSizeFull();
        tabContent.addComponent(courseDescription);
        
        Table testResults = new Table();
        testResults.setCaption("<h3>Testergebnisse</h3>");
        testResults.setCaptionAsHtml(true);
        testResults.addContainerProperty("Test", String.class, null);
        testResults.addContainerProperty("Score", Integer.class, 0);
        testResults.addItem(new Object[]{"Lektion 1", 100}, 1);
        testResults.addItem(new Object[]{"Lektion 2", 70}, 2);
        testResults.setSizeFull();
        testResults.setPageLength(testResults.size());
        tabContent.addComponent(testResults);
        
        ProgressBar learningProgressBar = new ProgressBar(0.0f);
        learningProgressBar.setValue(0.3f);
        learningProgressBar.setSizeFull();
        tabContent.addComponents(learningProgressBar);
        learningProgressBar.setCaptionAsHtml(true);
        learningProgressBar.setCaption("<h3>Lernfortschritt</h3>");
        
        Component controlButtons = buildControlButtons(tabContent, course.getTitle());
        tabContent.addComponent(controlButtons);
        tabContent.setComponentAlignment(controlButtons, Alignment.BOTTOM_CENTER);
        
        return tabContent;
    }
    
    /**
     * Builds a layout with the control buttons which allows to learn or leave a course.
     * @param tab course tab
     * @param title course title
     * @return layout with the control buttons
     */
    private Component buildControlButtons(Component tab, String title) {
        HorizontalLayout controlButtons = new HorizontalLayout();
        controlButtons.setMargin(true);
        controlButtons.setSpacing(true);
        
        Button leaveCourse = new Button("Verlassen");
        controlButtons.addComponent(leaveCourse);
        controlButtons.setComponentAlignment(leaveCourse, Alignment.MIDDLE_LEFT);
        leaveCourse.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	courseService.removeStudent(title, c.get().getEmail());
                coursesTabSheet.removeComponent(tab);
            }
            
        });
        
        Button study = new Button("Lernen");
        controlButtons.addComponent(study);
        controlButtons.setComponentAlignment(study, Alignment.MIDDLE_RIGHT);
        
        study.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	
            }
            
        });
        
        return controlButtons;
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }  
    
    /**
     * Builds a filter to search for a course by name.
     * @return layout with search field
     */
	public Component buildFilter() {
	    VerticalLayout search = new VerticalLayout();
	    search.setStyleName("search");
	    search.setMargin(false);
	    search.setSizeUndefined();
	    
		final TextField filter = new TextField();
		filter.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {
				TabSheet tabs = getTabSheet();
				Iterator<Component> it = tabs.iterator();
				Component comp;
				if (event.getText().equals("")) {
					while (it.hasNext()){
						comp = it.next();					
						tabs.getTab(comp).setVisible(true);
					}
				}else{
					while (it.hasNext()){
						comp = it.next();
						if (comp.getCaption().toLowerCase().contains(event.getText().toLowerCase())){
							tabs.getTab(comp).setVisible(true);
						} else {
							tabs.getTab(comp).setVisible(false);
						}
					}
				}
			}
		});
		filter.setInputPrompt("Suche");
		filter.setIcon(FontAwesome.SEARCH);
		filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filter.setSizeUndefined();
		search.addComponent(filter);
		search.setComponentAlignment(filter, Alignment.MIDDLE_RIGHT);
		return search;
	}
}
