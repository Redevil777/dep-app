package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by andrei on 15.09.16.
 */
@Entity
@Table(name = "department")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "dep_id")
    private long id;

    @Column(name = "dep_name")
    private String depName;

    public Department(){

    }

    public Department(String depName) {
        this.depName = depName;
    }

    public Department(long id, String depName){
        this.id = id;
        this.depName = depName;
    }



    public Department(long id, boolean enabled, String createAt, String updateAt, long createBy, long updateBy, String depName) {
        super(id, enabled, createAt, updateAt, createBy, updateBy);
        this.depName = depName;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
