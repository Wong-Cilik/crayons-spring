package com.crayons_2_0.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.maddon.ListContainer;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.UserDisplay;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SpringView(name = AdminView.VIEW_NAME)
@ViewScope
@SpringComponent
public final class AdminView extends VerticalLayout implements View {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CourseService courseService;
	
    List<UserDisplay> collection = new ArrayList<UserDisplay>();
    public static final String VIEW_NAME = "AdminView";
    private Table table;
    
    public AdminView() {

    }

    @PostConstruct
    void init(){
        setSizeFull();
        addStyleName("courseDisplay");
        addComponent(buildToolbar());

        table = buildTable();
        addComponent(table);
        setExpandRatio(table, 1);
    }
    
    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);
        return header;
    }

	@SuppressWarnings("deprecation")
	private Table buildTable() {
        final Table table = new Table();
        table.setSizeFull();
        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);

        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        
        for (CrayonsUser tmpUser : userService.findAll()) {
        	
        	collection.add(new UserDisplay(tmpUser.getEmail(), tmpUser.getFirstName() + " " + tmpUser.getLastName(), "Admin", courseService.findAllAuthorCoursesOfUser(tmpUser).size(), courseService.findAllCoursesOfUser(tmpUser).size()));
        }
        table.setSortContainerPropertyId("email");
        table.setSortAscending(false);

        table.setContainerDataSource(new TempContainer(collection));
        table.setVisibleColumns("email", "name", "role", "createdCourses", "visitedCourses");
        table.setColumnHeaders("eMail", "Name", "Rolle", "Erstellte Kurse", "Besuchte Kurse");
        
        table.addListener(new ItemClickListener(){
			@Override
			public void itemClick(ItemClickEvent event) {
				Notification fail = new Notification("//TODO");
				fail.setDelayMsec(2000);
				fail.setStyleName("bar fail small");
				fail.setPosition(Position.BOTTOM_CENTER);
				fail.show(Page.getCurrent());
			}

		});
		return table;
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

	private class TempContainer extends ListContainer<UserDisplay> {

		public TempContainer(final Collection<UserDisplay> collection) {
			super(collection);
		}

	}
}