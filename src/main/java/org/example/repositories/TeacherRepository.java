package org.example.repositories;

import org.example.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {

    void save(Teacher teacher);

    boolean update(Teacher teacher);

    Optional<Teacher> findById(Long id);

    List<Teacher> findAll();

    boolean deleteById(Long id);
}
