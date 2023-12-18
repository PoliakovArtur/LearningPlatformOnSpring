package controllers;

import org.example.dto.MessageDTO;
import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.facade.CourseFacade;
import org.example.сontrollers.CourseController;
import org.example.сontrollers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static controllers.JsonStringConverter.asJsonString;
import static entity_factory.EntitiesDtoForTests.COURSE_INCOMING_DTO;
import static entity_factory.EntitiesDtoForTests.COURSE_OUTGOING_DTO;
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

public class CourseControllerTest {

    CourseController controller;
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    private CourseFacade facade;

    @BeforeEach
    void setUp() {
        facade = mock(CourseFacade.class);
        controller = new CourseController(facade);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller, globalExceptionHandler).build();
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(facade.findAll()).thenReturn(List.of(COURSE_OUTGOING_DTO));
        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(List.of(COURSE_OUTGOING_DTO)))
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
                        .content(asJsonString(List.of(COURSE_OUTGOING_DTO))))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(facade.findById(ID)).thenReturn(COURSE_OUTGOING_DTO);
        mockMvc.perform(get("/courses/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(COURSE_OUTGOING_DTO))
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
                        .content(asJsonString(COURSE_INCOMING_DTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).save(COURSE_INCOMING_DTO);
    }

    @Test
    void save_shouldReturn400() throws Exception {
        String message = "Недостаточно данных для создания сущности";
        doThrow(new BadRequestException(message)).when(facade).save(COURSE_INCOMING_DTO);
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(COURSE_INCOMING_DTO)))
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
                        .content(asJsonString(COURSE_INCOMING_DTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).update(ID, COURSE_INCOMING_DTO);
    }

    @Test
    void update_shouldReturn404() throws Exception {
        String message = String.format("Курс     с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).update(ID, COURSE_INCOMING_DTO);
        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(COURSE_INCOMING_DTO)))
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
