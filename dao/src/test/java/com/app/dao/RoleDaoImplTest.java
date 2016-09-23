package com.app.dao;

import com.app.model.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by andrei on 17.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-spring-config.xml")
@Transactional
public class RoleDaoImplTest extends Assert {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void getAllRoles(){
        List<Role> roles = roleDao.getRoles();

        assertNotNull(roles);
        assertEquals(2, roles.size());
    }

    @Test
    public void addRoleTest(){
        Role role = new Role(2, "test");
        List<Role> roles = roleDao.getRoles();
        roleDao.addRole(role);
        List<Role> rolesAfterAdd = roleDao.getRoles();

        assertEquals(roles.size(), rolesAfterAdd.size()-1);
    }

    @Test
    public void getRoleByIdTest(){
        Role role = roleDao.getRoleById(1);

        assertNotNull(role);
        assertEquals("ROLE_ADMIN", role.getRoleName());
    }

    @Test
    public void getRoleByNameTest(){
        Role role = roleDao.getRoleByName("ROLE_ADMIN");

        assertNotNull(role);
        assertEquals("ROLE_ADMIN", role.getRoleName());
    }

    @Test
    public void editRoleTest(){
        Role role = new Role(1, "test");
        roleDao.editRole(role);
        Role roleEdited = roleDao.getRoleById(1);

        assertEquals(role.getRoleName(), roleEdited.getRoleName());
    }

    @Test
    public void deleteRoleTest(){
        List<Role> roles = roleDao.getRoles();
        roleDao.deleteRole(1);
        List<Role> rolesAfterDel = roleDao.getRoles();

        assertEquals(roles.size(), rolesAfterDel.size()+1);
    }
}
