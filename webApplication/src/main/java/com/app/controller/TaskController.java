package com.app.controller;

import com.app.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by andrey on 22.09.16.
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private final String TASK_REST = "http://localhost:9000/task/";
    private final String EMPLOYEE_REST = "http://localhost:9000/employee/";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ALL_TASKS_GET')")
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
    @PreAuthorize("hasAuthority('TASK_BY_ID_GET')")
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
    @PreAuthorize("hasAuthority('TITLES_OF_EMP_GET')")
    public ModelAndView getTitlesOfTaskByEmployeeId(@PathVariable("id") long id){
        ModelAndView view = new ModelAndView("taskEmp");
        RestTemplate restTemplate = new RestTemplate();
        List<String> types = Stream.of(TaskType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        List<String> priority = Stream.of(Priority.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        List<String> complete = Stream.of(Complete.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        try {
            Employee employee = CurrentEmployee.getEmployee();
            Task[] tasks = restTemplate.getForObject(TASK_REST + "/tasks/" + id, Task[].class);
            view.addObject("tasks", tasks);
            view.addObject("employee", employee);
            view.addObject("types", types);
            view.addObject("priority", priority);
            view.addObject("complete", complete);
            return view;
        } catch (Exception e){
            return view;
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DELETE_TASK_GET')")
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

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADD_TASK_GET')")
    public ModelAndView getAddTask(@PathVariable("id") long id){
        ModelAndView view = new ModelAndView("taskadd");
        view.addObject("empId", id);
        view.addObject("task", new Task());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADD_TASK_POST')")
    public ModelAndView addTask(@RequestParam("title") String title,
                                @RequestParam("taskType") String type,
                                @RequestParam("description") String description,
                                @RequestParam("startTime") String startTime,
                                @RequestParam("endTime") String endTime,
                                @RequestParam("empId") long empId,
                                @RequestParam("priority") String priority){
        ModelAndView view = new ModelAndView("redirect:/task/title/" + empId);
        RestTemplate restTemplate = new RestTemplate();

        String username = CurrentUserName.getCurrentUserName();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("title", title);
        map.add("type", type);
        map.add("description", description);
        map.add("startTime", startTime);
        map.add("endTime", endTime);
        map.add("empId", empId);
        map.add("priority", priority);
        map.add("username", username);
        try {
            restTemplate.postForObject(TASK_REST + "/add", map, String.class);
            return view;
        } catch (Exception e) {
            return view;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EDIT_TASK_GET')")
    public ModelAndView getEdit(@PathVariable("id") long id){
        ModelAndView view = new ModelAndView("taskEdit");
        RestTemplate restTemplate = new RestTemplate();

        List<String> types = Stream.of(TaskType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        List<String> priority = Stream.of(Priority.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        List<String> complete = Stream.of(Complete.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        try {
            Task task = restTemplate.getForObject(TASK_REST + "/id/" + id, Task.class);
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/all", Employee[].class);
            view.addObject("task", task);
            view.addObject("employees", employees);
            view.addObject("type", types);
            view.addObject("pr", priority);
            view.addObject("comp", complete);
            return view;
        } catch (Exception e) {
            view.addObject("error", e.getMessage());;
            return view;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('EDIT_TASK_POST')")
    public ModelAndView saveEdit(@RequestParam("id") long id,
                                 @RequestParam("title") String title,
                                 @RequestParam("taskType") String type,
                                 @RequestParam("description") String description,
                                 @RequestParam("startTime") String startTime,
                                 @RequestParam("endTime") String endTime,
                                 @RequestParam("empId") long empId,
                                 @RequestParam("priority") String priority,
                                 @RequestParam("complete") String complete){

        ModelAndView view = new ModelAndView("redirect:/task/title/" + empId);

        RestTemplate restTemplate = new RestTemplate();

        String userName = CurrentUserName.getCurrentUserName();
        ;
        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("id", id);
            map.add("title", title);
            map.add("type", type);
            map.add("description", description);
            map.add("startTime", startTime);
            map.add("endTime", endTime);
            map.add("empId", empId);
            map.add("priority", priority);
            map.add("complete", complete);
            map.add("username", userName);

            restTemplate.postForObject(TASK_REST + "/edit", map, String.class);
            return view;
        } catch (Exception e){
            return view;
        }
    }
}
