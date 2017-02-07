package com.crayons_2_0.component;

import java.util.Iterator;

import com.crayons_2_0.model.Course;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CourseFilter {
	public static Component buildFilter(TabSheet tabSheet) {
		final TextField filter = new TextField();
		filter.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {
				
			}
		});
		filter.setInputPrompt("Suche");
		filter.setIcon(FontAwesome.SEARCH);
		filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		return filter;
	}
	
	public static void refreshTabSheet(TabSheet tabSheet, String search) {
		
	}
}
