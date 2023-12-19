package facade;

import org.example.dto.TeacherDTO;
import org.example.facade.impl.TeacherFacadeImpl;
import org.example.mappers.TeacherMapper;
import org.example.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesDtoForTests.TEACHER_DTO;
import static entity_factory.EntitiesDtoForTests.TEACHER_DTO_WITHOUT_ID;
import static entity_factory.EntitiesForTests.TEACHER;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherFacadeTest {

    private TeacherMapper teacherMapper;
    private TeacherService teacherService;
    private TeacherFacadeImpl teacherFacade;

    @BeforeEach
    void setUp() {
        this.teacherMapper = Mappers.getMapper(TeacherMapper.class);
        teacherService = mock(TeacherService.class);
        this.teacherFacade = new TeacherFacadeImpl(teacherService, teacherMapper);
    }

    @Test
    void findById_shouldCorrectMapTeacher() {
        when(teacherService.findById(ID)).thenReturn(TEACHER);
        TeacherDTO actual = teacherFacade.findById(ID);
        assertEquals(actual, TEACHER_DTO);
    }

    @Test
    void findAll_shouldCorrectMapTeachers() {
        List<TeacherDTO> expected = List.of(TEACHER_DTO);
        when(teacherService.findAll()).thenReturn(List.of(TEACHER));
        List<TeacherDTO> actual = teacherFacade.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_shouldCallDeleteByIdInService() {
        teacherFacade.deleteById(ID);
        verify(teacherService).deleteById(ID);
    }

    @Test
    void save_shouldCorrectMapIncomingDto() {
        teacherFacade.save(TEACHER_DTO);
        verify(teacherService).save(TEACHER);
    }

    @Test
    void update_shouldCorrectMapIncomingDto() {
        teacherFacade.update(ID, TEACHER_DTO_WITHOUT_ID);
        verify(teacherService).update(TEACHER);
    }
}
