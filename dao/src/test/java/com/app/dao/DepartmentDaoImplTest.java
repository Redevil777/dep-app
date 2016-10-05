package com.app.dao;

import com.app.model.Department;
import com.app.model.DepartmentBuilder;
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
        Department department = new DepartmentBuilder().setDepName("test").createDepartment();
        departmentDao.addDepartment(department);
        List<Department> departmentsAfterAdd = departmentDao.getAllDepartments();
        System.out.println(departmentsAfterAdd.get(0).getCreateAt());
        assertEquals(departments.size(), departmentsAfterAdd.size()-1);
    }

    @Test
    public void getAllDepTest(){
        List<Department> departments = departmentDao.getAllDepartments();

        assertEquals(4, departments.size());
    }

    @Test
    public void deleteDepartmentByIdTest(){
        departmentDao.addDepartment(new DepartmentBuilder().setDepName("qwe").createDepartment());
        List<Department> departments = departmentDao.getAllDepartments();
        departmentDao.deleteDepartmentById(new DepartmentBuilder().createDepartment());
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
        Department department = new DepartmentBuilder().setId(2).setDepName("test").createDepartment();

        departmentDao.editDepartment(department);

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
