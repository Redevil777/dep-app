package com.app.dao.HibernateDao;

import com.app.dao.TaskDao;
import com.app.model.Complete;
import com.app.model.Task;
import com.app.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Value("from Task where emp_id = :id")
    private String getTaskByEmp;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addTask(Task task) {

        task.setEnabled(true);
        task.setCreateAt(LocalDateTime.now().toString());
        task.setUpdateAt(LocalDateTime.now().toString());
        task.setComplete(Complete.NOT);
        getSession().save(task);
    }

    @Override
    public void deleteTaskById(Task task) {
        Task task1 = getTaskById(task.getId());
        task.setEnabled(false);
        task.setUpdateAt(LocalDateTime.now().toString());
        getSession().update(task1);
    }

    @Override
    public void editTask(Task task) {
        Task editTask = getTaskById(task.getId());
        editTask.setTitle(task.getTitle());
        editTask.setComplete(task.getComplete());
        editTask.setDescription(task.getDescription());
        editTask.setTaskType(task.getTaskType());
        editTask.setStartTime(task.getStartTime());
        editTask.setEndTime(task.getEndTime());
        editTask.setEmpId(task.getEmpId());
        editTask.setPriority(task.getPriority());
        editTask.setUpdateAt(LocalDateTime.now().toString());

        getSession().update(editTask);
    }

    @Override
    public List<Task> getAllTasks() {
        System.out.println("task dao");
        List<Task> tasks = getSession().createQuery(getAllTasks).list();
        System.out.println(tasks.size());
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

    @Override
    public List<Task> getTasksByEmp(long id) {
        Query query = getSession().createQuery(getTaskByEmp);
        query.setParameter("id", id);
        List<Task> tasks = query.list();
        return tasks;
    }

    public long getUserById(String username){
        Query query = getSession().createQuery(getUserId);
        query.setParameter("username", username);

        List<User> users = query.list();
        long userId = users.get(0).getId();
        return userId;
    }
}
