package com.selly.demo.repository;

import com.selly.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // JPQL (Java Persistence Query Language)
    @Query("" +
            "SELECT CASE WHEN COUNT(s) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Student s "+
            "WHERE s.email = ?1")
    Boolean selectExistsEmail(String email);
}
