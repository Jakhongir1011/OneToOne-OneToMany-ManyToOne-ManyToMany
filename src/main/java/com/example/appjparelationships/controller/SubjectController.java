package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Subject;
import com.example.appjparelationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    // GET SUBJECT ALL  READ -> GetMapping yossak ham boladi 19 qator orniga
    @RequestMapping(value = "/subject",method = RequestMethod.GET)
    public List<Subject> getSubjects(){
        List<Subject> subjects = subjectRepository.findAll();
        return subjects;
    }

    // ADDED NEW SUBJECT
    @RequestMapping(value = "/subject",method = RequestMethod.POST)
    public String addedSubject(@RequestBody Subject subject){
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName){
            return "This subject already exist";
        }
        subjectRepository.save(subject);
        return "added Subject";
    }

    // GET SUBJECT BY ID
    @RequestMapping(value = "/subject/{id}",method = RequestMethod.GET)
    public Subject getByIdSubject( @PathVariable Integer id){

        Optional<Subject> byId = subjectRepository.findById(id);
         if (byId.isPresent()){
             Subject subject = byId.get();
         return subject;
         }
        return new Subject();
    }
    // DELETE SUBJECT BY ID
    @RequestMapping(value = "/subject/{id}",method = RequestMethod.DELETE)
    public String deleteSubjectById(@PathVariable Integer id){
        subjectRepository.deleteById(id);
        return "delete subject";
    }

    // POST -> update
    @RequestMapping(value = "/subject/{id}",method = RequestMethod.PUT)
    public String editedSubject(@RequestBody Subject subject, @PathVariable Integer id){
        Optional<Subject> byId = subjectRepository.findById(id);
        if (byId.isPresent()){
            Subject subjectById = byId.get();
            subjectById.setName(subject.getName());
            subjectRepository.save(subjectById);
        return "edited Subject";
        }
        return "edited not Subject";
    }
}



