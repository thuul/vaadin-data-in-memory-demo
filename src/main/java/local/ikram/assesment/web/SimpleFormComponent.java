/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.assesment.web;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import local.ikram.assesment.web.sp.Employee;
import local.ikram.assesment.web.sp.MemoryDataStore;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author walle
 * <p>
 * Created on Jul 27, 2016, 2:07:01 PM
 *
 */
public class SimpleFormComponent extends CustomComponent {

    private TextField firstName;
    private TextField lastName;
    private TextField phoneNumber;
    private TextField department;
    private TextField employeeId;
    private Grid tableGrid;
    private Button saveBtn, deleteBtn, addBtn;

    private boolean exist;
    private Employee employee;

    public SimpleFormComponent() {
        initForm();
    }

    private void initForm() {

        employeeId = new TextField("Employee Id: ");
        employeeId.setIcon(FontAwesome.FILE_PICTURE_O);
        employeeId.setRequired(true);
        employeeId.addValidator(new StringLengthValidator("Must be btw 7 and 9 characters", 7, 9, false));

        firstName = new TextField("First Name: ");
        firstName.setIcon(FontAwesome.USER);
        firstName.setRequired(true);
        firstName.addValidator(new NullValidator("Cannot be empty", false));

        lastName = new TextField("Last Name: ");
        lastName.setIcon(FontAwesome.USER);
        lastName.setRequired(true);
        lastName.addValidator(new NullValidator("Cannot be empty", false));

        phoneNumber = new TextField("Phone Number: ");
        phoneNumber.setIcon(FontAwesome.PHONE_SQUARE);
        phoneNumber.setRequired(true);
        phoneNumber.addValidator(new NullValidator("Cannot be empty", false));

        department = new TextField("Department: ");
        department.setIcon(FontAwesome.DESKTOP);
        department.setRequired(true);
        department.addValidator(new NullValidator("Cannot be empty", false));

        saveBtn = new Button("Save", FontAwesome.SAVE);
        saveBtn.addStyleName("save-btn");
        saveBtn.addClickListener((Button.ClickEvent e) -> {
            try {
                employeeId.validate();
                firstName.validate();
                lastName.validate();
                phoneNumber.validate();
                department.validate();
                update(getEmployee());
            } catch (Validator.InvalidValueException ex) {
                Logger.getLogger(SimpleFormComponent.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        deleteBtn = new Button("Delete", FontAwesome.RECYCLE);
        deleteBtn.addStyleName("delete-btn");
        deleteBtn.addClickListener((Button.ClickEvent e) -> {
            delete(getEmployee());
        });
        addBtn = new Button("Add Employee", FontAwesome.PLUS_SQUARE);
        addBtn.addStyleName("add-btn");
        addBtn.addClickListener((Button.ClickEvent e) -> {
            clear();
        });

        final HorizontalLayout buttonLayout = new HorizontalLayout(saveBtn, deleteBtn);
        buttonLayout.setSpacing(true);

        final FormLayout formLayout = new FormLayout();
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        formLayout.setData(this);
        formLayout.addComponents(employeeId, firstName, lastName, phoneNumber, department, buttonLayout);
        setCompositionRoot(formLayout);

        setEmployee(new Employee());
        BeanFieldGroup.bindFieldsUnbuffered(employee, this);
    }

    public void edit(Employee e) {
        if (e != null) {
            if (!StringUtils.isBlank(e.getEmployeeId())) {
                setExist(true);
                setEmployee(e);
                BeanFieldGroup.bindFieldsUnbuffered(e, this);
                employeeId.setEnabled(false);
            }
        }
    }

    private void update(Employee e) {
        if (isExist()) {
            MemoryDataStore.getInstance().update(e);
        } else {
            MemoryDataStore.getInstance().create(getEmployee());
        }
        clear();
        tableGrid.setContainerDataSource(new BeanItemContainer(Employee.class, MemoryDataStore.getInstance().loadGridData()));
    }

    private void delete(Employee e) {
        MemoryDataStore.getInstance().delete(e);
        clear();
        tableGrid.setContainerDataSource(new BeanItemContainer(Employee.class, MemoryDataStore.getInstance().loadGridData()));
    }

    private void clear() {
        employeeId.setValue("");
        firstName.setValue("");
        lastName.setValue("");
        phoneNumber.setValue("");
        department.setValue("");
        employeeId.setEnabled(true);
        setExist(false);
        setEmployee(new Employee());
        BeanFieldGroup.bindFieldsUnbuffered(employee, this);
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setTableGrid(Grid tableGrid) {
        this.tableGrid = tableGrid;
    }

    public Button getAddBtn() {
        return addBtn;
    }

}
