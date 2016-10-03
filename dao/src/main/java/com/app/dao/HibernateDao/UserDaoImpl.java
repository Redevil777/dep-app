package com.app.dao.HibernateDao;

import com.app.dao.RoleDao;
import com.app.dao.UserDao;
import com.app.model.Role;
import com.app.model.User;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrei on 16.09.16.
 */

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @Value("insert into user_roles VALUES (null, :user_id, :role_id)")
    private String addUserRoles;

    @Value("from User")
    private String getAllUsers;

    @Value("from User where username = :username")
    private String getUserByUsername;

    @Value("update user_roles set ROLE_ID =:roleId where USER_ID=:userId")
    private String updateUserRoles;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Autowired
    private RoleDao roleDao;


    public static void main(String[] a){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "admin";

        System.out.println(passwordEncoder.encode(pass));
    }

    @Override
    public void addUser(User user, ArrayList<String> roles) {
        try {
            User userCheck = getUserByName(user.getUsername());
            String message = "The user [" + userCheck.getUsername() + "] already exists";
            throw new Exception(message);
        } catch (Exception e) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            getSession().save(user);
            User addedUser = getUserByName(user.getUsername());
            for (int i = 0; i < roles.size(); i++){
                String qwe = roles.get(i);
                qwe = qwe.substring(2, qwe.length()-2);
                roles.set(i, qwe);
            }

            List<Role> roleId = roleDao.getRoles();

            for(int i = 0; i<roles.size(); i++){
                for(int q = 0; q<roleId.size();q++){
                    if(roleId.get(q).getRoleName().equals(roles.get(i))){
                        SQLQuery query = getSession().createSQLQuery(addUserRoles);
                        query.setParameter("user_id", addedUser.getId());
                        query.setParameter("role_id", String.valueOf(roleId.get(q).getId()));
                        query.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public void deleteUserById(long id) {
        User user = getUserById(id);

        user.setEnabled(false);
        getSession().update(user);
    }

    @Override
    public void editUser(User user, String role) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User userEdit = getUserById(user.getId());
        userEdit.setUsername(user.getUsername());
        userEdit.setPassword(passwordEncoder.encode(user.getPassword()));
        userEdit.setEmpId(user.getEmpId());
        getSession().update(userEdit);
        updateUserRoles(user.getId(), role);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = getSession().createQuery(getAllUsers).list();
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).isEnabled()==false){
                users.remove(i);
                i--;
            }
        }

        return users;
    }

    @Override
    public User getUserById(long id) {
        User user = getSession().load(User.class, id);
        return user;
    }

    @Override
    public User getUserByName(String username) {
        Query query = getSession().createQuery(getUserByUsername);
        query.setParameter("username", username);
        List<User> users = query.list();
        return users.get(0);
    }

    public void updateUserRoles(long userId, String roleName){
        Role role = roleDao.getRoleByName(roleName);
        long roleId = role.getId();
        Query query = getSession().createSQLQuery(updateUserRoles);
        query.setParameter("roleId", roleId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
