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
    @Enumerated(EnumType.STRING)
    @Column(name = "task_type")
    private TaskType taskType;
    @Column(name = "description")
    private String description;
    @Column(name = "dateWhen")
    private String dateWhen;
    @Column(name = "emp_id")
    private long empId;
    @Column(name = "complete")
    private boolean complete;

    public Task(){

    }

    public Task(String title, TaskType taskType, String description, String dateWhen, long empId, boolean complete) {
        this.title = title;
        this.taskType = taskType;
        this.description = description;
        this.dateWhen = dateWhen;
        this.empId = empId;
        this.complete = complete;
    }

    public Task(long id, boolean enabled, String createAt, String updateAt, long createBy, long updateBy,
                String title, TaskType taskType, String description, String dateWhen, long empId, boolean complete) {
        super(id, enabled, createAt, updateAt, createBy, updateBy);
        this.title = title;
        this.taskType = taskType;
        this.description = description;
        this.dateWhen = dateWhen;
        this.empId = empId;
        this.complete = complete;
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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getDateWhen() {
        return dateWhen;
    }

    public void setDateWhen(String dateWhen) {
        this.dateWhen = dateWhen;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
