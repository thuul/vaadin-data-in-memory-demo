package local.ikram.assesment.web;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import local.ikram.assesment.web.sp.Employee;
import local.ikram.assesment.web.sp.MemoryDataStore;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("apptheme")
@Widgetset("local.ikram.assesment.web.AppContextWidgetset")
public class AppContextUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        // Banner UI components
        final Label bannerLabel = new Label("Employee Management Form");
        bannerLabel.addStyleName("banner-head");

        final HorizontalLayout bannerNorth = new HorizontalLayout(bannerLabel);
        bannerNorth.setSpacing(true);
        bannerNorth.setComponentAlignment(bannerLabel, Alignment.MIDDLE_CENTER);

        // Top North UI components
        final Label searchLabel = new Label("Employee Id: ");
        final TextField searchField = new TextField();
        searchField.setInputPrompt("search by Employee Id");

        final HorizontalLayout topNorthLayout = new HorizontalLayout(searchLabel, searchField);
        topNorthLayout.setSpacing(true);
        topNorthLayout.setComponentAlignment(searchLabel, Alignment.MIDDLE_LEFT);
        topNorthLayout.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);

        // Panel Horizontal UI components
        final SimpleFormComponent form = new SimpleFormComponent();
        final Panel formPanel = new Panel("Employee Form", form);
        formPanel.setSizeUndefined();
        final VerticalLayout vLayout = new VerticalLayout(formPanel);

        topNorthLayout.addComponent(form.getAddBtn());

        // Grid Horizontal UI components
        final Grid tableGrid = new Grid();
        tableGrid.setColumns("employeeId", "firstName", "lastName", "fullName", "phoneNumber", "department");
        tableGrid.setHeight(400, Unit.PIXELS);
        tableGrid.setWidth(800, Unit.PIXELS);
        tableGrid.setContainerDataSource(new BeanItemContainer(Employee.class, MemoryDataStore.getInstance().loadGridData()));
        tableGrid.addSelectionListener((SelectionEvent e) -> {
            Employee innerEmployee = (Employee) tableGrid.getSelectedRow();
            form.edit(innerEmployee);
        });
        form.setTableGrid(tableGrid);

        //moved down here for convinience.
        searchField.addTextChangeListener((FieldEvents.TextChangeEvent e) -> {
            tableGrid.setContainerDataSource(new BeanItemContainer(Employee.class, MemoryDataStore.getInstance().loadGridData(e.getText())));
        });

        // Base Horizontal UI components
        final HorizontalLayout hLayout = new HorizontalLayout(tableGrid, vLayout);
        hLayout.setSpacing(true);

        final VerticalLayout baseLayout = new VerticalLayout(bannerNorth, topNorthLayout, hLayout);
        baseLayout.setMargin(true);
        baseLayout.setSpacing(true);
        baseLayout.setComponentAlignment(bannerNorth, Alignment.MIDDLE_CENTER);
        baseLayout.addStyleName("body-ground");

        setContent(baseLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "AppContextUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AppContextUI.class, productionMode = false)
    public static class AppContextUIServlet extends VaadinServlet {
    }
}
