--liquibase formatted sql

--changeset idzhambulov:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    first_name VARCHAR(64) NOT NULL ,
    last_name VARCHAR(64) NOT NULL ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    password VARCHAR(128) NOT NULL ,
    role VARCHAR(32) NOT NULL
);
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));
--rollback DROP TABLE users;

--changeset idzhambulov:2
CREATE TABLE IF NOT EXISTS car
(
    id BIGSERIAL PRIMARY KEY ,
    brand VARCHAR(32) NOT NULL ,
    model VARCHAR(64) NOT NULL ,
    manufacture_year INTEGER NOT NULL ,
    category VARCHAR(32) NOT NULL ,
    transmission VARCHAR(32) NOT NULL ,
    price INTEGER NOT NULL ,
    is_available BOOLEAN NOT NULL ,
    image VARCHAR(64)
);
--rollback DROP TABLE car;

--changeset idzhambulov:3
CREATE TABLE IF NOT EXISTS client
(
    id BIGINT PRIMARY KEY ,
    age INTEGER NOT NULL ,
    driver_license_number VARCHAR(64) NOT NULL UNIQUE ,
    passport_number VARCHAR(32) NOT NULL UNIQUE ,
    phone_number VARCHAR(32) NOT NULL UNIQUE ,
    FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);
--rollback DROP TABLE client;

--changeset idzhambulov:4
CREATE TABLE IF NOT EXISTS orders
(
    id BIGSERIAL PRIMARY KEY ,
    client_id BIGINT NOT NULL ,
    car_id BIGINT NOT NULL ,
    start_date TIMESTAMP ,
    finish_date TIMESTAMP ,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE ,
    FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE CASCADE
);
--rollback DROP TABLE orders;