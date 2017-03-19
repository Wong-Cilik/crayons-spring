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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class UnitConnectionEditor extends Window {
	// @DB

	// sollte noch ein set werden
	private UnitNode parent;

	// sollte noch ein set werden
	private UnitNode child;
	private static Graph graph;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public UnitConnectionEditor(Graph graphData) {
		graph = graphData;
		setSizeFull();
		setModal(true);
		setDraggable(true);
		setResizable(false);
		setClosable(true);
		setHeight(70.0f, Unit.PERCENTAGE);
		setWidth(35.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);
		Component title = buildTitle();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.TOP_LEFT);
		content.addComponent(buildUnitsChoiceDisconnect());
		content.addComponent(buildUnitsChoiceConnect());
		// content.setComponentAlignment(unitChoiceBoxes, Alignment.TOP_LEFT);

		// Component footer = buildFooter();
		// content.addComponent(footer);
		// content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}

	private Component buildUnitsChoiceDisconnect() {
		VerticalLayout layout = new VerticalLayout();
		Label selectUnits = new Label(lang.getString("SelectLearningUnits"));
		layout.addComponent(selectUnits);

		HorizontalLayout comboBoxes = new HorizontalLayout();
		comboBoxes.setSpacing(true);

		ComboBox selectPredecessor = new ComboBox(lang.getString("From"));
		comboBoxes.addComponent(selectPredecessor);
		for (UnitNode currentNode : graph.getUnitCollection()) {
			if (currentNode.getUnitNodeTitle() != "Start"
					&& currentNode.getUnitNodeTitle() != "End")
				selectPredecessor.addItem(currentNode.getUnitNodeTitle());
		}
		selectPredecessor.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				parent = graph.getNodeByName(selectPredecessor.getValue()
						.toString());
				if (parent.getUnitNodeTitle().equals("End")) {
					Notification failure = new Notification(lang
							.getString("TheEndNodeCantBeAParentNode"));
					failure.setDelayMsec(-1);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
				}
			}
		});

		ComboBox selectSuccessor = new ComboBox(lang.getString("To"));
		comboBoxes.addComponent(selectSuccessor);
		for (UnitNode currentNode : graph.getUnitCollection()) {
			if (currentNode.getUnitNodeTitle() != "Start"
					&& currentNode.getUnitNodeTitle() != "End")
				selectSuccessor.addItem(currentNode.getUnitNodeTitle());
		}
		selectSuccessor.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				child = graph.getNodeByName(selectSuccessor.getValue()
						.toString());
				boolean helper = true;
				if (child.getUnitNodeTitle().equals("Start")) {
					Notification failure = new Notification(lang
							.getString("TheStartNodeCantBeAChildNode"));
					failure.setDelayMsec(-1);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper && !child.getParentNodes().contains(parent)) {
					Notification failure = new Notification(
							lang.getString("TheSelectedUnitsAreNotConnected.OnlyExistingConnectionCanBeDeleted"));
					failure.setDelayMsec(-1);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper
						&& parent.getUnitNodeTitle().equals("Start")
						&& !(parent.getUnitNodeTitle().equals("Start") && child
								.getUnitNodeTitle().equals("Start"))
						&& graph.getStartUnit().getChildNodes().size() < 2) {
					Notification failure = new Notification(lang
							.getString("ThisConnectionCantBeDeleted"));
					failure.setDelayMsec(1000);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper
						&& child.getUnitNodeTitle().equals("End")
						&& !(parent.getUnitNodeTitle().equals("End") && child
								.getUnitNodeTitle().equals("End"))
						&& graph.getEndUnit().getParentNodes().size() < 2) {
					Notification failure = new Notification(lang
							.getString("ThisConnectionCantBeDeleted"));
					failure.setDelayMsec(1000);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}

				for (UnitNode currentNode : graph.getStartUnit()
						.getChildNodes())
				// boolean test =
				// child.getParentNodes().contains(graph.getStartUnit());
				{
					// System.out.println(currentNode.getUnitNodeTitle());
				}
				for (UnitNode currentNode : graph.getEndUnit().getParentNodes())
				// boolean test =
				// child.getParentNodes().contains(graph.getStartUnit());
				{
					// System.out.println(currentNode.getUnitNodeTitle());
				}
			}
		});
		Button disconnect = new Button(lang.getString("Disconnect"));
		disconnect.setStyleName(ValoTheme.BUTTON_DANGER);
		disconnect.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				boolean helper = true;
				if (child.getUnitNodeTitle().equals("Start")) {
					Notification failure = new Notification(lang
							.getString("TheStartNodeCantBeAChildNode"));
					failure.setDelayMsec(1500);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (parent.getUnitNodeTitle().equals("End")) {
					Notification failure = new Notification(lang
							.getString("TheEndNodeCantBeAParentNode"));
					failure.setDelayMsec(1500);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper && !child.getParentNodes().contains(parent)) {
					Notification failure = new Notification(
							lang.getString("TheSelectedUnitsAreNotConnected.OnlyExistingConnectionCanBeDeleted"));
					failure.setDelayMsec(-1);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper
						&& parent.getUnitNodeTitle().equals("Start")
						&& !(parent.getUnitNodeTitle().equals("Start") && child
								.getUnitNodeTitle().equals("Start"))
						&& graph.getStartUnit().getChildNodes().size() < 2) {
					Notification failure = new Notification(lang
							.getString("ThisConnectionCantBeDeleted"));
					failure.setDelayMsec(1000);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper
						&& child.getUnitNodeTitle().equals("End")
						&& !(parent.getUnitNodeTitle().equals("End") && child
								.getUnitNodeTitle().equals("End"))
						&& graph.getEndUnit().getParentNodes().size() < 2) {
					Notification failure = new Notification(lang
							.getString("ThisConnectionCantBeDeleted"));
					failure.setDelayMsec(1000);
					failure.setStyleName("bar failure");
					failure.setPosition(Position.MIDDLE_CENTER);
					failure.show(Page.getCurrent());
					helper = false;
				}
				if (helper && child.getParentNodes().contains(parent)) {
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

			}
		});

		layout.addComponent(comboBoxes);
		layout.addComponent(disconnect);
		layout.setSpacing(true);
		return layout;
	}

	private Component buildUnitsChoiceConnect() {
		VerticalLayout layout = new VerticalLayout();
		Label selectUnits = new Label(lang.getString("SelectLearningUnits"));
		layout.addComponent(selectUnits);

		HorizontalLayout comboBoxes = new HorizontalLayout();
		comboBoxes.setSpacing(true);

		ComboBox selectPredecessor = new ComboBox(lang.getString("From"));
		comboBoxes.addComponent(selectPredecessor);
		for (UnitNode currentNode : graph.getUnitCollection()) {
			if (currentNode.getUnitNodeTitle() != "Start"
					&& currentNode.getUnitNodeTitle() != "End") {
				selectPredecessor.addItem(currentNode.getUnitNodeTitle());
			}
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
			if (currentNode.getUnitNodeTitle() != "Start"
					&& currentNode.getUnitNodeTitle() != "End") {
				selectSuccessor.addItem(currentNode.getUnitNodeTitle());
			}
		}
		selectSuccessor.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				child = graph.getNodeByName(selectSuccessor.getValue()
						.toString());
			}
		});
		Button connect = new Button(lang.getString("Connect"));
		connect.setStyleName(ValoTheme.BUTTON_PRIMARY);
		connect.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				graph.addConnection(parent, child);
				CourseEditorView.refreshGraph(graph);
				close();
				UI.getCurrent().getPage().reload();
				Notification success = new Notification(lang
						.getString("UnitsAreConnectedSuccessfully"));
				success.setDelayMsec(500);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
				success.show(Page.getCurrent());

			}
		});

		layout.addComponent(comboBoxes);
		layout.addComponent(connect);
		layout.setSpacing(true);
		return layout;
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
