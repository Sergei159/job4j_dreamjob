create table if not exists CLIENT(
id serial primary key,
name text,
email text,
password text
);
ALTER TABLE clients ADD CONSTRAINT email_unique UNIQUE (email);