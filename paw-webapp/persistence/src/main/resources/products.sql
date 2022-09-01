CREATE TABLE IF NOT EXISTS products
(
    productName VARCHAR
(
    255
) PRIMARY KEY,
    description VARCHAR
(
    255
) NOT NULL,
    stock INTEGER NOT NULL,
    price INTEGER NOT NULL
    );