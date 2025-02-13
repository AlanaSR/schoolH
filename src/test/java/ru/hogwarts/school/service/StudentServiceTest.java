package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private final StudentService studentService = new StudentService();

    private final Student harry = new Student(0, "Harry Potter", 11);
    private final Student fred = new Student(1, "Fred Weasley", 13);

    @Test
    void addStudent() {
        studentService.addStudent(harry);
        assertEquals(1, studentService.getAllStudents().size());
    }

    @Test
    void findStudent() {
        studentService.addStudent(harry);
        assertEquals(1, studentService.getAllStudents().size());
        assertEquals(harry, studentService.findStudent(1L));
    }

    @Test
    void deleteStudent() {
        studentService.addStudent(harry);
        assertEquals(1, studentService.getAllStudents().size());
        studentService.deleteStudent(1L);
        assertEquals(0, studentService.getAllStudents().size());
    }

    @Test
    void editeStudent() {
        studentService.addStudent(harry);
        assertTrue(studentService.getAllStudents().contains(harry));
        studentService.editeStudent(fred);
        assertTrue(studentService.getAllStudents().contains(fred));
        assertEquals(1, studentService.getAllStudents().size());
    }

    @Test
    void studentsByAge() {
        studentService.addStudent(harry);
        studentService.addStudent(fred);
        assertEquals(2, studentService.getAllStudents().size());
        studentService.studentsByAge(13);
        assertTrue(studentService.getAllStudents().contains(fred));
    }

    @Test
    void getAllStudents() {
        studentService.addStudent(harry);
        studentService.addStudent(fred);
        assertEquals(2, studentService.getAllStudents().size());
    }
}