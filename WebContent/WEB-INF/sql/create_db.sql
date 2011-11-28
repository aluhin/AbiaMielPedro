BEGIN TRANSACTION;

CREATE TABLE customer(
    id serial PRIMARY KEY NOT NULL,
    nick varchar NOT NULL UNIQUE,
    password varchar NOT NULL,
    email varchar NOT NULL UNIQUE,
    first_name varchar,
    last_name varchar,
    address varchar,
    telephone varchar,
    active boolean,
    admin boolean
);

CREATE TABLE myorder(
    id serial PRIMARY KEY NOT NULL,
    title varchar NOT NULL, 
    ordered_at timestamp without time zone NOT NULL,
    comments varchar NOT NULL,
    customer_id serial NOT NULL,
    CONSTRAINT order_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE product(
    id serial PRIMARY KEY NOT NULL,
    name varchar NOT NULL UNIQUE,
    description varchar,
    price float4
);

CREATE TABLE order_product(
	id serial PRIMARY KEY NOT NULL,
    order_id serial NOT NULL,
    product_id serial NOT NULL,
    quantity integer,
    CONSTRAINT order_product_order_id_fkey FOREIGN KEY (order_id) REFERENCES myorder(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT order_product_product_id_fkey FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);

END TRANSACTION;