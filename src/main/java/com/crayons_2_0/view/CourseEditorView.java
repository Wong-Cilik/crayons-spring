package com.crayons_2_0.view;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons.view.dagred3.Dagre;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

/**
 * Course editor view consists of a learning graph and a set of buttons for it's
 * modification. It allows an author to modify the course by adding/removing
 * learning units and changing the dependencies between them and open unit
 * editor to modify a unit.
 *
 */
@SuppressWarnings("serial")
@SpringUI
@ViewScope
@SpringComponent
public class CourseEditorView extends VerticalLayout implements View {

	private @Autowired
	CourseService courseService;

	private @Autowired
	UnitService unitService;

	private static Graph graphData;

	public static final String VIEW_NAME = "Learning Graph";
	private final static Dagre graph = new Dagre();

	private static ResourceBundle lang = LanguageService.getInstance().getRes();
	private static ComboBox selectUnit;

	

	/**
	 * general refresher
	 * 
	 * @param graphTmp
	 */
	public static void refreshGraph(Graph graphTmp) {
		selectUnit.removeAllItems();
		for (String tmp : graphTmp.getNodeNameList()) {
			if (!tmp.equals("Start") && !tmp.equals("End")) {
				selectUnit.addItem(tmp);
			}
		}
		graph.setGraph(graphTmp.getNodeNameList(), graphTmp.getEdgeSequence());
		graphData = graphTmp;
		graph.setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}
