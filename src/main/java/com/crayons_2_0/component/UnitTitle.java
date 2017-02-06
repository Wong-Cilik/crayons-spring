package com.crayons_2_0.component;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class UnitTitle extends CustomComponent {
    private static final long serialVersionUID = 2343588123846949111L;
    
    private final Property<String> property = new ObjectProperty<String>(
            "Enter unit title here...");
    private final Component titleEditor;
    private final Component readOnly;
    
    public UnitTitle(String title) {
        setWidth(100.0f, Unit.PERCENTAGE);
        
        titleEditor = buildTitleEditor();
        readOnly = buildReadOnly();
        
        if (title != null) {
            property.setValue(title);
        }
        
        setCompositionRoot(titleEditor);
    }
    
    public Component getUserView() {
        Label title = new Label(property);
        title.setStyleName(ValoTheme.LABEL_LARGE);
        HorizontalLayout layout = new HorizontalLayout(title);
        layout.setWidthUndefined();
        layout.setComponentAlignment(title, Alignment.TOP_CENTER);
        return layout;
    }

    private Component buildReadOnly() {
        final Label title = new Label(property);
        title.setStyleName(ValoTheme.LABEL_LARGE);
        Button editButton = new Button(FontAwesome.EDIT);
        editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -6863363158492604043L;

            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(titleEditor);
            }
        });

        CssLayout result = new CssLayout(title, editButton);
        result.addStyleName("text-editor");
        result.setSizeFull();
        result.addLayoutClickListener(new LayoutClickListener() {
            private static final long serialVersionUID = 2211766026521962658L;

            @Override
            public void layoutClick(final LayoutClickEvent event) {
                if (event.getChildComponent() == title && event.isDoubleClick()) {
                    setCompositionRoot(titleEditor);
                }
            }
        });
        return result;
    }

    private Component buildTitleEditor() {
        final TextField textField = new TextField(property);
        textField.setWidth(100.0f, Unit.PERCENTAGE);
        textField.addAttachListener(new AttachListener() {
            private static final long serialVersionUID = -3246635949197534220L;

            @Override
            public void attach(final AttachEvent event) {
                textField.focus();
                textField.selectAll();
            }
        });
       
        Button save = new Button("Save");
        save.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 7805400627427951016L;

            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(readOnly);
            }
        });

        HorizontalLayout result = new HorizontalLayout(textField, save);
        //result.addStyleName("edit");
        result.setSizeFull();
        result.setSpacing(true);
        return result;
    }
}
