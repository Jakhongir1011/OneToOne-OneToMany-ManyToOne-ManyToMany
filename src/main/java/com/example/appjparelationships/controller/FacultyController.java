package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Faculty;
import com.example.appjparelationships.entity.University;
import com.example.appjparelationships.payload.FacultyDto;
import com.example.appjparelationships.repository.FacultyRepository;
import com.example.appjparelationships.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;


    @RequestMapping(value = "/faculty",method = RequestMethod.POST)
    public String addFaculty(@RequestBody FacultyDto facultyDto){

        boolean b = facultyRepository.existsByNameAndUniversityId
                (facultyDto.getName(), facultyDto.getUniversityId());
        if (b) return  "This university such faculty exist";

        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<University> byId = universityRepository.findById(facultyDto.getUniversityId());

        if (byId.isPresent()){
            University university = byId.get();
            faculty.setUniversity(university);
            facultyRepository.save(faculty);
            return "added Faculty";
        }
            return "University not found";
    }


    // Vazirlik uchun
    @RequestMapping(value = "/faculty",method = RequestMethod.GET)
    public List<Faculty> getFaculties(){
        List<Faculty> all = facultyRepository.findAll();
        return all;
    }


    // Unversetetdagi xodimlar uchun
    @RequestMapping(value = "/faculty/ByUniversityId/{universityId}", method = RequestMethod.GET)
    private List<Faculty> getFacultyByUniversityId(@PathVariable Integer universityId){

            List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
            return allByUniversityId;

    }


    @RequestMapping(value = "/faculty/{id}",method = RequestMethod.GET)
    public Faculty getFacultyById(@PathVariable Integer id){
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isPresent()){
            Faculty faculty = byId.get();
            return faculty;
        }
        return new Faculty();
    }


    @RequestMapping(value = "/faculty/{id}",method = RequestMethod.DELETE)
    public String deleteFacultyById(@PathVariable Integer id){
        boolean facultyId = facultyRepository.existsById(id);
        if (facultyId){
            facultyRepository.deleteById(id);
            return "Faculty By Id DELETE";
        }
        return "Faculty not found";
    }


    @RequestMapping(value = "/faculty/{id}",method = RequestMethod.PUT)
    public String editingFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isPresent()){
            Faculty faculty = byId.get();
            faculty.setName(facultyDto.getName());

            Optional<University> byId1 = universityRepository.findById(facultyDto.getUniversityId());
            if (byId1.isPresent()){
                University university = byId1.get();
                faculty.setUniversity(university);
                facultyRepository.save(faculty);
                return "Faculty editing";
            }
        }
        return "University not found";
    }

}
