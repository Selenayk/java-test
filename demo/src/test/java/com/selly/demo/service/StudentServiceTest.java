package com.selly.demo.service;

import com.selly.demo.exception.BadRequestException;
import com.selly.demo.exception.StudentNotFoundException;
import com.selly.demo.model.Gender;
import com.selly.demo.model.Student;
import com.selly.demo.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        // when
        studentService.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canGetStudentById() {
        //given
        long id = 10;
        Student student = new Student("Test", "test@test.com", Gender.MALE);
        given(studentRepository.findById(id)).willReturn(Optional.of(student));

        // when
        Student foundStudent = studentService.getStudentById(id);

        // then
        assertThat(foundStudent).isEqualTo(student);
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void willThrowWhenStudentNotFound() {
        // given
        long id = 10;
        given(studentRepository.findById(id)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> studentService.getStudentById(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exist.");

        verify(studentRepository).findById(id);
    }

    @Test
    void canAddStudent() {
        // given
        Student student = new Student("test", "test@test.com", Gender.MALE);

        // when
        studentService.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        // Assert that the captured student is the same as the one we passed in
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student("test", "test@test.com", Gender.MALE);
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " already exists.");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudentWhenExists() {
        // given
        long id = 10;
        given(studentRepository.existsById(id)).willReturn(true);

        // when
        studentService.deleteStudent(id);

        // then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowWhenStudentToDeleteNotFound() {
        // given
        long id = 10;
        given(studentRepository.existsById(id)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> studentService.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exist.");

        verify(studentRepository, never()).deleteById(any());
    }
}