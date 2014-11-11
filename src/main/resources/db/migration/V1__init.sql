CREATE USER "tom" PASSWORD 'tom' ADMIN;
CREATE USER "jack" PASSWORD 'jack' ADMIN;

CREATE TABLE tenant (
  id BIGINT IDENTITY PRIMARY KEY,
	dbu VARCHAR(25) NOT NULL,
	edbpwd VARCHAR(255),
	business_name VARCHAR(255) NOT NULL
);

INSERT INTO tenant (dbu, edbpwd, business_name) VALUES ('tom', 'tom', 'b1');
INSERT INTO tenant (dbu, edbpwd, business_name) VALUES ('jack', 'jack', 'b2');


CREATE TABLE inventory (
  id BIGINT IDENTITY PRIMARY KEY,
  name VARCHAR(55),
  tenant_dbu VARCHAR(16) NOT NULL ,
  tenant_id BIGINT NOT NULL
);


INSERT INTO inventory (name, tenant_dbu, tenant_id) VALUES('tom''s item', 'tom', 1);
INSERT INTO inventory (name, tenant_dbu, tenant_id) VALUES('jack''s item', 'jack', 2);

CREATE VIEW inventory_vw AS
SELECT id, name
FROM inventory
WHERE tenant_dbu = CURRENT_USER;

CREATE TRIGGER tr_inventory_before_insert
BEFORE INSERT ON inventory
REFERENCING NEW AS newrow FOR EACH ROW
BEGIN ATOMIC
IF (CURRENT_USER = 'root') THEN
  SET newrow.tenant_dbu = CURRENT_USER;
END IF;
END