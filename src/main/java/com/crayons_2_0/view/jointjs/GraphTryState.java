package com.crayons_2_0.view.jointjs;

import java.util.ArrayList;

import com.vaadin.shared.ui.JavaScriptComponentState;

@SuppressWarnings("serial")
public class GraphTryState extends JavaScriptComponentState {

	/**
	 * 
	 */

	private ArrayList<Integer> coords;

	public ArrayList<Integer> getCoords() {
		return coords;
	}

	public void setCoords(final ArrayList<Integer> coords) {
		this.coords = coords;
	}
}
