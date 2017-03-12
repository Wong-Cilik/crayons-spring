package com.crayons_2_0.component;

import java.util.ResourceBundle;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

// Code is based on https://github.com/vaadin/dashboard-demo/blob/7.7/src/main/java/com/vaadin/demo/dashboard/component/InlineTextEditor.java

@SuppressWarnings("serial")
public class TextEditor extends CustomComponent {

	private final Property<String> property = new ObjectProperty<String>(
			"Enter text here...");
	private Component textEditor;
	private Component readOnly;
	private CKEditorTextField ckEditorTextField;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public TextEditor(String prefillData, Boolean editable) {
		
		if (editable) {
			setWidth(100.0f, Unit.PERCENTAGE);
			addStyleName("inline-text-editor");

			if (prefillData != null) {
				property.setValue(prefillData);
			}

			textEditor = buildTextEditor();
			readOnly = buildReadOnly();

			setCompositionRoot(readOnly);
		} else {
			
			setWidth(100.0f, Unit.PERCENTAGE);
			addStyleName("inline-text-editor");

			if (prefillData != null) {
				property.setValue(prefillData);
			}

			textEditor = buildTextEditor();
			readOnly = buildUserOnly();
			setCompositionRoot(readOnly);
		}
	}


	private Component buildUserOnly() {
		final Label text = new Label(property);
		text.setContentMode(ContentMode.HTML);

		CssLayout result = new CssLayout(text);
		result.addStyleName("text-editor");
		result.setSizeFull();
		return result;
	}
	
	private Component buildReadOnly() {
		final Label text = new Label(property);
		text.setContentMode(ContentMode.HTML);

		Button editButton = new Button(FontAwesome.EDIT);
		editButton.addStyleName(ValoTheme.BUTTON_SMALL);
		editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		editButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				setCompositionRoot(textEditor);
			}
		});
		CssLayout result = new CssLayout(text, editButton);
		result.addStyleName("text-editor");
		result.setSizeFull();
		result.addLayoutClickListener(new LayoutClickListener() {

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
		CKEditorConfig config = new CKEditorConfig();
		config.useCompactTags();
		config.disableElementsPath();
		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config.disableSpellChecker();
		config.setWidth("100%");
		config.addCustomToolbarLine("{ items : ['Preview', 'Print','-',	'Cut', 'Copy', 'Paste','-',	'Undo',	'Redo','-',	'Find',	'Replace','-', 'Bold', 'Italic',	'Underline',	'Strike',	'Subscript',		'Superscript',	'RemoveFormat','-',	'NumberedList',	'BulletedList',	'Outdent',	'Indent',	'Blockquote'] }");
		config.addCustomToolbarLine("{ items : ['JustifyLeft',	'JustifyCenter',	'JustifyRight',	'JustifyBlock','-',	'Link',	'Unlink',	'Image',	'Flash',	'Table',	'HorizontalRule',	'Smiley',	'SpecialChar']}");
		config.addCustomToolbarLine("{ items : ['Styles',	'Format',	'Font',	'FontSize', 	'TextColor',	'BGColor',	'UIColor',	'Maximize']}");

		ckEditorTextField = new CKEditorTextField(config);
		ckEditorTextField.setWidth(100.0f, Unit.PERCENTAGE);
		ckEditorTextField.addAttachListener(new AttachListener() {
			/**
			 * 
			 */

			@Override
			public void attach(final AttachEvent event) {
				ckEditorTextField.focus();
			}
		});
		ckEditorTextField.setValue(property.getValue());
		ckEditorTextField
				.addValueChangeListener(new Property.ValueChangeListener() {

					public void valueChange(ValueChangeEvent event) {
						Notification.show("CKEditor v"
								+ ckEditorTextField.getVersion()
								+ " - contents: "
								+ event.getProperty().getValue().toString());
					}
				});
		/*
		 * final RichTextArea textArea = new RichTextArea(property);
		 * textArea.setWidth(100.0f, Unit.PERCENTAGE);
		 * textArea.addAttachListener(new AttachListener() { private static
		 * final long serialVersionUID = 8413778836678316866L;
		 * 
		 * @Override public void attach(final AttachEvent event) {
		 * textArea.focus(); textArea.selectAll(); } });
		 */
		Button save = new Button(lang.getString("Save"));
		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				property.setValue(ckEditorTextField.getValue());
				setCompositionRoot(readOnly);
			}
		});

		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				/* getParent() */
			}
		});

		CssLayout result = new CssLayout(ckEditorTextField, save);

		result.addStyleName("edit");
		result.setSizeFull();
		return result;
	}

	public String getContent() {
		return ckEditorTextField.getValue();
	}
}
