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

create table if not exists products(
    id serial primary key,
    sellerId integer not null,
    categoryId integer not null,
    name varchar(255) not null,
    description varchar(1024) not null,
    stock integer not null,
    price integer not null,
    foreign key (sellerId) references sellers(id)
    );

create table if not exists tags_to_products(
    id serial primary key,
    tag integer,
    productId integer,
    foreign key (productId) references products(id) on delete cascade
    );

create table if not exists images(
    id serial primary key,
    source bytea
    );

alter table products add column if not exists imageId integer default null;
alter table products add foreign key (imageId) references images(id) on delete set null;
alter table products add unique (name);

ALTER TABLE products ADD FOREIGN KEY (sellerId) REFERENCES sellers(id) ON DELETE CASCADE;

/*alter table users add column if not exists imageId integer default null;
alter table users add foreign key (imageId) references images(id) on delete set null;*/

ALTER TABLE users DROP COLUMN IF EXISTS username;
ALTER TABLE users ADD COLUMN IF NOT EXISTS locale varchar(5) default 'es';

ALTER TABLE sellers ADD COLUMN IF NOT EXISTS areaId integer default null;

insert into images (id, source)
select 0, null where not exists (select 1 from images where id = 0);