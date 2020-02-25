CREATE TABLE models_list (
    id serial primary key,
    name text
);

CREATE TABLE models(
model_id serial primary key,
name text,
models_id  int not null references models_list(id)
);

CREATE TABLE body (
    body_id serial primary key,
    name text
);
CREATE TABLE transmission (
    transmission_id serial primary key,
    name text
);
CREATE TABLE oil (
    oil_id serial primary key,
    name text
);
create table role(
    role_id serial primary key,
    status text
);
CREATE TABLE user_list(
    user_id serial primary key,
    name text,
    login text,
    role_id int not null references role(role_id)
);

CREATE TABLE items(
items_id serial primary key,
user_id  int not null references user_list(user_id),
brand_id int not null references models_list(id) ,
model_id int not null references models(model_id),
body_id int not null references body(body_id),
transmission_id int not null references transmission(transmission_id),
oil_id int not null references oil(oil_id),
price int,
image text,
status text
);
