package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    // 1.name ni Subjectdan olyapti
    // 2. aytyapmizkiy MB da shunday query bormi yani name bormi
    boolean existsByName(String name);
    List<Subject> getSubjectsById(String subjects);
}
