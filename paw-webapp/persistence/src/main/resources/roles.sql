CREATE TABLE if not exists roles(
    id serial primary key,
    name VARCHAR(20) UNIQUE NOT NULL
    );

create table if not exists user_roles(
    id serial primary key,
    user_id integer not null,
    role_id integer not null,
    foreign key (user_id) references users(id) on delete cascade,
    foreign key (role_id) references roles(id) on delete cascade
);

INSERT INTO roles(id, name) SELECT 1, 'ADMIN' WHERE NOT EXISTS (select id from roles where name = 'ADMIN') returning id;
INSERT INTO roles(id, name) SELECT 2, 'USER' WHERE NOT EXISTS (select id from roles where name = 'USER') returning id;
INSERT INTO roles(id, name) SELECT 3, 'SELLER' WHERE NOT EXISTS (select id from roles where name = 'SELLER') returning id;