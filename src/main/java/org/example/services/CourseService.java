package org.example.services;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Course;
import org.example.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private TeacherService teacherService;

    public CourseService(CourseRepository courseRepository, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
    }

    public void save(Course course) {
        if(course.getName() == null
                || course.getType() == null
                || course.getDescription() == null
                || course.getPrice() == null
                || course.getTeacher() == null)
            throw new BadRequestException("Недостаточно данных для создания сущности");
        Long teacherId = course.getTeacher().getId();
        teacherService.findById(teacherId);
        courseRepository.save(course);
    }

    public void update(Course course) {
        if(course.getName() == null
                && course.getType() == null
                && course.getDescription() == null
                && course.getPrice() == null
                && course.getTeacher() == null)
            throw new BadRequestException("Должно быть установлено хотя бы одно поле для обновления сущности");
        if(course.getTeacher() != null) {
            Long teacherId = course.getTeacher().getId();
            teacherService.findById(teacherId);
        }
        boolean isUpdated = courseRepository.update(course);
        if(!isUpdated)
            throw new NotFoundException(String.format("Курс c id {%s} не найден", course.getId()));
    }
    
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Курс c id {%s} не найден", id)));
    }

    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()) throw new NotFoundException("В данный момент нет ни одного сохраненного курса");
        return courses;
    }

    public void deleteById(Long id) {
        if(!courseRepository.deleteById(id))
            throw new NotFoundException(String.format("Курс c id {%s} не найден", id));
    }
}
