create table if not exists userDetails(
                                          id serial primary key ,
                                          userId integer unique,
                                          phone varchar(20),
    address varchar(255),
    foreign key(userId) references users(id)
    );

create table if not exists sellers(
                                      id serial primary key,
                                      mail varchar(255) unique
    );

create table if not exists sellerDetails(
                                            id serial primary key,
                                            sellerId integer unique not null ,
                                            phone varchar(20) not null,
    address varchar(255) not null,
    foreign key (sellerId) references sellers(id)
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