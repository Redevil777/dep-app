package com.app.dao;

import com.app.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrei on 17.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-config.xml")
@Transactional
public class UserDaoImplTest extends Assert {

    @Autowired
    private UserDao userDao;

    @Test
    public void getAllUsersTest(){
        List<User> users = userDao.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void addUserTest(){
        User user = new User(2, "test", "test", true);
        List<User> users = userDao.getAllUsers();
        ArrayList<String> list = new ArrayList<>();
        String role = "[ ROLE_ADMIN ]";
        list.add(role);
        userDao.addUser(user, list);
        List<User> usersAfterAdd = userDao.getAllUsers();

        assertEquals(users.size(), usersAfterAdd.size()-1);
    }

    @Test
    public void deleteUserTest(){
        List<User> users = userDao.getAllUsers();
        userDao.deleteUserById(1);
        List<User> usersAfterDel = userDao.getAllUsers();

        assertEquals(users.size(), usersAfterDel.size()+1);
    }

    @Test
    public void editUserTest(){
        User user = new User(1, "test", "test", true);
        userDao.editUser(user, "ROLE_ADMIN");
        User userEdited = userDao.getUserById(1);

        assertEquals(user.getUsername(), userEdited.getUsername());
    }

    @Test
    public void getUserByIdTest(){
        User user = userDao.getUserById(1);

        assertNotNull(user);
        assertEquals("user", user.getUsername());
    }

    @Test
    public void getUserByNameTest(){
        String name = "user";
        User user = userDao.getUserByName(name);

        assertNotNull(user);
        assertEquals(name, user.getUsername());
    }
}
