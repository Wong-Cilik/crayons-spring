package com.crayons_2_0.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.UnitPageLayout;
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
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UnitUserView.VIEW_NAME)
public class UnitUserView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Unit User View";

	private UnitPageLayout page;
	private ComboBox selectNextUnit;
	private ComboBox selectPreviousUnit;
	private Button next;
	private Button previous;


	private @Autowired
	UnitService unitService;

	private @Autowired
	CourseService courseService;

	@PostConstruct
	void init() {
		setSizeFull();
		setSpacing(true);
		page = new UnitPageLayout(false);
		page.setWidth(100.0f, Unit.PERCENTAGE);
		page.setStyleName("canvas");
		addComponent(page);
		setComponentAlignment(page, Alignment.TOP_CENTER);
		setExpandRatio(page, 8);
	
		Component footer = buildFooter();
		addComponent(footer);
		footer.setSizeUndefined();
		setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
		setReadOnly(true);
	}

	private void refresh() {
		page.replaceAllComponent(unitService.getUnitData(CurrentCourses
				.getInstance().getUnitTitle(), CurrentCourses.getInstance()
				.getTitle()), false);

		String title = CurrentCourses.getInstance().getTitle();
		UnitNode unit = courseService.getCourseData(title).getNodeByName(
				CurrentCourses.getInstance().getUnitTitle().split("#")[1]);

		selectPreviousUnit.removeAllItems();
		for (UnitNode tmpUnit : unit.getParentNodes()) {
			if (!tmpUnit.getUnitNodeTitle().equals("End")
					&& !tmpUnit.getUnitNodeTitle().equals("Start")) {
				selectPreviousUnit.addItem(tmpUnit.getUnitNodeTitle());
			}
		}

		selectNextUnit.removeAllItems();
		for (UnitNode tmpUnit : unit.getChildNodes()) {
			if (!tmpUnit.getUnitNodeTitle().equals("End")
					&& !tmpUnit.getUnitNodeTitle().equals("Start")) {
				selectNextUnit.addItem(tmpUnit.getUnitNodeTitle());
			}
		}

		if (selectNextUnit.size() == 0) {
			selectNextUnit.setEnabled(false);
			next.setEnabled(false);
		} else {
			selectNextUnit.setEnabled(true);
			next.setEnabled(true);
		}

		if (selectPreviousUnit.size() == 0) {
			selectPreviousUnit.setEnabled(false);
			previous.setEnabled(false);
		} else {
			selectPreviousUnit.setEnabled(true);
			previous.setEnabled(true);
		}
	}

	public UnitUserView() {

	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addStyleName("userview-footer");
		footer.setSizeFull();
		Component left = buildLeft();
		footer.addComponent(left);
		footer.setComponentAlignment(left, Alignment.MIDDLE_LEFT);
		left.addStyleName("userview-footer-left");
		Component right = buildRight();
		right.addStyleName("userview-footer-right");
		footer.addComponent(right);
		footer.setComponentAlignment(right, Alignment.MIDDLE_RIGHT);
		return footer;
	}

	private Component buildLeft() {
		HorizontalLayout left = new HorizontalLayout();
		left.setSpacing(true);
		previous = new Button("previous", FontAwesome.ARROW_LEFT);
		left.addComponent(previous);
		previous.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (selectPreviousUnit.getValue() != null) {
					CurrentCourses.getInstance().setUnitTitle(
							CurrentCourses.getInstance().getTitle() + "#"
									+ selectPreviousUnit.getValue().toString());
					refresh();
				}
			}
		});

		selectPreviousUnit = new ComboBox();
		selectPreviousUnit.setNullSelectionAllowed(false);
		left.addComponent(selectPreviousUnit);
		
		return left;
	}

	private Component buildRight() {
		HorizontalLayout right = new HorizontalLayout();
		right.setSpacing(true);
		right.setWidthUndefined();

		selectNextUnit = new ComboBox();
		selectNextUnit.setNullSelectionAllowed(false);
		right.addComponent(selectNextUnit);

		
		next = new Button("next", FontAwesome.ARROW_RIGHT);
		right.addComponent(next);
		next.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (selectNextUnit.getValue() != null) {
					CurrentCourses.getInstance().setUnitTitle(
							CurrentCourses.getInstance().getTitle() + "#"
									+ selectNextUnit.getValue().toString());
					refresh();
				}
			}

		});

		return right;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		refresh();
	}
}
