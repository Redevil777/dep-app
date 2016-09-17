package com.app.dao;

import com.app.model.Permission;

import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
public interface PermissionDao {

    public void addPermission(Permission permission);

    public Permission getPermissionById(int id);

    public Permission getPermissionByName(String permissionName) throws Exception;

    public void editPermission(Permission permission);

    public void deletePermission(int id);

    public List<Permission> getPermissions();
}
