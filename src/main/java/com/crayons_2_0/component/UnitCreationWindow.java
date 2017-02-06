package com.crayons_2_0.component;

import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.view.CourseEditorView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial" })
public final class UnitCreationWindow extends Window {

    // @DB

    // sollte noch ein set werden
    UnitNode parent;
    // wird noch ausgebessert
    String unitTitle;
    // sollte noch ein set werden
    UnitNode child;
    // TODO von DB holen
    Graph dummyGraph = CourseEditorView.buildExampleGraph();

    public UnitCreationWindow() {
        setSizeFull();
        setModal(true);
        setResizable(false);
        setClosable(true);
        setHeight(50.0f, Unit.PERCENTAGE);
        setWidth(40.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(true);
        setContent(content);

        Component title = buildTitle();
        content.addComponent(title);
        content.setComponentAlignment(title, Alignment.TOP_CENTER);

        TextField titleField = new TextField("Unit title");
        titleField.setCaption("unit title");
        titleField.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                unitTitle = titleField.getValue();
            }
        });

        content.addComponent(titleField);

        content.setComponentAlignment(titleField, Alignment.MIDDLE_LEFT);

        // content.addComponent(buildDescription());

        Component unitTypeChoice = buildUnitTypeChoice();
        content.addComponent(unitTypeChoice);
        content.setComponentAlignment(unitTypeChoice, Alignment.MIDDLE_LEFT);

        Component connectedUnitsChoice = buildConnectedUnitsChoice();
        content.addComponent(connectedUnitsChoice);
        content.setComponentAlignment(connectedUnitsChoice, Alignment.MIDDLE_LEFT);

        Component footer = buildFooter();
        content.addComponent(footer);
        content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
    }

    private Component buildConnectedUnitsChoice() {
        HorizontalLayout comboBoxes = new HorizontalLayout();
        comboBoxes.setMargin(true);
        comboBoxes.setSpacing(true);

        // TODO fetch graph from DB, currently using DummyGraph

        ComboBox selectPredecessor = new ComboBox("Select the previous unit");
        comboBoxes.addComponent(selectPredecessor);

        // Set<Node> predecessors = new HashSet<Node>();
        // predecessors.add(new Node("Node 1"));
        // predecessors.add(new Node("Node 2"));
        // selectPredecessor.addItems(predecessors);
        for (UnitNode currentNode : dummyGraph.getUnitCollection()) {
            if (currentNode.getUnitNodeTitle() != "End")
                selectPredecessor.addItem(currentNode.getUnitNodeTitle());
        }
        selectPredecessor.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                parent = dummyGraph.getNodeByName(selectPredecessor.getValue().toString());
            }
        });

        ComboBox selectSuccessor = new ComboBox("Select the next unit");
        comboBoxes.addComponent(selectSuccessor);
        // Set<Node> successors = new HashSet<Node>();
        // successors.add(new Node("Node 3"));
        // successors.add(new Node("Node 4"));
        // selectSuccessor.addItems(successors);

        for (UnitNode currentNode : dummyGraph.getUnitCollection()) {
            selectSuccessor.addItem(currentNode.getUnitNodeTitle());
        }
        selectSuccessor.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                child = dummyGraph.getNodeByName(selectSuccessor.getValue().toString());
            }
        });

        return comboBoxes;
    }

    private Component buildTitle() {
        Label title = new Label("Create a new unit");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }

    private Component buildUnitTypeChoice() {
        VerticalLayout unitTypeChoice = new VerticalLayout();
        CheckBox learningUnit = new CheckBox(UnitType.LEARNING_UNIT.getTitle());
        CheckBox testUnit = new CheckBox(UnitType.TEST_UNIT.getTitle());

        learningUnit.addValueChangeListener(event -> // Java 8
        testUnit.setValue(!learningUnit.getValue()));

        testUnit.addValueChangeListener(event -> // Java 8
        learningUnit.setValue(!testUnit.getValue()));

        unitTypeChoice.addComponents(learningUnit, testUnit);
        return unitTypeChoice;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(true);

        Button ok = new Button("Create");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UnitNode newUnit = new UnitNode(unitTitle, parent, child, dummyGraph);

                dummyGraph.addUnit(newUnit, parent);
                CourseEditorView.refreshGraph(dummyGraph);
                close();
                Notification success = new Notification("Unit created successfully");
                success.setDelayMsec(1000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_CENTER);
        return footer;
    }

    /*
     * private Component buildDescription() { return null; }
     */

    public enum UnitType {
        LEARNING_UNIT("Learning unit"), TEST_UNIT("Test unit"); // add
                                                                // description

        private final String title;

        UnitType(final String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
