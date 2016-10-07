package com.app;

import com.app.model.Department;
import org.apache.camel.Exchange;
import org.json.HTTP;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@IntegrationTest
public class ServiceCamelApplicationTests extends Assert {

    final RestTemplate restTemplate = new RestTemplate();

    private final String DEPARTMENT_REST =  "http://localhost:9000/department";


    @Test
    public void getAllDepartmentTest(){
        Department[] departments = restTemplate.getForObject(DEPARTMENT_REST+ "/all", Department[].class);
        assertEquals(11, departments.length);
        Department department = departments[0];
        assertEquals("programmer", department.getDepName());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(DEPARTMENT_REST + "/all", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void getDepartmentById() {
        Department department = restTemplate.getForObject(DEPARTMENT_REST + "/id/1", Department.class);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(DEPARTMENT_REST + "/id/1", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(department);
        assertEquals("programmer", department.getDepName());
    }

    @Test
    public void getDepartmentByWrongId() {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(DEPARTMENT_REST + "/id/100", String.class);
        } catch (Exception e){
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
