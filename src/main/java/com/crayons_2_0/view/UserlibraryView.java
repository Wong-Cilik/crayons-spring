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
    
    private Component buildTitle() {
        Label title = new Label("Kursübersicht");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    public TabSheet getTabSheet(){
    	return this.coursesTabSheet;
    }
    
    private Component buildCoursesTabSheet() {
        this.coursesTabSheet = new TabSheet();
        coursesTabSheet.setSizeFull();
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        for (Course tmp: courseService.findAllCoursesOfUser(c.get())) {
        	System.out.println(tmp.getTitle());
            coursesTabSheet.addComponent(buildCourseTab(tmp));
        }
        return coursesTabSheet;
    }
  
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
            	courseService.removeStudent(title);
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
    
	public Component buildFilter() {
	    //hack damit die lupe richtig angezeigt werden kann...
	    VerticalLayout search = new VerticalLayout();
	    search.setStyleName("search");
	    search.setMargin(false);
	    
	    
		final TextField filter = new TextField();
		filter.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {
				TabSheet tabs = getTabSheet();
				Iterator<Component> it = tabs.getComponentIterator();
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
		search.addComponent(filter);
		search.setComponentAlignment(filter, Alignment.MIDDLE_RIGHT);
		return search;
	}
}
