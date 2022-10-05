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