package com.crayons_2_0.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.UnitPageLayout;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UnitUserView.VIEW_NAME)
public class UnitUserView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Unit User View";

	UnitPageLayout page;
	private ComboBox selectNextUnit;
	private ComboBox selectPreviousUnit;
	private Button next;
	private Button previous;
	
	@Autowired
	CurrentCourses currentCourse;
	
	@Autowired
	UnitService unitService;

	@Autowired
	CourseService courseService;

	@PostConstruct
	void init() {
		setSizeFull();

		page = new UnitPageLayout(false);
		page.setWidth(100.0f, Unit.PERCENTAGE);
		page.setStyleName("canvas");
		addComponent(page);
		setExpandRatio(page, 8);

		Component footer = buildFooter();
		footer.setSizeFull();
		addComponent(footer);
		setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
		setExpandRatio(footer, 1);
		setReadOnly(true);
	}

	public void refresh (UnitPageLayout upl) {
		removeAllComponents();

		page = upl;
		page.setWidth(100.0f, Unit.PERCENTAGE);
		page.setStyleName("canvas");
		addComponent(page);
		setExpandRatio(page, 8);

		Component footer = buildFooter();
		footer.setSizeFull();
		addComponent(footer);
	}
	
	public void refresh() {
		page.replaceAllComponent(unitService.getUnitData(CurrentCourses
				.getInstance().getUnitTitle(), CurrentCourses.getInstance()
				.getTitle()), false);
		
		String title = CurrentCourses.getInstance().getTitle();
		Graph g = courseService.getCourseData(title);
		
		System.out.println(CurrentCourses.getInstance().getUnitTitle().split("#")[1]);
		UnitNode unit = g.getNodeByName(CurrentCourses.getInstance().getUnitTitle().split("#")[1]);
		
		Set <UnitNode> unitSet = unit.getParentNodes();
		
		selectNextUnit.removeAllItems();
		for (UnitNode tmpUnit : unitSet) {
			selectNextUnit.addItem(tmpUnit.getUnitNodeTitle());
		}
	}
	
	public UnitUserView() {
		
	}

	private Component buildFooter() {
		
		next = new Button("next", FontAwesome.ARROW_RIGHT);
		next.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				CurrentCourses.getInstance().setUnitTitle(selectNextUnit.getValue().toString());
				refresh();
			}

		});
		
		previous = new Button("previous", FontAwesome.ARROW_LEFT);
		previous.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
			}

		});
		
		selectNextUnit = new ComboBox();
		selectPreviousUnit  = new ComboBox();
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(previous);
		footer.addComponent(selectPreviousUnit);
		footer.addComponent(selectNextUnit);
		footer.addComponent(next);
		footer.setSpacing(true);
		footer.setMargin(true);
		footer.setSizeFull();
		
		return footer;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refresh();
	}
}
