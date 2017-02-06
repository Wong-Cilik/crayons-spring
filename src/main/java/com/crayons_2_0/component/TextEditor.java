package com.crayons_2_0.component;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.themes.ValoTheme;

// Code is based on https://github.com/vaadin/dashboard-demo/blob/7.7/src/main/java/com/vaadin/demo/dashboard/component/InlineTextEditor.java

public class TextEditor extends CustomComponent {
    private static final long serialVersionUID = 4186796136009413559L;
    
    private final Property<String> property = new ObjectProperty<String>(
            "Enter text here...");
    private final Component textEditor;
    private final Component readOnly;
    
    public TextEditor(String prefillData) {
        setWidth(100.0f, Unit.PERCENTAGE);
        addStyleName("inline-text-editor");
        
        textEditor = buildTextEditor();
        readOnly = buildReadOnly();
        
        if (prefillData != null) {
            property.setValue(prefillData);
        }
        
        setCompositionRoot(textEditor);
    }
    
    public Component getUserView() {
        final Label text = new Label(property);
        text.setContentMode(ContentMode.HTML);
        return text;
    }

    private Component buildReadOnly() {
        final Label text = new Label(property);
        text.setContentMode(ContentMode.HTML);

        Button editButton = new Button(FontAwesome.EDIT);
        editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -8467021048199100945L;

            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(textEditor);
            }
        });

        CssLayout result = new CssLayout(text, editButton);
        result.addStyleName("text-editor");
        result.setSizeFull();
        result.addLayoutClickListener(new LayoutClickListener() {
            private static final long serialVersionUID = 7363639788653598603L;

            @Override
            public void layoutClick(final LayoutClickEvent event) {
                if (event.getChildComponent() == text && event.isDoubleClick()) {
                    setCompositionRoot(textEditor);
                }
            }
        });
        
        return result;
    }
    
    private Component buildTextEditor() {
        /*CKEditorConfig config = new CKEditorConfig();
        config.useCompactTags();
        config.disableElementsPath();
        config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
        config.disableSpellChecker();
        config.setWidth("100%");
        
        final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
        ckEditorTextField.setWidth(100.0f, Unit.PERCENTAGE);
        ckEditorTextField.addAttachListener(new AttachListener() {
            @Override
            public void attach(final AttachEvent event) {
                ckEditorTextField.focus();
            }
        });
        ckEditorTextField.setValue(property.getValue());
        ckEditorTextField.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Notification.show("CKEditor v" + ckEditorTextField.getVersion() + " - contents: " + event.getProperty().getValue().toString());
            }
        });*/   
        
        final RichTextArea textArea = new RichTextArea(property);
        textArea.setWidth(100.0f, Unit.PERCENTAGE);
        textArea.addAttachListener(new AttachListener() {
            private static final long serialVersionUID = 8413778836678316866L;

            @Override
            public void attach(final AttachEvent event) {
                textArea.focus();
                textArea.selectAll();
            }
        });
       
        Button save = new Button("Save");
        save.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -4688758527425055901L;

            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(readOnly);
            }
        });
        
        save.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 6125553410927864047L;

            @Override
            public void buttonClick(final ClickEvent event) {
                /*getParent()*/
            }
        });
        
        CssLayout result = new CssLayout(textArea, save);

        result.addStyleName("edit");
        result.setSizeFull();
        return result;
    }
}
