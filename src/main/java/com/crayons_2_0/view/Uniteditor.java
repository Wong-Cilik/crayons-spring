package com.crayons_2_0.view;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.UnitPageLayout;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

/**
 * Unit editor view consists of representation of a learning unit and different
 * editing tools. It allows an author to modify the learning unit using drag and
 * drop editor.
 *
 */
@SuppressWarnings("serial")
@SpringView(name = Uniteditor.VIEW_NAME)
@SpringComponent
public class Uniteditor extends VerticalLayout implements View {

	

	static final String VIEW_NAME = "Unit Editor";
	private static ResourceBundle lang = LanguageService.getInstance().getRes();

	private static UnitPageLayout page;

	private @Autowired
	UnitService unitService;

	

	/**
	 * Builds together several components of the unit editor view.
	 */
	public Uniteditor() {

	}

	

	/**
	 * Defines title and icon for the page items: text, image, and multiple
	 * choice exercise.
	 */
	public enum PageItemType {
		TEXT(lang.getString("TextBlock"), FontAwesome.FONT), IMAGE(lang
				.getString("Image"), FontAwesome.FILE_IMAGE_O), MULTIPLE_CHOICE(
				lang.getString("MultipleChoiceExercise"),
				FontAwesome.CHECK_SQUARE_O);

		private final String title;
		private final FontAwesome icon;

		PageItemType(final String title, final FontAwesome icon) {
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

	private void refresh() {
		page.replaceAllComponent(unitService.getUnitData(CurrentCourses
				.getInstance().getUnitTitle(), CurrentCourses.getInstance()
				.getTitle()), true);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (unitService.hasData()) {
			refresh();
		} else {
			page.removeAllComponent();
		}
	}

	
}
