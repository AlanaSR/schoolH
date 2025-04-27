alter table Student
add constraint age_constraint check (age>16),
add constraint age_default set default 20;

alter table Student
add constraint name_unique unique (name) not null;

alter table Student
alter column unique (name) not null;

alter table Faculty
add constraint faculty_unique unique (name, color);
