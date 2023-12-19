package controllers;

import org.example.controllers.GlobalExceptionHandler;
import org.example.controllers.SubscriptionController;
import org.example.dto.MessageDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.facade.SubscriptionFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static controllers.JsonStringConverter.asJsonString;
import static entity_factory.EntitiesDtoForTests.INVALID_SUBSCRIPTION_INCOMING_DTO;
import static entity_factory.EntitiesDtoForTests.SUBSCRIPTION_INCOMING_DTO;
import static entity_factory.EntitiesDtoForTests.SUBSCRIPTION_OUT_GOING_DTO;
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

    private SubscriptionFacade facade;

    @BeforeEach
    void setUp() {
        facade = mock(SubscriptionFacade.class);
        controller = new SubscriptionController(facade);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(controller, globalExceptionHandler).build();
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(facade.findAll()).thenReturn(List.of(SUBSCRIPTION_OUT_GOING_DTO));
        mockMvc.perform(get("/subscriptions/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(List.of(SUBSCRIPTION_OUT_GOING_DTO)))
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
        when(facade.findById(INVALID_SUBSCRIPTION_INCOMING_DTO)).thenReturn(SUBSCRIPTION_OUT_GOING_DTO);
        mockMvc.perform(get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(INVALID_SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(SUBSCRIPTION_OUT_GOING_DTO))
                );
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        String message = String.format("Подписка c id {%s} не найдена", VALID_SUBSCRIPTION_ID);
        when(facade.findById(INVALID_SUBSCRIPTION_INCOMING_DTO)).thenThrow(new NotFoundException(message));
        mockMvc.perform(get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(INVALID_SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void deleteById_shouldReturn404() throws Exception {
        String message = String.format("Подписка с id {%s} не найдена", VALID_SUBSCRIPTION_ID);
        doThrow(new NotFoundException(message)).when(facade).deleteById(INVALID_SUBSCRIPTION_INCOMING_DTO);
        mockMvc.perform(delete("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(INVALID_SUBSCRIPTION_INCOMING_DTO)))
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
                        .content(asJsonString(SUBSCRIPTION_INCOMING_DTO)))
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
                        .content(asJsonString(SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).save(SUBSCRIPTION_INCOMING_DTO);
    }

    @Test
    void save_shouldReturn400() throws Exception {
        String message = "Неверно передан id";
        doThrow(new BadRequestException(message)).when(facade).save(INVALID_SUBSCRIPTION_INCOMING_DTO);
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(INVALID_SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_tryToSaveExistsSubscription() throws Exception {
        String message = "Такая подписка уже есть";
        doThrow(new BadRequestException(message)).when(facade).save(SUBSCRIPTION_INCOMING_DTO);
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

    @Test
    void save_shouldReturn404() throws Exception {
        String message = String.format("Студент с id %s не найден", ID);
        doThrow(new NotFoundException(message)).when(facade).save(SUBSCRIPTION_INCOMING_DTO);
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(SUBSCRIPTION_INCOMING_DTO)))
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
                        .content(asJsonString(SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isOk(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
        verify(facade).update(SUBSCRIPTION_INCOMING_DTO);
    }

    @Test
    void update_shouldReturn404() throws Exception {
        String message = String.format("Подписка с id {%s} не найдена", VALID_SUBSCRIPTION_ID);
        doThrow(new NotFoundException(message)).when(facade).update(SUBSCRIPTION_INCOMING_DTO);
        mockMvc.perform(put("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(SUBSCRIPTION_INCOMING_DTO)))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(asJsonString(new MessageDTO(message)))
                );
    }

}
