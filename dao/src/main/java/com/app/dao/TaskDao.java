package com.app.dao;

import com.app.model.Task;

import java.util.List;

/**
 * Created by andrey on 20.09.16.
 */
public interface TaskDao {
    public void addTask(Task task);
    public void deleteTaskById(Task task);
    public void editTask(Task task);
    public List<Task> getAllTasks();
    public Task getTaskById(long id);
    public List<Task> getTasksByEmp(long id);
}
