/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.assesment.web.sp;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author walle
 * <p>
 * Created on Jul 26, 2016, 6:14:34 PM
 *
 */
public class Employee implements Serializable, Cloneable {

    @NotNull
    private String firstName = "";

    @NotNull
    private String lastName = "";

    @NotNull
    private String fullName;

    @NotNull
    @javax.validation.constraints.Size(min = 7, max = 9)
    private String employeeId = "";

    @NotNull
    private String department = "";

    @NotNull
    private String phoneNumber = "";

    public Employee() {
    }

    public Employee(String firstName, String lastName, String fullName, String employeeId, String department, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.employeeId = employeeId;
        this.department = department;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName = (firstName.concat(" ").concat(lastName));
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }

}
