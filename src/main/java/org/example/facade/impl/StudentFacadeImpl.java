package org.example.facade.impl;

import org.example.dto.StudentDTO;
import org.example.facade.StudentFacade;
import org.example.mappers.StudentMapper;
import org.example.model.Student;
import org.example.services.StudentService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentFacadeImpl implements StudentFacade {

    private final StudentMapper mapper;
    private final StudentService service;

    public StudentFacadeImpl(StudentMapper mapper, StudentService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public void save(StudentDTO dto) {
        Student student = mapper.fromDTO(dto);
        service.save(student);
    }

    @Override
    public void update(Long id, StudentDTO studentDTO) {
        Student student = mapper.fromDTO(studentDTO);
        student.setId(id);
        service.update(student);
    }

    @Override
    public StudentDTO findById(Long id) {
        return mapper.toDTO(service.findById(id));
    }

    @Override
    public List<StudentDTO> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
