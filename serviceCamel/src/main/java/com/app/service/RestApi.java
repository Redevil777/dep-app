package com.app.service;

import com.app.dao.DepartmentDao;
import com.app.dao.HibernateDao.DepartmentDaoImpl;
import com.app.dao.HibernateDao.EmployeeDaoImpl;
import com.app.dao.HibernateDao.UserDaoImpl;
import com.app.model.Department;
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
                    .route()
                    .bean(DepartmentDaoImpl.class, "getAllDepartments")
                    .endRest()
                .get("/id/{id}")
                    .route()
                    .bean(DepartmentDaoImpl.class, "getDepartmentById(${header.id})")
                    .endRest()
                .get("/employees/{id}")
                    .route()
                    .bean(DepartmentDaoImpl.class, "getEmployeesBySelectedDepartment(${header.id})")
                    .endRest();

        rest("/employee")
                .get("/all")
                    .route()
                    .bean(EmployeeDaoImpl.class, "getAllEmployees")
                    .endRest()
                .get("/id/{id}")
                    .route()
                    .bean(EmployeeDaoImpl.class, "getEmployeeById(${header.id})")
                    .endRest()
                .get("task/{id}")
                    .route()
                    .bean(EmployeeDaoImpl.class, "getTasksByEmployee(header.id)")
                    .endRest();

        rest("/user")
                .get("/all")
                    .route()
                    .bean(UserDaoImpl.class, "getAllUsers")
                    .endRest()
                .get("/username/{username}")
                    .route()
                    .bean(UserDaoImpl.class, "getUserByName(header.username)")
                    .endRest();
    }
}
