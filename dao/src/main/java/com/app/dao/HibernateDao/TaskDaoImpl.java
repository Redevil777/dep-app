package com.app.dao.HibernateDao;

import com.app.dao.TaskDao;
import com.app.model.Employee;
import com.app.model.Task;
import com.app.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by andrey on 20.09.16.
 */
@Transactional
@Repository
public class TaskDaoImpl implements TaskDao {

    @Value("from User where username = :username")
    private String getUserId;

    @Value("from Task")
    private String getAllTasks;

    @Value("from Employee where id=:id")
    private String getEmployeesByTask;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addTask(Task task, String username) {
        long userId = getUserById(username);

        task.setEnabled(true);
        task.setCreateBy(userId);
        task.setUpdateBy(userId);
        task.setCreateAt(LocalDate.now().toString());
        task.setUpdateAt(LocalDate.now().toString());
        getSession().save(task);
    }

    @Override
    public void deleteTaskById(long id, String username) {
        long userId = getUserById(username);
        Task task = getTaskById(id);
        task.setEnabled(false);
        task.setUpdateBy(userId);
        task.setUpdateAt(LocalDate.now().toString());
        getSession().update(task);
    }

    @Override
    public void editTask(Task task, String username) {
        long userId = getUserById(username);
        Task editTask = getTaskById(task.getId());
        editTask.setTitle(task.getTitle());
        editTask.setDescription(task.getDescription());
        editTask.setStartTask(task.getStartTask());
        editTask.setEndTask(task.getEndTask());
        editTask.setEmpId(task.getEmpId());
        editTask.setUpdateBy(userId);
        editTask.setUpdateAt(LocalDate.now().toString());

        getSession().update(editTask);
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = getSession().createQuery(getAllTasks).list();
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).isEnabled()==false){
                tasks.remove(i);
                i--;
            }
        }
        return tasks;
    }

    @Override
    public Task getTaskById(long id) {
        return getSession().load(Task.class, id);
    }

    public long getUserById(String username){
        Query query = getSession().createQuery(getUserId);
        query.setParameter("username", username);

        List<User> users = query.list();
        long userId = users.get(0).getId();
        return userId;
    }
}
