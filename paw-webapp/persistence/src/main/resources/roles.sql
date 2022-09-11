CREATE TABLE if not exists roles(
    id serial primary key,
    name VARCHAR(20) UNIQUE NOT NULL
    );

create table if not exists authorities(
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

create table if not exists role_to_authorities(
    id serial primary key,
    role_id integer not null,
    authority_id integer not null,
    foreign key (role_id) references roles(id) on delete cascade,
    foreign key (authority_id) references authorities(id) on delete cascade
);

INSERT INTO roles(id, name) SELECT 1, 'ADMIN' WHERE NOT EXISTS (select id from roles where name = 'ADMIN') returning id;
INSERT INTO roles(id, name) SELECT 2, 'USER' WHERE NOT EXISTS (select id from roles where name = 'USER') returning id;
INSERT INTO roles(id, name) SELECT 3, 'SELLER' WHERE NOT EXISTS (select id from roles where name = 'SELLER') returning id;

INSERT INTO authorities(id, name) SELECT 1, 'canPublishProduct' WHERE NOT EXISTS (
SELECT id from authorities where name='canPublishProduct') returning id;
INSERT INTO authorities(id, name) SELECT 2, 'canBuyProduct' WHERE NOT EXISTS (
SELECT id from authorities where name='canBuyProduct') returning id;
INSERT INTO authorities(id, name) SELECT 3, 'canConfirmOrder' WHERE NOT EXISTS (
SELECT id from authorities where name='canConfirmOrder') returning id;
INSERT INTO authorities(id, name) SELECT 4, 'canEditUserProfile' WHERE NOT EXISTS (
SELECT id from authorities where name='canEditUserProfile') returning id;
INSERT INTO authorities(id, name) SELECT 5, 'canEditSellerProfile' WHERE NOT EXISTS (
SELECT id from authorities where name='canEditSellerProfile') returning id;

insert into role_to_authorities(id, role_id, authority_id) select 1,2,2 where not exists(select id from
role_to_authorities where role_id = 2 and authority_id = 2) returning id;
insert into role_to_authorities(id, role_id, authority_id) select 2,2,4 where not exists(select id from
role_to_authorities where role_id = 2 and authority_id = 4) returning id;
insert into role_to_authorities(id, role_id, authority_id) select 3,3,1 where not exists(select id from
role_to_authorities where role_id = 3 and authority_id = 1) returning id;
insert into role_to_authorities(id, role_id, authority_id) select 4,3,3 where not exists(select id from
role_to_authorities where role_id = 3 and authority_id = 3) returning id;
insert into role_to_authorities(id, role_id, authority_id) select 5,3,5 where not exists(select id from
role_to_authorities where role_id = 3 and authority_id = 5) returning id;
