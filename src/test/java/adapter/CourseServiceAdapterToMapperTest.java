package adapter;

import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.facade.impl.CourseServiceAdapterToMapperImpl;
import org.example.mappers.CourseMapper;
import org.example.model.Course;
import org.example.model.CourseType;
import org.example.model.Teacher;
import org.example.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;
import static entity_factory.EntitiesForTests.COURSE;
import static entity_factory.EntitiesForTests.FIXED_DATE;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceAdapterToMapperTest {

    private CourseMapper mapper;
    private CourseService service;
    private CourseServiceAdapterToMapperImpl adapter;
    private TeacherDTO teacherDTO = new TeacherDTO(ID, "Вареньев Алишер", 30000L, 30);
    private StudentDTO studentDTO = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);

    private CourseIncomingDTO incomingDTO = new CourseIncomingDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", ID,100000L);
    private CourseOutgoingDTO outgoingDTO = new CourseOutgoingDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", teacherDTO, List.of(studentDTO),100000L);
    private Course incomingParsedCourse;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CourseMapper.class);
        service = mock(CourseService.class);
        Teacher teacherId = new Teacher();
        teacherId.setId(ID);
        adapter = new CourseServiceAdapterToMapperImpl(mapper, service);
        incomingParsedCourse = new Course(ID, "Веб-Разработчик",
                CourseType.PROGRAMMING,  "Программирование на Java-Script", teacherId,100000L);
    }

    @Test
    void save_shouldCorrectMapCourse() {
        adapter.save(incomingDTO);
        verify(service).save(incomingParsedCourse);
    }

    @Test
    void findById_shouldCorrectMapCourseOutgoingDto() {
        when(service.findById(ID)).thenReturn(COURSE);
        CourseOutgoingDTO actual = adapter.findById(ID);
        assertEquals(outgoingDTO, actual);
    }

    @Test
    void findAll_shouldCorrectMapCourseOutgoingDto() {
        when(service.findAll()).thenReturn(List.of(COURSE));
        List<CourseOutgoingDTO> actual = adapter.findAll();
        assertEquals(List.of(outgoingDTO), actual);
    }

    @Test
    void deleteById_shouldCallDeleteByIdInService() {
        adapter.deleteById(ID);
        verify(service).deleteById(ID);
    }

    @Test
    void update_shouldCorrectMapIncomingDto() {
        adapter.update(ID, incomingDTO);
        verify(service).update(incomingParsedCourse);
    }
}
