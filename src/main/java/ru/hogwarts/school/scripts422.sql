create table drivers(
id integer primary key,
name text not null,
age integer check (age>=18),
lisence boolean default 'no',
car_id integer references cars (id)
)

create table cars(
id integer primary key,
brend text not null,
model text not null,
car_price integer not null,
car_price integer check (car_price>0)
)
