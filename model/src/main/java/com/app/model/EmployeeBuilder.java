package com.app.model;

public class EmployeeBuilder {
    private long id;
    private boolean enabled;
    private String createAt;
    private String updateAt;
    private long createBy;
    private long updateBy;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthday;
    private String email;
    private String phone;
    private String address;
    private long salary;
    private long depId;

    public EmployeeBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public EmployeeBuilder setCreateAt(String createAt) {
        this.createAt = createAt;
        return this;
    }

    public EmployeeBuilder setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public EmployeeBuilder setCreateBy(long createBy) {
        this.createBy = createBy;
        return this;
    }

    public EmployeeBuilder setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public EmployeeBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public EmployeeBuilder setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public EmployeeBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public EmployeeBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public EmployeeBuilder setSalary(long salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder setDepId(long depId) {
        this.depId = depId;
        return this;
    }

    public Employee createEmployee() {
        return new Employee(id, enabled, createAt, updateAt, createBy, updateBy, firstName, lastName, middleName, birthday, email, phone, address, salary, depId);
    }
}