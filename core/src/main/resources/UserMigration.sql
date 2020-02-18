CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS role_of_app_user;

CREATE TABLE role_of_app_user
(
    role_id uuid DEFAULT uuid_generate_v4() NOT NULL,
    role    varchar(15)                     NOT NULL,
    constraint pk_user_role PRIMARY KEY (role_id)
);

INSERT INTO role_of_app_user (role)
VALUES ('ADMIN'),
       ('USER');

-- select * from user_role;
-- drop table user_role;

DROP TABLE IF EXISTS app_users;

CREATE TABLE app_users
(
    user_id   uuid DEFAULT uuid_generate_v4() NOT NULL,
    name      varchar(255)                    NOT NULL,
    password  varchar(255)                    NOT NULL,
    email     varchar(255)                    NOT NULL,
    constraint pk_app_users PRIMARY KEY (user_id)
);


INSERT INTO app_users (name, password, email)
VALUES ('Alice', '1q2w3e4r', 'admin@gmail.com'),
       ('Bob', '123456', 'random@gmail.com');

-- select * from portal_user;
-- drop table portal_user;

DROP TABLE IF EXISTS app_user_role_of_app_user;

CREATE TABLE app_user_role_of_app_user
(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT fk_app_user_role_of_app_user_user_id FOREIGN KEY (user_id) REFERENCES app_users (user_id),
    CONSTRAINT fk_app_user_role_of_app_user_role_id FOREIGN KEY (role_id) REFERENCES role_of_app_user (role_id)
);

INSERT INTO app_user_role_of_app_user (user_id, role_id)
with id1 as (select user_id from app_users where name = 'Alice') ,
     id2 as (select role_id from role_of_app_user where role = 'USER')
    select  id1.user_id, id2.role_id from id1, id2;

INSERT INTO app_user_role_of_app_user (user_id, role_id)
with id1 as (select user_id from app_users where name = 'Bob') ,
     id2 as (select role_id from role_of_app_user where role = 'ADMIN')
select  id1.user_id, id2.role_id from id1, id2;

-- select * from portal_user_role;
-- drop table portal_user_role;
