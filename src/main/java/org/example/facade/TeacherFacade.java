package org.example.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.TeacherDTO;
import org.example.mappers.TeacherMapper;
import org.example.model.Teacher;
import org.example.services.TeacherService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TeacherFacade{
    private TeacherService service;
    private TeacherMapper mapper;

    public TeacherFacade(TeacherService service, TeacherMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public void save(TeacherDTO dto) {
        Teacher teacher = mapper.fromDTO(dto);
        service.save(teacher);
    }

    public void update(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = mapper.fromDTO(teacherDTO);
        teacher.setId(id);
        service.update(teacher);
    }

    public TeacherDTO findById(Long id) {
        return mapper.toDTO(service.findById(id));
    }

    public List<TeacherDTO> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
