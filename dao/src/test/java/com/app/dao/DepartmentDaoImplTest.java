package com.app.dao;

import com.app.model.Department;
import com.app.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-spring-config.xml"})
@Transactional
public class DepartmentDaoImplTest extends Assert {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void addDepartmentTest(){
        List<Department> departments = departmentDao.getAllDepartments();
        Department department = new Department("test");
        departmentDao.addDepartment(department, "user");
        List<Department> departmentsAfterAdd = departmentDao.getAllDepartments();
        assertEquals(departments.size(), departmentsAfterAdd.size()-1);
    }

    @Test
    public void getAllDepTest(){
        List<Department> departments = departmentDao.getAllDepartments();

        assertEquals(4, departments.size());
    }

    @Test
    public void deleteDepartmentByIdTest(){
        departmentDao.addDepartment(new Department("qwe"), "user");
        List<Department> departments = departmentDao.getAllDepartments();
        departmentDao.deleteDepartmentById(5l, "user");
        List<Department> departmentsAfterDel = departmentDao.getAllDepartments();

        assertEquals(departments.size(), departmentsAfterDel.size()+1);
    }

    @Test
    public void getDepartmentById(){
        Department department = departmentDao.getDepartmentById(1);
        assertNotNull(department);
        assertEquals("java developer", department.getDepName());
    }

    @Test
    public void editDepartmentTest(){
        Department department = new Department(2, "test");

        departmentDao.editDepartment(department, "user");

        Department departmentEdited = departmentDao.getDepartmentById(2);

        assertEquals("test", departmentEdited.getDepName());
    }

    @Test
    public void getEmployeesBySelectedDepTest(){
        List<Employee> employees = departmentDao.getEmployeesBySelectedDepartment(1);
        assertNotNull(employees);
        assertEquals(3, employees.size());
        assertEquals("Cristiano", employees.get(0).getFirstName());
    }
}
