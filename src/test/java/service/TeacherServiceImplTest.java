package service;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Teacher;
import org.example.repositories.TeacherRepository;
import org.example.services.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static entity_factory.EntitiesForTests.EMPTY_TEACHER;
import static entity_factory.EntitiesForTests.FULL_TEACHER;
import static entity_factory.EntitiesForTests.NOT_FULL_TEACHER;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherServiceImplTest {

    private TeacherRepository repository;
    private TeacherServiceImpl service;


    @BeforeEach
    void setUp() {
        repository = mock(TeacherRepository.class);
        service = new TeacherServiceImpl(repository);
    }

    @Test
    void save_shouldSaveTeacher() {
        service.save(FULL_TEACHER);
        verify(repository).save(FULL_TEACHER);
    }

    @Test
    void save_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> service.save(EMPTY_TEACHER));
        assertThrows(BadRequestException.class,() -> service.save(NOT_FULL_TEACHER));
    }


    @Test
    void findAll_shouldFindTeachers() {
        List<Teacher> expected = List.of(FULL_TEACHER);
        when(repository.findAll()).thenReturn(expected);
        List<Teacher> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldThrowNotFoundException() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void findById_shouldFindTeacher() {
        when(repository.findById(ID)).thenReturn(Optional.of(FULL_TEACHER));
        Teacher actual = service.findById(ID);
        assertEquals(FULL_TEACHER, actual);
    }

    @Test
    void findById_shouldThrowNotFoundException() {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.findById(ID));
    }

    @Test
    void deleteById_shouldDeleteTeacher() {
        when(repository.deleteById(ID)).thenReturn(true);
        service.deleteById(ID);
        verify(repository).deleteById(ID);
    }

    @Test
    void deleteById_shouldThrowNotFoundException() {
        when(repository.deleteById(ID)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> service.deleteById(ID));
    }

    @Test
    void updateById_shouldUpdateTeachers() {
        when(repository.update(FULL_TEACHER)).thenReturn(true);
        service.update(FULL_TEACHER);
        verify(repository).update(FULL_TEACHER);
    }

    @Test
    void updateById_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> service.update(EMPTY_TEACHER));
    }

    @Test
    void updateById_shouldNotFoundException() {
        when(repository.update(FULL_TEACHER)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> service.update(FULL_TEACHER));
    }

}