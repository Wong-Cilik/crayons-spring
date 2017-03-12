package com.crayons_2_0.component;

import java.util.Iterator;
import java.util.List;

import com.crayons_2_0.model.UnitData;
import com.crayons_2_0.view.Uniteditor.PageItemType;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Content of a learning unit represented as a vertical layout. Consists of the
 * unit title and drag and drop area where the elements of the learning unit
 * such as text, image, and multiple choice test are located.
 * 
 */
@SuppressWarnings("serial")
public final class UnitPageLayout extends CustomComponent {
	/**
	 * 
	 */
	private VerticalLayout layout;
	private DropHandler dropHandler;
	private DragAndDropWrapper dropArea;

	/**
	 * Builds together several components of the page layout.
	 */
	public UnitPageLayout(Boolean editable) {
		layout = new VerticalLayout();
		setCompositionRoot(layout);
		layout.addStyleName("canvas-layout");
		if (editable) {
			dropHandler = new ReorderLayoutDropHandler();
			addDropArea();
		}

	}

	/**
	 * Adds a drop area to the page. It is used when the page is empty to show
	 * the user where the components can be dropped.
	 */
	public void addDropArea() {
		//layout.addComponent(buildDropArea());
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}

	/**
	 * Builds a drop area for an empty page. The drop area can/will be later
	 * replaced by a page component such as text, image, or multiple choice
	 * exercise.
	 * 
	 * @return drop area as a DragAndDropWrapper
	 */
	@SuppressWarnings("unused")
	private Component buildDropArea() {
		Label dropAreaLabel = new Label("Drag items here");
		dropAreaLabel.setSizeUndefined();

		dropArea = new DragAndDropWrapper(dropAreaLabel);
		dropArea.addStyleName("placeholder");
		dropArea.setDropHandler(new DropHandler() {

			/**
			 * 
			 */
			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(final DragAndDropEvent event) {
				Transferable transferable = event.getTransferable();
				Component sourceComponent = transferable.getSourceComponent();

				if (sourceComponent != layout.getParent()) {
					Object type = ((AbstractComponent) sourceComponent)
							.getData();
					addComponent((PageItemType) type, null, true);
				}
			}
		});
		return dropArea;
	}

	/**
	 * Removes a component from the page.
	 * 
	 * @param component
	 *            component to be removed
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
	 * @param pageItemType
	 *            type of the component to be added
	 * @param prefillData
	 *            content of the component (null if the component is new)
	 */
	public void addComponent(final PageItemType pageItemType,
			final Object prefillData, Boolean editable) {
		//if (dropArea.getParent() != null) {
		//	layout.removeComponent(dropArea);
		//}
		Component x = new WrappedPageItem(createComponentFromPageItem(
				pageItemType, prefillData, editable));
		layout.addComponent(x);
	}

	/**
	 * Creates a component of the defined type.
	 * 
	 * @param type
	 *            type of the component: text, image, or multiple choice
	 * @param prefillData
	 *            content of the component (null if the component is new)
	 * @return component of the defined type with the corresponding content
	 */
	private Component createComponentFromPageItem(final PageItemType type,
			final Object prefillData, Boolean editable) {
		Component result = null;
		if (type == PageItemType.TEXT) {
			result = new TextEditor(
					prefillData != null ? String.valueOf(prefillData) : null, editable);
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
	public class WrappedPageItem extends DragAndDropWrapper {
		private Component content;

		/**
		 * 
		 */
		private WrappedPageItem(final Component content) {
			super(content);
			this.content = content;
			setDragStartMode(DragStartMode.WRAPPER);
		}

		public Component getContent() {
			return content;
		}

		@Override
		public DropHandler getDropHandler() {
			return dropHandler;
		}
	}

	/**
	 * DropHandler for the drop area of the page layout. Allows to reorder the
	 * components of the page. The implementation is based on
	 * "QuickTicketsDashboard" demo from Vaadin.
	 */
	private class ReorderLayoutDropHandler implements DropHandler {

		/**
		 * 
		 */

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
								(PageItemType) pageItemType, null, true));

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

	public void replaceAllComponent(List<UnitData> unitData, Boolean editable) {
		removeAllComponent();
		for (UnitData tmpUnit : unitData) {
			if (tmpUnit.getText() != null) {
				addComponent(PageItemType.TEXT, tmpUnit.getText(), editable);
			} else if (tmpUnit.getImage() != null) {

			} else if (tmpUnit.getQuestion() != null) {
				
			}
		}
	}

	public void removeAllComponent() {
		layout.removeAllComponents();
	}
}
