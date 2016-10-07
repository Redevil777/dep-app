package com.app;

import com.app.model.Department;
import com.app.model.Employee;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;




import java.time.LocalDate;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@IntegrationTest
public class ServiceCamelApplicationTests extends org.junit.Assert {

    final RestTemplate restTemplate = new RestTemplate();

    private final String EMPLOYEE_REST = "http://localhost:9000/employee/";
    private final String DEPARTMENT_REST =  "http://localhost:9000/department";


    @Test
    public void getAllDepartmentTest(){
        Department[] departments = restTemplate.getForObject(DEPARTMENT_REST+ "/all", Department[].class);
        assertEquals(4, departments.length);
        Department department = departments[0];
        assertEquals("java developer", department.getDepName());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(DEPARTMENT_REST + "/all", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void getDepartmentByIdTest() {
        Department department = restTemplate.getForObject(DEPARTMENT_REST + "/id/1", Department.class);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(DEPARTMENT_REST + "/id/1", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(department);
        assertEquals("programmer", department.getDepName());
    }

    @Test
    public void getDepartmentByWrongIdTest() {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(DEPARTMENT_REST + "/id/100", String.class);
        } catch (Exception e){
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addDepartmentTest() {

        Department[] departmentsBeforeAdd = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);

        String depName = "test";

        JSONObject request = new JSONObject();
        request.put("depName", depName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity(request.toString(), headers);

        restTemplate.exchange(DEPARTMENT_REST + "/add", HttpMethod.POST, entity, String.class);

        Department[] departmentsAfterAdd = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);

        Department department = restTemplate.getForObject(DEPARTMENT_REST + "/id/5", Department.class);

        assertEquals(departmentsBeforeAdd.length, departmentsAfterAdd.length-1);
        assertNotNull(department);
        assertEquals(depName, department.getDepName());
    }

    @Test
    public void addEmployeeTest() {
        String firstName = "test";
        String lastName = "test";
        String middleName = "test";
        LocalDate birthday = LocalDate.of(2016, 10, 07);
        String email = "test@test.test";
        String phone = "12345";
        String address = "test";
        int salary = 123;
        int dep_id = 1;


        JSONObject request = new JSONObject();
        request.put("firstName", firstName);
        request.put("lastName", lastName);
        request.put("middleName", middleName);
        request.put("birthday", birthday);
        request.put("email", email);
        request.put("phone", phone);
        request.put("address", address);
        request.put("salary", salary);
        request.put("depId", dep_id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity(request.toString(), headers);

        Employee[] employeesBeforeAdd = restTemplate.getForObject(EMPLOYEE_REST + "/all", Employee[].class);

        restTemplate.exchange(EMPLOYEE_REST + "/add", HttpMethod.POST, entity, String.class);

        Employee[] employeesAfterAdd = restTemplate.getForObject(EMPLOYEE_REST + "/all", Employee[].class);

        Employee employee = restTemplate.getForObject(EMPLOYEE_REST + "/id/11", Employee.class);

        assertNotNull(employee);
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(middleName, employee.getMiddleName());
        assertEquals(birthday.toString(), employee.getBirthday());
        assertEquals(email, employee.getEmail());
        assertEquals(phone, employee.getPhone());
        assertEquals(address, employee.getAddress());
        assertEquals(salary, employee.getSalary());
        assertEquals(dep_id, employee.getDepId());
        assertEquals(employeesBeforeAdd.length, employeesAfterAdd.length-1);
    }
}
