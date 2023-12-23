package controllers;

import org.example.dto.MessageDTO;
import org.example.dto.TeacherDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.facade.TeacherServiceAdapterToMapper;
import org.example.controllers.GlobalExceptionHandler;
import org.example.controllers.TeacherController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static controllers.JsonStringConverter.asJsonString;
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

class TeacherControllerTest {

    TeacherController controller;
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    private TeacherServiceAdapterToMapper facade;

    private TeacherDTO teacherDTO = new TeacherDTO(ID, "Вареньев Алишер", 30000L, 30);

    @BeforeEach
    void setUp() {
        facade = mock(TeacherServiceAdapterToMapper.class);
        controller = new TeacherController(facade);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller, globalExceptionHandler).build();
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(facade.findAll()).thenReturn(List.of(teacherDTO));
        mockMvc.perform(get("/teachers"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(List.of(teacherDTO)))
                );
    }

    @Test
    void getAll_shouldReturn404() throws Exception {
        String message = "В данный момент нет ни одного сохраненного учителя";
        when(facade.findAll()).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/teachers"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getAll_shouldReturn500() throws Exception {
        String message = "Сбой в работе сервера";
        when(facade.findAll()).thenThrow(InternalError.class);
        mockMvc.perform(get("/teachers")
                        .content(asJsonString(List.of(teacherDTO))))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(facade.findById(ID)).thenReturn(teacherDTO);
        mockMvc.perform(get("/teachers/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(teacherDTO))
                );
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        String message = String.format("Учитель c id {%s} не найден", ID);
        when(facade.findById(ID)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/teachers/1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn404() throws Exception {
        String message = String.format("Учитель с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).deleteById(ID);
        mockMvc.perform(delete("/teachers/1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn200() throws Exception {
        String message = String.format("Учитель с id {%s} успешно удален", ID);
        mockMvc.perform(delete("/teachers/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_shouldReturn200() throws Exception {
        String message = "Учитель сохранен";
        mockMvc.perform(post("/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(teacherDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).save(teacherDTO);
    }

    @Test
    void save_shouldReturn400() throws Exception {
        String message = "Недостаточно данных для создания сущности";
        doThrow(new BadRequestException(message)).when(facade).save(teacherDTO);
        mockMvc.perform(post("/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(teacherDTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn200() throws Exception {
        String message = String.format("Данные об учителе c id {%d} обновлены", ID);
        mockMvc.perform(put("/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(teacherDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).update(ID, teacherDTO);
    }

    @Test
    void update_shouldReturn404() throws Exception {
        String message = String.format("Учитель с id {%s} не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).update(ID, teacherDTO);
        mockMvc.perform(put("/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(teacherDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn400() throws Exception {
        String message = "Должно быть установлено хотя бы одно поле для обновления сущности";
        doThrow(new BadRequestException(message)).when(facade).update(ID, new TeacherDTO());
        mockMvc.perform(put("/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new TeacherDTO())))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

}
