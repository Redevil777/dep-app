package com.app;

import com.app.model.User;
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


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class UserServiceTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        this.mvc.perform(
                get("/user/all")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        this.mvc.perform(
                get("/user/id/1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserWithWrongIdTest() throws Exception {
        this.mvc.perform(
                get("/user/id/-1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addUserTest() throws Exception {
        this.mvc.perform(
                post("/user/add")
                .param("username","test")
                .param("password", "test")
                .param("role", "ROLE_ADMIN")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addUserWithIncorrectFormTest() throws Exception {
        User user = new User(1, null, null, true, 1);
        this.mvc.perform(
                post("/user/add")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("role", "ROLE_ADMIN")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserTest() throws Exception {
        this.mvc.perform(
                delete("/user/delete/1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserWithWrongIdTest() throws Exception {
        this.mvc.perform(
                delete("/user/delete/-1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editUserTest() throws Exception {
        this.mvc.perform(
                post("/user/edit")
                .param("id", "1")
                .param("username", "test")
                .param("password", "test")
                .param("role", "ROLE_ADMIN")
                        .param("empId", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
