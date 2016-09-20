package com.app.service;

import com.app.dao.TaskDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by andrey on 20.09.16.
 */
@Service
@RequestMapping(value = "/task")
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTask(){
        List<Task> tasks = taskDao.getAllTasks();

        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity deleteTask(@PathVariable("id") long id,
                                     @RequestParam("username") String username){
        try {
            taskDao.deleteTaskById(id, username);
            return new ResponseEntity("deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("error", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id){
        try {
            Task task = taskDao.getTaskById(id);
            if(task.getTitle()==null){
                throw new Exception();
            }
            return new ResponseEntity(task, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("not found task with id = " + id, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addTask(@RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("start_task") String start_task,
                                  @RequestParam("end_task") String end_task,
                                  @RequestParam("emp_id") long emp_id,
                                  @RequestParam("username") String username){
        Task task = new Task(title, description, start_task, end_task, emp_id);
        try {
            taskDao.addTask(task, username);
            return new ResponseEntity("new task added", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editTask(@RequestParam("id") long id,
                                   @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("start_task") String start_task,
                                   @RequestParam("end_task") String end_task,
                                   @RequestParam("emp_id") long emp_id,
                                   @RequestParam("username") String username){
        Task task = new Task(title, description, start_task, end_task, emp_id);
        task.setId(id);

        try {
            taskDao.editTask(task, username);
            return new ResponseEntity("edited task with id = " + id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }
    }
}

