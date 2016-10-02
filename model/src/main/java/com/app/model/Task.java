package com.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_when")
    private LocalDateTime dateWhen;
    @Column(name = "emp_id")
    private long empId;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;
    @Enumerated(EnumType.STRING)
    @Column(name = "complete")
    private Complete complete;

    public Task(){

    }

    public Task(String title, TaskType taskType, String description, LocalDateTime dateWhen, long empId, Priority priority, Complete complete) {
        this.title = title;
        this.taskType = taskType;
        this.description = description;
        this.dateWhen = dateWhen;
        this.empId = empId;
        this.priority = priority;
        this.complete = complete;
    }

    public Task(long id, boolean enabled, LocalDateTime createAt, LocalDateTime updateAt, long createBy, long updateBy,
                String title, TaskType taskType, String description, LocalDateTime dateWhen, long empId, Priority priority, Complete complete) {
        super(id, enabled, createAt, updateAt, createBy, updateBy);
        this.title = title;
        this.taskType = taskType;
        this.description = description;
        this.dateWhen = dateWhen;
        this.empId = empId;
        this.priority = priority;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDateWhen() {
        return dateWhen;
    }

    public void setDateWhen(LocalDateTime dateWhen) {
        this.dateWhen = dateWhen;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Complete getComplete() {
        return complete;
    }

    public void setComplete(Complete complete) {
        this.complete = complete;
    }
}
