package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Faculty;
import com.example.appjparelationships.entity.Group;
import com.example.appjparelationships.payload.GroupDto;
import com.example.appjparelationships.payload.UniversityDto;
import com.example.appjparelationships.repository.FacultyRepository;
import com.example.appjparelationships.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FacultyRepository facultyRepository;
    // READ
    @GetMapping
    public List<Group> getGroup(){
        List<Group> allGroup = groupRepository.findAll();
        return allGroup;
    }

    // Unversitet ma'sul xodimi uchun
    @GetMapping("/ByUniversityId/{id}")
    public List<Group> getFacultyByUniversityId(@PathVariable Integer universityId){
        List<Group> allByFaculty_university_id = groupRepository.findAllByFaculty_University_Id(universityId);
        return allByFaculty_university_id;
    }

    @PostMapping
    public String addedGroup(@RequestBody GroupDto groupDto){
        Group group = new Group();
        group.setName(groupDto.getName());

        Optional<Faculty> byId = facultyRepository.findById(groupDto.getFacultyId());

        if (byId.isPresent()){
            Faculty faculty = byId.get();
            group.setFaculty(faculty);
            groupRepository.save(group);
            return "added group";
        }
        return "Such faculty not found";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Integer id){

        boolean b = groupRepository.existsById(id);
        if (b){
            groupRepository.deleteById(id);
            return "delete faculty by id";
        }
        return  "Faculty not found";
    }

   @PutMapping("/{id}")
    public String editingGroup(@PathVariable Integer id, @RequestBody GroupDto groupDto){
       Optional<Group> byId = groupRepository.findById(id);
       if (byId.isPresent()) {
           Group group = byId.get();
           group.setName(groupDto.getName());

           Optional<Faculty> byId1 = facultyRepository.findById(groupDto.getFacultyId());
           if (byId1.isPresent()){
               Faculty faculty = byId1.get();
               group.setFaculty(faculty);
               groupRepository.save(group);
               return "group editing";
           }

       }
       return "faculty not found";
   }
}
