package ru.hogwarts.school.controller;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
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
    private AvatarRepository avatarRepository;
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
        student.setId(2L);
        student.setName("Harry");
        student.setAge(11);

        ResponseEntity<Student> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findStudent() {

        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);
        ResponseEntity<Student> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student", Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    void deleteStudentTest() {

        Student student = new Student();
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
        student2.setId(student.getId());
        student2.setAge(11);
        student2.setName("Harry");


        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT, new HttpEntity<>(student2), Student.class);

        Assertions
                .assertThat(response.getStatusCode().is2xxSuccessful());

        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student.getId());
    }

    @Test
    void studentsByAgeTest() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);

        List<Student> students = new ArrayList<>();
        int age = 11;

        studentController.addStudent(student);

        ResponseEntity<List<Student>> actual = restTemplate.exchange("http://localhost:" + port + "/student/age?age=" + age,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

        studentController.deleteStudent(student.getId());
    }

    @Test
    void getAllStudents() {

        ResponseEntity<List<Student>> actual = restTemplate.exchange("http://localhost:" + port + "/student/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findByAgeBetweenTest() {
        Student student1 = new Student();
        student1.setName("Ron");
        student1.setAge(11);
        Student student2 = new Student();
        student2.setName("Fred");
        student2.setAge(14);
        Student student3 = new Student();
        student3.setName("Persey");
        student3.setAge(17);
        studentController.addStudent(student1);
        studentController.addStudent(student2);
        studentController.addStudent(student3);


        ResponseEntity<List<Student>> actual = restTemplate
                .exchange("http://localhost:" + port + "/student/between?from=12&to=15",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        studentController.deleteStudent(student1.getId());
        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student3.getId());
    }

    @Test
    void getStudentWithFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");
        facultyController.addFaculty(faculty);

        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);
        student.setFaculty(faculty);
        studentController.addStudent(student);

        ResponseEntity <Faculty> actual = restTemplate
                .getForEntity("http://localhost:" + port + "/student/studentFaculty/" + student.getId(),
                        Faculty.class);

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
