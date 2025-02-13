package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student addStudent(Student student) {
        student.setId(++lastId);
        return students.put(student.getId(), student);
    }

    public  Student findStudent (Long id) {
        return students.get(id);
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }

    public Student editeStudent(Student student) {
        return students.put(student.getId(), student);
    }

    public Collection<Student> studentsByAge(int age) {
        ArrayList<Student> studentsAge = new ArrayList<>();
        for (Student student : students.values()) {
            if (Objects.equals(student.getAge(),age)) {
                studentsAge.add(student);
            }
        }
        return studentsAge;
    }
}
