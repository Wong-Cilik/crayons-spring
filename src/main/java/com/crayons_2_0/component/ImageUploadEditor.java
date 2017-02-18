package com.crayons_2_0.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

// Code is based on https://github.com/vaadin/book-examples/blob/master/src/com/vaadin/book/examples/component/UploadExample.java

@SuppressWarnings("serial")
public class ImageUploadEditor extends CustomComponent {

	private final Image image = new Image();
	private final Property<String> imageTitle = new ObjectProperty<String>("");
	private final Property<String> imageSource = new ObjectProperty<String>("");
	private final Component selectImageEditor;
	private final Component showImage;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public ImageUploadEditor() {
		setWidth(100.0f, Unit.PERCENTAGE);

		selectImageEditor = buildSelectImageEditor();
		showImage = showImage();

		setCompositionRoot(selectImageEditor);
	}

	private Component showImage() {
		Button editButton = new Button(FontAwesome.EDIT);
		editButton.addStyleName(ValoTheme.BUTTON_SMALL);
		editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		editButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				setCompositionRoot(selectImageEditor);
			}
		});

		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSpacing(true);

		imageLayout.addComponent(image);
		imageLayout.setComponentAlignment(image, Alignment.TOP_CENTER);
		final Label title = new Label(imageTitle);
		title.setWidthUndefined();
		title.setStyleName(ValoTheme.LABEL_LIGHT);
		imageLayout.addComponent(title);
		imageLayout.setComponentAlignment(title, Alignment.TOP_CENTER);

		CssLayout result = new CssLayout(imageLayout, editButton);
		result.addStyleName("text-editor");
		result.setSizeFull();
		result.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(final LayoutClickEvent event) {
				if (event.getChildComponent() == image && event.isDoubleClick()) {
					setCompositionRoot(selectImageEditor);
				}
			}
		});
		return result;
	}

	private VerticalLayout buildSelectImageEditor() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100.0f, Unit.PERCENTAGE);
		layout.setSpacing(true);

		image.setVisible(false);

		class ImageReceiver implements Receiver, SucceededListener {

			public File file;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				image.setVisible(true);
				image.setSource(new FileResource(file));
				setCompositionRoot(showImage);
			}

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				FileOutputStream stream = null;
				String username = System.getProperty("user.name");
				try {
					file = new File("C:/Users/" + username + "/vaadin-uploads"
							+ filename);
					stream = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					new Notification(lang.getString("CouldNotOpenFile")
							+ "<br/>", e.getMessage(),
							Notification.Type.ERROR_MESSAGE).show(Page
							.getCurrent());
					return null;
				}
				return stream;
			}

		}

		ImageReceiver receiver = new ImageReceiver();

		final Upload upload = new Upload(lang.getString("UploadImage"),
				receiver);
		upload.setButtonCaption(lang.getString("Upload"));
		upload.addSucceededListener(receiver);

		// TODO: set upload limit
		final long UPLOAD_LIMIT = 1000000l;
		upload.addStartedListener(new StartedListener() {

			@Override
			public void uploadStarted(StartedEvent event) {
				if (event.getContentLength() > UPLOAD_LIMIT) {
					Notification.show(lang.getString("TooBigFile"),
							Notification.Type.ERROR_MESSAGE);
					upload.interruptUpload();
				}
			}
		});

		// Check the size also during progress
		upload.addProgressListener(new ProgressListener() {

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (readBytes > UPLOAD_LIMIT) {
					Notification.show(lang.getString("TooBigFile"),
							Notification.Type.ERROR_MESSAGE);
					upload.interruptUpload();
				}
			}
		});

		image.setWidth(400, Unit.PIXELS);

		final Label titleFieldLabel = new Label(lang.getString("ImageTitle"));
		final TextField titleField = new TextField(imageTitle);
		titleField.setWidth(100.0f, Unit.PERCENTAGE);
		HorizontalLayout titleLayout = new HorizontalLayout(titleFieldLabel,
				titleField);
		titleLayout.setSpacing(true);
		layout.addComponent(titleLayout);

		titleField.addAttachListener(new AttachListener() {
			/**
			 * 
			 */
			@Override
			public void attach(final AttachEvent event) {
				titleField.focus();
				titleField.selectAll();
			}
		});

		final Label sourceFieldLabel = new Label(lang.getString("ImageSourse")); // SourCe
																					// Tippfehler??
		final TextField sourceField = new TextField(imageSource);
		sourceField.setWidth(100.0f, Unit.PERCENTAGE);
		HorizontalLayout sourceLayout = new HorizontalLayout(sourceFieldLabel,
				sourceField);
		sourceLayout.setSpacing(true);
		layout.addComponent(sourceLayout);

		sourceField.addAttachListener(new AttachListener() {
			/**
			 * 
			 */
			@Override
			public void attach(final AttachEvent event) {
				sourceField.focus();
				sourceField.selectAll();
			}
		});

		layout.addComponents(upload);
		layout.setComponentAlignment(upload, Alignment.MIDDLE_CENTER);

		// TODO: create uploads directory
		String username = System.getProperty("user.name");
		File uploads = new File("C:/Users/" + username + "/vaadin-uploads");
		if (!uploads.exists() && !uploads.mkdir())
			layout.addComponent(new Label(lang
					.getString("ERRORCouldNotCreateUploadDir")));

		return layout;
	}
}
