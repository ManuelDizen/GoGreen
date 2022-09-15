create table if not exists users(
    id serial primary key,
    firstName varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255) unique not null,
    username varchar(255) unique not null,
    password varchar(255) not null
    );

create table if not exists sellers(
    id serial primary key,
    userId integer not null,
    phone varchar(20) unique not null,
    address varchar(255) not null,
    foreign key(userId) references users(id) on delete cascade
    );

create table if not exists category(
    id serial not null unique primary key,
    name varchar(255) unique not null
    );

create table if not exists products(
    id serial primary key,
    sellerId integer not null,
    categoryId integer not null,
    name varchar(255) not null,
    description varchar(1024) not null,
    stock integer not null,
    price integer not null,
    foreign key (sellerId) references sellers(id),
    foreign key (categoryId) references category(id)
    );

create table if not exists images(
    id serial primary key,
    source bytea
    );

alter table products add column if not exists imageId integer default null;
alter table products add foreign key (imageId) references images(id) on delete set null;

alter table users add column if not exists imageId integer default null;
alter table users add foreign key (imageId) references images(id) on delete set null;