package controllers;

import org.example.dto.MessageDTO;
import org.example.dto.StudentDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.facade.StudentServiceAdapterToMapper;
import org.example.controllers.GlobalExceptionHandler;
import org.example.controllers.StudentController;
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

class StudentControllerTest {

    StudentController controller;

    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    private StudentServiceAdapterToMapper facade;

    private StudentDTO studentDTO = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);

    @BeforeEach
    void setUp() {
        facade = mock(StudentServiceAdapterToMapper.class);
        controller = new StudentController(facade);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller, globalExceptionHandler).build();
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(facade.findAll()).thenReturn(List.of(studentDTO));
        mockMvc.perform(get("/students"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(List.of(studentDTO)))
                );
    }

    @Test
    void getAll_shouldReturn404() throws Exception {
        String message = "В данный момент нет ни одного сохраненного студента";
        when(facade.findAll()).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/students"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getAll_shouldReturn500() throws Exception {
        String message = "Сбой в работе сервера";
        when(facade.findAll()).thenThrow(InternalError.class);
        mockMvc.perform(get("/students"))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(facade.findById(ID)).thenReturn(studentDTO);
        mockMvc.perform(get("/students/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(studentDTO))
                );
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        String message = String.format("Студент c id {%s} не найден", ID);
        when(facade.findById(ID)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/students/1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn404() throws Exception {
        String message = String.format("Студент с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).deleteById(ID);
        mockMvc.perform(delete("/students/1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn200() throws Exception {
        String message = String.format("Студент с id {%s} успешно удален", ID);
        mockMvc.perform(delete("/students/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_shouldReturn200() throws Exception {
        String message = "Студент сохранен";
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).save(studentDTO);
    }

    @Test
    void save_shouldReturn400() throws Exception {
        String message = "Недостаточно данных для создания сущности";
        doThrow(new BadRequestException(message)).when(facade).save(studentDTO);
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn200() throws Exception {
        String message = String.format("Данные о студенте c id {%d} обновлены", ID);
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).update(ID, studentDTO);
    }

    @Test
    void update_shouldReturn404() throws Exception {
        String message = String.format("Студент с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).update(ID, studentDTO);
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn400() throws Exception {
        String message = "Должно быть установлено хотя бы одно поле для обновления сущности";
        doThrow(new BadRequestException(message)).when(facade).update(ID, new StudentDTO());
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new StudentDTO())))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }
}
