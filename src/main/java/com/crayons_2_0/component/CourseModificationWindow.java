package com.crayons_2_0.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CourseModificationWindow extends Window {
    private TabSheet tabSheet;
    private Component tab;
    private String courseTitle;
    
    public CourseModificationWindow(String courseTitle, Component tab, TabSheet tabSheet) {
        this.courseTitle = courseTitle;
        this.tab = tab;
        this.tabSheet = tabSheet;
        setModal(true);
        setResizable(false);
        setClosable(true);
        setHeight(40.0f, Unit.PERCENTAGE);
        setWidth(40.0f, Unit.PERCENTAGE);
        
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setSpacing(true);
        setContent(content);
        
        Component courseDescription = buildCourseDescription();
        content.addComponent(courseDescription);
    }
    
    private Component buildCourseDescription() {
        VerticalLayout courseDescription = new VerticalLayout();
        courseDescription.setSpacing(true);
        courseDescription.setMargin(true);
        
        HorizontalLayout courseTitle = new HorizontalLayout();
        courseTitle.setSpacing(true);
        Label courseTitleLabel = new Label("Kurstitel");
        TextField courseTitleField = new TextField(null, this.courseTitle);
        courseTitle.addComponents(courseTitleLabel, courseTitleField);
        //courseTitleField.addValueChangeListener();
        courseDescription.addComponent(courseTitle);
        courseTitleField.setImmediate(true); 
        
        VerticalLayout couseDescription = new VerticalLayout();
        couseDescription.setSizeFull();
        Label couseDescriptionLabel = new Label("Kursbeschreibung");
        //TODO: get a course description
        TextField couseDescriptionField = new TextField();
        couseDescriptionField.setSizeFull();
        couseDescription.addComponents(couseDescriptionLabel, couseDescriptionField);
        couseDescription.setSizeFull();
        //couseDescriptionField.addValueChangeListener();
        courseDescription.addComponent(couseDescription);
        
        Component controlButtons = buildControlButtons(courseTitleField, couseDescriptionField);
        controlButtons.setSizeFull();
        courseDescription.addComponent(controlButtons);
        courseDescription.setComponentAlignment(controlButtons, Alignment.BOTTOM_CENTER);
        
        return courseDescription;
    }
    
    private Component buildControlButtons(TextField courseTitleField, TextField couseDescriptionField) {
        HorizontalLayout controlButtons = new HorizontalLayout();
        controlButtons.setMargin(true);
        controlButtons.setSpacing(true);
        
        Button saveCourse = new Button("Save");
        controlButtons.addComponent(saveCourse);
        controlButtons.setComponentAlignment(saveCourse, Alignment.BOTTOM_LEFT);
        saveCourse.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                //TODO: save modifications
                tabSheet.getTab(tab).setCaption(courseTitleField.getValue());
                close();
                Notification success = new Notification(
                        "Course is modified successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
            }
            
        });
        
        Button deleteCourse = new Button("Delete course");
        controlButtons.addComponent(deleteCourse);
        controlButtons.setComponentAlignment(deleteCourse, Alignment.BOTTOM_RIGHT);
        deleteCourse.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                tabSheet.removeComponent(tab);
                close();
                Notification success = new Notification(
                        "Course is deleted successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
            }
            
        });
        
        return controlButtons;
    }
}
