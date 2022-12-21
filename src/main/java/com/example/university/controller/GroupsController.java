package com.example.university.controller;

import com.example.university.entity.*;
import com.example.university.payLoad.GroupsDto;
import com.example.university.repository.FacultyRepository;
import com.example.university.repository.GroupsRepository;
import com.example.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GroupsController {

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    StudentRepository studentRepository;

    // Create(POST) new GROUP
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public String addGroup(@RequestBody GroupsDto groupsDto) {

        Groups groups = new Groups();
        groups.setName(groupsDto.getName());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupsDto.getFacultyID());
        if (optionalFaculty.isEmpty()) {
            return "Faculty not found";
        }
        groups.setFaculty(optionalFaculty.get());

        ArrayList<Student> students = new ArrayList<>();
        List<Integer> studentIDs = groupsDto.getStudentIDs();
        for (Integer studentID : studentIDs) {
            Optional<Student> optionalStudent = studentRepository.findById(studentID);
            if (optionalStudent.isEmpty()) {
                return "Student not found";
            }
            students.add(optionalStudent.get());
        }

        groups.setStudents(students);
        groupsRepository.save(groups);

        return "Group added";
    }

    // Read(GET) GROUPS list
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public List<Groups> getGroups() {

        List<Groups> groups = groupsRepository.findAll();
        return groups;
    }

    // Update(PUT) GROUP by id
    @RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
    public String updateGroup(@PathVariable("id") Integer id, @RequestBody GroupsDto groupsDto) {
        Optional<Groups> optionalGroups = groupsRepository.findById(id);
        if (optionalGroups.isEmpty()) {
            return "Group not found";
        }
        Groups editingGroup = new Groups();
        editingGroup.setName(groupsDto.getName());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupsDto.getFacultyID());
        if (optionalFaculty.isEmpty()) {
            return "Faculty not found";
        }
        editingGroup.setFaculty(optionalFaculty.get());

        ArrayList<Student> students = new ArrayList<>();
        List<Integer> studentIDs = groupsDto.getStudentIDs();
        for (Integer studentID : studentIDs) {
            Optional<Student> optionalStudent = studentRepository.findById(studentID);
            if (optionalStudent.isEmpty()) {
                return "Student not found";
            }
            students.add(optionalStudent.get());
        }

        editingGroup.setStudents(students);
        groupsRepository.save(editingGroup);

        return "Group edited";
    }

    // Delete(DELETE) GROUP by id
    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    public String deleteGroup(@PathVariable Integer id) {
        Optional<Groups> optionalGroups = groupsRepository.findById(id);
        if (optionalGroups.isPresent()) {
            groupsRepository.deleteById(id);
            return "Group deleted";
        }
        return "Group not found";
    }
}
