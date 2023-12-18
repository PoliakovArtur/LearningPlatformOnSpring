package org.example.facade;

import org.example.dto.StudentDTO;

import java.util.List;

public interface StudentFacade {

    void save(StudentDTO dto);

    void update(Long id, StudentDTO studentDTO);

    StudentDTO findById(Long id);

    List<StudentDTO> findAll();

    void deleteById(Long id);
}
