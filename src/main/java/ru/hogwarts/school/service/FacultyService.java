package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

@Service
public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    public Faculty editeFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Collection<Faculty> colorFaculty(String color) {
        ArrayList<Faculty> facultiesByColor = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                facultiesByColor.add(faculty);
            }
        }
        return facultiesByColor;
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }
}

