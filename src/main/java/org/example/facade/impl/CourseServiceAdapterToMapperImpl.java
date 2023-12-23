package org.example.facade.impl;

import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.facade.CourseServiceAdapterToMapper;
import org.example.mappers.CourseMapper;
import org.example.model.Course;
import org.example.model.Teacher;
import org.example.services.CourseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseServiceAdapterToMapperImpl implements CourseServiceAdapterToMapper {

    private final CourseMapper mapper;
    private final CourseService service;

    public CourseServiceAdapterToMapperImpl(CourseMapper mapper, CourseService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public void save(CourseIncomingDTO incomingDTO) {
        Course course = mapper.fromDTO(incomingDTO);
        Long teacherId = incomingDTO.getTeacherId();
        if(teacherId != null) {
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            course.setTeacher(teacher);
        }
        service.save(course);
    }

    @Override
    public void update(Long id, CourseIncomingDTO incomingDTO) {
        Course course = mapper.fromDTO(incomingDTO);
        course.setId(id);
        Long teacherId = incomingDTO.getTeacherId();
        if(teacherId != null) {
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            course.setTeacher(teacher);
        }
        service.update(course);
    }

    @Override
    public CourseOutgoingDTO findById(Long id) {
        return mapper.toDTO(service.findById(id));
    }

    @Override
    public List<CourseOutgoingDTO> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
