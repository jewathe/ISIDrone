use isidrone;
select count(`order`.id)as size ,order_info.product_id  from `order` inner join order_info on `order`.id = order_info.order_id group by order_info.product_id; 

select * from `order`;


/*
insert into order_info(order_id, product_id, qty, price) values(11,11,1,20), (12,12,13,620),(13,5,15,620),(14,11,3,20);
insert into `order`( user_id, date, is_shipped) values(6, NOW(), 1);
select * from `order`;

insert into order_info(order_id, product_id, qty, price) values(12,1,14,60);
*/
