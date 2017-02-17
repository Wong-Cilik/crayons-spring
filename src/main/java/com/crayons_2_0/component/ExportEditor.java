package com.crayons_2_0.component;

import java.util.ResourceBundle;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Component;

public class ExportEditor {
	
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public ExportEditor() {
		// TODO Auto-generated constructor stub
	}

	public Component ExportButton() {

		Button export = new Button(lang.getString("Export"));
		export.addStyleName(ValoTheme.BUTTON_PRIMARY);
		export.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				for (Window window : UI.getCurrent().getWindows())
					window.close();
				Notification success = new Notification(lang.getString("fileIsExportedSuccessfully"));
				success.setDelayMsec(2000);
				success.setStyleName("bar success small");
				success.setPosition(Position.BOTTOM_CENTER);
				success.show(Page.getCurrent());

			}
		});
		return export;

	}

}
