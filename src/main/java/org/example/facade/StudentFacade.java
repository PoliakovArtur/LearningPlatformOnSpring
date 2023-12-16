package org.example.facade;

import org.example.dto.StudentDTO;
import org.example.dto.StudentDTO;
import org.example.mappers.StudentMapper;
import org.example.model.Student;
import org.example.services.StudentService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentFacade {

    private StudentMapper mapper;
    private StudentService service;

    public StudentFacade(StudentMapper mapper, StudentService service) {
        this.mapper = mapper;
        this.service = service;
    }

    public void save(StudentDTO dto) {
        Student student = mapper.fromDTO(dto);
        service.save(student);
    }

    public void update(Long id, StudentDTO studentDTO) {
        Student student = mapper.fromDTO(studentDTO);
        student.setId(id);
        service.update(student);
    }

    public StudentDTO findById(Long id) {
        return mapper.toDTO(service.findById(id));
    }

    public List<StudentDTO> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
