package com.app.controller;

import com.app.model.*;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 19.08.16.
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    private final String EMPLOYEE_REST = "http://localhost:9000/employee/";
    private final String DEPARTMENT_REST = "http://localhost:9000/department/";
    private final String USER_REST = "http://localhost:9000/user/";
    private final String TASK_REST = "http://localhost:9000/task/";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ALL_EMPLOYEES_GET')")
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("employee");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/all", Employee[].class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            User[] users = restTemplate.getForObject(USER_REST + "/all", User[].class);
            Task[] tasks = restTemplate.getForObject(TASK_REST + "/all", Task[].class);
            Employee employee = CurrentEmployee.getEmployee();
            view.addObject("departments", departments);
            view.addObject("employees", employees);
            view.addObject("tasks", tasks);
            view.addObject("users", users);
            view.addObject("employee", employee);
        } catch (Exception e) {
            view.addObject("error", "Not found any employees.");
        }
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADD_EMPLOYEE_GET')")
    public ModelAndView getAddEmployee() {

        ModelAndView view = new ModelAndView("employeeadd");

        RestTemplate restTemplate = new RestTemplate();


        view.addObject("employees", new EmployeeBuilder().createEmployee());
        try {
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("departments", departments);
        } catch (Exception e){

        }
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADD_EMPLOYEE_POST')")
    public ModelAndView addEmployee(RedirectAttributes redirectAttributes,
                                    @RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("middleName") String middleName,
                                    @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                                    @RequestParam("email") String email,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("address") String address,
                                    @RequestParam("salary") Long salary,
                                    @RequestParam("depId") Long dep_id) {
        RestTemplate restTemplate = new RestTemplate();

        String userName = CurrentUserName.getCurrentUserName();

        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("firstName", firstName);
            map.add("lastName", lastName);
            map.add("middleName", middleName);
            map.add("birthday", birthday.toString());
            map.add("email", email);
            map.add("phone", phone);
            map.add("address", address);
            map.add("salary", salary.toString());
            map.add("depId", dep_id.toString());
            map.add("userName", userName);

            restTemplate.postForObject(EMPLOYEE_REST + "/add", map, String.class);

            redirectAttributes.addFlashAttribute("message", "New employee added.");
            return new ModelAndView("redirect:/employee/all");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Can't add new employee, may be department list is empty");
            return new ModelAndView("redirect:/employee/all");
        }


    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DELETE_EMPLOYEE_GET')")
    public ModelAndView deleteEmployeeById(RedirectAttributes redirectAttributes,
                                           @PathVariable long id) {

        ModelAndView view = new ModelAndView("redirect:/employee/all");

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        String userName = CurrentUserName.getCurrentUserName();
        map.add("userName", userName);

        try {
            restTemplate.postForObject(EMPLOYEE_REST + "/delete/" + id, map, String.class);

            redirectAttributes.addFlashAttribute("message", "employee deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Can't delete employee with id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EDIT_EMPLOYEE_GET')")
    public ModelAndView getEdit(@PathVariable Long id) {

        ModelAndView view = new ModelAndView("employeeedit");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee employee = restTemplate.getForObject(EMPLOYEE_REST + "/id/" + id, Employee.class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("employee", employee);
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "can't edit employee with id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('EDIT_EMPLOYEE_POST')")
    public ModelAndView saveEdit(RedirectAttributes redirectAttributes,
                                 @RequestParam("id") String id,
                                 @RequestParam("firstName") String fname,
                                 @RequestParam("lastName") String lname,
                                 @RequestParam("middleName") String mname,
                                 @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                                 @RequestParam("email") String email,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("address") String address,
                                 @RequestParam("salary") Long salary,
                                 @RequestParam("depId") Long dep_id) {

        ModelAndView view = new ModelAndView("redirect:/employee/all");

        RestTemplate restTemplate = new RestTemplate();

        String userName = CurrentUserName.getCurrentUserName();

        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("id", id);
            map.add("firstName", fname);
            map.add("lastName", lname);
            map.add("middleName", mname);
            map.add("birthday", birthday.toString());
            map.add("email", email);
            map.add("phone", phone);
            map.add("address", address);
            map.add("salary", salary.toString());
            map.add("depId", dep_id.toString());
            map.add("userName", userName);

            restTemplate.postForObject(EMPLOYEE_REST + "/edit", map, String.class);

            redirectAttributes.addFlashAttribute("message", "employee edited");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "can't edit employee with id = " + id);
        }

        return view;
    }

    /**
     *
     * @param date
     * @return
     */
    @RequestMapping(value = "/date", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EMPLOYEE_BY_DOB')")
    public ModelAndView getEmployeesByDateOfBirth(
            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        ModelAndView view = new ModelAndView("employeedate");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/date/" + date, Employee[].class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("employees", employees);
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "Can't find employees for this date.");
        }

        return view;
    }

    @RequestMapping(value = "/between", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EMPLOYEE_BETWEEN_DOB')")
    public ModelAndView getEmployeeBetweenDates(
            @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        ModelAndView view = new ModelAndView("employeesearch");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/between/" + from + "/" + to, Employee[].class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("employees", employees);
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "Can't find employees between this date!");
        }
        return view;
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EMPLOYEE_DETAILS')")
    public ModelAndView showEmployeeDetails(@PathVariable Long id) {
        ModelAndView view = new ModelAndView("allEmployees");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee employee = restTemplate.getForObject(EMPLOYEE_REST + "/id/" + id, Employee.class);
            view.addObject("info", employee);
        } catch (Exception e) {

        }
        return view;
    }
}
