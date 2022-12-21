package com.example.university.controller;

import com.example.university.entity.District;
import com.example.university.entity.Region;
import com.example.university.payLoad.DistrictDto;
import com.example.university.repository.DistrictRepository;
import com.example.university.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    RegionRepository regionRepository;

    // Create(POST) new DISTRICT
    @RequestMapping(value = "/district", method = RequestMethod.PUT)
    public String addDistrict(@RequestBody DistrictDto districtDto) {

        District district = new District();
        district.setName(districtDto.getName());

        Optional<Region> optionalRegion = regionRepository.findById(districtDto.getRegionID());
        if (optionalRegion.isEmpty()) {
            return "Region not found";
        }
        district.setRegion(optionalRegion.get());
        districtRepository.save(district);

        return "District added";
    }

    // Read(GET) DISTRICTS list
    @RequestMapping(value = "/district", method = RequestMethod.GET)
    public List<District> getDistricts() {

        List<District> districtList = districtRepository.findAll();
        return districtList;
    }

    // Update(PUT) DISTRICT by id
    @RequestMapping(value = "/district/{id}", method = RequestMethod.PUT)
    public String updateDitrict(@PathVariable("id") Integer id, @RequestBody DistrictDto districtDto) {
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isEmpty()) {
            return "District not found";
        }
        District editingDistrict = optionalDistrict.get();
        editingDistrict.setName(districtDto.getName());

        Optional<Region> optionalRegion = regionRepository.findById(districtDto.getRegionID());
        if (optionalRegion.isEmpty()) {
            return "Region not found";
        }
        editingDistrict.setRegion(optionalRegion.get());
        districtRepository.save(editingDistrict);

        return "District edited";
    }

    //Delete(DELETE) DISTRICT by id
    @RequestMapping(value = "/district/{id}", method = RequestMethod.DELETE)
    public String deleteDistrict(@PathVariable Integer id) {
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isPresent()) {
            districtRepository.deleteById(id);
            return "District deleted";
        }
        return "District not found";
    }
}
