package com.app.dao;

import com.app.model.Employee;
import com.app.model.EmployeeBuilder;
import com.app.model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by andrei on 17.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-spring-config.xml"})
@Transactional
public class EmployeeDaoImplTest extends Assert {

    @Autowired
    private EmployeeDao employeeDao;


    @Test
    public void getAllEmployees(){
        List<Employee> employees = employeeDao.getAllEmployees();
        assertNotNull(employees);
        assertEquals(10, employees.size());
    }

    @Test
    public void addEmployeeTest(){
        List<Employee> employees = employeeDao.getAllEmployees();
        Employee employee = createNewEmployee();

        employeeDao.addEmployee(employee);

        List<Employee> employeesAfterAdd = employeeDao.getAllEmployees();

        assertEquals(employees.size(), employeesAfterAdd.size()-1);
    }

    @Test
    public void deleteEmployeeByIdTest(){
        List<Employee> employees = employeeDao.getAllEmployees();
        employeeDao.deleteEmployeeById(new Employee());
        List<Employee> employeesAfterDel = employeeDao.getAllEmployees();

        assertEquals(employees.size(), employeesAfterDel.size()+1);
    }

    @Test
    public void getEmployeeById(){
        Employee employee = employeeDao.getEmployeeById(2);
        assertNotNull(employee);
        assertEquals("Rooney", employee.getLastName());
    }

    @Test
    public void editEmployeeTest(){
        Employee employee = createNewEmployee();
        employee.setId(1);
        employeeDao.editEmployee(employee);
        Employee employeeEdited = employeeDao.getEmployeeById(1);

        assertEquals("test", employeeEdited.getFirstName());
        assertEquals("test", employeeEdited.getLastName());
        assertEquals("test", employeeEdited.getMiddleName());
        assertEquals(LocalDate.of(1111, 11, 11), employeeEdited.getBirthday());
        assertEquals("test", employeeEdited.getEmail());
        assertEquals("12345", employeeEdited.getPhone());
        assertEquals("test", employeeEdited.getAddress());
        assertEquals(123, employeeEdited.getSalary());
        assertEquals(1, employeeEdited.getDepId());
    }

    @Test
    public void getEmployeeByDOF(){
        List<Employee> employee = employeeDao.getEmployeesByDOF(LocalDate.of(1974, 11, 16));

        assertNotNull(employee);
        assertEquals(LocalDate.of(1974, 11, 16), employee.get(0).getBirthday());
        assertEquals("Poul", employee.get(0).getFirstName());
    }

    @Test
    public void getEmployeesBetweenDOB(){
        List<Employee> employees = employeeDao.getEmployeesBetweenDOF(LocalDate.of(1984, 02, 04), LocalDate.of(1987, 12, 20));

        assertNotNull(employees);
        assertEquals(6, employees.size());
    }

    @Test
    public void getTasksByEmployee(){
        List<Task> tasks = employeeDao.getTasksByEmployee(1);
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("task 1", tasks.get(0).getTitle());
    }

    public Employee createNewEmployee(){
        Employee employee = new EmployeeBuilder()
                .setFirstName("test")
                .setLastName("test")
                .setMiddleName("test")
                //.setBirthday(LocalDate.of(1111, 11, 11))
                .setEmail("test")
                .setPhone("12345")
                .setAddress("test")
                .setSalary(123)
                .setDepId(1)
                .createEmployee();

        return employee;
    }
}
