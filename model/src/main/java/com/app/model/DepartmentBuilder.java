package com.app.model;

public class DepartmentBuilder {
    private String depName;
    private long id;
    private boolean enabled;
    private String createAt;
    private String updateAt;
    private long createBy;
    private long updateBy;

    public DepartmentBuilder setDepName(String depName) {
        this.depName = depName;
        return this;
    }

    public DepartmentBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public DepartmentBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public DepartmentBuilder setCreateAt(String createAt) {
        this.createAt = createAt;
        return this;
    }

    public DepartmentBuilder setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public DepartmentBuilder setCreateBy(long createBy) {
        this.createBy = createBy;
        return this;
    }

    public DepartmentBuilder setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public Department createDepartment() {
        return new Department(depName);
    }
}