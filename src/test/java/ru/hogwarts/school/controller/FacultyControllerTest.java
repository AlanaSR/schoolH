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
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void addFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");

        ResponseEntity<Faculty> faculties = this.restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Assertions.assertThat(faculties.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(faculties.getBody().getName()).isEqualTo(faculty.getName());
    }

    @Test
    void findFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");

        ResponseEntity<Faculty> faculties = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty", Faculty.class);
        Assertions.assertThat(faculties.getBody()).isEqualTo(faculty);
    }

    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty();

        faculty.setName("Griff");
        faculty.setColor("black");
        facultyController.addFaculty(faculty);

        restTemplate.delete("http://localhost:" + port + "/faculty" + faculty.getId());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty",
                String.class).isEmpty());

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    void editeFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Griff");
        faculty.setColor("white");

        facultyRepository.save(faculty);

        Faculty faculty1 = new Faculty();
        faculty1.setId(faculty.getId());
        faculty1.setName("Griffindor");
        faculty1.setColor("red");

        ResponseEntity<Faculty> response = restTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT, new HttpEntity<>(faculty1), Faculty.class);

        Assertions
                .assertThat(response.getStatusCode().is2xxSuccessful());

        facultyController.deleteFaculty(faculty.getId());
        facultyController.deleteFaculty(faculty1.getId());
    }

    @Test
    void testFindFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");
        facultyController.addFaculty(faculty);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/color/" + faculty.getColor(),
                String.class)).isNotNull();
        assertThat(faculty.getColor()).isEqualTo("red");

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    void getAllFaculties() {

        ResponseEntity<List<Faculty>> actual = restTemplate.exchange("http://localhost:" + port + "/faculty/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Faculty>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getFacultyWithStudents() {
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");
        facultyController.addFaculty(faculty);

        Student student = new Student();
        student.setName("Ron");
        student.setAge(15);
        student.setFaculty(faculty);


        ResponseEntity<List<Faculty>> actual = restTemplate
                .exchange("http://localhost:" + port + "/faculty/facultyStudents/" + faculty.getId(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Faculty>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

        facultyController.deleteFaculty(faculty.getId());
    }
}