package org.example.repositories;

import org.example.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    boolean save(Course course);

    boolean update(Course course);

    Optional<Course> findById(Long id);

    List<Course> findAll();

    boolean deleteById(Long id);
}
