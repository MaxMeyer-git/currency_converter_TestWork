CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS USER_ROLE;

CREATE TABLE USER_ROLE
(
    role_id INT         NOT NULL,
    role    varchar(50) NOT NULL,
    constraint PK_USER_ROLE PRIMARY KEY (role_id)
);

INSERT INTO USER_ROLE (role_id, role)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'GUEST');

-- select * from USER_ROLE;
-- drop table USER_ROLE;

DROP TABLE IF EXISTS APP_USERS;

CREATE TABLE APP_USERS
(
    user_id  uuid DEFAULT uuid_generate_v4() NOT NULL,
    name     varchar(255)                    NOT NULL,
    password varchar(255)                    NOT NULL,
    email    varchar(255)                    NOT NULL,
    constraint PK_APP_USERS PRIMARY KEY (user_id)
);


INSERT INTO APP_USERS (name, password, email)
VALUES ('Alice', '1q2w3e4r', 'alice@gmail.com'),
       ('Bob', '123456', 'bob@gmail.com'),
       ('Chuck', '654321', 'chuck@gmail.com');

-- select * from APP_USERS;
-- drop table APP_USERS;

DROP TABLE IF EXISTS APP_USER_USER_ROLE;

CREATE TABLE APP_USER_USER_ROLE
(
    user_id uuid NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT FK_APP_USER_USER_ROLE_USER_ID FOREIGN KEY (user_id) REFERENCES APP_USERS (user_id),
    CONSTRAINT FK_APP_USER_USER_ROLE_ROLE_ID FOREIGN KEY (role_id) REFERENCES USER_ROLE (role_id)
);

INSERT INTO APP_USER_USER_ROLE (user_id, role_id)
with id1 as (select user_id from APP_USERS where name = 'Alice'),
     id2 as (select role_id from USER_ROLE where role = 'USER')
select id1.user_id, id2.role_id
from id1,
     id2;

INSERT INTO APP_USER_USER_ROLE (user_id, role_id)
with id1 as (select user_id from APP_USERS where name = 'Bob'),
     id2 as (select role_id from USER_ROLE where role = 'ADMIN')
select id1.user_id, id2.role_id
from id1,
     id2;

INSERT INTO APP_USER_USER_ROLE (user_id, role_id)
with id1 as (select user_id from APP_USERS where name = 'Chuck')
select id1.user_id, 2
from id1;

-- select * from portal_user_role;
-- drop table portal_user_role;
