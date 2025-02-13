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
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(Long id) {
        return students.get(id);
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public Student editeStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Collection<Student> studentsByAge(int age) {
        ArrayList<Student> studentsAge = new ArrayList<>();
        for (Student student : students.values()) {
            if (Objects.equals(student.getAge(), age)) {
                studentsAge.add(student);
            }
        }
        return studentsAge;
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }
}
