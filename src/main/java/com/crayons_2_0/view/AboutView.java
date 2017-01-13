package com.crayons_2_0.view;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

import com.crayons_2_0.component.MultipleChoice;
import com.crayons_2_0.service.DatabaseException;
import com.crayons_2_0.service.JDBCConnection;
import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageControl;

@SpringUI
public class AboutView extends VerticalLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "About";
    
    ResourceBundle lang = LanguageControl.getInstance().getRes();

    public AboutView() {
        VerticalLayout aboutContent = new VerticalLayout();
        aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.addComponent(new MultipleChoice());
        aboutContent.addComponent(
                new Label(FontAwesome.INFO_CIRCLE.getHtml()
                        + " This application is using Vaadin "
                        + Version.getFullVersion(), ContentMode.HTML));

        setSizeFull();
        setStyleName("about-view");
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
        TwinColSelect sample = new TwinColSelect();
        for (int i = 0; i < 6; i++) {
            sample.addItem(i);
            sample.setItemCaption(i, "Option " + i);
        }
        sample.setRows(6);
        sample.setNullSelectionAllowed(true);
        sample.setMultiSelect(true);
        sample.setImmediate(true);
        sample.setLeftColumnCaption("Available options");
        sample.setRightColumnCaption("Selected options");
 
 
        sample.addValueChangeListener(e -> Notification.show("Value changed:"));
        aboutContent.addComponent(sample);
        
      // NEW-DATENBANK----TEST--------------------------------------------------------------------
        Button testDB = new Button("Teste Datenbank");
        testDB.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				try {
					Statement statement = JDBCConnection.getInstance().getStatement();
					ResultSet set = statement.executeQuery("SELECT * FROM realm.user");
					
					while (set.next()) {
						Label levin = new Label(set.getString(1));
				        aboutContent.addComponent(levin);
					}
				} catch (DatabaseException | SQLException e1) {
					e1.printStackTrace();
				}
		        Label levin = new Label("Hier müssten die Namen erscheinen");
		        aboutContent.addComponent(levin);
		        
			}
		});
        aboutContent.addComponent(testDB);
        //-------------------------------------------------------------------------------
        
        Button buttonGerman = new Button(lang.getString("German"));
        buttonGerman.setIcon(FontAwesome.BEER);
        buttonGerman.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				LanguageControl.getInstance().setCurrentLocale(Language.German);
				Page.getCurrent().reload();
			}
		});
        aboutContent.addComponent(buttonGerman);
        
        Button buttonEnglish = new Button(lang.getString("English"));
        buttonEnglish.setIcon(FontAwesome.COFFEE);
        buttonEnglish.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				LanguageControl.getInstance().setCurrentLocale(Language.English);
				Page.getCurrent().reload();
			}
		});
        aboutContent.addComponent(buttonEnglish);
        
        //---------------------------------------------------------------------------------
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
