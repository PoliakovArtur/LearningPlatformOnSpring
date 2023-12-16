package org.example.facade;

import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.mappers.CourseMapper;
import org.example.model.Course;
import org.example.model.Teacher;
import org.example.services.CourseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseFacade {

    private CourseMapper mapper;
    private CourseService service;

    public CourseFacade(CourseMapper mapper, CourseService service) {
        this.mapper = mapper;
        this.service = service;
    }

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

    public CourseOutgoingDTO findById(Long id) {
        return mapper.toDTO(service.findById(id));
    }

    public List<CourseOutgoingDTO> findAll() {
        return mapper.toDtoList(service.findAll());
    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
