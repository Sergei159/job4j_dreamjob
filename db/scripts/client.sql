create table if not exists CLIENT(
id serial primary key,
name  text not null,
email  text not null,
password  text not null,
CONSTRAINT email_unique UNIQUE (email)
);

