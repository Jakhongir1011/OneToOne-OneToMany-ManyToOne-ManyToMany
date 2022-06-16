package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Integer> {

    boolean existsGroupById(Integer id);

    // 1.Jpa orqali join qiladi

    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);

    // 2.
    @Query("select gr from gropus gr where gr.faculty.university.id=:universityId")
    List<Group> getGroupByUniversity(Integer universityId);

    // 3. Native query
    @Query(value = "select * from gropus g join faculty f on f.id = g.faculty_id join university u on u.id = f.university_id where u.id=:universityId", nativeQuery = true)
   List<Group> getGroupsByUniversityIdNative(Integer universityId);

    // ( 1 = 2 = 3 ) uchchalasi ham joi bilan ishlaydi
}
