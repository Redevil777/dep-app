package com.app.dao.HibernateDao;

import com.app.dao.PermissionDao;
import com.app.model.Permission;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
@Transactional
@Repository
public class PermissionDaoImpl implements PermissionDao {

    @Value("from Permission where permissionname = :permissionname")
    private String getPermissionByPerName;

    @Value("from Permission")
    private String getAllPermissions;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addPermission(Permission permission) {
        try {
            Permission permissionCheck = getPermissionByName(permission.getPermissionName());

        } catch (Exception e) {
            getSession().save(permission);
        }
    }

    @Override
    public Permission getPermissionById(long id) {
        return getSession().load(Permission.class, id);
    }

    @Override
    public Permission getPermissionByName(String permissionName) throws Exception {
        Query query = getSession().createQuery(getPermissionByPerName);
        query.setParameter("permissionname", permissionName);
        if(query.list().size()==0){
            throw new Exception();
        } else {
            List<Permission> permissions = query.list();
            return permissions.get(0);
        }
    }

    @Override
    public void editPermission(Permission permission) {
        Permission permissionEdit = getPermissionById(permission.getId());
        permissionEdit.setPermissionName(permission.getPermissionName());
        getSession().update(permissionEdit);
    }

    @Override
    public void deletePermission(long id) {
        Permission permission = getPermissionById(id);
        getSession().delete(permission);
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> permissions = getSession().createQuery(getAllPermissions).list();
        return permissions;
    }
}
