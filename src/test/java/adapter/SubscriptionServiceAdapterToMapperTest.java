package adapter;

import org.example.dto.StudentDTO;
import org.example.dto.subscription_dto.CourseForSubscriptionDTO;
import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.facade.impl.SubscriptionServiceAdapterToMapperImpl;
import org.example.mappers.SubscriptionKeyMapper;
import org.example.mappers.SubscriptionMapper;
import org.example.model.CourseType;
import org.example.services.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static entity_factory.EntitiesForTests.FIXED_DATE;
import static entity_factory.EntitiesForTests.ID;
import static entity_factory.EntitiesForTests.SUBSCRIPTION;
import static entity_factory.EntitiesForTests.VALID_SUBSCRIPTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionServiceAdapterToMapperTest {

    private SubscriptionServiceAdapterToMapperImpl facade;
    private SubscriptionService service;
    private SubscriptionMapper entityMapper;
    private SubscriptionKeyMapper idMapper;
    private SubscriptionIncomingDTO incomingDTO = new SubscriptionIncomingDTO(ID, ID);
    private StudentDTO studentDTO = new StudentDTO(ID, "Петров Александр", 30, FIXED_DATE);
    private CourseForSubscriptionDTO courseDto = new CourseForSubscriptionDTO(ID, "Веб-Разработчик",
            CourseType.PROGRAMMING,100000L);
    private SubscriptionOutGoingDTO outGoingDTO = new SubscriptionOutGoingDTO(FIXED_DATE, studentDTO, courseDto);


    @BeforeEach
    void setUp() {
        service = mock(SubscriptionService.class);
        entityMapper = Mappers.getMapper(SubscriptionMapper.class);
        idMapper = Mappers.getMapper(SubscriptionKeyMapper.class);
        facade = new SubscriptionServiceAdapterToMapperImpl(service, entityMapper, idMapper);
    }

    @Test
    void save_shouldCorrectMapId() {
        facade.save(incomingDTO);
        verify(service).save(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void update_shouldCorrectMapId() {
        facade.update(incomingDTO);
        verify(service).update(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void delete_shouldCorrectMapId() {
        facade.deleteById(incomingDTO);
        verify(service).deleteById(VALID_SUBSCRIPTION_ID);
    }

    @Test
    void findById_shouldCorrectMapOutgoingDto() {
        when(service.findById(VALID_SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);
        SubscriptionOutGoingDTO actual = facade.findById(incomingDTO);
        assertEquals(outGoingDTO, actual);
    }

    @Test
    void findByAll_shouldCorrectMapOutgoingDto() {
        when(service.findAll()).thenReturn(List.of(SUBSCRIPTION));
        List<SubscriptionOutGoingDTO> actual = facade.findAll();
        assertEquals(List.of(outGoingDTO), actual);
    }
}
