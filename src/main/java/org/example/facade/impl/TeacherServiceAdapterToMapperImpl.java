package org.example.facade.impl;

import org.example.dto.TeacherDTO;
import org.example.facade.TeacherServiceAdapterToMapper;
import org.example.mappers.TeacherMapper;
import org.example.model.Teacher;
import org.example.services.TeacherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherServiceAdapterToMapperImpl implements TeacherServiceAdapterToMapper {
    private final TeacherService service;
    private final TeacherMapper mapper;

    public TeacherServiceAdapterToMapperImpl(TeacherService service, TeacherMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public void save(TeacherDTO dto) {
        Teacher teacher = mapper.fromDTO(dto);
        service.save(teacher);
    }

    @Override
    public void update(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = mapper.fromDTO(teacherDTO);
        teacher.setId(id);
        service.update(teacher);
    }

    @Override
    public TeacherDTO findById(Long id) {
        return mapper.toDTO(service.findById(id));
    }

    @Override
    public List<TeacherDTO> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
