package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.getById(id);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student editeStudent(Student student) {
        return studentRepository.save(student);
    }


    public Collection<Student> studentsByAge(int age) {
        return studentRepository.studentsByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
