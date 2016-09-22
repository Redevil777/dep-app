package com.app.controller;

import com.app.model.Employee;
import com.app.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by andrey on 22.09.16.
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private final String TASK_REST = "http://localhost:9000/task/";
    private final String EMPLOYEE_REST = "http://localhost:9000/employee/";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllTasks(){
        ModelAndView view = new ModelAndView("taskAll");
        RestTemplate restTemplate = new RestTemplate();
        try {
            Task[] tasks = restTemplate.getForObject(TASK_REST + "/all", Task[].class);
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/all", Employee[].class);
            view.addObject("tasks", tasks);
            view.addObject("employees", employees);
            return view;
        } catch (Exception e){
            view.addObject("error", "error");
            return view;
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ModelAndView getTaskByID(@PathVariable("id") long id){
        ModelAndView view = new ModelAndView("task");
        RestTemplate restTemplate = new RestTemplate();
        try {
            Task task = restTemplate.getForObject(TASK_REST + "/id/" + id, Task.class);
            view.addObject("task", task);
            return view;
        } catch (Exception e){
            view.addObject("error", "error");
            return view;
        }
    }

    @RequestMapping(value = "/title/{id}", method = RequestMethod.GET)
    public ModelAndView getTitlesOfTaskByEmployeeId(@PathVariable("id") long id){
        ModelAndView view = new ModelAndView("taskTitles");
        RestTemplate restTemplate = new RestTemplate();
        try {
            Task[] tasks = restTemplate.getForObject(TASK_REST + "/tasks/" + id, Task[].class);
            view.addObject("tasks", tasks);
            return view;
        } catch (Exception e){
            return view;
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTask(@PathVariable("id") long id){
        ModelAndView view = new ModelAndView("redirect:/task/all");
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        String userName = CurrentUserName.getCurrentUserName();
        map.add("username", userName);

        try {
            restTemplate.postForObject(TASK_REST + "/delete/" + id, map, String.class);
            return view;
        } catch (Exception e){
            return view;
        }
    }
}
