package controllers;

import org.example.dto.MessageDTO;
import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.facade.CourseServiceAdapterToMapper;
import org.example.controllers.CourseController;
import org.example.controllers.GlobalExceptionHandler;
import org.example.model.Course;
import org.example.model.CourseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static controllers.JsonStringConverter.asJsonString;
import static entity_factory.EntitiesForTests.FIXED_DATE;
import static entity_factory.EntitiesForTests.ID;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CourseControllerTest {

    CourseController controller;
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    private CourseServiceAdapterToMapper facade;

    private TeacherDTO teacherDTO = new TeacherDTO(ID, "Вареньев Алишер", 30000L, 30);
    private StudentDTO studentDTO = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);

    private CourseIncomingDTO incomingDTO = new CourseIncomingDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", ID,100000L);
    private CourseOutgoingDTO outgoingDTO = new CourseOutgoingDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,  "Программирование на Java-Script", teacherDTO, List.of(studentDTO),100000L);


    @BeforeEach
    void setUp() {
        facade = mock(CourseServiceAdapterToMapper.class);
        controller = new CourseController(facade);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller, globalExceptionHandler).build();
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(facade.findAll()).thenReturn(List.of(outgoingDTO));
        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(List.of(outgoingDTO)))
                );
    }

    @Test
    void getAll_shouldReturn404() throws Exception {
        String message = "В данный момент нет ни одного сохраненного учителя";
        when(facade.findAll()).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/courses"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getAll_shouldReturn500() throws Exception {
        String message = "Сбой в работе сервера";
        when(facade.findAll()).thenThrow(InternalError.class);
        mockMvc.perform(get("/courses")
                        .content(asJsonString(List.of(outgoingDTO))))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(facade.findById(ID)).thenReturn(outgoingDTO);
        mockMvc.perform(get("/courses/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(outgoingDTO))
                );
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        String message = String.format("Курс c id {%s} не найден", ID);
        when(facade.findById(ID)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/courses/1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn404() throws Exception {
        String message = String.format("Курс с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).deleteById(ID);
        mockMvc.perform(delete("/courses/1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn200() throws Exception {
        String message = String.format("Курс с id {%s} успешно удален", ID);
        mockMvc.perform(delete("/courses/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_shouldReturn200() throws Exception {
        String message = "Курс сохранен";
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).save(incomingDTO);
    }

    @Test
    void save_shouldReturn400() throws Exception {
        String message = "Недостаточно данных для создания сущности";
        doThrow(new BadRequestException(message)).when(facade).save(incomingDTO);
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn200() throws Exception {
        String message = String.format("Данные о курсе c id {%d} обновлены", ID);
        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).update(ID, incomingDTO);
    }

    @Test
    void update_shouldReturn404() throws Exception {
        String message = String.format("Курс     с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).update(ID, incomingDTO);
        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn400() throws Exception {
        String message = "Должно быть установлено хотя бы одно поле для обновления сущности";
        doThrow(new BadRequestException(message)).when(facade).update(ID, new CourseIncomingDTO());
        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CourseIncomingDTO())))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }
}
