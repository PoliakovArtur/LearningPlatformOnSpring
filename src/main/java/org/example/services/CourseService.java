package org.example.services;

import org.example.model.Course;

import java.util.List;

public interface CourseService {

    void save(Course course);

    void update(Course course);
    
    Course findById(Long id);

    List<Course> findAll();

    void deleteById(Long id);
}
