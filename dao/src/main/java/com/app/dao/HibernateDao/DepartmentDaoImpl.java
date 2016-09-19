package com.app.dao.HibernateDao;

import com.app.dao.DepartmentDao;
import com.app.model.Department;
import com.app.model.Employee;
import com.app.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
@Transactional
@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Value("from User where username = :username")
    private String getUserId;

    @Value("from Department")
    private String getAllDepartments;

    @Value("from Employee where dep_id = :id")
    private String getEmployeesByDep;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addDepartment(Department department, String username) {
        long userId = getUserById(username);

        department.setCreateAt(LocalDate.now().toString());
        department.setUpdateAt(LocalDate.now().toString());
        department.setEnabled(true);
        department.setCreateBy(userId);
        department.setUpdateBy(userId);

        getSession().save(department);
    }

    @Override
    public String deleteDepartmentById(long id, String username) {
        long userId = getUserById(username);
        Department department = getDepartmentById(id);
        List<Employee> employees = getEmployeesBySelectedDepartment(id);
        if(employees.size()==0){
            department.setUpdateBy(userId);
            department.setUpdateAt(LocalDate.now().toString());
            department.setEnabled(false);
            getSession().update(department);
            return "Ok";
        } else {
            return "neOk";
        }
    }

    @Override
    public void editDepartment(Department department, String username) {
        long userId = getUserById(username);
        Department departmentEdit = getDepartmentById(department.getId());
        departmentEdit.setUpdateAt(LocalDate.now().toString());
        departmentEdit.setDepName(department.getDepName());
        departmentEdit.setUpdateBy(userId);
        getSession().update(departmentEdit);
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = getSession().createQuery(getAllDepartments).list();
        for(int i = 0; i < departments.size(); i++){
            if(departments.get(i).isEnabled()==false){
                departments.remove(i);
                i--;
            }
        }
        return departments;
    }

    @Override
    public Department getDepartmentById(long id) {
        return getSession().load(Department.class, id);
    }

    @Override
    public List<Employee> getEmployeesBySelectedDepartment(long id) {
        Query query = getSession().createQuery(getEmployeesByDep);
        query.setParameter("id", id);
        List<Employee> employees = query.list();
        return employees;
    }

    public long getUserById(String username){
        Query query = getSession().createQuery(getUserId);
        query.setParameter("username", username);

        List<User> users = query.list();
        long userId = users.get(0).getId();
        return userId;
    }
}
