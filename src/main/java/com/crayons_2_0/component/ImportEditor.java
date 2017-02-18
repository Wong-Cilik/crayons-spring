package com.crayons_2_0.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Creates a window for choosing a learning unit to be imported.
 *
 */
@SuppressWarnings("serial")
public class ImportEditor extends Window {

	/**
	 * 
	 */
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	/**
	 * Builds together several components of the import editor.
	 */
	public ImportEditor() {
		setSizeFull();
		setModal(true);
		setResizable(false);
		setClosable(true);
		setHeight(20.0f, Unit.PERCENTAGE);
		setWidth(40.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);

		Component title = buildTitle();
		title.setSizeUndefined();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.TOP_CENTER);

		Component unitDownloader = buildUnitDownloader();
		content.addComponent(unitDownloader);
		content.setComponentAlignment(unitDownloader, Alignment.MIDDLE_LEFT);
	}

	/**
	 * Builds title of the window.
	 * 
	 * @return the window title
	 */
	private Component buildTitle() {
		Label title = new Label(lang.getString("ImportExistingLearningUnit"));
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	/**
	 * Builds a component for choosing an existing learning unit to be imported.
	 * 
	 * @return the component with a path to the unit and "Import"-button
	 */
	private Component buildUnitDownloader() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100.0f, Unit.PERCENTAGE);
		layout.setSpacing(true);

		class UnitReceiver implements Receiver, SucceededListener {
			/**
			 * 
			 */
			public File file;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				for (Window window : UI.getCurrent().getWindows())
					window.close();
				Notification success = new Notification(
						lang.getString("FileIsImportedSuccessfully"));
				success.setDelayMsec(2000);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
				success.show(Page.getCurrent());
			}

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				FileOutputStream stream = null;
				// TODO: set directory
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

		UnitReceiver receiver = new UnitReceiver();

		final Upload upload = new Upload(null, receiver);
		upload.setButtonCaption(lang.getString("Import"));
		upload.addSucceededListener(receiver);
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
