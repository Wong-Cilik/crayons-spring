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

@SuppressWarnings("serial")
public class UnitConnectionEditor extends Window {
	// @DB

	// sollte noch ein set werden
	UnitNode parent;
	// wird noch ausgebessert
	String unitTitle;
	// sollte noch ein set werden
	UnitNode child;
	static Graph graph;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public UnitConnectionEditor(Graph graphData) {
		graph = graphData;
		setSizeFull();
		setModal(true);
		setResizable(false);
		setClosable(true);
		setHeight(32.0f, Unit.PERCENTAGE);
		setWidth(35.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);
		Component title = buildTitle();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.TOP_LEFT);

		Component unitChoiseBoxes = buildUnitsChoiceBoxes();
		content.addComponent(unitChoiseBoxes);
		content.setComponentAlignment(unitChoiseBoxes, Alignment.TOP_LEFT);

		Component footer = buildFooter();
		content.addComponent(footer);
		content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}

	private Component buildUnitsChoiceBoxes() {
		VerticalLayout layout = new VerticalLayout();
		Label selectUnits = new Label(lang.getString("SelectLearningUnits"));
		layout.addComponent(selectUnits);

		HorizontalLayout comboBoxes = new HorizontalLayout();
		comboBoxes.setSpacing(true);

		ComboBox selectPredecessor = new ComboBox(lang.getString("From"));
		comboBoxes.addComponent(selectPredecessor);
		for (UnitNode currentNode : graph.getUnitCollection()) {
			selectPredecessor.addItem(currentNode.getUnitNodeTitle());
		}
		selectPredecessor.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				parent = graph.getNodeByName(selectPredecessor.getValue()
						.toString());
			}
		});

		ComboBox selectSuccessor = new ComboBox(lang.getString("To"));
		comboBoxes.addComponent(selectSuccessor);
		for (UnitNode currentNode : graph.getUnitCollection()) {
			selectSuccessor.addItem(currentNode.getUnitNodeTitle());
		}
		selectSuccessor.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				child = graph.getNodeByName(selectSuccessor.getValue()
						.toString());
			}
		});

		layout.addComponent(comboBoxes);
		return layout;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);
		footer.setSpacing(true);

		Button disconnect = new Button(lang.getString("Disconnect"));
		disconnect.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				graph.deleteConnection(parent, child);
				CourseEditorView.refreshGraph(graph);
				close();
				Notification success = new Notification(lang
						.getString("UnitsAreDisconnectedSuccessfully"));
				success.setDelayMsec(1000);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
				success.show(Page.getCurrent());

			}
		});
		footer.addComponent(disconnect);
		footer.setComponentAlignment(disconnect, Alignment.BOTTOM_LEFT);

		Button connect = new Button(lang.getString("Connect"));
		connect.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				graph.addConnection(parent, child);
				CourseEditorView.refreshGraph(graph);
				close();
				Notification success = new Notification(lang
						.getString("UitsAreConnectedSuccessfully"));
				success.setDelayMsec(1000);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
				success.show(Page.getCurrent());

			}
		});
		footer.addComponent(connect);
		footer.setComponentAlignment(connect, Alignment.BOTTOM_RIGHT);
		return footer;
	}

	private Component buildTitle() {
		Label title = new Label(lang.getString("ConnectUnits"));
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	public static void refreshData(Graph graphData) {
		graph = graphData;

	}
}
