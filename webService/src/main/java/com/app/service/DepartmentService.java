package com.app.service;

import com.app.dao.DepartmentDao;
import com.app.model.Department;
import com.app.model.DepartmentBuilder;
import com.app.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andrei on 17.09.16.
 */
@Service
@RequestMapping(value = "/department")
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departments = departmentDao.getAllDepartments();

        return new ResponseEntity(departments, HttpStatus.OK);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity addDepartment(@RequestBody Department department){

        //Department department = new DepartmentBuilder().setDepName(depName).createDepartment();
        try {
            departmentDao.addDepartment(department);
            return new ResponseEntity("", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),  HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        try {
            Department department = departmentDao.getDepartmentById(id);
             if(department.getDepName()==null) {
                 throw new Exception();
             }
            return new ResponseEntity(department, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Department not found with id=" + id + ", error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editDepartment(@RequestParam("id") Long id,
                                         @RequestParam("depName") String name,
                                         @RequestParam("userName") String userName){

        Department department = new DepartmentBuilder().setId(id).setDepName(name).createDepartment();

        try {
            departmentDao.editDepartment(department);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Check input data!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity deleteDepartment(@PathVariable("id") long id,
                                           @RequestParam("userName") String userName){

        Department department = new Department();
        department.setId(id);
        try {
            String message = departmentDao.deleteDepartmentById(department);
            if(message.equals("neOk")) throw new Exception();
            return new ResponseEntity("", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/employees/{depId}", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getEmployeesBySelectedDepartment(@PathVariable("depId") long dep_id){

        try {
            List<Employee> employees = departmentDao.getEmployeesBySelectedDepartment(dep_id);
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
