create table clients (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    cpf varchar(11) not null unique,
    birth_date date not null,
    email varchar(255) not null unique,
    phone varchar(20),
    created_at timestamp not null,
    updated_at timestamp not null
);

create table users (
    id bigint auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(255) not null,
    is_admin boolean not null
);
