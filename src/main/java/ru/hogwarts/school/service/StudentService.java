package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Student editeStudent(Student student) {
        logger.info("Was invoked method for edite student");
        return studentRepository.save(student);
    }


    public Collection<Student> findAllByAge(Integer age) {
        logger.info("Was invoked method for find students by age");
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for output all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findAllByAgeBetween(Integer from, Integer to) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(from, to);
    }

    public Integer numberOfStudentInUniversity() {
        logger.info("Was invoked method for determining the number of students in a university");
        return studentRepository.numberOfStudentsInUniversity();
    }

    public Integer avgAgeOfStudents() {
        logger.info("Was invoked method for determining the average age of students at a university");
        return studentRepository.avgAgeOfStudents();
    }

    public List<Student> getFiveLastStudents() {
        logger.info("Was invoked method for output five last students");
        return studentRepository.getFiveLastStudents();
    }

    public List<String> getAllStudentNameFirstA() {
        return studentRepository.findAll().stream().filter(student -> student.getName().startsWith("A"))
                .sorted()
                .map(Student::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

    }

    public Integer averageAgeStudentsStream() {
        Integer age = (int) studentRepository.findAll().stream()
                .map(Student::getAge)
                .mapToInt(a -> a).average()
//                .map(a -> {
//                    System.out.println("Average age of students" + a);
//                    return a;
//                })
//                .sum();
                .getAsDouble();
        return age;
    }

    public List<Student> studentsPrintParallel() {
        logger.info("Was invoked method for printing names with parallel method");
        List<Student> students = studentRepository.findAll();

        System.out.println("Первый студент " + students.get(0).getName());
        System.out.println("Второй студент " + students.get(1).getName());

        new Thread(() -> {
            System.out.println("Третий студент " + students.get(2).getName());
            System.out.println("Четвертый студент " + students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println("Пятый студент " + students.get(4).getName());
            System.out.println("Шестой студент " + students.get(5).getName());
        }).start();

        return students;
    }

    public List<Student> studentsPrintParallelSync() {
        logger.info("Was invoked method for printing synchronized names with parallel method");
        List<Student> students = studentRepository.findAll();

        studentsPrintSync(students.get(0));
        studentsPrintSync(students.get(1));

        new Thread(() -> {
            studentsPrintSync(students.get(2));
            studentsPrintSync(students.get(3));
        }).start();

        new Thread(() -> {
            studentsPrintSync(students.get(4));
            studentsPrintSync(students.get(5));
        }).start();

        return students;
    }

    public synchronized void studentsPrintSync(Student student) {
        System.out.println(student.getId() + " студент " + student.getName());
    }
}

