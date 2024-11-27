package com.selly.demo.repository;

import com.selly.demo.model.Gender;
import com.selly.demo.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private final String testEmail = "test@test.com";
    private Student testStudent;

    @BeforeEach
    void setUp() {
        // Initialize student test data before each test method
        testStudent = new Student("Test", testEmail, Gender.MALE);
        studentRepository.save(testStudent);
    }



    @AfterEach
    void tearDown() {
        // Release test data after each test method
        studentRepository.delete(testStudent);
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        // when
        boolean exists = studentRepository.selectExistsEmail(testEmail);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExist() {
        // given
        String nonExistEmail = "nonexistemail@test.com";

        // when
        boolean exists = studentRepository.selectExistsEmail(nonExistEmail);

        // then
        assertThat(exists).isFalse();
    }
}