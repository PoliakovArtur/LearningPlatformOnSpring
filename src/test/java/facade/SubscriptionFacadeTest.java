package facade;

import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.facade.impl.SubscriptionFacadeImpl;
import org.example.mappers.SubscriptionKeyMapper;
import org.example.mappers.SubscriptionMapper;
import org.example.services.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesDtoForTests.INVALID_SUBSCRIPTION_INCOMING_DTO;
import static entity_factory.EntitiesDtoForTests.SUBSCRIPTION_INCOMING_DTO;
import static entity_factory.EntitiesDtoForTests.SUBSCRIPTION_OUT_GOING_DTO;
import static entity_factory.EntitiesForTests.SUBSCRIPTION;
import static entity_factory.EntitiesForTests.VALID_SUBSCRIPTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionFacadeTest {

    private SubscriptionFacadeImpl facade;
    private SubscriptionService service;
    private SubscriptionMapper entityMapper;
    private SubscriptionKeyMapper idMapper;

    @BeforeEach
    void setUp() {
        service = mock(SubscriptionService.class);
        entityMapper = Mappers.getMapper(SubscriptionMapper.class);
        idMapper = Mappers.getMapper(SubscriptionKeyMapper.class);
        facade = new SubscriptionFacadeImpl(service, entityMapper, idMapper);
    }

    @Test
    void save_shouldCorrectMapId() {
        facade.save(SUBSCRIPTION_INCOMING_DTO);
        verify(service).save(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void update_shouldCorrectMapId() {
        facade.update(SUBSCRIPTION_INCOMING_DTO);
        verify(service).update(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void delete_shouldCorrectMapId() {
        facade.deleteById(SUBSCRIPTION_INCOMING_DTO);
        verify(service).deleteById(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void findById_shouldCorrectMapOutgoingDto() {
        when(service.findById(VALID_SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);
        SubscriptionOutGoingDTO actual = facade.findById(SUBSCRIPTION_INCOMING_DTO);
        assertEquals(SUBSCRIPTION_OUT_GOING_DTO, actual);
    }

    @Test
    void findByAll_shouldCorrectMapOutgoingDto() {
        when(service.findAll()).thenReturn(List.of(SUBSCRIPTION));
        List<SubscriptionOutGoingDTO> actual = facade.findAll();
        assertEquals(List.of(SUBSCRIPTION_OUT_GOING_DTO), actual);
    }
}
