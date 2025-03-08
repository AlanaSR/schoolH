package ru.hogwarts.school.controller;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void contextLoadsFacultyTest() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void addStudentTest() throws Exception {

        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);

//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:" +
//                port + "/student"), student, String.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       Assertions.assertThat(this.restTemplate
                .postForObject("http.localhost:" + port + "/student", student, String.class))
                .isNotNull();
//        studentController.deleteStudent(student.getId());
    }

    @Test
    void findStudent() {
        Assertions.
                assertThat(this.restTemplate.getForObject("http.localhost:" + port + "/student", Student.class))
                .isNotNull();
    }

    @Test
    void deleteStudentTest() {

        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(11);
        studentController.addStudent(student);

        restTemplate.delete("http://localhost:" + port + "/student" + student.getId());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student",
                String.class).isEmpty());

        studentController.deleteStudent(student.getId());
    }

    @Test
    void editeStudentTest() {
        Student student = new Student();
        student.setAge(12);
        student.setName("Ron");

        studentController.addStudent(student);
        Student student2 = new Student();
        student2.setId(student2.getId());
        student2.setAge(11);
        student2.setName("Harry");


        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT, new HttpEntity<>(student2), Student.class);

        Assertions
                .assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Harry");

        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student.getId());
    }

    @Test
    void studentsByAgeTest() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);

        studentController.addStudent(student);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age/" + student.getAge(),
                String.class)).isNotNull();
        assertThat(student.getName()).isEqualTo("Harry");

        studentController.deleteStudent(student.getId());
    }

    @Test
    void getAllStudents() {
        Assertions.assertThat(this.restTemplate
                        .getForObject("http://localhost:" + port + "/student/all", String.class))
                .isNotNull();
    }

    @Test
    void findByAgeBetweenTest() {
        Student student1 = new Student(1L, "Ron", 11);
        Student student2 = new Student(2L, "Fred", 14);
        Student student3 = new Student(3L, "Persy", 17);

        studentController.addStudent(student1);
        studentController.addStudent(student2);
        studentController.addStudent(student3);

        String result = restTemplate.getForObject("http://localhost:" + port + "/student/between?from=12&to=15", String.class);
        assertThat(result).isNotNull();

        studentController.deleteStudent(student1.getId());
        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student3.getId());
    }

    @Test
    void getStudentWithFacultyTest() {
        Student student = new Student();
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        facultyController.addFaculty(faculty);

        student.setName("Harry");
        student.setAge(11);
        student.setFaculty(faculty);
        studentController.addStudent(student);

        Faculty actual = this.restTemplate.getForObject("http://localhost:"
                + port + "/studentFaculty" + student.getId(), Faculty.class);

        assertThat(actual.getId()).isEqualTo(new Faculty().getId());
    }
}
