CREATE TABLE  if not exists post (
   id SERIAL PRIMARY KEY,
   name text,
   description text,
   city text,
   created timestamp

);