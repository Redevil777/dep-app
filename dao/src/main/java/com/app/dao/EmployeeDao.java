package com.app.dao;

import com.app.model.Employee;

import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
public interface EmployeeDao {

    public void addEmployee(Employee employee, String username);

    public void deleteEmployeeById(long id, String username);

    public void editEmployee(Employee employee, String username);

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(long id);

    public List<Employee> getEmployeesByDOF(String date);

    public List<Employee> getEmployeesBetweenDOF(String from, String to);
}
