package com.app.dao;

import com.app.model.Permission;
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
@ContextConfiguration(locations = {"classpath:/test-spring-config.xml"})
@Transactional
public class PermissionDaoImplTest extends Assert {

    @Autowired
    private PermissionDao permissionDao;

    @Test
    public void getAllPermissions(){
        List<Permission> permissions = permissionDao.getPermissions();
        assertEquals(3, permissions.size());
    }

    @Test
    public void addPermissionTest(){
        Permission permission = new Permission("test");

        List<Permission> permissions = permissionDao.getPermissions();
        permissionDao.addPermission(permission);
        List<Permission> permissionsAfterAdd = permissionDao.getPermissions();

        assertEquals(permissions.size(), permissionsAfterAdd.size()-1);
    }

    @Test
    public void getPermissionById(){
        Permission permission = permissionDao.getPermissionById(1);

        assertNotNull(permission);
        assertEquals("add_department", permission.getPermissionName());
    }

    @Test
    public void getPermissionByName() throws Exception {
        String name = "add_department";

        Permission permission = permissionDao.getPermissionByName("add_department");

        assertNotNull(permission);
        assertEquals("add_department", permission.getPermissionName());
    }

    @Test
    public void editPermissionTest(){
        Permission permission = new Permission("test");
        permission.setId(1);
        permissionDao.editPermission(permission);
        Permission permissionEdited = permissionDao.getPermissionById(1);

        assertEquals(permission.getPermissionName(), permissionEdited.getPermissionName());
    }

    @Test
    public void deletePermissionById(){
        List<Permission> permissions = permissionDao.getPermissions();
        permissionDao.deletePermission(1);
        List<Permission> permissionsAfterDel = permissionDao.getPermissions();

        assertEquals(permissions.size(), permissionsAfterDel.size()+1);
    }
}
