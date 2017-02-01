package com.crayons_2_0.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;

import com.crayons_2_0.component.UnitEditor;
import com.crayons_2_0.component.UnitEditor.CourseEditorListener;
import com.crayons_2_0.controller.OpenUnitEditorListener;
import com.crayons_2_0.mockup.autorenbereich;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.view.authorlib.AuthorlibraryForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
public class Authorlibrary extends VerticalLayout implements View, CourseEditorListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "Authorlibrary";
    ResourceBundle lang = LanguageService.getInstance().getRes();
    private TabSheet tabSheet;
    
    public Authorlibrary() {
    	
    	
    	
    	// ALT ------ Bitte Neu -> Neu und Form verwenden
    	
    	VerticalLayout content = new VerticalLayout();

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        addComponent(content);
        content.addComponent(buildTitle());
        this.tabSheet = buildCoursesTabSheet();
        content.addComponent(this.tabSheet);
        
    	
    	
    	
    	// NEU NEU NEU NEU
    	/*
    	AuthorlibraryForm content = new AuthorlibraryForm();
    	addComponent(content);
    	
    	setSizeFull();
        setStyleName("about-view");
        setSpacing(true);
        setMargin(true);
        */
        
    }
    
    private TabSheet getTabSheet() {
        return this.tabSheet;
    }
    
    private Component buildTitle() {
        Label title = new Label("Kursübersicht");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    private TabSheet buildCoursesTabSheet() {
        TabSheet coursesTabSheet = new TabSheet();
        coursesTabSheet.setSizeFull();
        coursesTabSheet.addTab(buildAddNewCourseTab());
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        coursesTabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        coursesTabSheet.addComponent(buildCourseTab("Lineare Algebra"));
        return coursesTabSheet;
    }
    
    // TODO: layout with a "constructor" to build a new course
    private Component buildAddNewCourseTab() {
        VerticalLayout tabContent = new VerticalLayout();
        tabContent.setIcon(FontAwesome.PLUS);
        tabContent.setSpacing(true);
        tabContent.setMargin(true);
        
        HorizontalLayout courseTitle = new HorizontalLayout();
        courseTitle.setSpacing(true);
        Label courseTitleLabel = new Label("Kurstitel");
        TextField courseTitleField = new TextField();
        courseTitle.addComponents(courseTitleLabel, courseTitleField);
        //courseTitleField.addValueChangeListener();
        tabContent.addComponent(courseTitle);
        courseTitleField.setImmediate(true); 
        
        VerticalLayout couseDescription = new VerticalLayout();
        couseDescription.setSpacing(true);
        couseDescription.setSizeFull();
        Label couseDescriptionLabel = new Label("Kursbeschreibung");
        TextField couseDescriptionField = new TextField();
        couseDescriptionField.setSizeFull();
        couseDescription.addComponents(couseDescriptionLabel, couseDescriptionField);
        couseDescription.setSizeFull();
        //couseDescriptionField.addValueChangeListener();
        tabContent.addComponent(couseDescription);
        
        Button createCourse = new Button("Kurs erstellen");
        tabContent.addComponent(createCourse);
        tabContent.setComponentAlignment(createCourse, Alignment.BOTTOM_CENTER);
        
        createCourse.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification success = new Notification(
                        "Course created successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
                String value = (String) courseTitleField.getValue();
                getTabSheet().addComponent(buildCourseTab(value));
            }
        });
        
        return tabContent;
    }
  
    //TODO: Kurs aus Datenbank übergeben 
    private Component buildCourseTab(String title) {
        VerticalLayout tabContent = new VerticalLayout();
        tabContent.setCaption(title);
        tabContent.setSpacing(true);
        tabContent.setMargin(true);
        
        TwinColSelect selectStudents = new TwinColSelect("Select students");
        selectStudents.setRows(10);
        selectStudents.setSizeFull();
        selectStudents.setLeftColumnCaption("List of all students");
        selectStudents.setRightColumnCaption("Participants");
        
        String nonParticipants[] = {"Heidi Klum", "Kate Moss", "Natalia Vodianova", "Cara Delevingne"};
        for (int i=0; i<nonParticipants.length; i++)
            selectStudents.addItem(nonParticipants[i]);
                 
        selectStudents.setImmediate(true);
        tabContent.addComponent(selectStudents);
        
        tabContent.addComponent(buildControlButtons());
        
        return tabContent;
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

    @Override
    public void enter(ViewChangeEvent event) {
    }


    @Override
    public void titleChanged(String newTitle, UnitEditor editor) {
        // TODO Auto-generated method stub
        
    }  
    
    
    
    //--------------------------------------
    
    
    
}
