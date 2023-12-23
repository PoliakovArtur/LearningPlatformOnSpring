package adapter;

import org.example.dto.TeacherDTO;
import org.example.facade.impl.TeacherServiceAdapterToMapperImpl;
import org.example.mappers.TeacherMapper;
import org.example.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesForTests.ID;
import static entity_factory.EntitiesForTests.TEACHER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherServiceAdapterToMapperTest {

    private TeacherMapper mapper;
    private TeacherService service;
    private TeacherServiceAdapterToMapperImpl adapter;
    private TeacherDTO expectedDto = new TeacherDTO(ID, "Вареньев Алишер", 30000L, 30);

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(TeacherMapper.class);
        service = mock(TeacherService.class);
        this.adapter = new TeacherServiceAdapterToMapperImpl(service, mapper);
    }

    @Test
    void findById_shouldCorrectMapTeacher() {
        when(service.findById(ID)).thenReturn(TEACHER);
        TeacherDTO actualDto = adapter.findById(ID);
        assertEquals(actualDto, expectedDto);
    }

    @Test
    void findAll_shouldCorrectMapTeachers() {
        List<TeacherDTO> expectedList = List.of(expectedDto);
        when(service.findAll()).thenReturn(List.of(TEACHER));
        List<TeacherDTO> actualList = adapter.findAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteById_shouldCallDeleteByIdInService() {
        adapter.deleteById(ID);
        verify(service).deleteById(ID);
    }

    @Test
    void save_shouldCorrectMapIncomingDto() {
        adapter.save(expectedDto);
        verify(service).save(TEACHER);
    }

    @Test
    void update_shouldCorrectMapIncomingDto() {
        adapter.update(ID, expectedDto);
        verify(service).update(TEACHER);
    }
}
