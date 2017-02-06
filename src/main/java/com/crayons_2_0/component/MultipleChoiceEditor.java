package com.crayons_2_0.component;

import java.util.ArrayList;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;

public class MultipleChoiceEditor extends CustomComponent {
    private static final long serialVersionUID = 5884398958435787340L;
    
    private final OptionGroup questionsList = new OptionGroup();
    private final Property<String> questionText = new ObjectProperty<String>(
            "Enter the question here...");
    private String rightAnswer = "";
    
    public MultipleChoiceEditor(String questionText, ArrayList<String> questionList, String rightAnswer) {
        setWidth(100.0f, Unit.PERCENTAGE);
        addStyleName("inline-text-editor");
        
        if (questionText != null)
            this.questionText.setValue(questionText);
        
        if (questionList != null)
            for (String question: questionList)
                questionsList.addItem(question);
        
        if (rightAnswer != null)
            this.rightAnswer = rightAnswer;
        
        setCompositionRoot(buildMultipleChoiceEditor());
    }
    
    public Component getUserView() {
        Button checkAnswer = new Button("Check");
        checkAnswer.addStyleName(ValoTheme.BUTTON_SMALL);
        checkAnswer.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -439858117472036615L;

            @Override
            public void buttonClick(final ClickEvent event) {
                // TODO: check answer
            }
        });
        Label questionText = new Label(this.questionText);
        VerticalLayout result = new VerticalLayout(questionText, questionsList, checkAnswer);
        result.setComponentAlignment(checkAnswer, Alignment.BOTTOM_RIGHT);
        // TODO: return unselected!!!
        return result;
    }
    
    public boolean checkAnswer(String givenAnswer) {
        return givenAnswer.equals(rightAnswer);
    }

    private Component buildReadOnly() {
        final Label questionText = new Label(this.questionText);
        
        Button editButton = new Button(FontAwesome.EDIT);
        editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 2128892506778715818L;

            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(buildMultipleChoiceEditor());
            }
        });
        
        CssLayout result = new CssLayout(questionText, questionsList, editButton);
        result.addStyleName("text-editor");
        result.setSizeFull();
        return result;
    }

    private Component buildMultipleChoiceEditor() {
        final TextArea questionText = new TextArea(this.questionText);
        questionText.setWidth(100.0f, Unit.PERCENTAGE);
        questionText.addAttachListener(new AttachListener() {
            private static final long serialVersionUID = -585304752892396542L;

            @Override
            public void attach(final AttachEvent event) {
                questionText.focus();
                questionText.selectAll();
            }
        });
        
        final TextField textField = new TextField();
        textField.setWidth(100.0f, Unit.PERCENTAGE);
        
        questionsList.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 8103764137753316059L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                rightAnswer = (String) event.getProperty().getValue();
            }
        });
        
        Button addQuestionButton = new Button("Add question");
        addQuestionButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -6884263769895434223L;

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
            private static final long serialVersionUID = 8068966193937340344L;

            @Override
            public void buttonClick(final ClickEvent event) {
                System.out.println(rightAnswer);
                setCompositionRoot(buildReadOnly());
            }
        });
        
        VerticalLayout result = new VerticalLayout(questionText);
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
