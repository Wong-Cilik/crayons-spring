package com.crayons_2_0.component;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;

public class MultipleChoiceEditor extends CustomComponent {
    private final OptionGroup questionsList = new OptionGroup();
    
    public MultipleChoiceEditor(ArrayList<String> prefillData) {
        setWidth(100.0f, Unit.PERCENTAGE);
        addStyleName("inline-text-editor");
        
        if (prefillData != null)
            for (String question: prefillData)
                questionsList.addItem(question);
        
        setCompositionRoot(buildMultipleChoiceEditor());
    }

    private Component buildReadOnly() {
        Button editButton = new Button(FontAwesome.EDIT);
        editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(buildMultipleChoiceEditor());
            }
        });

        CssLayout result = new CssLayout(questionsList, editButton);
        result.addStyleName("text-editor");
        result.setSizeFull();
        return result;
    }

    private Component buildMultipleChoiceEditor() {
        // TODO: question description
        /*questionsList.setMultiSelect(false);
        questionsList.setWidth(100.0f, Unit.PERCENTAGE);*/
        
        final TextField textField = new TextField();
        textField.setWidth(100.0f, Unit.PERCENTAGE);
        
        Button addQuestionButton = new Button("Add question");
        addQuestionButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                questionsList.addItem((String) textField.getValue());
                textField.clear();
            }
        });
        
        HorizontalLayout addQuestion = new HorizontalLayout(textField, addQuestionButton);
        addQuestion.setSpacing(true);
        
        Button save = new Button("Save");
        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(buildReadOnly());
            }
        });
        
        VerticalLayout result = new VerticalLayout();
        if (questionsList != null)
            result.addComponent(questionsList);
        HorizontalLayout controlButtons = new HorizontalLayout(addQuestion, save);
        controlButtons.setSpacing(true);
        result.addComponent(controlButtons);
        result.setSpacing(true);
        result.setSizeFull();
        return result;
    }
}
