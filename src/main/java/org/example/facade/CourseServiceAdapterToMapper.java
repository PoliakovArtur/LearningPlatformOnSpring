package org.example.facade;

import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;

import java.util.List;

public interface CourseServiceAdapterToMapper {

    void save(CourseIncomingDTO incomingDTO);

    void update(Long id, CourseIncomingDTO incomingDTO);

    CourseOutgoingDTO findById(Long id);

    List<CourseOutgoingDTO> findAll();

    void deleteById(Long id);
}
