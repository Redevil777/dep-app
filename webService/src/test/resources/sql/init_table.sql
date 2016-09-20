insert into users (id, username, password, enabled) VALUES (1, 'user','pass', 1);


INSERT into roles (id, rolename) VALUES (1, 'ROLE_ADMIN');

INSERT into user_roles(user_id, role_id) VALUES (1, 1);

INSERT INTO department (id, dep_name, create_at, update_at, enabled, create_by, update_by) VALUES (1, 'java developer', '1970-01-01','1970-01-01',1,1, 1), (2, 'c++ developer', '1970-01-01','1970-01-01',1,1, 1),  (3, 'python developer', '1970-01-01','1970-01-01',1,1, 1),  (4, 'objective-c developer', '1970-01-01','1970-01-01',1,1, 1), (5, 'test', '1970-01-01', '1970-01-01',1,1,1);



INSERT INTO employee (id, first_name, last_name, middle_name, birthday, email, phone, address, salary, dep_id, create_at, update_at, enabled, create_by, update_by) VALUES  (1, 'Cristiano', 'Ronaldo', 'Aviero', '1985-02-05','cr@best.pro','777-77-77','madrid', 322, 1,'1980-01-01','1980-01-01', 1, 1,1), (2,'Wayne', 'Rooney', 'Mark', '1985-10-24','mu@best.world','101-01-01','manchester', 300, 1,'1980-01-01','1980-01-01', 1, 1,1), (3, 'Carlos', 'Tevez', 'Alberto', '1984-02-05','tev@car.com','145-123-11','buenos-aires',250, 1,'1980-01-01','1980-01-01', 1, 1,1), (4, 'Anthony', 'Martial', 'Joran', '1995-12-05','mart@sial.com','753-15-59','manchester', 100, 2,'1980-01-01','1980-01-01', 1, 1,1), (5, 'David', 'de Gea', 'Quintana', '1990-11-07','dav@mu.com','711-71-71','manchester', 200, 2,'1980-01-01','1980-01-01', 1, 1,1), (6, 'Lionel', 'Messi', 'Andres', '1987-06-24','barca@mail.com','100-10-10','barcelona', 300, 2,'1980-01-01','1980-01-01', 1, 1,1), (7, 'Lius', 'Suarez', 'Alberto', '1987-01-24','kus@kus.cl','147-89-63', 'barcelona', 250, 3,'1980-01-01','1980-01-01', 1, 1,1), (8, 'Gareth', 'Bale', 'Frank', '1989-07-16','boom@tut.by','333-22-11','madrid', 250, 3,'1980-01-01','1980-01-01', 1, 1,1), (9, 'Karim', 'Benzema', 'Mostafa', '1987-12-19','ccc@video.free','220-78-45','madrid', 200, 4,'1980-01-01','1980-01-01', 1, 1,1),(10, 'Poul', 'Scholes', 'Aron', '1974-11-16','he@score.goals','181-18-18','manchester', 180, 4,'1980-01-01','1980-01-01', 1, 1,1);

INSERT INTO permissions (id, permissionname) VALUES (1, 'add_department'), (2, 'add_employee'), (3, 'add_user');

insert into role_permissions (role_id, permission_id) values (1, 1), (1, 2);

INSERT INTO tasks (id, title, description, start_task, end_task, emp_id, create_at, update_at, enabled, create_by, update_by) VALUES (1, 'task 1', 'need complete module dao', '2016-09-20', '2016-09-21', 1, '1980-01-01','1980-01-01', 1, 1,1);
