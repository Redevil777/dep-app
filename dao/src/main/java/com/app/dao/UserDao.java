package com.app.dao;

import com.app.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
public interface UserDao {
    public void addUser(User user);

    public void deleteUserById(long id);

    public void editUser(User user);

    public List<User> getAllUsers();

    public User getUserById(long id);

    public User getUserByName(String username);
}
