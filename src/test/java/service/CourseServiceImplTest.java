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
import static entity_factory.EntitiesForTests.FULL_COURSE;
import static entity_factory.EntitiesForTests.NOT_FULL_COURSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    private CourseRepository repository;
    private CourseServiceImpl courseServiceImpl;
    private TeacherService teacherService;
    private final static Long TEST_ID = 1L;


    @BeforeEach
    void setUp() {
        repository = mock(CourseRepository.class);
        teacherService = mock(TeacherService.class);
        courseServiceImpl = new CourseServiceImpl(repository, teacherService);
    }

    @Test
    void save_shouldSaveCourse() {
        courseServiceImpl.save(FULL_COURSE);
        verify(repository).save(FULL_COURSE);
    }

    @Test
    void save_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> courseServiceImpl.save(EMPTY_COURSE));
        assertThrows(BadRequestException.class,() -> courseServiceImpl.save(NOT_FULL_COURSE));
    }

    @Test
    void save_shouldNotFoundTeacher() {
        when(teacherService.findById(TEST_ID)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.save(FULL_COURSE));
    }


    @Test
    void findAll_shouldFindCourses() {
        List<Course> expected = List.of(FULL_COURSE);
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
        when(repository.findById(TEST_ID)).thenReturn(Optional.of(FULL_COURSE));
        Course actual = courseServiceImpl.findById(TEST_ID);
        assertEquals(FULL_COURSE, actual);
    }

    @Test
    void findById_shouldThrowNotFoundException() {
        when(repository.findById(TEST_ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> courseServiceImpl.findById(TEST_ID));
    }

    @Test
    void deleteById_shouldDeleteCourse() {
        when(repository.deleteById(TEST_ID)).thenReturn(true);
        courseServiceImpl.deleteById(TEST_ID);
        verify(repository).deleteById(TEST_ID);
    }

    @Test
    void deleteById_shouldThrowNotFoundException() {
        when(repository.deleteById(TEST_ID)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.deleteById(TEST_ID));
    }

    @Test
    void updateById_shouldUpdateCourses() {
        when(repository.update(FULL_COURSE)).thenReturn(true);
        courseServiceImpl.update(FULL_COURSE);
        verify(repository).update(FULL_COURSE);
    }

    @Test
    void updateById_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> courseServiceImpl.update(EMPTY_COURSE));
    }

    @Test
    void updateById_shouldNotFoundTeacher() {
        when(teacherService.findById(TEST_ID)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.update(FULL_COURSE));
    }

    @Test
    void updateById_shouldNotFoundException() {
        when(repository.update(FULL_COURSE)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> courseServiceImpl.update(FULL_COURSE));
    }
}