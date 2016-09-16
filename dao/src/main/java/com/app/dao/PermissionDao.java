package com.app.dao;

import com.app.model.Permission;

import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
public interface PermissionDao {

    public void addPermission(Permission permission);

    public Permission getPermission(int id);

    public Permission getPermission(String permissionName) throws Exception;

    public void updatePermission(Permission permission);

    public void deletePermission(int id);

    public List<Permission> getPermissions();
}
