package com.example.session06.controller;


import com.example.session06.entity.Student;
import com.example.session06.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // GET all students
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    // GET student by ID
    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create new student
    @PostMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student.getFullName() == null || student.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        student.setId(null); // Đảm bảo id do database tự sinh
        Student saved = studentRepository.save(student);
        return ResponseEntity.status(201).body(saved);
    }

    // PUT update full student
    @PutMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student studentDetails) {

        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = existing.get();
        student.setFullName(studentDetails.getFullName());
        student.setEmail(studentDetails.getEmail());
        student.setGpa(studentDetails.getGpa());

        Student updated = studentRepository.save(student);
        return ResponseEntity.ok(updated);
    }

    // PATCH update partial student
    @PatchMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Student> partialUpdate(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = existing.get();

        if (updates.containsKey("fullName"))
            student.setFullName((String) updates.get("fullName"));
        if (updates.containsKey("email"))
            student.setEmail((String) updates.get("email"));
        if (updates.containsKey("gpa"))
            student.setGpa(Double.valueOf(updates.get("gpa").toString()));

        Student updated = studentRepository.save(student);
        return ResponseEntity.ok(updated);
    }

    // DELETE student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
