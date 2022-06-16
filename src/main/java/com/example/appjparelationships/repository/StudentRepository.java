package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

    Page<Student> findStudentsByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);

    Page<Student> findStudentsByGroup_FacultyId(Integer group_faculty_id, Pageable pageable);

    Student findStudentsByGroupId(Integer group_id);

    String deleteAllByGroup_Faculty_University(Integer group_faculty_university);
}
