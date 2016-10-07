package com.app.controller;

import com.app.model.Department;
import com.app.model.DepartmentBuilder;
import com.app.model.Employee;
import com.app.model.User;
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

/**
 * Created by andrey on 19.08.16.
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

    private final String DEPARTMENT_REST = "http://localhost:9000/department/";
    private final String USER_REST = "http://localhost:9000/user/";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ALL_DEPARTMENTS_GET')")
    public ModelAndView getAllDepartments() {

        ModelAndView view = new ModelAndView("department");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            User[] users = restTemplate.getForObject(USER_REST + "/all", User[].class);
            Employee employee = CurrentEmployee.getEmployee();
            view.addObject("employee", employee);
            view.addObject("departments", departments);
            view.addObject("department", new DepartmentBuilder().createDepartment());
            view.addObject("users", users);
        } catch (Exception e) {
            System.out.println("catch");
            view.addObject("error", "Not found any departments.");
        }
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADD_DEPARTMENT_GET')")
    public ModelAndView getAddDepartment(){

        ModelAndView view = new ModelAndView("departmentadd");

        view.addObject("department", new DepartmentBuilder().createDepartment());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADD_DEPARTMENT_POST')")
    public ModelAndView AddDepartment(RedirectAttributes redirectAttributes,
                                      @RequestParam("depName") String depName){

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        String userName = CurrentUserName.getCurrentUserName();

        map.add("depName", depName);
        map.add("userName", userName);

        Department department = new DepartmentBuilder().createDepartment();
        department.setDepName(depName);

        try {
            restTemplate.postForObject(DEPARTMENT_REST + "/add", department, Department.class);
            redirectAttributes.addFlashAttribute("message", "New department added.");
            return new ModelAndView("redirect:/department/all");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Department with this name already exist.");

            return new ModelAndView("redirect:/department/all");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DELETE_DEPARTMENT_GET')")
    public ModelAndView deleteDepartmentById(RedirectAttributes redirectAttributes,
                                             @PathVariable long id) {

        ModelAndView view = new ModelAndView("redirect:/department/all");

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        String userName = CurrentUserName.getCurrentUserName();
        map.add("userName", userName);
        Department department = new Department();
        department.setId(id);

        try {
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.postForObject(DEPARTMENT_REST + "/delete", department, Department.class);
            redirectAttributes.addFlashAttribute("message", "Department deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute( "error", "Can't remove department with id = " + id);
        }
        return view;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EDIT_DEPARTMENT_GET')")
    public ModelAndView getEditDepartment(@PathVariable long id){

        ModelAndView view = new ModelAndView("departmentedit");

        try {
            RestTemplate restTemplate = new RestTemplate();

            Department department = restTemplate.getForObject(DEPARTMENT_REST + "/id/" + id, Department.class);

            User[] users = restTemplate.getForObject(USER_REST + "/all", User[].class);
            view.addObject("users", users);
            view.addObject("department", department);
        } catch (Exception e) {
            view.addObject("error", "Can't edit department whit id = " + id);
        }
        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('EDIT_DEPARTMENT_POST')")
    public ModelAndView saveEditDepartment(RedirectAttributes redirectAttributes,
                                           @RequestParam("id")   long   id,
                                           @RequestParam("depName") String name){
        ModelAndView view = new ModelAndView("redirect:/department/all");

        RestTemplate restTemplate = new RestTemplate();

        String userName = CurrentUserName.getCurrentUserName();

        Department department = new DepartmentBuilder().createDepartment();
        department.setId(id);
        department.setDepName(name);

        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("id", String.valueOf(id));
            map.add("depName", name);
            map.add("userName", userName);
            restTemplate.postForObject(DEPARTMENT_REST + "/edit", department, Department.class);

            redirectAttributes.addFlashAttribute("message", "Department edited");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Can't edit department whit id =" + id);
        }
        return view;
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EMPLOYEES_BY_DEP_GET')")
    public ModelAndView getEmployeesBySelectedDepartment(@PathVariable long id){

        ModelAndView view = new ModelAndView("departmentemployee");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(DEPARTMENT_REST + "/employees/" + id, Employee[].class);

            view.addObject("employees", employees);
        } catch (Exception e) {
            view.addObject("message", "Not found any employees.");
        }
        return view;
    }
}
