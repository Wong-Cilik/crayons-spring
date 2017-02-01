package com.crayons_2_0.view.authorlib;



import java.util.ResourceBundle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;

import com.crayons_2_0.config.CurrentUserDummy;
import com.crayons_2_0.controller.OpenUnitEditorListener;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.view.CourseEditorView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;


// @Controller
public class AuthorlibraryForm extends VerticalLayout {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private Component title;
	private TabSheet coursesTabSheet;
	
	
	
	ResourceBundle lang = LanguageService.getInstance().getRes();

	@Autowired
    private CourseService courseService;
	
	public AuthorlibraryForm() {
		
		title = buildTitle();
		addComponent(title);
		
		createCoursesTabSheet();
		addComponent(coursesTabSheet);
		
		Component controlButtons = buildControlButtons();
		addComponent(controlButtons);
		
	}
	
	private Component buildTitle() {
        Label title = new Label("Kursübersicht");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
	
	
	private void createCoursesTabSheet() {
		coursesTabSheet = new TabSheet();
		
		for (Course tmpCourse : courseService.findAllCoursesOfUser(CurrentUserDummy.get())) {
		
			VerticalLayout tabContent = new VerticalLayout();
			tabContent.setCaption(tmpCourse.getTitle());
	        tabContent.setSpacing(true);
	        tabContent.setMargin(true);
	        
			Label description = new Label(tmpCourse.getDescription());
			tabContent.addComponent(description);
			
			Button exampleButton = new Button();
			exampleButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Notification.show("ToDo", "ExampleButton", Notification.Type.WARNING_MESSAGE);
					
				}
			});
			tabContent.addComponent(exampleButton);
			
			coursesTabSheet.addTab(tabContent);
		}
		
		coursesTabSheet.setSizeFull();
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		
		
	}


	/**
	 * @return the coursesTabSheet
	 */
	public TabSheet getCoursesTabSheet() {
		return coursesTabSheet;
	}

	/**
	 * @param coursesTabSheet the coursesTabSheet to set
	 */
	public void setCoursesTabSheet(TabSheet coursesTabSheet) {
		this.coursesTabSheet = coursesTabSheet;
	}
	
	private Component buildControlButtons() {
        HorizontalLayout controlButtons = new HorizontalLayout();
        controlButtons.setMargin(true);
        controlButtons.setSpacing(true);
        
        Button studentView = new Button("Student view");
        controlButtons.addComponent(studentView);
        
        Button graphEditor = new Button("Graph editor");
        controlButtons.addComponent(graphEditor);
        graphEditor.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                
                //get courseeditor listener from db
                // Add it to the root component
                //UI.getCurrent().addWindow(new UnitEditor());
                
                
                // fieldGroup.commit();
                // Updated user should also be persisted to database. But
                // not in this demo.

               
                // DashboardEventBus.post(new ProfileUpdatedEvent());

                /*
                 * try { //fieldGroup.commit(); // Updated user should also be
                 * persisted to database. But // not in this demo.
                 * 
                 * Notification success = new Notification(
                 * "Profile updated successfully"); success.setDelayMsec(2000);
                 * success.setStyleName("bar success small");
                 * success.setPosition(Position.BOTTOM_CENTER);
                 * success.show(Page.getCurrent());
                 * 
                 * //DashboardEventBus.post(new ProfileUpdatedEvent()); close();
                 * } catch (CommitException e) {
                 * Notification.show("Error while updating profile",
                 * Type.ERROR_MESSAGE); }
                 */

                UI.getCurrent().getNavigator().navigateTo(CourseEditorView.VIEW_NAME);
                //getUI().getUI().getPage().setLocation(uri);
            }
        });
        
        Button courseDescription = new Button("Course description");
        controlButtons.addComponent(courseDescription);
        
        Button courseEditor = new Button("Unit Editor");
        /*
        courseEditor.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(UnitEditorView.VIEW_NAME);
				
			}
		});
		*/
        courseEditor.addClickListener(new OpenUnitEditorListener());
        controlButtons.addComponent(courseEditor);
        
        return controlButtons;
    }
	

}
