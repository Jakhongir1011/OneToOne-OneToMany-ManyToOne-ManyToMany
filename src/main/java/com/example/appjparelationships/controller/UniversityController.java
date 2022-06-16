package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Address;
import com.example.appjparelationships.entity.University;
import com.example.appjparelationships.payload.UniversityDto;
import com.example.appjparelationships.repository.AddressRepository;
import com.example.appjparelationships.repository.UniversityRepository;
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


    // READ
    @RequestMapping(value = "/university",method = RequestMethod.GET)
    public List<University> getUniversityController(){
        List<University> universities = universityRepository.findAll();
        return universities;
    }

    // READ *
    @RequestMapping(value = "/university/{id}",method = RequestMethod.GET)
    public University getByIdUniversity(@PathVariable Integer id){
        Optional<University> byId = universityRepository.findById(id);
        if (byId.isPresent()){
            University university = byId.get();
            return university;
        }
        return null;
    }

    // CREATE
    @RequestMapping(value = "/university",method = RequestMethod.POST)
    public String addedUniversity(@RequestBody UniversityDto universityDto){
        //We opened a new program
        boolean b = universityRepository.existsUniversitiesByName(universityDto.getName());
        if (!b){
            Address address = new Address();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            // We saved our newly received Address Object to DB and gave us the address
            Address saveAddress = addressRepository.save(address);

            University university = new University();
            university.setName(universityDto.getName());
            university.setAddress(saveAddress);
            universityRepository.save(university);

            return "added university";
        }
        return "there is such a university";

    }

    // Update put
    @RequestMapping(value = "/university/{id}",method = RequestMethod.PUT)
    public String editedUniversity(@RequestBody UniversityDto universityDto, @PathVariable Integer id){

        Optional<University> universityById = universityRepository.findById(id);
        if (universityById.isPresent()){
            // get new University of box
            University universityId = universityById.get();
            universityId.setName(universityDto.getName());

            Address address = universityId.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());

            universityRepository.save(universityId);
            addressRepository.save(address);

            return "editing University";
        }
        return "University not found";
    }

    @RequestMapping(value = "/university/{id}",method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){
         universityRepository.deleteById(id);
        return "delete University";
    }






}
