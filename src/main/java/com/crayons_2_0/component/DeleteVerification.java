package com.crayons_2_0.component;

import java.util.ResourceBundle;

import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.view.CourseEditorView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Delete verification window allows user to delete the nodes from a graph.
 */
@SuppressWarnings("serial")
public final class DeleteVerification extends Window {
	// @DB

	// sollte noch ein set werden
	private UnitNode deleteUnit;

	// TODO von DB holen
	private static Graph graph;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	/**
	 * Delete verification window constructor. 
	 * 
	 * @param graphData
	 *         graph that represents the selected course
	 */
	public DeleteVerification(Graph graphData) {
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
		content.setComponentAlignment(title, Alignment.TOP_LEFT);

		Component unitChoise = buildUnitChoice();
		content.addComponent(unitChoise);
		content.setComponentAlignment(unitChoise, Alignment.MIDDLE_LEFT);

		Component footer = buildFooter();
		content.addComponent(footer);
		content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}

	/**
	 * 
	 * @return combo box with the units which can be deleted 
	 */
	private Component buildUnitChoice() {
		ComboBox selectUnit = new ComboBox(
				lang.getString("SelectTheUnitToBeDeleted"));
		for (UnitNode currentNode : graph.getUnitCollection()) {
			selectUnit.addItem(currentNode.getUnitNodeTitle());
		}
		selectUnit.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				deleteUnit = graph.getNodeByName(selectUnit.getValue()
						.toString());
			}
		});
		return selectUnit;
	}

	public void foo() {
		CourseEditorView.refreshGraph(graph);
	}
	
	/**
	 * Builds a footer with a delete button to confirm the selected node deletion. 
	 * 
	 * @return horizontal layout which wraps a delete button 
	 */
	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button(lang.getString("Delete"));
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (graph.deleteUnit(deleteUnit)) {

					close();
					Notification success = new Notification(lang
							.getString("UnitIsDeletedSuccessfully"));
					success.setDelayMsec(500);
					success.setStyleName("bar success small");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());
					deleteUnit = null;
				} else {
					Notification failed = new Notification(lang
							.getString("UnitFailedToDelete"));
					failed.setDelayMsec(500);
					failed.setStyleName("bar success small");
					failed.setPosition(Position.BOTTOM_CENTER);
					failed.show(Page.getCurrent());
				}
			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_CENTER);
		return footer;
	}

	private Component buildTitle() {
		Label title = new Label(lang.getString("DeleteAUnit"));
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	public static void refreshData(Graph graphData) {
		graph = graphData;

	}
}
