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

insert ignore into category(id, name) values('1', 'food'), ('2', 'drinks');

insert ignore into product(id, image, name, price, rating, stock, category_id, is_archived) values
					('1', 'https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=620&quality=45&dpr=2&s=none', 'product 1', 55, 4.5, 90, '1', false),
					('2', 'https://hips.hearstapps.com/hmg-prod/images/happy-dog-outdoors-royalty-free-image-1652927740.jpg?crop=0.447xw:1.00xh;0.187xw,0&resize=980:*', 'product 2', 66, 4, 80, '2', false);
