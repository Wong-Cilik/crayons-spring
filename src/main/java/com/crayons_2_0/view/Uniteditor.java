package com.crayons_2_0.view;

import java.util.Iterator;

import com.crayons_2_0.component.ImageUploadEditor;
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
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@SuppressWarnings({ "serial"})
public class Uniteditor extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "Unit Editor";

	private final PageLayout page;

    public Uniteditor() {
        setSizeFull();
        //addStyleName("editor"); //TODO(Natalia): read and modify the style
        addStyleName(ValoTheme.DRAG_AND_DROP_WRAPPER_NO_HORIZONTAL_DRAG_HINTS);

        Component pageItemsPalette = buildPageItemsPalette();
        addComponent(pageItemsPalette);
        setComponentAlignment(pageItemsPalette, Alignment.TOP_CENTER);

        page = new PageLayout();
        page.setWidth(100.0f, Unit.PERCENTAGE);
        page.setStyleName("canvas"); //TODO(Natalia): read and modify the style
        addComponent(page);
        setExpandRatio(page, 1);
        
        Component deleteArea = buildDeleteArea();
        addComponent(deleteArea);
        setComponentAlignment(deleteArea, Alignment.BOTTOM_CENTER);
    }

    private Component buildPageItemsPalette() {
        HorizontalLayout paletteLayout = new HorizontalLayout();
        paletteLayout.setSpacing(true);
        paletteLayout.setWidthUndefined();
        paletteLayout.addStyleName("palette");
        
        /*paletteLayout.addComponent(buildPaletteItem(PageItemType.SPLIT));*/
        paletteLayout.addComponent(buildPaletteItem(PageItemType.TEXT));
        paletteLayout.addComponent(buildPaletteItem(PageItemType.IMAGE));
        paletteLayout.addComponent(buildPaletteItem(PageItemType.MULTIPLE_CHOICE));

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
    
    private Component buildDeleteArea() {
        Label deleteButton = new Label(PageItemType.DELETE_BUTTON.getIcon().getHtml() + PageItemType.DELETE_BUTTON.getTitle(),
                ContentMode.HTML);
        deleteButton.setSizeUndefined();
        deleteButton.setStyleName(ValoTheme.LABEL_LARGE);
        DragAndDropWrapper dropArea = new DragAndDropWrapper(deleteButton);
        dropArea.addStyleName("no-vertical-drag-hints");
        dropArea.addStyleName("no-horizontal-drag-hints");
        dropArea.setSizeFull();
        HorizontalLayout dropAreaLayout = new HorizontalLayout(dropArea);
        
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
        
        return dropAreaLayout;
    }

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

    public void addPageComponent(final PageItemType pageItemType,
            final Object prefillData) {
        page.addComponent(pageItemType, prefillData);
    }

    public final class PageLayout extends CustomComponent {

        private VerticalLayout layout;
        private final DropHandler dropHandler;
        private DragAndDropWrapper dropArea;

        public PageLayout() {
            layout = new VerticalLayout();
            setCompositionRoot(layout);
            layout.addStyleName("canvas-layout"); //TODO(Natalia): read and modify the style

            Component unitTitle = new UnitTitle(null);
            layout.addComponent(unitTitle);
            layout.setComponentAlignment(unitTitle, Alignment.TOP_CENTER);

            dropHandler = new ReorderLayoutDropHandler();
            
            addDropArea();
        }
        
        public void addDropArea() {
            layout.addComponent(buildDropArea());
        }
        
        private Component buildDropArea() {
            Label dropAreaLabel = new Label("Drag items here");
            dropAreaLabel.setSizeUndefined();

            dropArea = new DragAndDropWrapper(dropAreaLabel);
            dropArea.addStyleName("placeholder"); //TODO(Natalia): read and modify the style
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
        
        public void removeComponent(Component component) {
            layout.removeComponent(component);
        }
        
        public boolean isEmpty() {
            return (layout.getComponentCount() == 1);
        }
        
        /*public Iterator<Component> getComponents() {
            return layout.iterator();
        }*/

        public void addComponent(final PageItemType pageItemType,
                final Object prefillData) {
            if (dropArea.getParent() != null) {
                layout.removeComponent(dropArea);
            }
            /*if (pageItemType == PageItemType.SPLIT) {
                layout.addComponent(new HorizontalLayout(new WrappedPageItem(buildDropArea()), 
                        new WrappedPageItem(buildDropArea())));
            } else {*/
                layout.addComponent(
                        new WrappedPageItem(createComponentFromPageItem(
                                pageItemType, prefillData)), 1);
            /*}*/
        }

        private Component createComponentFromPageItem(
                final PageItemType type, final Object prefillData) {
            Component result = null;
            if (type == PageItemType.TEXT) {
                result = new TextEditor(prefillData != null ? String.valueOf(prefillData) : null);
            } else if (type == PageItemType.IMAGE) {
                result = new ImageUploadEditor();
            } else if (type == PageItemType.MULTIPLE_CHOICE) {
                result = new MultipleChoiceEditor(null, null, null);
            } /*else if (type == PageItemType.TRANSACTIONS) {
            }
                result = new TransactionsListing(
                        (Collection<Transaction>) prefillData);
            }*/

            return result;
        }

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

        private class ReorderLayoutDropHandler implements DropHandler {

            @Override
            public AcceptCriterion getAcceptCriterion() {
                // return new SourceIs(component)
                return AcceptAll.get();
            }
            
            //TODO(Natalia): read it
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

    public enum PageItemType {
        TEXT("Text Block", FontAwesome.FONT), IMAGE("Image",
                FontAwesome.FILE_IMAGE_O), MULTIPLE_CHOICE("Multiple choice excercise",
                FontAwesome.CHECK_SQUARE_O), TRANSACTIONS("Latest transactions",
                null),DELETE_BUTTON("Delete", FontAwesome.TRASH)/*, SPLIT("Split", FontAwesome.COLUMNS)*/;

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

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}