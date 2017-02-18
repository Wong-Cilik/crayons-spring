package com.crayons_2_0.component;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentGraph;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.view.Uniteditor;
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

@SuppressWarnings("serial")
public class SelectUnitForEditWindow extends Window {
	
	@Autowired
	CurrentGraph currentGraph;

	/**
	 * 
	 */
	private static Graph graph;
	private ResourceBundle lang = LanguageService.getInstance().getRes();
	private ComboBox selectUnit;

	public SelectUnitForEditWindow(Graph graphData) {
		graph = graphData;
		setSizeFull();
		setModal(true);
		setResizable(false);
		setClosable(true);
		setHeight(30.0f, Unit.PERCENTAGE);
		setWidth(30.0f, Unit.PERCENTAGE);

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
		selectUnit = new ComboBox(lang.getString("SelectTheUnitForEdit"));
		for (UnitNode tmp : graph.getUnitCollection()) {
			selectUnit.addItem(tmp.getUnitNodeTitle());
		}
		return selectUnit;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button(lang.getString("Edit"));
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				currentGraph.setUnitTitle((String)selectUnit.getValue());
				Uniteditor.refreshLayout(graph.getNodeByName((String)selectUnit.getValue()).getLayout());
				close();
				UI.getCurrent().getNavigator().navigateTo(Uniteditor.VIEW_NAME);

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_CENTER);
		return footer;
	}

	private Component buildTitle() {
		Label title = new Label(lang.getString("OpenTheUnitEditor"));
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	public static void refreshData(Graph graphData) {
		graph = graphData;

	}

	/*
	 * private Component buildDescription() { return null; }
	 */
}
