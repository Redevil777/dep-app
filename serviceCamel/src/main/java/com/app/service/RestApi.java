package com.app.service;

import com.app.dao.HibernateDao.EmployeeDaoImpl;
import com.app.dao.HibernateDao.UserDaoImpl;
import com.app.model.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * Created by andrey on 05.10.16.
 */
@Component
public class RestApi extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .contextPath("/").apiContextPath("/api-doc")
                .apiProperty("api.title", "Camel REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);


        rest("/department")
                .get("/all")
                    .outType(Department.class)
                    .to("bean:departmentDaoImpl?method=getAllDepartments")
                .get("/id/{id}")
                    .outType(Department.class)
                    .to("bean:departmentDaoImpl?method=getDepartmentById(${header.id})")
                .get("/employees/{id}")
                    .outType(Department.class)
                    .to("bean:departmentDaoImpl?method=getEmployeesBySelectedDepartment(${header.id})")
                .post("/add")
                    .type(Department.class)
                    .to("bean:departmentDaoImpl?method=addDepartment")
                .post("/delete")
                    .type(Department.class)
                    .to("bean:departmentDaoImpl?method=deleteDepartmentById")
                .post("/edit")
                    .type(Department.class)
                    .to("bean:departmentDaoImpl?method=editDepartment");

        rest("/employee")
                .get("/all")
                    .outType(Employee.class)
                    .to("bean:employeeDaoImpl?method=getAllEmployees")
                .get("/id/{id}")
                    .outType(Employee.class)
                    .to("bean:employeeDaoImpl?method=getEmployeeById(${header.id})")
                .get("task/{id}")
                    .outType(Employee.class)
                    .to("bean:employeeDaoImpl?method=getTasksByEmployee(header.id)")
                .post("/add")
                    .type(Employee.class)
                    .to("bean:employeeDaoImpl?method=addEmployee")
                .post("/delete")
                    .type(Employee.class)
                    .to("bean:employeeDaoImpl?method=deleteEmployeeById")
                .post("/edit")
                    .type(Employee.class)
                    .to("bean:employeeDaoImpl?method=editEmployee");

        rest("/user")
                .get("/all")
                    .outType(User.class)
                    .to("bean:userDaoImpl?method=getAllUsers")
                .get("/username/{username}")
                    .outType(User.class)
                    .to("bean:userDaoImpl?method=getUserByName(header.username)")
                .post("/add")
                    .type(User.class)
                    .to("bean:userDaoImpl?method=addUser")
                .delete("/delete/{id}")
                    .outType(User.class)
                    .to("bean:userDaoImpl?method=deleteUserById(${header.id})")
                .post("/edit")
                    .type(User.class)
                    .to("bean:userDaoImpl?method=editUser");
        rest("/role")
                .get("/all")
                    .outType(Role.class)
                    .to("bean:roleDaoImpl?method=getRoles");

        rest("/task")
                .get("/all")
                    .outType(Task.class)
                    .to("bean:taskDaoImpl?method=getAllTasks")
                .post("/delete")
                    .type(Task.class)
                    .to("bean:taskDaoImpl?method=deleteTaskById")
                .get("/id/{id}")
                    .outType(Task.class)
                    .to("bean:taskDaoImpl?method=getTaskById(${header.id})")
                .get("/tasks/{id}")
                    .outType(Task.class)
                    .to("bean:taskDaoImpl?method=getTasksByEmp(${header.id})")
                .post("/edit")
                    .type(Task.class)
                    .to("bean:taskDaoImpl?method=editTask")
                .post("/add")
                    .type(Task.class)
                    .to("bean:taskDaoImpl?method=addTask");
    }
}
