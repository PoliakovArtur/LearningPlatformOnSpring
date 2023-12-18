package org.example.facade;

import org.example.dto.TeacherDTO;
import java.util.List;

public interface TeacherFacade {
    void save(TeacherDTO dto);

    void update(Long id, TeacherDTO teacherDTO);

    TeacherDTO findById(Long id);

    List<TeacherDTO> findAll();

    void deleteById(Long id);
}
