package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by andrei on 15.09.16.
 */
@Entity
@Table(name = "department")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department extends AbstractEntity {

    @Column(name = "dep_name")
    private String depName;

    public Department(){

    }

    public Department(boolean enabled, String createAt, String updateAt, long createBy, long updateBy, String depName) {
        super(enabled, createAt, updateAt, createBy, updateBy);
        this.depName = depName;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
