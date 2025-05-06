--liquibase formated sql

--changeset alana: 1
create index student_name_index on Student (name);

--changeset alana: 2
create index name_color_faculty_index on Faculty (name, color);