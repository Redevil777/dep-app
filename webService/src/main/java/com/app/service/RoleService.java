package com.app.service;

import com.app.dao.RoleDao;
import com.app.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by andrey on 02.09.16.
 */
@Service
@RequestMapping(value = "/role")
public class RoleService {

    @Autowired
    private RoleDao roleDAO;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleDAO.getRoles();
        System.out.println(roles.get(0));
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
