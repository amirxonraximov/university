package com.example.university.controller;

import com.example.university.entity.Address;
import com.example.university.entity.District;
import com.example.university.payLoad.AddressDto;
import com.example.university.repository.AddressRepository;
import com.example.university.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DistrictRepository districtRepository;

    // Create(POST) new ADDRESS
    @RequestMapping(value = "/address", method = RequestMethod.PUT)
    public String addAddress(@RequestBody AddressDto addressDto) {

        Address address = new Address();

        Optional<District> optionalDistrict = districtRepository.findById(addressDto.getDistrictID());
        if (optionalDistrict.isEmpty()) {
            return "District not found";
        }
        address.setDistrict(optionalDistrict.get());
        address.setStreet(addressDto.getStreet());
        address.setHouseNumber(addressDto.getHouseNumber());
        addressRepository.save(address);

        return "Address added";
    }

    // Read(GET) ADDRESSES list
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public List<Address> getAddresses() {

        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }

    // Update(PUT) ADDRESS by id
    @RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
    public String updateAddress(@PathVariable("id") Integer id, @RequestBody AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return "Address not found";
        }
        Address editingAddress = optionalAddress.get();

        Optional<District> optionalDistrict = districtRepository.findById(addressDto.getDistrictID());
        if (optionalDistrict.isEmpty()) {
            return "District not found";
        }
        editingAddress.setDistrict(optionalDistrict.get());
        editingAddress.setStreet(addressDto.getStreet());
        editingAddress.setHouseNumber(addressDto.getHouseNumber());
        addressRepository.save(editingAddress);

        return "Address edited";
    }

    //Delete(DELETE) ADDRESS by id
    @RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
    public String deleteAddress(@PathVariable Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(id);
            return "Address deleted";
        }
        return "Address not found";
    }
}
