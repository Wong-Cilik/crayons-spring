package com.crayons_2_0.view;

import java.util.ResourceBundle;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * View shown when trying to navigate to a view that does not exist using
 * {@link com.vaadin.navigator.Navigator}.
 * 
 * 
 */
@SuppressWarnings("serial")
@SpringView(name = ErrorView.VIEW_NAME) class ErrorView extends VerticalLayout implements View {

	/**
     * 
     */
	public static final String VIEW_NAME = "Error view";

	ResourceBundle lang = LanguageService.getInstance().getRes();
	private Label explanation;

	public ErrorView() {
		setMargin(true);
		setSpacing(true);

		Label header = new Label(lang.getString("TheViewCouldNotBeFound"));
		header.addStyleName(Reindeer.LABEL_H1);
		addComponent(header);
		addComponent(explanation = new Label());
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		explanation.setValue(String.format(lang
				.getString("YouTriedToNavigateToAView('%s')thatDoesNotExist."),
				event.getViewName()));
	}
}