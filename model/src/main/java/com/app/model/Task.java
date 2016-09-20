package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by andrey on 20.09.16.
 */
@Entity
@Table(name = "tasks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task extends AbstractEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "start_task")
    private String startTask;
    @Column(name = "end_task")
    private String endTask;
    @Column(name = "emp_id")
    private long empId;

    public Task(){

    }

    public Task(String title, String description, String startTask, String endTask, long empId){
        this.title = title;
        this.description = description;
        this.startTask = startTask;
        this.endTask = endTask;
        this.empId = empId;
    }

    public Task(long id, boolean enabled, String createAt, String updateAt, long createBy, long updateBy,
                String title, String description, String startTask, String endTask, long empId) {
        super(id, enabled, createAt, updateAt, createBy, updateBy);
        this.title = title;
        this.description = description;
        this.startTask = startTask;
        this.endTask = endTask;
        this.empId = empId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTask() {
        return startTask;
    }

    public void setStartTask(String startTask) {
        this.startTask = startTask;
    }

    public String getEndTask() {
        return endTask;
    }

    public void setEndTask(String endTask) {
        this.endTask = endTask;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }
}
