package com.app;

import com.app.model.Employee;
import com.app.model.EmployeeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andrey on 19.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-config.xml")
@WebAppConfiguration
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/init_table.sql") })
@Transactional
public class EmployeeServiceTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        this.mvc.perform(
                get("/employee/all")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        this.mvc.perform(
                get("/employee/id/"+1)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeWithWrongIdTest() throws Exception {
        this.mvc.perform(
                get("/employee/id/"+-1)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addEmployeeTest() throws Exception {
        this.mvc.perform(
                post("/employee/add")
                .param("firstName", "test")
                .param("lastName", "test")
                .param("middleName", "test")
                .param("birthday", "1111-11-11")
                .param("email", "test@test.com")
                .param("phone", "12345")
                .param("address", "test")
                .param("salary", "322")
                .param("depId", "1")
                .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddWrongEmployee() throws Exception {
        Employee employee = new EmployeeBuilder()
                .setFirstName(null)
                .setLastName(null)
                .setMiddleName(null)
                .setBirthday(null)
                .setEmail(null)
                .setPhone(null)
                .setAddress(null)
                .setSalary(0)
                .setDepId(0)
                .createEmployee();

        this.mvc.perform(
                post("/employee/add")
                        .param("firstName", employee.getFirstName())
                        .param("lastName", employee.getLastName())
                        .param("middleName", employee.getMiddleName())
                        //.param("birthday", employee.getBirthday())
                        .param("email", employee.getEmail())
                        .param("phone", employee.getPhone())
                        .param("address", employee.getAddress())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("depId", String.valueOf(employee.getDepId()))
                        .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        this.mvc.perform(
                post("/employee/delete/"+1)
                        .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEditEmployee() throws Exception{

        Employee employee = createEmployee();



        this.mvc.perform(
                post("/employee/edit")
                        .param("id", "10")
                        .param("firstName", employee.getFirstName())
                        .param("lastName", employee.getLastName())
                        .param("middleName", employee.getMiddleName())
                        .param("birthday", employee.getBirthday().toString())
                        .param("email", employee.getEmail())
                        .param("phone", employee.getPhone())
                        .param("address", employee.getAddress())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("depId", String.valueOf(employee.getDepId()))
                        .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByDOBTest() throws Exception {
        this.mvc.perform(
                get("/employee/date/"+"1985-10-24")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeWithWrongDOBTest() throws Exception {
        this.mvc.perform(
                get("/employee/date/" + "1985-10-22")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void getEmployeesBetweenDOBTest() throws Exception {
        this.mvc.perform(
                get("/employee/between/" + "1984-02-04/1987-06-24")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesBetweenWrongDatesTest() throws Exception {
        this.mvc.perform(
                get("/employee/between/" + "1111-11-11/1111-12-12")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTaskByEmployeeTest() throws Exception {
        this.mvc.perform(
                get("/employee/task/1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    public Employee createEmployee(){
        Employee employee = new EmployeeBuilder()
                .setFirstName("test")
                .setLastName("test")
                .setMiddleName("test")
                .setBirthday(LocalDate.of(1111, 11, 11))
                .setEmail("test")
                .setPhone("111-11-11")
                .setAddress("test")
                .setSalary(0)
                .setDepId(0)
                .createEmployee();
        return employee;
    }
}
