package com.crayons_2_0.component;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MultipleChoice2 extends VerticalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Label title;
	
	private Label questionText;
	
	private CheckBox checkBox;
	
	public MultipleChoice2() {
		
		this.checkBox = new CheckBox("AAA", false);
		this.addComponent(title);
		this.addComponent(questionText);
		this.addComponent(checkBox);
		
		this.addContextClickListener(new ContextClickListener() {
			@Override
			public void contextClick(ContextClickEvent event) {
				
				if (event.getComponent().equals(title)) {
					TextField titleTextField = new TextField(title.getValue());
					replaceComponent(title, titleTextField);
				} else if (event.getComponent().equals(questionText)) {
					TextField qTextTextField = new TextField(questionText.getValue());
					replaceComponent(title, qTextTextField);
				}
					
			}
		});
		
	}

	/**
	 * @return the title
	 */
	public Label getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(Label title) {
		this.title = title;
	}

	/**
	 * @return the questionText
	 */
	public Label getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(Label questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the checkBox
	 */
	public CheckBox getCheckBox() {
		return checkBox;
	}

	/**
	 * @param checkBox the checkBox to set
	 */
	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}



}
