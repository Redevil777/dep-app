package com.app.dao;

import com.app.model.Role;

import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
public interface RoleDao {
    public void addRole(Role role);

    public Role getRole(int id);

    public Role getRole(String roleName);

    public void updateRole(Role role);

    public void deleteRole(int id);

    public List<Role> getRoles();
}
