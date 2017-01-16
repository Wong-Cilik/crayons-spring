package com.crayons_2_0.service.graph;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class DeleteUnitListener  implements Button.ClickListener {

    @Override
    public void buttonClick(ClickEvent event) {
        for (Window window: UI.getCurrent().getWindows()) window.close();
        Notification success = new Notification(
                "Unit is deleted successfully");
        success.setDelayMsec(2000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }
}
