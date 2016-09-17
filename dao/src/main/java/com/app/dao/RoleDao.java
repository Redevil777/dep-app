package com.app.dao;

import com.app.model.Role;

import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
public interface RoleDao {
    public void addRole(Role role);

    public Role getRoleById(int id);

    public Role getRoleByName(String roleName);

    public void editRole(Role role);

    public void deleteRole(int id);

    public List<Role> getRoles();
}
