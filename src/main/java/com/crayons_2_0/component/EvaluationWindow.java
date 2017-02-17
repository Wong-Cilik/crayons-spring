package com.crayons_2_0.component;

import java.util.Map;
import java.util.ResourceBundle;

import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.view.CourseUserView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class EvaluationWindow extends Window {
	
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public EvaluationWindow(Map<String, Boolean> responces) {
		setSizeFull();
		setModal(true);
		setResizable(false);
		setHeight(50.0f, Unit.PERCENTAGE);
		setWidth(40.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);

		Component title = buildTitle();
		title.setSizeUndefined();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.TOP_CENTER);
		content.setExpandRatio(title, 1);

		VerticalLayout testResults = buildTestResultsLayout(responces);
		content.addComponent(testResults);
		content.setComponentAlignment(testResults, Alignment.TOP_CENTER);
		content.setExpandRatio(testResults, 4);
		testResults.setStyleName("canvas");

		Component footer = buildFooter();
		content.addComponent(footer);
		content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
		content.setExpandRatio(footer, 1);
	}

	private Component buildFooter() {
		Button ok = new Button(lang.getString("Ok"));
		ok.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
				UI.getCurrent().getNavigator()
						.navigateTo(CourseUserView.VIEW_NAME);
			}
		});
		ok.focus();
		return ok;
	}

	private VerticalLayout buildTestResultsLayout(Map<String, Boolean> responces) {
		VerticalLayout content = new VerticalLayout();
		content.setSpacing(true);
		for (Map.Entry<String, Boolean> singleResponce : responces.entrySet()) {
			HorizontalLayout result = new HorizontalLayout();
			result.setSizeUndefined();
			result.setSpacing(true);

			Label questionText = new Label(singleResponce.getKey());
			result.addComponent(questionText);
			result.setComponentAlignment(questionText, Alignment.MIDDLE_LEFT);

			Label resultIcon = new Label();
			if (singleResponce.getValue())
				resultIcon.setIcon(FontAwesome.CHECK);
			else
				resultIcon.setIcon(FontAwesome.TIMES);
			result.addComponent(resultIcon);
			result.setComponentAlignment(resultIcon, Alignment.MIDDLE_RIGHT);

			content.addComponent(result);
		}
		return content;
	}

	private Component buildTitle() {
		Label title = new Label(lang.getString("TestResults"));		
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}
}
