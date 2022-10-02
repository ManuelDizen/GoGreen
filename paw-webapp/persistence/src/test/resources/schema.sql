create table if not exists users(
                                    id serial primary key,
                                    firstName varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    locale varchar(5) default 'es'
    );