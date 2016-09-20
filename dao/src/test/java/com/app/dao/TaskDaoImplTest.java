package com.app.dao;

import com.app.model.Employee;
import com.app.model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by andrey on 20.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-spring-config.xml")
@Transactional
public class TaskDaoImplTest  extends Assert{

    @Autowired
    private TaskDao taskDao;

    @Test
    public void getAllTasksTest(){
        List<Task> tasks = taskDao.getAllTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
    }

    @Test
    public void addTaskTest(){
        Task task = new Task("new task", "test", "2016-05-05", "2016-06-06", 2);
        List<Task> tasksBeforeAdd = taskDao.getAllTasks();
        taskDao.addTask(task, "user");
        List<Task> tasksAfterAdd = taskDao.getAllTasks();

        assertEquals(tasksBeforeAdd.size(), tasksAfterAdd.size()-1);
    }

    @Test
    public void getTaskByIdTest(){
        Task task = taskDao.getTaskById(1);

        assertNotNull(task);
        assertEquals("task 1", task.getTitle());
    }

    @Test
    public void deleteTaskTest(){
        List<Task> tasks = taskDao.getAllTasks();
        taskDao.deleteTaskById(1, "user");
        List<Task> tasksAfterDel = taskDao.getAllTasks();

        assertEquals(tasks.size(), tasksAfterDel.size()+1);
    }

    @Test
    public void editTaskTest(){
        Task task = new Task("new task", "test", "2016-05-05", "2016-06-06", 2);
        task.setId(1);
        taskDao.editTask(task, "user");

        Task taskEdited = taskDao.getTaskById(1);

        assertEquals("new task", taskEdited.getTitle());
    }
}
