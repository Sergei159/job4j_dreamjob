CREATE TABLE post if not exists (
   id SERIAL PRIMARY KEY,
   name text,
   description text,
   created timestamp
);