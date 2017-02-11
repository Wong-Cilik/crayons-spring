package com.crayons_2_0.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.crayons.view.dagred3.Dagre;
import com.crayons_2_0.model.Authority;
import com.crayons_2_0.component.GraphViewCreator;
import com.crayons_2_0.component.SelectUnitForEditWindow;
import com.crayons_2_0.component.SelectUnitToLearnWindow;
import com.crayons_2_0.component.UnitCreationWindow;
import com.crayons_2_0.component.UnitConnectionEditor;
import com.crayons_2_0.component.DeleteVerification;
import com.vaadin.annotations.Theme;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringUI
public class CourseUserView extends VerticalLayout implements View {
    
    public static final String VIEW_NAME = "User Learning Graph";

    public CourseUserView() {
        setSizeFull();
        
        //TODO: generate graph for a particular user
        
        Component footer = buildFooter();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
    }
    
    private Component buildFooter(){
        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(true);
        footer.setSizeFull();
        footer.setSpacing(true);
        
        Component backButton = buildBackButton();
        footer.addComponent(backButton);  
        footer.setComponentAlignment(backButton, Alignment.BOTTOM_LEFT);
        
        Component chooseUnitButton = buildChooseUnitButton();
        footer.addComponent(chooseUnitButton);
        footer.setComponentAlignment(chooseUnitButton, Alignment.BOTTOM_RIGHT);
        
        return footer;
    }
    
    private Component buildBackButton() {
        Button backButton = new Button("Back", FontAwesome.ARROW_LEFT);
        backButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(UserlibraryView.VIEW_NAME);
            }
            
        });
        return backButton;
    }
    
    private Component buildChooseUnitButton() {
        Button backButton = new Button("Choose unit");
        backButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        backButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().addWindow(new SelectUnitToLearnWindow());
            }
            
        });
        return backButton;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}