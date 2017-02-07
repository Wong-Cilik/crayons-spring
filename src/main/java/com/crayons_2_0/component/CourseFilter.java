package com.crayons_2_0.component;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CourseFilter {
	public static Component buildFilter() {
		final TextField filter = new TextField();
		filter.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {
				// refresh tabsheet view
			}
		});
		filter.setInputPrompt("Filter");
		filter.setIcon(FontAwesome.SEARCH);
		filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		return filter;
	}
}
