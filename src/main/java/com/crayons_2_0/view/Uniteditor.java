package com.crayons_2_0.view;

import java.util.Iterator;

import com.crayons_2_0.component.ImageUploadEditor;
import com.crayons_2_0.component.ImportEditor;
import com.crayons_2_0.component.MultipleChoiceEditor;
import com.crayons_2_0.component.TextEditor;
import com.crayons_2_0.component.UnitTitle;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Unit editor view consists of representation of a learning unit and different editing tools.
 * It allows an author to modify the learning unit using drag and drop editor. 
 *
 */
@SpringView(name = Uniteditor.VIEW_NAME)
public class Uniteditor extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "Unit Editor";

	private final PageLayout page;

	/**
	 * Builds together several components of the unit editor view. 
	 */
    public Uniteditor() {
        setSizeFull();
        addStyleName(ValoTheme.DRAG_AND_DROP_WRAPPER_NO_HORIZONTAL_DRAG_HINTS);

        Component pageItemsPalette = buildPageItemsPalette();
        addComponent(pageItemsPalette);
        setComponentAlignment(pageItemsPalette, Alignment.TOP_CENTER);

        page = new PageLayout();
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
     * Builds a horizontal menu with 3 items: Text, Image, and Multiple Choice. 
     * The items can be added to the page via drag-and-drop. The menu is located on the top of the unit editor.
     * 
     * @return the drag and drop menu
     */
    private Component buildPageItemsPalette() {
        HorizontalLayout paletteLayout = new HorizontalLayout();
        paletteLayout.setSpacing(true);
        paletteLayout.setWidthUndefined();
        paletteLayout.addStyleName("palette");
        
        paletteLayout.addComponent(buildPaletteItem(PageItemType.TEXT));
        paletteLayout.addComponent(buildPaletteItem(PageItemType.IMAGE));
        paletteLayout.addComponent(buildPaletteItem(PageItemType.MULTIPLE_CHOICE));

        // Click listener implements drag and drop functionality of the items 
        paletteLayout.addLayoutClickListener(new LayoutClickListener() {
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
     * Builds a footer which includes primary control buttons, import/export and delete buttons.
     * @return the footer
     */
    private Component buildFooter() {
        Label deleteButton = new Label(FontAwesome.TRASH.getHtml() + "Delete",
                ContentMode.HTML);
        deleteButton.setSizeUndefined();
        deleteButton.setStyleName(ValoTheme.LABEL_LARGE);
        DragAndDropWrapper dropArea = new DragAndDropWrapper(deleteButton);
        dropArea.addStyleName("no-vertical-drag-hints");
        dropArea.addStyleName("no-horizontal-drag-hints");
        dropArea.setSizeUndefined();
        dropArea.setDropHandler(new DropHandler() {

            @Override
            public void drop(final DragAndDropEvent event) {
                Transferable transferable = event.getTransferable();
                Component sourceComponent = transferable
                        .getSourceComponent();
                

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

            @Override
            public void layoutClick(final LayoutClickEvent event) {
                Notification instruction = new Notification(
                        "Drag and drop the elemenets to be deleted over the button");
                instruction.setDelayMsec(2000);
                instruction.setStyleName("bar success small");
                instruction.setPosition(Position.BOTTOM_CENTER);
                instruction.show(Page.getCurrent());
            }
        });
        
        Button saveButton = new Button("Save", FontAwesome.CHECK);
        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO: save changes in the learning unit
                Notification savedSuccessful = new Notification(
                        "Learning unit saved successfully");
                savedSuccessful.setDelayMsec(1000);
                savedSuccessful.setStyleName("bar success small");
                savedSuccessful.setPosition(Position.BOTTOM_CENTER);
                savedSuccessful.show(Page.getCurrent());
            }
            
        });

        Button backButton = new Button("Back", FontAwesome.ARROW_LEFT);
        backButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                //if (unit was modified)
                UI.getCurrent().addWindow(new UnsavedChangesWindow());
                //else
                //UI.getCurrent().getNavigator().navigateTo(CourseEditorView.VIEW_NAME);
            }
            
        });
        
        Button importButton = new Button("Import", FontAwesome.DOWNLOAD);
        importButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().addWindow(new ImportEditor());
            }
            
        });
        
        Button exportButton = new Button("Export", FontAwesome.UPLOAD);
        exportButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
               // TODO: download learning unit
                Notification success = new Notification("File is exported successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
            }
            
        });

        HorizontalLayout controlButtons = new HorizontalLayout(backButton, saveButton, importButton, exportButton);
        controlButtons.setSpacing(true);
        
        HorizontalLayout footer = new HorizontalLayout(controlButtons, dropAreaLayout);
        footer.setSpacing(true);
        footer.setMargin(true);
        footer.setSizeFull();
        footer.setComponentAlignment(controlButtons, Alignment.MIDDLE_LEFT);
        footer.setComponentAlignment(dropAreaLayout, Alignment.MIDDLE_RIGHT);
 
        return footer;
    }

    /**
     * Builds a label with given icon and title and wraps it in a DragAndDropWrapper.
     * 
     * @param pageItemType defines icon and title or the palette item
     * @return palette item wrapped in a DragAndDropWrapper
     */
    private Component buildPaletteItem(final PageItemType pageItemType) {
        Label caption = new Label(pageItemType.getIcon().getHtml() + pageItemType.getTitle(),
                ContentMode.HTML);
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
     * @param pageItemType type of the component to be added
     * @param prefillData content of the component (null if the component is new)
     */
    public void addPageComponent(final PageItemType pageItemType,
            final Object prefillData) {
        page.addComponent(pageItemType, prefillData);
    }

    /**
     * Content of a learning unit represented as a vertical layout. 
     * Consists of the unit title and drag and drop area where the elements of the learning unit 
     * such as text, image, and multiple choice test are located.
     * 
     */
    public final class PageLayout extends CustomComponent {
    	//TODO check if here save unit to save the unit
        private VerticalLayout layout;
        private final DropHandler dropHandler;
        private DragAndDropWrapper dropArea;
        
        /**
         * Builds together several components of the page layout.
         */
        public PageLayout() {
            layout = new VerticalLayout();
            setCompositionRoot(layout);
            layout.addStyleName("canvas-layout");

            Component unitTitle = new UnitTitle(null);
            layout.addComponent(unitTitle);
            layout.setComponentAlignment(unitTitle, Alignment.TOP_CENTER);

            dropHandler = new ReorderLayoutDropHandler();
            
            addDropArea();
        }
        
        /**
         * Adds a drop area to the page. It is used when the page is empty to show the user where the components 
         * can be dropped.  
         */
        public void addDropArea() {
            layout.addComponent(buildDropArea());
        }
        
        /**
         * Builds a drop area for an empty page. The drop area can/will be later replaced
         * by a page component such as text, image, or multiple choice exercise.
         * 
         * @return drop area as a DragAndDropWrapper
         */
        private Component buildDropArea() {
            Label dropAreaLabel = new Label("Drag items here");
            dropAreaLabel.setSizeUndefined();

            dropArea = new DragAndDropWrapper(dropAreaLabel);
            dropArea.addStyleName("placeholder");
            dropArea.setDropHandler(new DropHandler() {

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }

                @Override
                public void drop(final DragAndDropEvent event) {
                    Transferable transferable = event.getTransferable();
                    Component sourceComponent = transferable
                            .getSourceComponent();

                    if (sourceComponent != layout.getParent()) {
                        Object type = ((AbstractComponent) sourceComponent)
                                .getData();
                        addComponent((PageItemType) type, null);
                    }
                }
            });
            return dropArea;
        }
        
        /**
         * Removes a component from the page.
         * 
         * @param component component to be removed
         */
        public void removeComponent(Component component) {
            layout.removeComponent(component);
        }
        
        /**
         * Checks if the page is empty. Is used only after a component was removed.
         * 
         * @return true if the page is empty, false otherwise.
         */
        public boolean isEmpty() {
            return (layout.getComponentCount() == 1);
        }

        /**
         * Adds a component of the defined type to the page.
         * 
         * @param pageItemType type of the component to be added
         * @param prefillData content of the component (null if the component is new)
         */
        public void addComponent(final PageItemType pageItemType,
                final Object prefillData) {
            if (dropArea.getParent() != null) {
                layout.removeComponent(dropArea);
            }
            layout.addComponent(
                    new WrappedPageItem(createComponentFromPageItem(
                            pageItemType, prefillData)), 1);
        }

        /**
         * Creates a component of the defined type.
         * 
         * @param type type of the component: text, image, or multiple choice
         * @param prefillData content of the component (null if the component is new)
         * @return component of the defined type with the corresponding content
         */
        private Component createComponentFromPageItem(
                final PageItemType type, final Object prefillData) {
            Component result = null;
            if (type == PageItemType.TEXT) {
                result = new TextEditor(prefillData != null ? String.valueOf(prefillData) : null);
            } else if (type == PageItemType.IMAGE) {
                result = new ImageUploadEditor();
            } else if (type == PageItemType.MULTIPLE_CHOICE) {
                result = new MultipleChoiceEditor(null, null, null);
            }

            return result;
        }

        /**
         * 
         *
         */
        private class WrappedPageItem extends DragAndDropWrapper {

            public WrappedPageItem(final Component content) {
                super(content);
                setDragStartMode(DragStartMode.WRAPPER);
            }

            @Override
            public DropHandler getDropHandler() {
                return dropHandler;
            }
        }
        
        /**
         * DropHandler for the drop area of the page layout. Allows to reorder the components of the page.
         * The implementation is based on "QuickTicketsDashboard" demo from Vaadin.        
         */
        private class ReorderLayoutDropHandler implements DropHandler {

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
            
            @Override
            public void drop(final DragAndDropEvent dropEvent) {
                Transferable transferable = dropEvent.getTransferable();
                Component sourceComponent = transferable.getSourceComponent();

                TargetDetails dropTargetData = dropEvent.getTargetDetails();
                DropTarget target = dropTargetData.getTarget();

                if (sourceComponent.getParent() != layout) {
                    Object pageItemType = ((AbstractComponent) sourceComponent)
                            .getData();

                    AbstractComponent c = new WrappedPageItem(
                            createComponentFromPageItem(
                                    (PageItemType) pageItemType, null));

                    int index = 0;
                    Iterator<Component> componentIterator = layout.iterator();
                    Component next = null;
                    while (next != target && componentIterator.hasNext()) {
                        next = componentIterator.next();
                        if (next != sourceComponent) {
                            index++;
                        }
                    }

                    if (dropTargetData.getData("verticalLocation").equals(
                            VerticalDropLocation.TOP.toString())) {
                        index--;
                        if (index <= 0) {
                            index = 1;
                        }
                    }

                    layout.addComponent(c, index);
                }

                if (sourceComponent instanceof WrappedPageItem) {
                    // find the location where to move the dragged component
                    boolean sourceWasAfterTarget = true;
                    int index = 0;
                    Iterator<Component> componentIterator = layout.iterator();
                    Component next = null;
                    while (next != target && componentIterator.hasNext()) {
                        next = componentIterator.next();
                        if (next != sourceComponent) {
                            index++;
                        } else {
                            sourceWasAfterTarget = false;
                        }
                    }
                    if (next == null || next != target) {
                        // component not found - if dragging from another layout
                        return;
                    }

                    // drop on top of target?
                    if (dropTargetData.getData("verticalLocation").equals(
                            VerticalDropLocation.MIDDLE.toString())) {
                        if (sourceWasAfterTarget) {
                            index--;
                        }
                    }

                    // drop before the target?
                    else if (dropTargetData.getData("verticalLocation").equals(
                            VerticalDropLocation.TOP.toString())) {
                        index--;
                        if (index <= 0) {
                            index = 1;
                        }
                    }

                    // move component within the layout
                    layout.removeComponent(sourceComponent);
                    layout.addComponent(sourceComponent, index);
                }
            }
        }

    }

    /**
     * Defines title and icon for the page items: text, image, and multiple choice exercise.
     */
    public enum PageItemType {
        TEXT("Text Block", FontAwesome.FONT), IMAGE("Image",
                FontAwesome.FILE_IMAGE_O), MULTIPLE_CHOICE("Multiple choice exercise",
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
     * Dialog window which checks if the changes in the learning unit should be saved or not.
     * Is called by a click on the back button.
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
         * @return the footer component which will be placed on the bottom of the window
         */
        private Component buildFooter() {
            Button yesButton = new Button("Yes");
            yesButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
            yesButton.focus();
            yesButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                   close();
                   //TODO: save changes
                   UI.getCurrent().getNavigator().navigateTo(CourseEditorView.VIEW_NAME);
                }
            });
            
            Button noButton = new Button("No");
            noButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                   close();
                   //TODO: discard changes
                   UI.getCurrent().getNavigator().navigateTo(CourseEditorView.VIEW_NAME);
                }
            });
            
            Button cancelButton = new Button("Cancel");
            
            cancelButton.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                   close();
                }
            });

            HorizontalLayout layout = new HorizontalLayout(yesButton, noButton, cancelButton);
            layout.setSpacing(true);
            return layout;
        }
       
        /**
         * Builds a title.
         * 
         * @return title of the window
         */
        private Component buildTitle() {
            Label title = new Label("The learning unit was modified. Do you want to save changes?");
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
