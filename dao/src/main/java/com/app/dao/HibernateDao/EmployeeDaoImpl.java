package com.app.dao.HibernateDao;

import com.app.dao.EmployeeDao;
import com.app.model.Employee;
import com.app.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by andrei on 15.09.16.
 */
@Transactional
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Value("from User where username = :username")
    private String getUserId;

    @Value("from Employee")
    private String getAllEmployees;

    @Value("from Employee where birthday = :birthday")
    private String getEmployeesByDOF;

    @Value("from Employee where birthday between :from and :to")
    private String getEmployeesBetweenDOF;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addEmployee(Employee employee, String username) {
        long userId = getUserId(username);
        employee.setCreateAt(LocalDate.now().toString());
        employee.setUpdateAt(LocalDate.now().toString());
        employee.setEnabled(true);
        employee.setCreateBy(userId);
        employee.setUpdateBy(userId);

        getSession().save(employee);
    }

    @Override
    public void deleteEmployeeById(long id, String username) {
        long userId = getUserId(username);
        Employee employee = getEmployeeById(id);
        employee.setUpdateAt(LocalDate.now().toString());
        employee.setUpdateBy(userId);
        employee.setEnabled(false);

        getSession().update(employee);
    }

    @Override
    public void editEmployee(Employee employee, String username) {
        long userId = getUserId(username);
        Employee employeeEdit = getEmployeeById(employee.getId());
        employeeEdit.setUpdateAt(LocalDate.now().toString());
        employeeEdit.setUpdateBy(userId);
        employeeEdit.setFirstName(employee.getFirstName());
        employeeEdit.setLastName(employee.getLastName());
        employeeEdit.setMiddleName(employee.getMiddleName());
        employeeEdit.setBirthday(employee.getBirthday());
        employeeEdit.setEmail(employee.getEmail());
        employeeEdit.setPhone(employee.getPhone());
        employeeEdit.setAddress(employee.getAddress());
        employeeEdit.setSalary(employee.getSalary());
        employeeEdit.setDepId(employee.getDepId());

        getSession().update(employeeEdit);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = getSession().createQuery(getAllEmployees).list();
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).isEnabled()==false){
                employees.remove(i);
                i--;
            }
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(long id) {
        return getSession().load(Employee.class, id);
    }

    @Override
    public List<Employee> getEmployeesByDOF(String date) {
        Query query = getSession().createQuery(getEmployeesByDOF);
        query.setParameter("birthday", date);
        List<Employee> employees = query.list();
        return employees;
    }

    @Override
    public List<Employee> getEmployeesBetweenDOF(String from, String to) {
        Query query = getSession().createQuery(getEmployeesBetweenDOF);
        query.setParameter("from", from);
        query.setParameter("to", to);

        List<Employee> employees = query.list();
        return employees;
    }


    public long getUserId(String username){
        Query query = getSession().createQuery(getUserId);
        query.setParameter("username", username);

        List<User> users = query.list();

        long userId = users.get(0).getId();
        return userId;
    }
}
