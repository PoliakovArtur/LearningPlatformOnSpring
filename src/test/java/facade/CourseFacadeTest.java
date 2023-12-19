package facade;

import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.facade.impl.CourseFacadeImpl;
import org.example.mappers.CourseMapper;
import org.example.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesDtoForTests.COURSE_INCOMING_DTO;
import static entity_factory.EntitiesDtoForTests.COURSE_INCOMING_DTO_WITHOUT_ID;
import static entity_factory.EntitiesDtoForTests.COURSE_OUTGOING_DTO;
import static entity_factory.EntitiesForTests.COURSE;
import static entity_factory.EntitiesForTests.FULL_COURSE_WITH_ID_TEACHER;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseFacadeTest {

    private CourseMapper mapper;
    private CourseService service;
    private CourseFacadeImpl facade;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CourseMapper.class);
        service = mock(CourseService.class);
        facade = new CourseFacadeImpl(mapper, service);
    }

    @Test
    void save_shouldCorrectMapCourse() {
        facade.save(COURSE_INCOMING_DTO);
        verify(service).save(FULL_COURSE_WITH_ID_TEACHER);
    }

    @Test
    void findById_shouldCorrectMapCourseOutgoingDto() {
        when(service.findById(ID)).thenReturn(COURSE);
        CourseOutgoingDTO actual = facade.findById(ID);
        assertEquals(COURSE_OUTGOING_DTO, actual);
    }

    @Test
    void findAll_shouldCorrectMapCourseOutgoingDto() {
        when(service.findAll()).thenReturn(List.of(COURSE));
        List<CourseOutgoingDTO> actual = facade.findAll();
        assertEquals(List.of(COURSE_OUTGOING_DTO), actual);
    }

    @Test
    void deleteById_shouldCallDeleteByIdInService() {
        facade.deleteById(ID);
        verify(service).deleteById(ID);
    }

    @Test
    void update_shouldCorrectMapIncomingDto() {
        facade.update(ID, COURSE_INCOMING_DTO_WITHOUT_ID);
        verify(service).update(FULL_COURSE_WITH_ID_TEACHER);
    }
}
