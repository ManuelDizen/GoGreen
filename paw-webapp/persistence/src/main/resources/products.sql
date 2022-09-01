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

insert into sellers (mail) values ('seller1@gmail.com'), ('seller2@gmail.com'), ('seller3@gmail.com');
insert into category (name) values ('Higiene'), ('Salud'), ('Cuidado de la piel');
insert into sellerDetails (sellerId, phone, address) values((select id from sellers where mail='seller1@gmail.com'),
                                                            '1133445566', 'Riobamba 123'),
                                                           ((select id from sellers where mail='seller2@gmail.com'),
                                                            '1184848484', 'Av Callao 123'),
                                                           ((select id from sellers where mail='seller3@gmail.com'),
                                                            '1133554422', 'Ayacucho 123');
insert into products (sellerId, categoryId, name, description, stock, price)
values((select id from sellers where mail='seller1@gmail.com'),
       (select id from category where name='Higiene'),
       'Jabon solido',
       'Un jabon solido que cuida mucho el medio ambiente',
       1000,
       100),
      ((select id from sellers where mail='seller2@gmail.com'),
       (select id from category where name='Salud'),
       'Medicamento para la caída de pelo',
       'Un medicamento que ayuda contra la caida del pelo',
       1000,
       200),
      ((select id from sellers where mail='seller3@gmail.com'),
       (select id from category where name='Cuidado de la piel'),
       'Crema exfoliante',
       'Una crema para tu cuidado personal',
       1000,
       100);