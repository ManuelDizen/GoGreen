create table if not exists users(
                                    id serial primary key,
                                    firstName varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    locale varchar(5) default 'es'
    );

create table if not exists sellers(
                                      id serial primary key,
                                      userId integer not null,
                                      phone varchar(20) unique not null,
    address varchar(255) not null,
    foreign key(userId) references users(id) on delete cascade
    );

create table if not exists category(
                                       id serial not null primary key,
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
    imageId integer default null,
    );

create table if not exists tags_to_products(
                                               id serial primary key,
                                               tag integer,
                                               productId integer,
                                               foreign key (productId) references products(id) on delete cascade
    );



