package com.crayons_2_0.controller;

import com.crayons_2_0.view.CurrentCourseDummy;
import com.crayons_2_0.view.UnitEditorView;
import com.crayons_2_0.view.authorlib.AuthorlibraryForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class OpenUnitEditorListener implements Button.ClickListener {

	/**
	 * 
	 */
	@Override
	public void buttonClick(ClickEvent event) {

		Button source = event.getButton();
		AuthorlibraryForm parent = (AuthorlibraryForm) source.getParent();

		TabSheet coursesTabSheet = parent.getCoursesTabSheet();

		CurrentCourseDummy.getInstance().setCourseTitle(
				coursesTabSheet.getSelectedTab().getCaption());

		UI.getCurrent().getNavigator().navigateTo(UnitEditorView.VIEW_NAME);

	}

}
