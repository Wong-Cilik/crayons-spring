package com.crayons_2_0.component;

import java.util.HashSet;
import java.util.Set;

import com.crayons_2_0.model.graph.Node;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial" })
public final class UnitCreationWindow extends Window {
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
        
        Component nameField = buildTitleField();
        content.addComponent(nameField);
        content.setComponentAlignment(nameField, Alignment.MIDDLE_LEFT);
        
        //content.addComponent(buildDescription());
        
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
        
        ComboBox selectPredecessor = new ComboBox("Select the previous unit");
        comboBoxes.addComponent(selectPredecessor);
        //Set<Node> predecessors = new HashSet<Node>();
        //predecessors.add(new Node("Node 1"));
        //predecessors.add(new Node("Node 2"));
        //selectPredecessor.addItems(predecessors);
        selectPredecessor.addItem("Node 1");
        selectPredecessor.addItem("Node 2");
        
        ComboBox selectSuccessor = new ComboBox("Select the next unit");
        comboBoxes.addComponent(selectSuccessor);
        //Set<Node> successors = new HashSet<Node>();
        //successors.add(new Node("Node 3"));
        //successors.add(new Node("Node 4"));
        //selectSuccessor.addItems(successors);
        selectSuccessor.addItem("Node 3");
        selectSuccessor.addItem("Node 4");
        
        return comboBoxes;
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
                close();
                Notification success = new Notification(
                        "Unit created successfully");
                success.setDelayMsec(2000);
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

    private Component buildUnitTypeChoice() {
        VerticalLayout unitTypeChoice = new VerticalLayout();
        CheckBox learningUnit = new CheckBox(UnitType.LEARNING_UNIT.getTitle());
        CheckBox testUnit = new CheckBox(UnitType.TEST_UNIT.getTitle());

        learningUnit.addValueChangeListener(event -> // Java 8
            testUnit.setValue(! learningUnit.getValue()));

        testUnit.addValueChangeListener(event -> // Java 8
            learningUnit.setValue(! testUnit.getValue()));
        
        unitTypeChoice.addComponents(learningUnit, testUnit);
        return unitTypeChoice;
    }

    private Component buildTitleField() {
        TextField titleField = new TextField("Unit title");
        titleField.setValue("unit title");
        return titleField;
    }

    private Component buildTitle() {
        Label title = new Label("Create a new unit");
        title.addStyleName(ValoTheme.LABEL_H2);
        return title;
    }
    
    /*private Component buildDescription() {
        return null;
    }*/
    
    public enum UnitType {
        LEARNING_UNIT("Learning unit"), TEST_UNIT("Test unit"); // add description
        
        private final String title;
        
        UnitType(final String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
