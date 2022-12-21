package com.example.university.controller;

import com.example.university.entity.Address;
import com.example.university.entity.University;
import com.example.university.payLoad.UniversityDto;
import com.example.university.repository.AddressRepository;
import com.example.university.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;

    // Create(POST) new UNIVERSITY
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {

        University university = new University();
        university.setName(universityDto.getName());

        Optional<Address> optionalAddress = addressRepository.findById(universityDto.getAddressID());
        if (optionalAddress.isEmpty()) {
            return "Address not found";
        }
        university.setAddress(optionalAddress.get());
        universityRepository.save(university);

        return "University added";
    }

    // Read(GET) UNIVERSITIES list
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {

        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    // Update(PUT) UNIVERSITY by id
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String updateUniversity(@PathVariable("id") Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {

            University editingUniversity = optionalUniversity.get();
            editingUniversity.setName(universityDto.getName());

            Optional<Address> optionalAddress = addressRepository.findById(universityDto.getAddressID());
            if (optionalAddress.isEmpty()) {
                return "Address not found";
            }
            editingUniversity.setAddress(optionalAddress.get());
            universityRepository.save(editingUniversity);

            return "University edited";
        }
        return  "University not found";
    }

    // Delete(DELETE) UNIVERSITY by id
    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            universityRepository.deleteById(id);
            return "University deleted";
        }
        return "University not found";
    }
}
