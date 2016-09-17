DROP TABLE department if EXISTS ;
DROP TABLE employee if EXISTS ;
DROP TABLE user_roles if EXISTS;
DROP TABLE role_permissions if EXISTS ;
drop table permissions if EXISTS ;
drop table roles if EXISTS ;
drop table users if EXISTS ;

CREATE TABLE users (
    id int IDENTITY ,
    username VARCHAR (50),
    password VARCHAR (50),
    enabled boolean not NULL
);
CREATE TABLE department  (
    id int IDENTITY,
    dep_name VARCHAR(255) NOT NULL,
    create_at DATE NOT NULL,
    update_at DATE not null,
    enabled boolean not NULL,
    create_by int NOT NULL,
    update_by int not null
);

CREATE TABLE employee (
    id int IDENTITY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    email VARCHAR(50),
    phone VARCHAR (15),
    address VARCHAR(100),
    salary int NOT NULL,
    dep_id int NOT NULL,
    create_at DATE NOT NULL,
    update_at DATE not null,
    enabled boolean not NULL,
    create_by int NOT NULL,
    update_by int not null
);

CREATE TABLE user_roles(
    id int IDENTITY,
    user_id int,
    role_id INT
);

CREATE TABLE if NOT EXISTS roles  (
    id int IDENTITY,
    rolename VARCHAR(30)
);

CREATE TABLE permissions(
    id int IDENTITY,
    permissionname VARCHAR(50)
);



