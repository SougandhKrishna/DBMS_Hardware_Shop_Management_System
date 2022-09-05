ALTER TABLE purchases
    DROP FOREIGN KEY fk_iid;

DROP TABLE items;
DROP TABLE purchases;
DROP TABLE shopkeeper;
DROP TABLE shopowner;

DROP DATABASE Hardware_shop;