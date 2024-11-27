package com.selly.demo.service;

import com.selly.demo.exception.BadRequestException;
import com.selly.demo.exception.StudentNotFoundException;
import com.selly.demo.model.Student;
import com.selly.demo.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + studentId + " does not exist."));
    }

    // Add a new student
    public void addStudent(Student student) {
        // Check if email already exists
        Boolean existsEmail = studentRepository.selectExistsEmail(student.getEmail());
        if (existsEmail) {
            throw new BadRequestException("Email " + student.getEmail() + " already exists.");
        }
        studentRepository.save(student);
    }

    // Delete student by ID
    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student with id " + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);
    }
}
