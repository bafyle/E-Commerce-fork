-- use db_example;

-- insert ignore into cart values('1');
-- insert ignore into user(dtype, id, enabled, email, password, name, cart_id) values('Customer', '123', true, 'test@mail.com', '1234', 'test testing', 1);
-- insert ignore into user(dtype, id, enabled, email, password, name, cart_id) values('Admin', '1233', true, 'admin@mail.com', '1234', null, null);

use db_example;

insert ignore into cart values('1');
insert ignore into user(name, enabled, locked, id, email, password, verfication_code) values('Customer', true, false, '123', 'customer@mail.com', '1234', null);
insert ignore into customer(cart_id, id) values('1', '123');

insert ignore into user(name, enabled, locked, id, email, password, verfication_code) values('Admin', true, false, '124', 'admin@mail.com', '1234', null);
insert ignore into admin(id) values('124');