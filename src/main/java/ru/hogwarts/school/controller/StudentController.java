package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Student> editeStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editeStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/age")
    public Collection<Student> studentsByAge(@RequestParam Integer age) {
        return studentService.findAllByAge(age);
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/between")
    public Collection<Student> findByAgeBetween(@RequestParam Integer from,
                                                @RequestParam Integer to) {
        return studentService.findAllByAgeBetween(from, to);
    }

    @GetMapping("/studentFaculty/{id}")
    public Faculty getStudentWithFaculty(@PathVariable Long id) {
        return studentService.findStudent(id).getFaculty();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> numberOfStudentInUniversity() {
        int count = studentService.numberOfStudentInUniversity();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/avg")
    public ResponseEntity<Integer> avgAgeOfStudents() {
        int avgAge = studentService.avgAgeOfStudents();
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping("/last")
    public List<Student> getFiveLastStudents() {
        return studentService.getFiveLastStudents();
    }

    @GetMapping("/average")
    public ResponseEntity<Integer> averageAgeOfStudents() {
        Integer average = studentService.averageAgeStudentsStream();
        return ResponseEntity.ok(average);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> studentsNamesFirstA() {
        List<String> namesA = studentService.getAllStudentNameFirstA();
        return ResponseEntity.ok(namesA);
    }
}