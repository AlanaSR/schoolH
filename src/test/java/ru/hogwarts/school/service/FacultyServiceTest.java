package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {

    private final FacultyService facultyService = new FacultyService();

    private final Faculty griffindor = new Faculty(0, "Griffindor", "red");
    private final Faculty huffelpuff = new Faculty(0, "Hufflepuff", "Yellow");

    @Test
    void addFaculty() {
        facultyService.addFaculty(griffindor);
        assertEquals(1, facultyService.getAllFaculties().size());
    }

    @Test
    void findFaculty() {
        facultyService.addFaculty(griffindor);
        assertEquals(1, facultyService.getAllFaculties().size());
        assertEquals(griffindor, facultyService.findFaculty(1));
    }

    @Test
    void deleteFaculty() {
        facultyService.addFaculty(griffindor);
        assertEquals(1, facultyService.getAllFaculties().size());
        facultyService.deleteFaculty(1L);
        assertTrue(facultyService.getAllFaculties().isEmpty());
    }

    @Test
    void editeFaculty() {
        facultyService.addFaculty(griffindor);
        assertTrue(facultyService.getAllFaculties().contains(griffindor));
        facultyService.editeFaculty(huffelpuff);
        assertTrue(facultyService.getAllFaculties().contains(huffelpuff));
    }

    @Test
    void colorFaculty() {
        facultyService.addFaculty(griffindor);
        facultyService.addFaculty(huffelpuff);
        assertEquals(2, facultyService.getAllFaculties().size());
        assertEquals(1, facultyService.colorFaculty("red").size());
    }
}