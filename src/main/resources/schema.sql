CREATE TABLE IF NOT EXISTS users (email VARCHAR(100), password VARCHAR(100), firstname VARCHAR(100),lastname VARCHAR(100), language VARCHAR(100), permission integer)
create table if not exists courses (title varchar(100), description varchar(100), author varchar(100), students varchar(1000), data bytea)
create table if not exists units (title varchar(100), unitType varchar(100))