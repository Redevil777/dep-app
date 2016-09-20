package com.app.dao;

import com.app.model.Task;

import java.util.List;

/**
 * Created by andrey on 20.09.16.
 */
public interface TaskDao {
    public void addTask(Task task, String username);
    public void deleteTaskById(long id, String username);
    public void editTask(Task task, String username);
    public List<Task> getAllTasks();
    public Task getTaskById(long id);
}
