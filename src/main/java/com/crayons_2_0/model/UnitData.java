package com.crayons_2_0.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UnitData implements Serializable {

	private String text;

	public UnitData(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
