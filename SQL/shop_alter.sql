ALTER TABLE purchases
    ADD CONSTRAINT fk_iid FOREIGN KEY (item_id) REFERENCES items(iid);

ALTER TABLE items AUTO_INCREMENT=100;