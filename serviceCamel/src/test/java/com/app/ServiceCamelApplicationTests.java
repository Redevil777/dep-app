package com.app;

import com.app.model.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
    public void getAllDepTest(){
        Department[] departments = restTemplate.getForObject(DEPARTMENT_REST+ "/all", Department[].class);
        assertEquals(11, departments.length);
        Department department = departments[0];
        assertEquals("programmer", department.getDepName());
    }


}
