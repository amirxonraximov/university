package com.example.university.controller;

import com.example.university.entity.Continent;
import com.example.university.payLoad.ContinentDto;
import com.example.university.repository.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContinentController {

    @Autowired
    ContinentRepository continentRepository;

    // Create(POST) new CONTINENT
    @RequestMapping(value = "/continent", method = RequestMethod.PUT)
    public String addContinent(@RequestBody ContinentDto continentDto) {

        Continent continent = new Continent();
        continent.setName(continentDto.getName());
        continentRepository.save(continent);

        return "Continent added";
    }

    // Read(GET) CONTINENTS list
    @RequestMapping(value = "/continent", method = RequestMethod.GET)
    public List<Continent> getContinents() {

        List<Continent> continentList = continentRepository.findAll();
        return continentList;
    }

    // Update(PUT) CONTINENT by id
    @RequestMapping(value = "/continent/{id}", method = RequestMethod.PUT)
    public String updateContinent(@PathVariable("id") Integer id, @RequestBody ContinentDto continentDto) {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isPresent()) {

            Continent editingContinent = optionalContinent.get();
            editingContinent.setName(continentDto.getName());
            continentRepository.save(editingContinent);

            return "Continent added";
        }
        return "Continent not found";
    }

    //Delete(DELETE) CONTINENT by id
    @RequestMapping(value = "/continent/{id}", method = RequestMethod.DELETE)
    public String deleteContinent(@PathVariable Integer id) {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isPresent()) {
            continentRepository.deleteById(id);
            return "Continent deleted";
        }
        return "Continent not found";
    }
}
