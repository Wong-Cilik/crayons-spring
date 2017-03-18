package com.crayons_2_0.model;

import java.io.Serializable;

import com.vaadin.data.Property;
import com.vaadin.ui.Image;
import com.vaadin.ui.OptionGroup;

@SuppressWarnings("serial")
public class UnitData implements Serializable {

	private String text;
	private Image image;
	private String imageTitle;
	private Property<String> property;
	private OptionGroup optionGroup;
	private String question;

	public UnitData(String question, Property<String> property, OptionGroup optionGroup) {
		this.question = question;
		this.property = property;
		this.optionGroup = optionGroup;
	}

	public UnitData(Image image, String imageTitle) {
		this.setImage(image);
		this.setImageTitle(imageTitle);
	}

	public UnitData(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public Property<String> getProperty() {
		return property;
	}

	public void setProperty(Property<String> property) {
		this.property = property;
	}

	public OptionGroup getOptionGroup() {
		return optionGroup;
	}

	public void setOptionGroup(OptionGroup optionGroup) {
		this.optionGroup = optionGroup;
	}

	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
}
