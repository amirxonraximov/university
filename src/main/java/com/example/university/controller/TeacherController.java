package com.example.university.controller;

import com.example.university.entity.Address;
import com.example.university.entity.Subject;
import com.example.university.entity.Teacher;
import com.example.university.payLoad.TeacherDto;
import com.example.university.repository.AddressRepository;
import com.example.university.repository.SubjectRepository;
import com.example.university.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;

    // Create(POST) new TEACHER
    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public String addTeacher(@RequestBody TeacherDto teacherDto) {

        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());

        Optional<Address> optionalAddress = addressRepository.findById(teacherDto.getAddressID());
        if (optionalAddress.isEmpty()) {
            return "Address not found";
        }
        teacher.setAddress(optionalAddress.get());

        Optional<Subject> optionalSubject = subjectRepository.findById(teacherDto.getSubjectID());
        if (optionalSubject.isEmpty()) {
            return "Subject not found";
        }
        teacher.setSubject(optionalSubject.get());

        teacherRepository.save(teacher);

        return "Teacher added";
    }

    // Read(GET) TEACHERS list
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public List<Teacher> getTeachers() {

        List<Teacher> teacherList = teacherRepository.findAll();
        return teacherList;
    }

    // Update(PUT) TEACHER by id
    @RequestMapping(value = "/teacher/{id}", method = RequestMethod.PUT)
    public String updateTeacher(@PathVariable("id") Integer id, @RequestBody TeacherDto teacherDto) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent()) {

            Teacher editingTeacher = optionalTeacher.get();
            editingTeacher.setFirstName(teacherDto.getFirstName());
            editingTeacher.setLastName(teacherDto.getLastName());

            Optional<Address> optionalAddress = addressRepository.findById(teacherDto.getAddressID());
            if (optionalAddress.isEmpty()) {
                return "Address not found";
            }
            editingTeacher.setAddress(optionalAddress.get());

            Optional<Subject> optionalSubject = subjectRepository.findById(teacherDto.getSubjectID());
            if (optionalSubject.isEmpty()) {
                return "Subject not found";
            }
            editingTeacher.setSubject(optionalSubject.get());

            teacherRepository.save(editingTeacher);

            return "Teacher edited";
        }
        return "Teacher not found";
    }

    // Delete(DELETE) TEACHER by id
    @RequestMapping(value = "/teacher/{id}", method = RequestMethod.DELETE)
    public String deleteTeacher(@PathVariable Integer id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent()) {
            teacherRepository.deleteById(id);
            return "Teacher deleted";
        }
        return "Teacher not found";
    }
}
