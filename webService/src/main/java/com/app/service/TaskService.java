package com.app.service;

import com.app.dao.TaskDao;
import com.app.model.Complete;
import com.app.model.Priority;
import com.app.model.Task;
import com.app.model.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
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
        try {
            List<Task> tasks = taskDao.getAllTasks();
            return new ResponseEntity(tasks, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("not found any tasks", HttpStatus.NOT_FOUND);
        }

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

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTasksByEmp(@PathVariable("id") long id){
        try {
            List<Task> tasks = taskDao.getTasksByEmp(id);
            return new ResponseEntity(tasks, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("error", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editTask(@RequestParam("id") long id,
                                   @RequestParam("title") String title,
                                   @RequestParam("type") TaskType taskType,
                                   @RequestParam("description") String description,
                                   @RequestParam("dateWhen") LocalDateTime dateWhen,
                                   @RequestParam("empId") long empId,
                                   @RequestParam("priority") Priority priority,
                                   @RequestParam("complete") Complete complete,
                                   @RequestParam("username") String username){
        Task task = new Task(title, taskType, description, dateWhen, empId, priority, complete);
        task.setId(id);
        try {
            taskDao.editTask(task, username);
            return new ResponseEntity("edited task with id = " + id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addTask(@RequestParam("id") long id,
                                  @RequestParam("title") String title,
                                  @RequestParam("type") String type,
                                  @RequestParam("description") String description,
                                  @RequestParam("dateWhen") LocalDateTime dateWhen,
                                  @RequestParam("priority") String priority,
                                  @RequestParam("username") String username){

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDateWhen(dateWhen);
        task.setEmpId(id);
        for(TaskType t:TaskType.values()){
            if(t.toString().equals(type)){
                task.setTaskType(t);
            }
        }
        for (Priority p: Priority.values()){
            if(p.toString().equals(priority)){
                task.setPriority(p);
            }
        }

        try {
            taskDao.addTask(task, username);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

