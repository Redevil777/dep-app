package com.app.service;

import com.app.dao.EmployeeDao;
import com.app.model.Employee;
import com.app.model.EmployeeBuilder;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by andrei on 17.09.16.
 */
@Service
@RequestMapping(value = "/employee")
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeDao.getAllEmployees();

        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addEmployee(@RequestParam("firstName") String fname,
                                      @RequestParam("lastName") String lname,
                                      @RequestParam("middleName") String mname,
                                      @RequestParam("birthday") String birthday,
                                      @RequestParam("email") String email,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("address") String address,
                                      @RequestParam("salary") long salary,
                                      @RequestParam("depId") long dep_id,
                                      @RequestParam("userName") String userName) {

        Employee employee = new EmployeeBuilder()
                .setFirstName(fname)
                .setLastName(lname)
                .setMiddleName(mname)
                .setBirthday(birthday)
                .setEmail(email)
                .setPhone(phone)
                .setAddress(address)
                .setSalary(salary)
                .setDepId(dep_id)
                .setCreateAt(LocalDateTime.now().toString())
                .setUpdateAt(LocalDateTime.now().toString())
                .setEnabled(true)
                .setCreateBy(1)
                .setUpdateBy(1)
                .createEmployee();

        try {
            employeeDao.addEmployee(employee, userName);
            return new ResponseEntity("", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {

        try {
            Employee employee = employeeDao.getEmployeeById(id);
            if(employee.getFirstName()==null){
                throw  new Exception();
            }
            return new ResponseEntity(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Employee not found with id=" + id + ", error: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity deleteEmployee(@PathVariable("id") Long id,
                                         @RequestParam("userName") String userName) {
        try {
            employeeDao.deleteEmployeeById(id, userName);
            return new ResponseEntity("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editEmployee(@RequestParam("id") long id,
                                       @RequestParam("firstName") String fname,
                                       @RequestParam("lastName") String lname,
                                       @RequestParam("middleName") String mname,
                                       @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") String birthday,
                                       @RequestParam("email") String email,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("address") String address,
                                       @RequestParam("salary") long salary,
                                       @RequestParam("depId") long dep_id,
                                       @RequestParam("userName") String userName) {

        Employee employee = new EmployeeBuilder()
                .setId(id)
                .setFirstName(fname)
                .setLastName(lname)
                .setMiddleName(mname)
                .setBirthday(birthday)
                .setEmail(email)
                .setPhone(phone)
                .setAddress(address)
                .setSalary(salary)
                .setDepId(dep_id)
                .createEmployee();

        try {
            employeeDao.editEmployee(employee, userName);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/date/{date}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        List<Employee> employees = null;

        try {
            employees = employeeDao.getEmployeesByDOF(date);
            if(employees.isEmpty()) throw new Exception();
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/between/{from}/{to}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeBetweenDates(@PathVariable("from")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                  @PathVariable("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        List<Employee> employees = null;

        try {
            employees = employeeDao.getEmployeesBetweenDOF(from, to);
            if(employees.isEmpty()) throw new Exception();
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTaskByEmployee(@PathVariable("id") long id){
        try {
            List<Task> tasks = employeeDao.getTasksByEmployee(id);
            return new ResponseEntity(tasks, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("error", HttpStatus.NOT_FOUND);
        }
    }
}