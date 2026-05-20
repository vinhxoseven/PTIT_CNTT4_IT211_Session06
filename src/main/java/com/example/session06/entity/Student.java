package com.example.session06.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private Double gpa;

    // Constructor không tham số
    public Student() {}

    // Constructor đầy đủ tham số
    public Student(Long id, String fullName, String email, Double gpa) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gpa = gpa;
    }

    // Getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Double getGpa() { return gpa; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}