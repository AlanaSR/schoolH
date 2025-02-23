package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> findStudent(Long id) {
        return studentRepository.findAll().stream()
                .filter(s->s.getId().equals(id))
                .findFirst();
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student editeStudent(Student student) {
        return studentRepository.save(student);
    }


    public Collection<Student> findAllByAge(Integer age) {
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findAllByAgeBetween(Integer from, Integer to) {
        return studentRepository.findByAgeBetween(from, to);
    }
}

