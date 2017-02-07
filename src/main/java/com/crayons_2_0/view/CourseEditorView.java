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

@SuppressWarnings("serial")
@SpringUI
public class CourseEditorView extends VerticalLayout implements View {
    
    public static final String VIEW_NAME = "Learning Graph";
    //javascript element 
    final static Dagre graph = new Dagre();
    public CourseEditorView() {
        setSizeFull();
        
        /*Graph graph = new Graph();
        User user = new User("a", "a", null);
        GraphViewCreator gvc = new GraphViewCreator(graph, user);
        addComponent(gvc.getGraphView());*/
        
        //JavaScribt Element
        
        //TODO fetch graph from DB, currently using Dummy graph
        Graph dummyGraph = buildExampleGraph();
        
        //put Nodenames in an Array for javascribt
        
        // sets the state of the javascript element
        graph.setGraph(dummyGraph.getNodeNameList(),dummyGraph.getEdgeSequence());
        graph.setSizeFull();
        addComponent(graph);
        setComponentAlignment(graph, Alignment.TOP_CENTER);
        
        Component footer = buildFooter();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
    }
    //Example Graph for UI Development
    public static Graph buildExampleGraph(){
        
        
        String dummy = "dummy";
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //CrayonsUser dummyUser = new User(dummy, "pass", true, true, false, false, authorities);
        CrayonsUser dummyUser = new CrayonsUser("first", "last", "dummy", "pass", true, true, false, false, authorities);
        Course dummyCourse = new Course(dummy,dummyUser);
        Graph dummyGraph = new Graph(dummyCourse);
        
        //@DB UnitNodes will be created and added to their courses in the UnitCreationWindow
        UnitNode one = new UnitNode("one", dummyGraph.getStartUnit(), dummyGraph);
        UnitNode two = new UnitNode("two", dummyGraph.getStartUnit(), dummyGraph);
        UnitNode three = new UnitNode("three", two, dummyGraph);
        UnitNode four = new UnitNode("four", two, dummyGraph);
        UnitNode five = new UnitNode("five", three, dummyGraph);
        UnitNode six = new UnitNode("six", four, dummyGraph);
        UnitNode seven = new UnitNode("seven", five, dummyGraph);
        
        dummyGraph.addUnit(one, one.getParentNodes());
        dummyGraph.addUnit(two, two.getParentNodes());
        dummyGraph.addUnit(three, three.getParentNodes());
        dummyGraph.addUnit(four, four.getParentNodes());
        dummyGraph.addUnit(five, five.getParentNodes());
        dummyGraph.addUnit(six, six.getParentNodes());
        dummyGraph.addUnit(seven, seven.getParentNodes());
        
        
        
        return dummyGraph;
        
        
        
        
    }
    public static void refreshGraph(Graph graphTmp){
        graph.setGraph(graphTmp.getNodeNameList(),graphTmp.getEdgeSequence());
        
    }
    
    private Component buildFooter(){
        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(true);
        footer.setSizeFull();
        footer.setSpacing(true);
        Component backButton = buildBackButton();
        footer.addComponent(backButton);
        footer.setComponentAlignment(backButton, Alignment.BOTTOM_LEFT);
        Component editMenu = buildEditMenu();
        footer.addComponent(editMenu);
        footer.setComponentAlignment(editMenu, Alignment.BOTTOM_RIGHT);
        
        return footer;
    }
    
    private Component buildBackButton() {
        Button button = new Button("Back");
        button.setIcon(FontAwesome.ARROW_LEFT);
        button.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
        button.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(Authorlibrary.VIEW_NAME);
            }
        });
        return button;
    }
    
    private Component buildEditMenu() {
        HorizontalLayout editMenuLayout = new HorizontalLayout();
        editMenuLayout.setSpacing(true);
        editMenuLayout.setWidthUndefined();

        editMenuLayout.addComponent(buildEditMenuItem(EditMenuItemType.ADD_UNIT, new UnitCreationWindow()));
        editMenuLayout.addComponent(buildEditMenuItem(EditMenuItemType.EDIT_UNIT, new SelectUnitForEditWindow()));
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
                FontAwesome.TRASH), EDIT_UNIT("Edit", FontAwesome.PENCIL);
        
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

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}
