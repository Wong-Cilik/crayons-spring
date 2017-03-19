package com.crayons_2_0.model;

import java.io.Serializable;
import java.util.List;

import com.vaadin.ui.Image;

@SuppressWarnings("serial")
public class UnitData implements Serializable {

	private String text;
	private Image image;
	private String imageTitle;
	private String question;
	private List<String> answerList;
	private String answer;

	public UnitData(String question, List<String> answerList, String answer) {
		this.question = question;
		this.answer = answer;
		this.answerList = answerList;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
