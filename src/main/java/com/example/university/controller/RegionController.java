package com.example.university.controller;

import com.example.university.entity.Country;
import com.example.university.entity.Region;
import com.example.university.payLoad.RegionDto;
import com.example.university.repository.CountryRepository;
import com.example.university.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RegionController {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CountryRepository countryRepository;

    // Create(POST) new REGION
    @RequestMapping(value = "/region", method = RequestMethod.PUT)
    public String addRegion(@RequestBody RegionDto regionDto) {

        Region region = new Region();
        region.setName(regionDto.getName());

        Optional<Country> optionalCountry = countryRepository.findById(regionDto.getCountryID());
        if (optionalCountry.isEmpty()) {
            return "Country not found";
        }
        region.setCountry(optionalCountry.get());
        regionRepository.save(region);

        return "Region added";
    }

    // Read(GET) REGIONS list
    @RequestMapping(value = "/region", method = RequestMethod.GET)
    public List<Region> getRegions() {

        List<Region> regionList = regionRepository.findAll();
        return regionList;
    }

    // Update(PUT) REGION by id
    @RequestMapping(value = "/region/{id}", method = RequestMethod.PUT)
    public String updateRegion(@PathVariable("id") Integer id, @RequestBody RegionDto regionDto) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (optionalRegion.isEmpty()) {
            return "Region not found";
        }
        Region editingRegion = optionalRegion.get();
        editingRegion.setName(regionDto.getName());

        Optional<Country> optionalCountry = countryRepository.findById(regionDto.getCountryID());
        if (optionalCountry.isEmpty()) {
            return "Country not found";
        }
        editingRegion.setCountry(optionalCountry.get());
        regionRepository.save(editingRegion);

        return "Region edited";
    }

    //Delete(DELETE) REGION by id
    @RequestMapping(value = "/region/{id}", method = RequestMethod.DELETE)
    public String deleteRegion(@PathVariable Integer id) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (optionalRegion.isPresent()) {
            regionRepository.deleteById(id);
            return "Region deleted";
        }
        return "Region not found";
    }
}
