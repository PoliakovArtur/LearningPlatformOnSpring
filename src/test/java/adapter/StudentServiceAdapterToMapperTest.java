package adapter;

import org.example.dto.StudentDTO;
import org.example.facade.impl.StudentServiceAdapterToMapperImpl;
import org.example.mappers.StudentMapper;
import org.example.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesForTests.FIXED_DATE;
import static entity_factory.EntitiesForTests.STUDENT;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceAdapterToMapperTest {

    private StudentMapper mapper;
    private StudentService service;
    private StudentServiceAdapterToMapperImpl adapter;
    private StudentDTO expectedDto = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(StudentMapper.class);
        service = mock(StudentService.class);
        this.adapter = new StudentServiceAdapterToMapperImpl(mapper, service);
    }

    @Test
    void findById_shouldCorrectMapStudent() {
        when(service.findById(ID)).thenReturn(STUDENT);
        StudentDTO actual = adapter.findById(ID);
        assertEquals(actual, expectedDto);
    }

    @Test
    void findAll_shouldCorrectMapStudents() {
        List<StudentDTO> expected = List.of(expectedDto);
        when(service.findAll()).thenReturn(List.of(STUDENT));
        List<StudentDTO> actual = adapter.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_shouldCallDeleteByIdInService() {
        adapter.deleteById(ID);
        verify(service).deleteById(ID);
    }

    @Test
    void save_shouldCorrectMapIncomingDto() {
        adapter.save(expectedDto);
        verify(service).save(STUDENT);
    }

    @Test
    void update_shouldCorrectMapIncomingDto() {
        adapter.update(ID, expectedDto);
        verify(service).update(STUDENT);
    }
}