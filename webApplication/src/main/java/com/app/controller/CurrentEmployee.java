package com.app.controller;

import com.app.model.Employee;
import com.app.model.User;
import org.springframework.web.client.RestTemplate;

/**
 * Created by andrei on 02.10.16.
 */
public class CurrentEmployee {
    private static final String USER_REST = "http://localhost:9000/user/";
    private static final String EMPLOYEE_REST = "http://localhost:9000/employee/";


    public static Employee getEmployee(){
        String username = CurrentUserName.getCurrentUserName();

        RestTemplate restTemplate = new RestTemplate();
        Employee employee = null;
        try {
            User user = restTemplate.getForObject(USER_REST + "/username/" + username, User.class);
            employee = restTemplate.getForObject(EMPLOYEE_REST + "/id/" + user.getEmpId(), Employee.class);
        } catch (Exception e){

        }
        return employee;
    }

}
