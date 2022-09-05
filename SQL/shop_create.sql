CREATE DATABASE Hardware_shop;
USE Hardware_shop;

CREATE TABLE purchases (
    serial_no int NOT NULL AUTO_INCREMENT,
    cname VARCHAR(100) NOT NULL,
    phone_number VARCHAR(100) NOT NULL,
    item_id int NOT NULL,
    quantity int NOT NULL,
    CONSTRAINT pk_purchases PRIMARY KEY (serial_no)
);

CREATE TABLE items (
    iid int NOT NULL AUTO_INCREMENT,
    iname VARCHAR(100) NOT NULL,
    iprice int NOT NULL,
    iquantity int NOT NULL,   
    CONSTRAINT pk_item PRIMARY KEY (iid) 
);

CREATE TABLE shopkeeper(
    sk_id VARCHAR(100) NOT NULL,
    sk_name VARCHAR(100) NOT NULL,
    sk_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_shopkeeper PRIMARY KEY (sk_id)
);

CREATE TABLE shopowner (
    so_id VARCHAR(100) NOT NULL,
    so_name VARCHAR(100) NOT NULL,
    so_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_shopowner PRIMARY KEY (so_id)
);