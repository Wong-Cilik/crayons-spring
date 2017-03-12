package com.crayons_2_0.view;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.UnitPageLayout;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UnitService;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

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

	

	public static final String VIEW_NAME = "Unit Editor";
	private static ResourceBundle lang = LanguageService.getInstance().getRes();

	private static UnitPageLayout page;

	private @Autowired
	UnitService unitService;

	@PostConstruct
	void init() {
		setSizeFull();
		addStyleName(ValoTheme.DRAG_AND_DROP_WRAPPER_NO_HORIZONTAL_DRAG_HINTS);

		Component pageItemsPalette = buildPageItemsPalette();
		addComponent(pageItemsPalette);
		setComponentAlignment(pageItemsPalette, Alignment.TOP_CENTER);

		page = new UnitPageLayout(true);
		page.setWidth(100.0f, Unit.PERCENTAGE);
		page.setStyleName("canvas");
		addComponent(page);
		setExpandRatio(page, 8);

		Component footer = buildFooter();
		footer.setSizeFull();
		addComponent(footer);
		setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
		setExpandRatio(footer, 1);
	}

	/**
	 * Builds together several components of the unit editor view.
	 */
	public Uniteditor() {

	}

	public void refresh(UnitPageLayout upl) {
		removeAllComponents();
		Component pageItemsPalette = buildPageItemsPalette();
		addComponent(pageItemsPalette);
		setComponentAlignment(pageItemsPalette, Alignment.TOP_CENTER);

		page = upl;
		page.setWidth(100.0f, Unit.PERCENTAGE);
		page.setStyleName("canvas");
		addComponent(page);
		setExpandRatio(page, 8);

		Component footer = buildFooter();
		footer.setSizeFull();
		addComponent(footer);
	}

	/**
	 * Builds a horizontal menu with 3 items: Text, Image, and Multiple Choice.
	 * The items can be added to the page via drag-and-drop. The menu is located
	 * on the top of the unit editor.
	 * 
	 * @return the drag and drop menu
	 */
	private Component buildPageItemsPalette() {
		HorizontalLayout paletteLayout = new HorizontalLayout();
		paletteLayout.setSpacing(true);
		paletteLayout.setWidthUndefined();
		paletteLayout.addStyleName("palette");

		paletteLayout.addComponent(buildPaletteItem(PageItemType.TEXT));
		//paletteLayout.addComponent(buildPaletteItem(PageItemType.IMAGE));
		paletteLayout
				.addComponent(buildPaletteItem(PageItemType.MULTIPLE_CHOICE));

		// Click listener implements drag and drop functionality of the items
		paletteLayout.addLayoutClickListener(new LayoutClickListener() {
			/**
			 * 
			 */

			@Override
			public void layoutClick(final LayoutClickEvent event) {
				if (event.getChildComponent() != null) {
					PageItemType data = (PageItemType) ((DragAndDropWrapper) event
							.getChildComponent()).getData();
					addPageComponent(data, null);
				}
			}
		});

		return paletteLayout;
	}

	/**
	 * Builds a footer which includes primary control buttons, import/export and
	 * delete buttons.
	 * 
	 * @return the footer
	 */
	private Component buildFooter() {
		Label deleteButton = new Label(FontAwesome.TRASH.getHtml()
				+ lang.getString("Delete"), ContentMode.HTML);
		deleteButton.setSizeUndefined();
		deleteButton.setStyleName(ValoTheme.LABEL_LARGE);
		DragAndDropWrapper dropArea = new DragAndDropWrapper(deleteButton);
		dropArea.addStyleName("no-vertical-drag-hints");
		dropArea.addStyleName("no-horizontal-drag-hints");
		dropArea.setSizeUndefined();
		dropArea.setDropHandler(new DropHandler() {

			/**
			 * 
			 */

			@Override
			public void drop(final DragAndDropEvent event) {
				Transferable transferable = event.getTransferable();
				Component sourceComponent = transferable.getSourceComponent();

				page.removeComponent(sourceComponent);
				if (page.isEmpty())
					page.addDropArea();
			}

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}
		});

		HorizontalLayout dropAreaLayout = new HorizontalLayout(dropArea);
		dropAreaLayout.setSizeUndefined();
		dropAreaLayout.addLayoutClickListener(new LayoutClickListener() {

			/**
			 * 
			 */

			@Override
			public void layoutClick(final LayoutClickEvent event) {
				Notification instruction = new Notification(lang
						.getString("DragAndDropDeleteButton"));
				instruction.setDelayMsec(2000);
				instruction.setStyleName("bar success small");
				instruction.setPosition(Position.BOTTOM_CENTER);
				instruction.show(Page.getCurrent());
			}
		});

		Button saveButton = new Button(lang.getString("Save"),
				FontAwesome.CHECK);
		saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		saveButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				VerticalLayout layout = page.getLayout();
				unitService.saveUnitData(layout, CurrentCourses.getInstance()
						.getUnitTitle(), CurrentCourses.getInstance()
						.getTitle());
			}
		});

		Button backButton = new Button(lang.getString("Back"),
				FontAwesome.ARROW_LEFT);
		backButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				// if (unit was modified)
				UI.getCurrent().addWindow(new UnsavedChangesWindow());
				// else
				// UI.getCurrent().getNavigator().navigateTo(CourseEditorView.VIEW_NAME);
			}

		});

		Button importButton = new Button(lang.getString("Import"),
				FontAwesome.DOWNLOAD);
		importButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				refresh();
			}

		});

		Button exportButton = new Button(lang.getString("Export"),
				FontAwesome.UPLOAD);
		exportButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				VerticalLayout layout = page.getLayout();
				unitService.saveUnitData(layout, CurrentCourses.getInstance()
						.getUnitTitle(), CurrentCourses.getInstance()
						.getTitle());
			}

		});

		HorizontalLayout controlButtons = new HorizontalLayout(backButton,
				saveButton, importButton, exportButton);
		controlButtons.setSpacing(true);

		HorizontalLayout footer = new HorizontalLayout(controlButtons,
				dropAreaLayout);
		footer.setSpacing(true);
		footer.setMargin(true);
		footer.setSizeFull();
		footer.setComponentAlignment(controlButtons, Alignment.MIDDLE_LEFT);
		footer.setComponentAlignment(dropAreaLayout, Alignment.MIDDLE_RIGHT);

		return footer;
	}

	/**
	 * Builds a label with given icon and title and wraps it in a
	 * DragAndDropWrapper.
	 * 
	 * @param pageItemType
	 *            defines icon and title or the palette item
	 * @return palette item wrapped in a DragAndDropWrapper
	 */
	private Component buildPaletteItem(final PageItemType pageItemType) {
		Label caption = new Label(pageItemType.getIcon().getHtml()
				+ pageItemType.getTitle(), ContentMode.HTML);
		caption.setSizeUndefined();

		DragAndDropWrapper wrapper = new DragAndDropWrapper(caption);
		wrapper.setSizeUndefined();
		wrapper.setDragStartMode(DragStartMode.WRAPPER);
		wrapper.setData(pageItemType);
		return wrapper;
	}

	/**
	 * Adds a component of the defined type to the page.
	 * 
	 * @param pageItemType
	 *            type of the component to be added
	 * @param prefillData
	 *            content of the component (null if the component is new)
	 */
	public void addPageComponent(final PageItemType pageItemType,
			final Object prefillData) {
		page.addComponent(pageItemType, prefillData, true);
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

	/**
	 * Dialog window which checks if the changes in the learning unit should be
	 * saved or not. Is called by a click on the back button.
	 * 
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

				/**
				 * 
				 */
				@Override
				public void buttonClick(ClickEvent event) {
					close();
					// TODO: save changes
					UI.getCurrent().getNavigator()
							.navigateTo(CourseEditorView.VIEW_NAME);
				}
			});

			Button noButton = new Button(lang.getString("No"));
			noButton.addClickListener(new ClickListener() {

				/**
				 * 
				 */
				@Override
				public void buttonClick(ClickEvent event) {
					close();
					// TODO: discard changes
					UI.getCurrent().getNavigator()
							.navigateTo(CourseEditorView.VIEW_NAME);
				}
			});

			Button cancelButton = new Button(lang.getString("Cancel"));

			cancelButton.addClickListener(new ClickListener() {

				/**
				 * 
				 */
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
			Label title = new Label(lang.getString("LearningUnitModified"));
			title.addStyleName(ValoTheme.LABEL_H3);
			HorizontalLayout layout = new HorizontalLayout(title);
			layout.setSizeUndefined();
			layout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
			return layout;
		}
	}

	public void refresh() {
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

	public static UnitPageLayout getPageLayout() {
		return page;
	}
}
