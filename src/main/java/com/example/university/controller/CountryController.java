package com.example.university.controller;

import com.example.university.entity.Continent;
import com.example.university.entity.Country;
import com.example.university.payLoad.CountryDto;
import com.example.university.repository.ContinentRepository;
import com.example.university.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ContinentRepository continentRepository;

    // Create(POST) new COUNTRY
    @RequestMapping(value = "/country", method = RequestMethod.PUT)
    public String addCountry(@RequestBody CountryDto countryDto) {

        Country country = new Country();
        country.setName(countryDto.getName());

        Optional<Continent> optionalContinent = continentRepository.findById(countryDto.getContinentID());
        if (optionalContinent.isEmpty()) {
            return "Continent not found";
        }
        country.setContinent(optionalContinent.get());
        countryRepository.save(country);

        return "Country added";
    }

    // Read(GET) COUNTRIES list
    @RequestMapping(value = "/contry", method = RequestMethod.GET)
    public List<Country> getCountries() {

        List<Country> countryList = countryRepository.findAll();
        return countryList;
    }

    // Update(PUT) COUNTRY by id
    @RequestMapping(value = "/country/{id}", method = RequestMethod.PUT)
    public String updateCountry(@PathVariable("id") Integer id, @RequestBody CountryDto countryDto) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isEmpty()) {
            return "Country not found";
        }
        Country editingCountry = optionalCountry.get();
        editingCountry.setName(countryDto.getName());

        Optional<Continent> optionalContinent = continentRepository.findById(countryDto.getContinentID());
        if (optionalContinent.isEmpty()) {
            return "Continent not found";
        }
        editingCountry.setContinent(optionalContinent.get());
        countryRepository.save(editingCountry);

        return "Country edited";
    }

    //Delete(DELETE) COUNTRY by id
    @RequestMapping(value = "/country/{id}", method = RequestMethod.DELETE)
    public String deleteCountry(@PathVariable Integer id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isPresent()) {
            countryRepository.deleteById(id);
            return "Country deleted";
        }
        return "Country not found";
    }
}
