CREATE DATABASE "Feladat"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Hungarian_Hungary.1250'
       LC_CTYPE = 'Hungarian_Hungary.1250'
       CONNECTION LIMIT = -1;

CREATE table "order"(
    order_id int not null unique,
    buyer_name text not null,
    buyer_email text not null,
    order_date date not null,
    order_total_value numeric not null,
    address text not null,
    postcode int not null,
    CONSTRAINT order_pk PRIMARY KEY (order_id)
);


CREATE TYPE status AS ENUM (
  'IN_STOCK', 
  'OUT_OF_STOCK');

CREATE TABLE order_item (

	order_item_id int not null unique,
	order_id int REFERENCES "order" (order_id) not null,
	sale_price numeric not null,
	shipping_price numeric not null,
	total_item_price numeric not null,
	sku text not null,
	status status not null,
	CONSTRAINT order_item_pk PRIMARY KEY (order_item_id)
);