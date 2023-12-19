package org.example.services.impl;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Course;
import org.example.model.Teacher;
import org.example.repositories.CourseRepository;
import org.example.services.CourseService;
import org.example.services.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    public CourseServiceImpl(CourseRepository courseRepository, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
    }

    @Override
    public void save(Course course) {
        if(course.getName() == null
                || course.getType() == null
                || course.getDescription() == null
                || course.getPrice() == null
                || course.getTeacher() == null)
            throw new BadRequestException("Недостаточно данных для создания сущности");
        Long teacherId = course.getTeacher().getId();
        Teacher teacher = teacherService.findById(teacherId);
        course.setTeacher(teacher);
        courseRepository.save(course);
    }

    @Override
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

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Курс c id {%s} не найден", id)));
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()) throw new NotFoundException("В данный момент нет ни одного сохраненного курса");
        return courses;
    }

    @Override
    public void deleteById(Long id) {
        if(!courseRepository.deleteById(id))
            throw new NotFoundException(String.format("Курс c id {%s} не найден", id));
    }
}
