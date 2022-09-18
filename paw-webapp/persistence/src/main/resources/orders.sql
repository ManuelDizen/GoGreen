create table if not exists orders{
    id serial primary key,
    productId integer not null,
    sellerId integer not null,
    buyerId integer not null,
    amount integer not null,
    price integer not null
    }
/* Tema FKs acá: Me preocupa que al borrar un producto, al ser on cascade,
   las FK que referencian al producto y/o al seller desaparezcan.

   TODO: Charlar sobre como resolver esto.
 */