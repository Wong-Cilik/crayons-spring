package com.crayons_2_0.component;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
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

@SuppressWarnings("serial")
public class MultipleChoiceEditor extends CustomComponent {

	private final OptionGroup answers = new OptionGroup();
	private final Property<String> questionText = new ObjectProperty<String>(
			"Enter the question here...");
	private String rightAnswer = "";

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public MultipleChoiceEditor(String questionText, ArrayList<String> answers,
			String rightAnswer) {
		setWidth(100.0f, Unit.PERCENTAGE);
		addStyleName("inline-text-editor");

		if (questionText != null)
			this.questionText.setValue(questionText);

		if (answers != null)
			for (String question : answers)
				this.answers.addItem(question);

		if (rightAnswer != null)
			this.rightAnswer = rightAnswer;

		setCompositionRoot(buildMultipleChoiceEditor());
	}

	private Component buildReadOnly() {
		final Label questionText = new Label(this.questionText);

		Button editButton = new Button(FontAwesome.EDIT);
		editButton.addStyleName(ValoTheme.BUTTON_SMALL);
		editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		editButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				setCompositionRoot(buildMultipleChoiceEditor());
			}
		});

		CssLayout result = new CssLayout(questionText, answers, editButton);
		result.addStyleName("text-editor");
		result.setSizeFull();
		return result;
	}

	private Component buildMultipleChoiceEditor() {
		final TextArea questionText = new TextArea(this.questionText);
		questionText.setWidth(100.0f, Unit.PERCENTAGE);
		questionText.addAttachListener(new AttachListener() {
			@Override
			public void attach(final AttachEvent event) {
				questionText.focus();
				questionText.selectAll();
			}
		});

		final TextField textField = new TextField();
		textField.setWidth(100.0f, Unit.PERCENTAGE);

		answers.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				rightAnswer = (String) event.getProperty().getValue();
			}
		});

		Button addQuestionButton = new Button(lang.getString("AddQuestion"));
		addQuestionButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				answers.addItem((String) textField.getValue());
				textField.clear();
			}
		});

		HorizontalLayout addQuestion = new HorizontalLayout(textField,
				addQuestionButton);
		addQuestion.setSpacing(true);

		Button save = new Button(lang.getString("Save"));
		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				System.out.println(rightAnswer);
				setCompositionRoot(buildReadOnly());
			}
		});

		VerticalLayout result = new VerticalLayout(questionText);
		if (answers != null)
			result.addComponent(answers);
		HorizontalLayout controlButtons = new HorizontalLayout(addQuestion,
				save);
		controlButtons.setSpacing(true);
		result.addComponent(controlButtons);
		result.setSpacing(true);
		result.setSizeFull();
		return result;
	}
}
