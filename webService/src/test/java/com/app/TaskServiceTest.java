package com.app;

import com.app.model.Task;
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
 * Created by andrey on 20.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-config.xml")
@WebAppConfiguration
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/init_table.sql") })
@Transactional
public class TaskServiceTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getAllTasksTest() throws Exception {
        this.mvc.perform(
                get("/task/all")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaskByIdTest() throws Exception {
        this.mvc.perform(
                post("/task/delete/1")
                        .param("username", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaskWithWrongIdTest() throws Exception {
        this.mvc.perform(
                post("/task/delete/-1")
                        .param("username", "user")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        this.mvc.perform(
                get("/task/id/1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTaskWithWrongIdTest() throws Exception {
        this.mvc.perform(
                get("/task/id/-1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addTaskTest() throws Exception {
        this.mvc.perform(
                post("/task/add")
                        .param("id", "1")
                        .param("title", "test")
                        .param("type", "CALL")
                        .param("description", "test")
                        .param("dateWhen", "1111-11-11")
                        .param("priority", "LOW")
                        .param("username", "user")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addTaskWithNullTittleTest() throws Exception {
        Task task = new Task();
        task.setTitle(null);

        this.mvc.perform(
                post("/task/add")
                        .param("id", "1")
                        .param("title", task.getTitle())
                        .param("type", "CALL")
                        .param("description", "test")
                        .param("dateWhen", "1111-11-11")
                        .param("priority", "LOW")
                        .param("username", "user")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editTaskTest() throws Exception {
        this.mvc.perform(
                post("/task/edit")
                        .param("id", "1")
                        .param("title", "test")
                        .param("type", "CALL")
                        .param("description", "test")
                        .param("dateWhen", "1111-11-11")
                        .param("empId","2")
                        .param("priority", "LOW")
                        .param("complete", "NOT")
                        .param("username", "user")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
