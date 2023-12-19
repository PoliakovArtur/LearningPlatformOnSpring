package facade;

import org.example.dto.StudentDTO;
import org.example.facade.impl.StudentFacadeImpl;
import org.example.mappers.StudentMapper;
import org.example.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesDtoForTests.STUDENT_DTO;
import static entity_factory.EntitiesDtoForTests.STUDENT_DTO_WITHOUT_ID;
import static entity_factory.EntitiesForTests.STUDENT;
import static entity_factory.EntitiesForTests.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentFacadeTest {

    private StudentMapper studentMapper;
    private StudentService studentService;
    private StudentFacadeImpl studentFacade;

    @BeforeEach
    void setUp() {
        this.studentMapper = Mappers.getMapper(StudentMapper.class);
        studentService = mock(StudentService.class);
        this.studentFacade = new StudentFacadeImpl(studentMapper, studentService);
    }

    @Test
    void findById_shouldCorrectMapStudent() {
        when(studentService.findById(ID)).thenReturn(STUDENT);
        StudentDTO actual = studentFacade.findById(ID);
        assertEquals(actual, STUDENT_DTO);
    }

    @Test
    void findAll_shouldCorrectMapStudents() {
        List<StudentDTO> expected = List.of(STUDENT_DTO);
        when(studentService.findAll()).thenReturn(List.of(STUDENT));
        List<StudentDTO> actual = studentFacade.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_shouldCallDeleteByIdInService() {
        studentFacade.deleteById(ID);
        verify(studentService).deleteById(ID);
    }

    @Test
    void save_shouldCorrectMapIncomingDto() {
        studentFacade.save(STUDENT_DTO);
        verify(studentService).save(STUDENT);
    }

    @Test
    void update_shouldCorrectMapIncomingDto() {
        studentFacade.update(ID, STUDENT_DTO_WITHOUT_ID);
        verify(studentService).update(STUDENT);
    }
}