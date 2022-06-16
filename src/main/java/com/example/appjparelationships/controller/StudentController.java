package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Address;
import com.example.appjparelationships.entity.Group;
import com.example.appjparelationships.entity.Student;
import com.example.appjparelationships.entity.Subject;
import com.example.appjparelationships.payload.StudentDto;
import com.example.appjparelationships.repository.GroupRepository;
import com.example.appjparelationships.repository.StudentRepository;
import com.example.appjparelationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping
public class StudentController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;


   @GetMapping("student")
    public List<Student> getStudent(){
       List<Student> all = studentRepository.findAll();
       return all;
   }


    @GetMapping("/forMinistry")
    // 1. RequestParam int tipida nechanchi sahifada turganligi keladi
    public Page<Student> getStudentListMinistry(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Student> studentRepositoryAll = studentRepository.findAll(pageable);
        return studentRepositoryAll;
    }

    // 2. for University
  @GetMapping("/forUniversity{universityId}")
    public Page<Student> getStudentListUniversity(@RequestParam int page, @PathVariable Integer universityId){
    Pageable pageable =  PageRequest.of(page, 10);
    Page<Student> studentsUniversity = studentRepository.findStudentsByGroup_Faculty_UniversityId(universityId, pageable);
    return studentsUniversity;
}

   // 3. for Faculty

    @GetMapping("/faculty/{facultyId}")
    public Page<Student> getStudentListFaculty(@PathVariable Integer facultyId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Student> studentsFaculty = studentRepository.findStudentsByGroup_FacultyId(facultyId, pageable);
        return studentsFaculty;
    }

   // 4. for Group

    @GetMapping("/group/{groupId}")
    public Student getStudentListGroupId(@PathVariable Integer groupId){

        Student studentsByGroupId = studentRepository.findStudentsByGroupId(groupId);
        return studentsByGroupId;
    }


    @PostMapping("/student")
    public String addedStudent(@RequestBody StudentDto studentDto){
       try {
           Student student = new Student();
           student.setFirstName(studentDto.getFirstName());
           student.setLastName(studentDto.getLastName());

           Address address = new Address();
           address.setCity(studentDto.getCity());
           address.setDistrict(studentDto.getDistrict());
           address.setStreet(studentDto.getStreet());
           student.setAddress(address);

          List<Subject> subjects = new ArrayList<>();

//         List<Subject> subjectsById = subjectRepository.getSubjectsById(String.valueOf(studentDto.getSubjects()));
           student.setSubject(subjects);

           Group groupId = groupRepository.getById(studentDto.getGroupId());
           student.setGroup(groupId);

           studentRepository.save(student);
           return "Student save";

       }catch (Exception e){
           return "error: " + e;
       }

    }

    // VAZIRLIK UCHUN
    @DeleteMapping("/forUniversity{universityId}")
    public String deleteByUniversityId(@PathVariable Integer universityId){
       studentRepository.deleteAllByGroup_Faculty_University(universityId);
        return "it's delete student of University";
    }

}
