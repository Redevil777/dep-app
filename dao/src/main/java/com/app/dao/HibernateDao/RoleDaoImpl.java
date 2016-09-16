package com.app.dao.HibernateDao;

import com.app.dao.RoleDao;
import com.app.model.Role;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by andrei on 16.09.16.
 */
@Transactional
@Repository
public class RoleDaoImpl implements RoleDao {

    @Value("from Role where rolename = :rolename")
    private String getRoleByRoleName;

    @Value("from Role")
    private String getAllRoles;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addRole(Role role) {
        getSession().save(role);
    }

    @Override
    public Role getRole(int id) {
        Role role = getSession().load(Role.class, id);

        return role;
    }

    @Override
    public Role getRole(String roleName) {
        Query query = getSession().createQuery(getRoleByRoleName);
        query.setParameter("rolename",roleName);

        List<Role> roles = query.list();
        return roles.get(0);
    }

    @Override
    public void updateRole(Role role) {
        Role roleEdit = getRole(role.getId());
        roleEdit.setRoleName(role.getRoleName());
        getSession().update(roleEdit);
    }

    @Override
    public void deleteRole(int id) {
        Role role = getRole(id);
        getSession().delete(role);
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = getSession().createQuery(getAllRoles).list();
        return roles;
    }
}
