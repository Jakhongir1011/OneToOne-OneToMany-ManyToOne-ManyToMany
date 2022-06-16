package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 1-> qaysi Entity bilan gaplashmoqchisan
// 2-> Class ini type ni ber
@Repository
public interface UniversityRepository extends JpaRepository<University,Integer> {
    boolean existsUniversitiesByName(String name);
}
