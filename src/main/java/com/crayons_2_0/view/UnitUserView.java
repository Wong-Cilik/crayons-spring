package com.crayons_2_0.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.UnitPageLayout;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UnitUserView.VIEW_NAME)
public class UnitUserView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Unit User View";

	UnitPageLayout page;
	
	@Autowired
	CurrentCourses currentCourse;
	
	@Autowired
	UnitService unitService;

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
	}
	
	public UnitUserView() {
		
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
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
