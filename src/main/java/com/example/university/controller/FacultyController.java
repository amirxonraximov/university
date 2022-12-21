package com.example.university.controller;

import com.example.university.entity.Faculty;
import com.example.university.entity.University;
import com.example.university.payLoad.FacultyDto;
import com.example.university.repository.FacultyRepository;
import com.example.university.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    UniversityRepository universityRepository;

    // Create(POST) new FACULTY
    @RequestMapping(value = "/faculty", method = RequestMethod.POST)
    public String addFaculty(@RequestBody FacultyDto facultyDto) {

        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());

        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityID());
        if (optionalUniversity.isEmpty()) {
            return "University not found";
        }
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);

        return "Faculty added";
    }

    // Read(GET) FACULTIES list
    @RequestMapping(value = "/faculty", method = RequestMethod.GET)
    public List<Faculty> getFaculties() {

        List<Faculty> facultyList = facultyRepository.findAll();
        return facultyList;
    }

    // Update(PUT) FACULTY by id
    @RequestMapping(value = "/faculty/{id}", method = RequestMethod.PUT)
    public String updateFaculty(@PathVariable("id") Integer id, @RequestBody FacultyDto facultyDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {

            Faculty editingFaculty = optionalFaculty.get();
            editingFaculty.setName(facultyDto.getName());

            Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityID());
            if (optionalUniversity.isEmpty()) {
                return "University not found";
            }
            editingFaculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(editingFaculty);

            return "Faculty edited";
        }
        return  "Faculty not found";
    }

    // Delete(DELETE) FACULTY by id
    @RequestMapping(value = "/faculty/{id}", method = RequestMethod.DELETE)
    public String deleteFaculty(@PathVariable Integer id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        }
        return "Faculty not found";
    }
}
