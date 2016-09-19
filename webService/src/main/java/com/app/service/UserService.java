package com.app.service;

import com.app.dao.UserDao;
import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 22.08.16.
 */

@Service
@RequestMapping(value = "/user")
public class UserService {


    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        List<User> users = userDao.getAllUsers();

        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getDepartmentById(@PathVariable("id") int id) {
        try {
            User user= userDao.getUserById(id);
            if(user.getUsername()==null){
                throw new Exception();
            }
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Department not found with id=" + id + ", error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestParam("username") String name,
                                  @RequestParam("password") String password,
                                  @RequestParam("role") ArrayList<String> role){

        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setEnabled(true);
        try {
            userDao.addUser(user, role);

            return new ResponseEntity("", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),  HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") long id){

        try {
            userDao.deleteUserById(id);
            return new ResponseEntity("", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editUser(@RequestParam("id") Long id,
                                   @RequestParam("username") String name,
                                   @RequestParam("password") String password,
                                   @RequestParam("role") String role){

        User user = new User();
        user.setId(id);
        user.setUsername(name);
        user.setPassword(password);

        try {
            userDao.editUser(user, role);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Check input data!", HttpStatus.BAD_REQUEST);
        }
    }
}
