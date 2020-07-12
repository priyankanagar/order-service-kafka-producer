CREATE TABLE `Order` (
  `order_id` int(11) unsigned NOT NULL,
  `order_amount` DECIMAL NOT NULL,
  PRIMARY KEY (`order_id`)
) ;


CREATE TABLE `ORDER_DETAILS` (
  `id` int(11) unsigned NOT NULL,
  `order_id` int(11) unsigned NOT NULL,
   `item_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `Order` (`order_id`)
);

