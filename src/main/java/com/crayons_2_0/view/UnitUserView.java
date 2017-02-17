package com.crayons_2_0.view;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crayons_2_0.component.EvaluationWindow;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = UnitUserView.VIEW_NAME)
public class UnitUserView extends VerticalLayout implements View{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Unit User View";
    
    private VerticalLayout page;
    private final Button doneButton;
    private final Button cancelButton;
    private final Map<String, Boolean> responces;
    
    public UnitUserView(String unitName) {
        setSizeFull();
        
        doneButton = new Button("Done", FontAwesome.CHECK);
        doneButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        doneButton.addClickListener(new ClickListener() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().addWindow(new EvaluationWindow(responces));
            }
            
        });

        cancelButton = new Button("Cancel", FontAwesome.TIMES);
        cancelButton.setStyleName(ValoTheme.BUTTON_DANGER);
        cancelButton.addClickListener(new ClickListener() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(ClickEvent event) {
                // TODO: are you sure?
                UI.getCurrent().getNavigator().navigateTo(CourseUserView.VIEW_NAME);
            }
            
        });
        
        
        responces = new HashMap<String, Boolean>();

        Component pageTitle = buildTitle(unitName);
        addComponent(pageTitle);
        setComponentAlignment(pageTitle, Alignment.TOP_CENTER);
        setExpandRatio(pageTitle, 1);
        
        page = new VerticalLayout();
        page.setSpacing(true);
        page.setMargin(true);
        page.setWidth(100.0f, Unit.PERCENTAGE);
        page.setStyleName("canvas");
        addComponent(page);
        setExpandRatio(page, 7);
        
        // Test input
        addTextField("<h1>The future, present and past</h1> may not be as different as we think, " +
                "says science writer and astrophysicist Adam Becker. He explains this " +
                "mind-bending idea to BBC Earth's Michael Marshall and Melissa Hogenboom, " +
                "with help from the animators at Pomona Pictures");
        Image image = new Image();
        image.setSource(new FileResource(new File("/src/main/resources/com/crayons_2_0/images/architecture-detailed-hi.png")));
        image.setWidth(400, Unit.PIXELS);
        addImageArea("Vaadin detailed architecture", image);
        addMultipleChoise("How old are you?", Arrays.asList("10", "20", "30"), "20");
        addMultipleChoise("What's your name?", Arrays.asList("Natalia", "Julius", "Marc"), "Natalia");
        
        Component controlButtons = buildControlButtons();
        addComponent(controlButtons);
        setComponentAlignment(controlButtons, Alignment.BOTTOM_CENTER);
        setExpandRatio(controlButtons, 1);
        
        
    }
    
    private Component buildTitle(String unitName) {
        Label title = new Label(unitName);
        title.setStyleName(ValoTheme.LABEL_LARGE);
        HorizontalLayout layout = new HorizontalLayout(title);
        layout.setWidthUndefined();
        layout.setComponentAlignment(title, Alignment.TOP_CENTER);
        layout.setMargin(true);
        return layout;
    }
    
    public void addTextField(String text) {
        page.addComponent(buildTextField(text));
    }
    
    private Component buildTextField(String text) {
        Label textField = new Label(text);
        textField.setCaptionAsHtml(true);
        textField.setContentMode(ContentMode.HTML);
        return textField;
    }
    
    public void addImageArea(String imageTitle, Image image) {
        page.addComponent(buildImageArea(imageTitle, image));
    }
    
    private Component buildImageArea(String imageTitle, Image image) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        
        layout.addComponent(image);
        layout.setComponentAlignment(image, Alignment.TOP_CENTER);
        
        Label title = new Label(imageTitle);
        title.setWidthUndefined();
        title.setStyleName(ValoTheme.LABEL_LIGHT);
        layout.addComponent(title);
        layout.setComponentAlignment(title, Alignment.BOTTOM_CENTER);
        
        return layout;
    }
    
    public void addMultipleChoise(String questionText, List<String> answersList, String rightAnswer) {
        page.addComponent(buildMultipleChoise(questionText, answersList, rightAnswer));
    }
    
    private Component buildMultipleChoise(String questionText, List<String> answersList, String rightAnswer) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        Label question = new Label(questionText);
        
        OptionGroup answers = new OptionGroup();
        if (answers != null)
            for (String answer: answersList)
                answers.addItem(answer);
        
        layout.addComponents(question, answers);
        
        answers.addValueChangeListener(new ValueChangeListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void valueChange(ValueChangeEvent event) {
                responces.put(questionText, ((String) answers.getValue()).equals(rightAnswer)); 
            }
        });
        
        /*doneButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                responces.put(questionText, ((String) answers.getValue()).equals(rightAnswer)); 
            }
            
        });*/
        
        return layout;
    }
    
    private Component buildControlButtons() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.addComponents(cancelButton, doneButton);
        layout.setComponentAlignment(cancelButton, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(doneButton, Alignment.MIDDLE_RIGHT);
        
        return layout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}
