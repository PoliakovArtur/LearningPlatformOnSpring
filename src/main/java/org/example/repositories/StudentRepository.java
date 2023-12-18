package org.example.repositories;

import org.example.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    void save(Student student);

    boolean update(Student student);

    Optional<Student> findById(Long id);

    List<Student> findAll();

    boolean deleteById(Long id);
}
