package service;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Course;
import org.example.repositories.CourseRepository;
import org.example.services.TeacherService;
import org.example.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static entity_factory.EntitiesForTests.EMPTY_COURSE;
import static entity_factory.EntitiesForTests.COURSE;
import static entity_factory.EntitiesForTests.ID;
import static entity_factory.EntitiesForTests.COURSE_WITH_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    private CourseRepository repository;
    private CourseServiceImpl courseServiceImpl;
    private TeacherService teacherService;
    private Course course = new Course();

    @BeforeEach
    void setUp() {
        repository = mock(CourseRepository.class);
        teacherService = mock(TeacherService.class);
        courseServiceImpl = new CourseServiceImpl(repository);
        course.setTeacher(COURSE.getTeacher());
        course.setDescription(COURSE.getDescription());
        course.setId(COURSE.getId());
        course.setName(COURSE.getName());
        course.setType(COURSE.getType());
        course.setPrice(COURSE.getPrice());
    }

    @Test
    void save_shouldSaveCourse() {
        when(repository.save(course)).thenReturn(true);
        courseServiceImpl.save(course);
        verify(repository).save(course);
    }

    @Test
    void save_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> courseServiceImpl.save(EMPTY_COURSE));
        assertThrows(BadRequestException.class,() -> courseServiceImpl.save(COURSE_WITH_NAME));
    }

    @Test
    void save_shouldNotFoundTeacher() {
        when(teacherService.findById(ID)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.save(course));
    }

    @Test
    void findAll_shouldFindCourses() {
        List<Course> expected = List.of(COURSE);
        when(repository.findAll()).thenReturn(expected);
        List<Course> actual = courseServiceImpl.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldThrowNotFoundException() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> courseServiceImpl.findAll());
    }

    @Test
    void findById_shouldFindCourse() {
        when(repository.findById(ID)).thenReturn(Optional.of(COURSE));
        Course actual = courseServiceImpl.findById(ID);
        assertEquals(COURSE, actual);
    }

    @Test
    void findById_shouldThrowNotFoundException() {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> courseServiceImpl.findById(ID));
    }

    @Test
    void deleteById_shouldDeleteCourse() {
        when(repository.deleteById(ID)).thenReturn(true);
        courseServiceImpl.deleteById(ID);
        verify(repository).deleteById(ID);
    }

    @Test
    void deleteById_shouldThrowNotFoundException() {
        when(repository.deleteById(ID)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.deleteById(ID));
    }

    @Test
    void updateById_shouldUpdateCourses() {
        when(repository.update(COURSE)).thenReturn(true);
        courseServiceImpl.update(COURSE);
        verify(repository).update(COURSE);
    }

    @Test
    void updateById_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> courseServiceImpl.update(EMPTY_COURSE));
    }

    @Test
    void updateById_shouldNotFoundTeacher() {
        when(teacherService.findById(ID)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.update(COURSE));
    }

    @Test
    void updateById_shouldNotFoundException() {
        when(repository.update(COURSE)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.update(COURSE));
    }
}