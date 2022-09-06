drop table if exists users;

create table if not exists users
(
    id         bigserial primary key,
    username   varchar(80) not null,
    password   varchar(80) not null
);
