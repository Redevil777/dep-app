package com.app;

import com.app.model.Department;
import com.app.model.DepartmentBuilder;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andrei on 17.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-config.xml")
@WebAppConfiguration
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/init_table.sql") })
@Transactional
public class DepartmentServiceTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }



    @Test
    public void getAllDepartmentsTest() throws Exception {

        this.mvc.perform(
                get("/department/all")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addDepartmentTest() throws Exception {
        this.mvc.perform(
                post("/department/add")
                .param("depName","test")
                .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addDepartmentWithNullNameTest() throws Exception {

        Department department = new DepartmentBuilder().createDepartment();
        department.setDepName(null);
        this.mvc.perform(
                post("/department/add")
                        .param("depName", department.getDepName())
                        .param("userName", "user")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getDepartmentByIdTest() throws Exception {
        this.mvc.perform(
                get("/department/id/"+1)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getDepartmentByWrongIdTest() throws Exception {
        this.mvc.perform(
                get("/department/id/"+-1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDepartmentTest() throws Exception {
        this.mvc.perform(
                post("/department/delete/"+5)
                        .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDepartmentWithIncorrectIdTest() throws Exception {

        this.mvc.perform(
                post("/department/delete/" + -1)
                .param("userName", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateDepartment() throws Exception {

        this.mvc.perform(
                post("/department/edit/")
                        .param("id", String.valueOf(1))
                        .param("depName", "change")
                        .param("userName", "user")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmployeesBySelectedDepartment() throws Exception {

        this.mvc.perform(
                get("/department/employees/" + 1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
