package controllers;

import org.example.controllers.GlobalExceptionHandler;
import org.example.controllers.SubscriptionController;
import org.example.dto.MessageDTO;
import org.example.dto.StudentDTO;
import org.example.dto.subscription_dto.CourseForSubscriptionDTO;
import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.facade.SubscriptionServiceAdapterToMapper;
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
import static entity_factory.EntitiesForTests.VALID_SUBSCRIPTION_ID;
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

public class SubscriptionControllerTest {

    SubscriptionController controller;
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    private SubscriptionServiceAdapterToMapper facade;

    private SubscriptionIncomingDTO incomingDTO = new SubscriptionIncomingDTO(ID, ID);
    private StudentDTO studentDTO = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);
    private CourseForSubscriptionDTO courseDto = new CourseForSubscriptionDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,100000L);
    private SubscriptionOutGoingDTO outGoingDTO = new SubscriptionOutGoingDTO(FIXED_DATE, studentDTO, courseDto);

    @BeforeEach
    void setUp() {
        facade = mock(SubscriptionServiceAdapterToMapper.class);
        controller = new SubscriptionController(facade);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller, globalExceptionHandler).build();
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(facade.findAll()).thenReturn(List.of(outGoingDTO));
        mockMvc.perform(get("/subscriptions/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(List.of(outGoingDTO)))
                );
    }

    @Test
    void getAll_shouldReturn404() throws Exception {
        String message = "На данный момент нет ни одной подписки";
        when(facade.findAll()).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/subscriptions/all"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getAll_shouldReturn500() throws Exception {
        String message = "Сбой в работе сервера";
        when(facade.findAll()).thenThrow(InternalError.class);
        mockMvc.perform(get("/subscriptions/all"))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(facade.findById(incomingDTO)).thenReturn(outGoingDTO);
        mockMvc.perform(get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(outGoingDTO))
                );
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        String message = String.format("Подписка c id {%s} не найдена", VALID_SUBSCRIPTION_ID);
        when(facade.findById(incomingDTO)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn404() throws Exception {
        String message = String.format("Подписка с id {%s} не найдена", VALID_SUBSCRIPTION_ID);
        doThrow(new NotFoundException(message)).when(facade).deleteById(incomingDTO);
        mockMvc.perform(delete("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn200() throws Exception {
        String message = "Подписка удалена";
        mockMvc.perform(delete("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_shouldReturn200() throws Exception {
        String message = "Подписка добавлена";
        mockMvc.perform(post("/subscriptions")
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
        String message = "Неверно передан id";
        doThrow(new BadRequestException(message)).when(facade).save(incomingDTO);
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_tryToSaveExistsSubscription() throws Exception {
        String message = "Такая подписка уже есть";
        doThrow(new BadRequestException(message)).when(facade).save(incomingDTO);
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_shouldReturn404() throws Exception {
        String message = String.format("Студент с id %s не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).save(incomingDTO);
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void update_shouldReturn200() throws Exception {
        String message = "Подписка обновлена";
        mockMvc.perform(put("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).update(incomingDTO);
    }

    @Test
    void update_shouldReturn404() throws Exception {
        String message = String.format("Подписка с id {%s} не найдена", VALID_SUBSCRIPTION_ID);
        doThrow(new NotFoundException(message)).when(facade).update(incomingDTO);
        mockMvc.perform(put("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

}
