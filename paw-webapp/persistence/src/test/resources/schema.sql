create table if not exists images(
    id serial primary key,
    source varbinary(100)
);

create table if not exists users(
    id serial primary key,
    firstName varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    locale varchar(5) default 'es',
    notifications boolean default false,
    image_id integer,
    foreign key(image_id) references images(id) on delete set null
    );

create table if not exists sellers(
    id serial primary key,
    userId integer not null,
    phone varchar(20) unique not null,
    address varchar(255) not null,
    foreign key(userId) references users(id) on delete cascade
    );

create table if not exists news(
   id serial primary key,
   imageid integer,
   message varchar(1024) not null,
   sellerid integer not null,
   datetime timestamp not null,
   foreign key(imageid) references images(id) on delete set null,
   foreign key(sellerid) references sellers(id) on delete cascade
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
    productstatus_id integer not null,
    foreign key(imageId) references images(id) on delete set null
);

create table if not exists comments(
    id serial primary key,
    datetime timestamp not null,
    message varchar(500) not null,
    productid integer,
    userid integer,
    reply varchar(500) default null,
    foreign key (productid) references products(id) on delete cascade,
    foreign key (userid) references users(id) on delete set null
);

create table if not exists favorites(
  seller_id integer,
  user_id integer,
  foreign key(seller_id) references sellers(id) on delete cascade,
  foreign key(user_id) references users(id) on delete cascade,
  primary key(seller_id, user_id)
);


create table if not exists tags_to_products(
       id serial primary key,
       tag integer,
       productId integer,
       foreign key (productId) references products(id) on delete cascade
    );

create table if not exists orders(
    id serial primary key,
    productName varchar(255) not null,
    sellerName varchar(255) not null,
    sellerSurname varchar(255) not null,
    sellerEmail varchar(255) not null,
    buyerName varchar(255) not null,
    buyerSurname varchar(255) not null,
    buyerEmail varchar(255) not null,
    amount integer not null,
    price integer not null,
    datetime timestamp not null,
    message varchar(1024)
    );

create table if not exists roles(
    id serial primary key,
    name varchar(20) not null,
    unique(name)
);

create table if not exists user_roles(
    id serial primary key,
    user_id integer,
    role_id integer,
    foreign key(user_id) references users(id) on delete cascade,
    foreign key(role_id) references roles(id) on delete cascade
);

create table if not exists token(
  id serial primary key,
  token varchar(255) not null,
  user_id integer not null,
  exp_date timestamp not null,
  used boolean not null
);

/*alter table sellers add column areaId integer default null;*/