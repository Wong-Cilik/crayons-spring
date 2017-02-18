package com.crayons_2_0.view;

import com.crayons_2_0.component.SelectUnitToLearnWindow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Course editor view consists of a learning graph and a button to navigate to a
 * learning unit.
 *
 */
@SuppressWarnings("serial")
@SpringView(name = CourseUserView.VIEW_NAME)
public class CourseUserView extends VerticalLayout implements View {

	/**
	 * 
	 */

	public static final String VIEW_NAME = "User Learning Graph";

	public CourseUserView() {
		setSizeFull();

		// TODO: generate graph for a particular user

		Component footer = buildFooter();
		addComponent(footer);
		setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}

	/**
	 * Builds a footer which includes the control buttons to choose a unit or
	 * navigate back to the user library.
	 * 
	 * @return the footer
	 */
	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setMargin(true);
		footer.setSizeFull();
		footer.setSpacing(true);

		Button backButton = new Button("Back", FontAwesome.ARROW_LEFT);
		backButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator()
						.navigateTo(UserlibraryView.VIEW_NAME);
			}

		});
		footer.addComponent(backButton);
		footer.setComponentAlignment(backButton, Alignment.BOTTOM_LEFT);

		Button chooseUnitButton = new Button("Choose unit");
		backButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		backButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new SelectUnitToLearnWindow());
			}

		});
		footer.addComponent(chooseUnitButton);
		footer.setComponentAlignment(chooseUnitButton, Alignment.BOTTOM_RIGHT);

		return footer;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}