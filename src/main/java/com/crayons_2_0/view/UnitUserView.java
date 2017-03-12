package com.crayons_2_0.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.UnitPageLayout;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UnitUserView.VIEW_NAME)
public class UnitUserView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Unit User View";

	private UnitPageLayout page;
	
	
	
	private @Autowired
	UnitService unitService;

	

	
	
	private void refresh() {
		page.replaceAllComponent(unitService.getUnitData(CurrentCourses
				.getInstance().getUnitTitle(), CurrentCourses.getInstance()
				.getTitle()), false);
	}
	
	public UnitUserView() {
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refresh();
	}
}
