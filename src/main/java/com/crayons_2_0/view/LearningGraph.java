package com.crayons_2_0.view;

<<<<<<< HEAD
import com.crayons_2_0.component.UnitCreationWindow;
import com.crayons_2_0.component.UnitConnectionEditor;
import com.crayons_2_0.component.DeleteVerification;
import com.vaadin.annotations.Theme;
=======
import com.crayons_2_0.component.AddUnitWindow;
import com.crayons_2_0.component.ConnectWindow;
import com.crayons_2_0.component.DeleteWindow;
import com.crayons_2_0.model.graph.GraphCreator;
>>>>>>> branch 'master' of https://github.com/jubusch/crayons-spring.git
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
public class LearningGraph extends VerticalLayout {
    
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "Learning Graph";
    
    
    public LearningGraph() {
        setSizeFull();
        addStyleName("editor");
        
        // get id's of the current course and user 
        /*long graphID = 0;
        long userID = 0;
        addComponent(GraphCreator.getGraphView(graphID, userID));
        */
        Component editMenu = buildEditMenu();
        addComponent(editMenu);
        setComponentAlignment(editMenu, Alignment.BOTTOM_LEFT);
    }
    
    private Component buildEditMenu() {
        HorizontalLayout editMenuLayout = new HorizontalLayout();
        editMenuLayout.setSpacing(true);
        editMenuLayout.setWidthUndefined();

        editMenuLayout.addComponent(buildEditMenuItem(EditMenuItemType.ADD_UNIT, new UnitCreationWindow()));
        editMenuLayout.addComponent(buildEditMenuItem(EditMenuItemType.CONNECT_UNITS, new UnitConnectionEditor()));
        editMenuLayout.addComponent(buildEditMenuItem(EditMenuItemType.DELETE_UNIT, new DeleteVerification()));
        editMenuLayout.setSpacing(true);
        
        return editMenuLayout;
    }
    
    private Button buildEditMenuItem(EditMenuItemType item, Window window) {
        Button button = new Button(item.getTitle());
        button.setIcon(item.getIcon());
        button.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
        button.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().addWindow(window);
            }
        });
        return button;
    }
    
    public enum EditMenuItemType {
        ADD_UNIT("Add", FontAwesome.PLUS), CONNECT_UNITS("Connect",
                FontAwesome.LINK), DELETE_UNIT("Delete",
                FontAwesome.TRASH);
        
        private final String title;
        private final FontAwesome icon;

        EditMenuItemType(final String title, final FontAwesome icon) {
            this.title = title;
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public FontAwesome getIcon() {
            return icon;
        }
    }
}
