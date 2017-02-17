package com.crayons_2_0.component;

import com.crayons_2_0.view.UnitUserView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class SelectUnitToLearnWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO: put user and course as parameters to get information from db
	public SelectUnitToLearnWindow(/* User user, Course course */) {
		setSizeFull();
		setModal(true);
		setResizable(false);
		setClosable(true);
		setHeight(40.0f, Unit.PERCENTAGE);
		setWidth(40.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);

		Component title = buildTitle();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.TOP_CENTER);

		// content.addComponent(buildDescription());

		Component unitChoise = buildUnitChoice();
		content.addComponent(unitChoise);
		content.setComponentAlignment(unitChoise, Alignment.MIDDLE_LEFT);

		Component footer = buildFooter();
		content.addComponent(footer);
		content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}

	private Component buildUnitChoice() {
		ComboBox selectUnit = new ComboBox("Select unit");
		/*
		 * for (all units of the user in the course which are available) {
		 * selectUnit.addItem("unit name"); }
		 */
		selectUnit.addItem("Unit 1");
		selectUnit.addItem("Unit 2");
		return selectUnit;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button learn = new Button("Learn");
		learn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		learn.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
				// TODO: return selected unit
				UI.getCurrent().getNavigator()
						.navigateTo(UnitUserView.VIEW_NAME);
			}
		});
		learn.focus();

		Button cancel = new Button("Cancel");
		cancel.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		footer.addComponents(cancel, learn);
		footer.setComponentAlignment(cancel, Alignment.MIDDLE_LEFT);
		footer.setComponentAlignment(learn, Alignment.MIDDLE_RIGHT);
		return footer;
	}

	private Component buildTitle() {
		Label title = new Label("Open a learning unit");
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	/*
	 * private Component buildDescription() { return null; }
	 */
}
