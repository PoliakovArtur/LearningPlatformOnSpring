package service;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Student;
import org.example.repositories.StudentRepository;
import org.example.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static entity_factory.EntitiesForTests.EMPTY_STUDENT;
import static entity_factory.EntitiesForTests.STUDENT;
import static entity_factory.EntitiesForTests.STUDENT_WITH_NAME;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    private StudentRepository repository;
    private StudentServiceImpl service;


    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        service = new StudentServiceImpl(repository);
    }

    @Test
    void save_shouldSaveStudent() {
        service.save(STUDENT);
        verify(repository).save(STUDENT);
    }

    @Test
    void save_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> service.save(EMPTY_STUDENT));
        assertThrows(BadRequestException.class,() -> service.save(STUDENT_WITH_NAME));
    }


    @Test
    void findAll_shouldFindStudents() {
        List<Student> expected = List.of(STUDENT);
        when(repository.findAll()).thenReturn(expected);
        List<Student> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldThrowNotFoundException() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void findById_shouldFindStudent() {
        when(repository.findById(ID)).thenReturn(Optional.of(STUDENT));
        Student actual = service.findById(ID);
        assertEquals(STUDENT, actual);
    }

    @Test
    void findById_shouldThrowNotFoundException() {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.findById(ID));
    }

    @Test
    void deleteById_shouldDeleteStudent() {
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
    void updateById_shouldUpdateStudents() {
        when(repository.update(STUDENT)).thenReturn(true);
        service.update(STUDENT);
        verify(repository).update(STUDENT);
    }

    @Test
    void updateById_shouldThrowBadRequestException() {
        assertThrows(BadRequestException.class,() -> service.update(EMPTY_STUDENT));
    }

    @Test
    void updateById_shouldNotFoundException() {
        when(repository.update(STUDENT)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> service.update(STUDENT));
    }
}
