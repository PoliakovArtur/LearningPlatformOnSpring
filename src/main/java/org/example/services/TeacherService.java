package org.example.services;

import org.example.model.Teacher;

import java.util.List;

public interface TeacherService {
    void save(Teacher teacher);

    void update(Teacher teacher);

    Teacher findById(Long id);

    List<Teacher> findAll();

    void deleteById(Long id);
}
