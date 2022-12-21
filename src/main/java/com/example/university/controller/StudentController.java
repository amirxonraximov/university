package com.example.university.controller;

import com.example.university.entity.Address;
import com.example.university.entity.Student;
import com.example.university.entity.Subject;
import com.example.university.payLoad.StudentDto;
import com.example.university.repository.AddressRepository;
import com.example.university.repository.StudentRepository;
import com.example.university.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;


    // Create(POST) new STUDENT
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public String addStudent(@RequestBody StudentDto studentDto) {

        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressID());
        if (optionalAddress.isEmpty()) {
            return "Address not found";
        }
        student.setAddress(optionalAddress.get());

        ArrayList<Subject> subjects = new ArrayList<>();
        List<Integer> subjectIDs = studentDto.getSubjectIDs();
        for (Integer subjectID : subjectIDs) {
            Optional<Subject> subject = subjectRepository.findById(subjectID);
            if (subject.isEmpty()) {
                return "Subject not found";
            }
            subjects.add(subject.get());
        }

        student.setSubjects(subjects);
        studentRepository.save(student);

        return "Student added";
    }

    // Read(GET) STUDENTS list
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public List<Student> getStudents() {

        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    // Update(PUT) STUDENT by id
    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public String updateStudent(@PathVariable("id") Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return "Student not found";
        }

        Student editingStudent = optionalStudent.get();
        editingStudent.setFirstName(studentDto.getFirstName());
        editingStudent.setLastName(studentDto.getLastName());

        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressID());
        if (optionalAddress.isEmpty()) {
            return "Address not found";
        }
        editingStudent.setAddress(optionalAddress.get());

        ArrayList<Subject> subjects = new ArrayList<>();
        List<Integer> subjectIDs = studentDto.getSubjectIDs();
        for (Integer subjectID : subjectIDs) {
            Optional<Subject> subject = subjectRepository.findById(subjectID);
            if (subject.isEmpty()) {
                return "Subject not found";
            }
            subjects.add(subject.get());
        }

        editingStudent.setSubjects(subjects);
        studentRepository.save(editingStudent);

        return "Student edited";
    }

    // Delete(DELETE) STUDENT by id
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public String deleteStudent(@PathVariable Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepository.deleteById(id);
            return "Student deleted";
        }
        return "Student not found";
    }
}
