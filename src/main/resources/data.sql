use db_example;

insert ignore into cart values('1');
insert ignore into user(dtype, id, enabled, email, password, name, cart_id) values('Customer', '123', true, 'test@mail.com', '1234', 'test testing', 1);