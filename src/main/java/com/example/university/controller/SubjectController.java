package com.example.university.controller;

import com.example.university.entity.Subject;
import com.example.university.payLoad.SubjectDto;
import com.example.university.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    // Create(POST) new SUBJECT
    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    public String addSubject(@RequestBody SubjectDto subjectDto) {

        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subjectRepository.save(subject);

        return "Subject added";
    }

    // Read(GET) SUBJECTS list
    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    public List<Subject> getSubjects() {

        List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    // Update(PUT) SUBJECT by id
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.PUT)
    public String updateSubject(@PathVariable("id") Integer id, @RequestBody SubjectDto subjectDto) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty()) {
            return "Subject not found";
        }
        Subject editingSubject = optionalSubject.get();
        editingSubject.setName(subjectDto.getName());
        subjectRepository.save(editingSubject);

        return "Subject edited";
    }

    // Delete(DELETE) SUBJECT by id
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.DELETE)
    public String deleteSubject(@PathVariable Integer id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isPresent()) {
            subjectRepository.deleteById(id);
            return "Subject deleted";
        }
        return "Subject not found";
    }
}
