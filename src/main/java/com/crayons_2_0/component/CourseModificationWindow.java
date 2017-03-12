package com.crayons_2_0.component;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringUI
@ViewScope
@SpringComponent
public class CourseModificationWindow extends Window {

	/**
	 * 
	 */
	ResourceBundle lang = LanguageService.getInstance().getRes();
	private TabSheet tabSheet;
	private Component tab;
	private Course course;
	@Autowired
	CourseService courseService;

	public CourseModificationWindow(Course course, Component tab,
			TabSheet tabSheet) {
		this.course = course;
		this.tab = tab;
		this.tabSheet = tabSheet;
		setModal(true);
		setResizable(false);
		setClosable(true);
		setHeight(50.0f, Unit.PERCENTAGE);
		setWidth(30.0f, Unit.PERCENTAGE);
		
		this.setContent(buildContent());
	}

	private Component buildContent() {
		VerticalLayout content = new VerticalLayout();
		content.setWidth("100%");
		content.setSpacing(true);
		content.setMargin(true);

		Label windowTitle = new Label();
		windowTitle.setSizeUndefined();
		windowTitle.addStyleName(ValoTheme.LABEL_H3);
		windowTitle.addStyleName(ValoTheme.LABEL_BOLD);
		windowTitle.setValue(lang.getString("ModifyCourse"));
		content.addComponent(windowTitle);
		content.setComponentAlignment(windowTitle, Alignment.TOP_CENTER);

		HorizontalLayout courseTitle = new HorizontalLayout();
		courseTitle.setSizeUndefined();
		courseTitle.setSpacing(true);
		Label courseTitleLabel = new Label(lang.getString("CourseTitle"));
		TextField courseTitleField = new TextField(null, this.course.getTitle());
		courseTitle.addComponents(courseTitleLabel, courseTitleField);
		content.addComponent(courseTitle);
		content.setComponentAlignment(courseTitle, Alignment.TOP_LEFT);
		courseTitleField.setImmediate(true);

		VerticalLayout couseDescription = new VerticalLayout();
		couseDescription.setMargin(false);
		couseDescription.setSizeFull();
		Label couseDescriptionLabel = new Label(
				lang.getString("CourseDescription"));
		TextArea couseDescriptionField = new TextArea();
		couseDescriptionField.setWordwrap(true);
		couseDescriptionField.setRows(3);
		couseDescriptionField.setWidth("100%");

		couseDescriptionField.setValue(this.course.getDescription());
		couseDescription.addComponents(couseDescriptionLabel,
				couseDescriptionField);
		content.addComponent(couseDescription);
		content.setComponentAlignment(couseDescription, Alignment.MIDDLE_LEFT);

		HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        Component controlButtons = buildControlButtons(courseTitleField,
                couseDescriptionField);
        footer.addComponent(controlButtons);
        footer.setComponentAlignment(controlButtons, Alignment.BOTTOM_CENTER);
        content.addComponent(footer);

		return content;
	}

	private Component buildControlButtons(TextField courseTitleField,
			TextArea couseDescriptionField) {
		HorizontalLayout controlButtons = new HorizontalLayout();
		controlButtons.setSizeUndefined();
		controlButtons.setMargin(true);
		controlButtons.setSpacing(true);

		Button saveCourse = new Button(lang.getString("Save"));
		saveCourse.setStyleName(ValoTheme.BUTTON_PRIMARY);
		controlButtons.addComponent(saveCourse);
		controlButtons.setComponentAlignment(saveCourse,
				Alignment.BOTTOM_CENTER);
		saveCourse.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				Course tmp = courseService.findCourseByTitle(course.getTitle());
				tmp.setTitle(courseTitleField.getValue());
				tmp.setDescription(couseDescriptionField.getValue());
				courseService.update(tmp, course.getTitle());
				tabSheet.getTab(tab).setCaption(courseTitleField.getValue());
				tabSheet.getTab(tab).setDescription(
						couseDescriptionField.getValue());
				close();
				Notification success = new Notification(lang
						.getString("CourseIsModifiedSuccessfully"));
				success.setDelayMsec(2000);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
				success.show(Page.getCurrent());
			}

		});
		return controlButtons;
	}
}
