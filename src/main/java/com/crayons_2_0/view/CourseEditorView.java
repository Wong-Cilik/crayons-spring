package com.crayons_2_0.view;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons.view.dagred3.Dagre;
import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.authentication.CurrentGraph;
import com.crayons_2_0.component.DeleteVerification;
import com.crayons_2_0.component.UnitConnectionEditor;
import com.crayons_2_0.component.UnitCreationWindow;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

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

	@Autowired
	CourseService courseService;

	@Autowired
	static
	UnitService unitService;

	static Graph graphData;

	public static final String VIEW_NAME = "Learning Graph";
	// javascript element
	final static Dagre graph = new Dagre();

	private static ResourceBundle lang = LanguageService.getInstance().getRes();
	private static ComboBox selectUnit;

	@PostConstruct
	void init() {
		setSizeFull();
		graphData = courseService.getDummyGraph();
		graph.setGraph(graphData.getNodeNameList(), graphData.getEdgeSequence());
		graph.setSizeFull();
		addComponent(graph);
		setComponentAlignment(graph, Alignment.TOP_CENTER);

		Component footer = buildFooter();
		addComponent(footer);
		footer.setSizeUndefined();
		setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}
	
	/**
	 * general refresher
	 * @param graphTmp
	 */
	public static void refreshGraph(Graph graphTmp) {
		selectUnit.removeAllItems();
		selectUnit.addItems(graphTmp.getNodeNameList());
		graph.setGraph(graphTmp.getNodeNameList(), graphTmp.getEdgeSequence());
		graphData = graphTmp;
	}
	
	/**
	 * refresher for UnitCreationWindow
	 * 
	 * @param graphTmp
	 * @param unitTitle
	 * @param courseTitle
	 */
	public static void refreshGraph(Graph graphTmp, String unitTitle, String courseTitle) {
		System.out.println(unitTitle + " " + courseTitle);
		unitService.storeUnitDummyData(unitTitle + "-" + courseTitle, CurrentCourses.getInstance().getTitle());
		selectUnit.removeAllItems();
		selectUnit.addItems(graphTmp.getNodeNameList());
		graph.setGraph(graphTmp.getNodeNameList(), graphTmp.getEdgeSequence());
		graphData = graphTmp;
	}

	/**
	 * Builds a footer which includes primary control buttons for the editor and
	 * the graph.
	 * 
	 * @return the footer
	 */
	private Component buildFooter() {
		CssLayout footer = new CssLayout();
		footer.addStyleName("courseeditor-footer");
		footer.setSizeFull();
		Component controlButtons = buildControlButtons();
		footer.addComponent(controlButtons);
		controlButtons.addStyleName("courseeditor-footer-left");
		Component editMenu = buildEditMenu();
		editMenu.addStyleName("courseeditor-footer-right");
		footer.addComponent(editMenu);
		return footer;
	}

	/**
	 * Builds control buttons which allows user to save the course and to return
	 * to the author library.
	 * 
	 * @return layout wit the control buttons
	 */
	private Component buildControlButtons() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		Button backButton = new Button(lang.getString("Back"),
				FontAwesome.ARROW_LEFT);
		layout.addComponent(backButton);
		backButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// if (unit was modified)
				UI.getCurrent().addWindow(new UnsavedChangesWindow());
				// else
				// UI.getCurrent().getNavigator().navigateTo(Authorlibrary.VIEW_NAME);
			}

		});
		Button save = new Button(lang.getString("Save"));
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		layout.addComponent(save);
		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				courseService.saveCourseData(graphData,
						CurrentCourses.getInstance().getTitle());
			}
		});
		return layout;
	}

	/**
	 * Builds a layout with control buttons for the graph
	 * 
	 * @return layout with control buttons
	 */
	private Component buildEditMenu() {
		HorizontalLayout editMenuLayout = new HorizontalLayout();
		editMenuLayout.setSpacing(true);
		editMenuLayout.setWidthUndefined();

		selectUnit = new ComboBox(lang.getString("SelectTheUnitForEdit"));
		for (UnitNode tmp : graphData.getUnitCollection()) {
			selectUnit.addItem(tmp.getUnitNodeTitle());
		}
		editMenuLayout.addComponent(selectUnit);
		// create buttons with refresh data
		Button unitCreationButton = new Button(
				EditMenuButtonType.ADD_UNIT.getTitle(),
				EditMenuButtonType.ADD_UNIT.getIcon());
		editMenuLayout.addComponent(unitCreationButton);
		unitCreationButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UnitCreationWindow.refreshData(graphData);
				UI.getCurrent().addWindow(new UnitCreationWindow(graphData));
			}
		});
		Button selectUnitForEdit = new Button(
				EditMenuButtonType.EDIT_UNIT.getTitle(),
				EditMenuButtonType.EDIT_UNIT.getIcon());
		editMenuLayout.addComponent(selectUnitForEdit);
		selectUnitForEdit.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				CurrentCourses.getInstance().setUnitTitle(
						(String) selectUnit.getValue());
				Uniteditor.refreshLayout(unitService.getUnitData(
						(String) selectUnit.getValue(), CurrentGraph
								.getInstance().getCourseTitle()));
				UI.getCurrent().getNavigator().navigateTo(Uniteditor.VIEW_NAME);
			}
		});

		Button unitConnection = new Button(
				EditMenuButtonType.CONNECT_UNITS.getTitle(),
				EditMenuButtonType.CONNECT_UNITS.getIcon());
		editMenuLayout.addComponent(unitConnection);
		unitConnection.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UnitConnectionEditor.refreshData(graphData);
				UI.getCurrent().addWindow(new UnitConnectionEditor(graphData));
			}
		});

		Button deleteUnit = new Button(
				EditMenuButtonType.DELETE_UNIT.getTitle(),
				EditMenuButtonType.DELETE_UNIT.getIcon());
		editMenuLayout.addComponent(deleteUnit);
		deleteUnit.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				DeleteVerification.refreshData(graphData);
				UI.getCurrent().addWindow(new DeleteVerification(graphData));
			}
		});

		return editMenuLayout;
	}

	/**
	 * Defines title and icon for the edit menu buttons.
	 */
	public enum EditMenuButtonType {
		ADD_UNIT(lang.getString("AddUnit"), FontAwesome.PLUS), CONNECT_UNITS(
				lang.getString("ModifyConnections"), FontAwesome.LINK), DELETE_UNIT(
				lang.getString("DeleteUnit"), FontAwesome.TRASH), EDIT_UNIT(
				lang.getString("SelectUnit"), FontAwesome.PENCIL);

		private final String title;
		private final FontAwesome icon;

		EditMenuButtonType(final String title, final FontAwesome icon) {
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

	/**
	 * Dialog window which checks if the changes in the learning unit should be
	 * saved or not. Is called by a click on the back button.
	 */
	private class UnsavedChangesWindow extends Window {
		/**
		 * Builds together several components of the window.
		 */
		public UnsavedChangesWindow() {
			setSizeFull();
			setModal(true);
			setResizable(false);
			setClosable(false);
			setHeight(20.0f, Unit.PERCENTAGE);
			setWidth(40.0f, Unit.PERCENTAGE);

			VerticalLayout content = new VerticalLayout();
			content.setSizeFull();
			content.setMargin(true);
			setContent(content);

			Component title = buildTitle();
			title.setSizeFull();
			content.addComponent(title);
			content.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
			content.setExpandRatio(title, 2);

			Component footer = buildFooter();
			content.addComponent(footer);
			content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
			content.setExpandRatio(footer, 1);
		}

		/**
		 * Builds a footer which includes yes, no and cancel buttons.
		 * 
		 * @return the footer component which will be placed on the bottom of
		 *         the window
		 */
		private Component buildFooter() {
			Button yesButton = new Button(lang.getString("Yes"));
			yesButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
			yesButton.focus();
			yesButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					close();
					courseService.saveCourseData(graphData,
							CurrentCourses.getInstance().getTitle());
					UI.getCurrent().getNavigator()
							.navigateTo(Authorlibrary.VIEW_NAME);
				}
			});

			Button noButton = new Button(lang.getString("No"));
			noButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					close();
					// TODO: discard changes
					UI.getCurrent().getNavigator()
							.navigateTo(Authorlibrary.VIEW_NAME);
				}
			});

			Button cancelButton = new Button(lang.getString("Cancel"));

			cancelButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					close();
				}
			});

			HorizontalLayout layout = new HorizontalLayout(yesButton, noButton,
					cancelButton);
			layout.setSpacing(true);
			return layout;
		}

		/**
		 * Builds a title.
		 * 
		 * @return title of the window
		 */
		private Component buildTitle() {
			Label title = new Label(lang.getString("GraphModified"));
			title.addStyleName(ValoTheme.LABEL_H3);
			HorizontalLayout layout = new HorizontalLayout(title);
			layout.setSizeUndefined();
			layout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
			return layout;
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
