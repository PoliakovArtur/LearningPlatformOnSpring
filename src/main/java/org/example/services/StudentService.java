package org.example.services;

import org.example.model.Student;

import java.util.List;

public interface StudentService {

    void save(Student student);

    void update(Student student);

    Student findById(Long id);

    List<Student> findAll();

    void deleteById(Long id);
}
