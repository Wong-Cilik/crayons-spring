package com.crayons_2_0.model;

import java.io.Serializable;

import com.vaadin.ui.Image;

@SuppressWarnings("serial")
public class UnitData implements Serializable {

	private String text;
	private Image image;
	private String imageTitle;

	public UnitData(String text) {
		this.text = text;
	}

	public UnitData(Image image, String imageTitle) {
		this.setImage(image);
		this.setImageTitle(imageTitle);
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

	public Object getQuestion() {
		// TODO Auto-generated method stub
		return null;
	}
}
