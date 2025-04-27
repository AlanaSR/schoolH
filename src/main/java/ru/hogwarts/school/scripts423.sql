select student.name, student.age, faculty.name
from student
left join faculty on student.faculty=faculty.name;

select student.name, avatar.id
from student
inner join avatar on student.avatar = avatar.id;