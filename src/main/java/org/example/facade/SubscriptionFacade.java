package org.example.facade;

import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDto;
import org.example.mappers.SubscriptionKeyMapper;
import org.example.mappers.SubscriptionMapper;
import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.services.SubscriptionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionFacade {

    private SubscriptionService service;
    private SubscriptionMapper subscriptionMapper;
    private SubscriptionKeyMapper subscriptionKeyMapper;

    public SubscriptionFacade(SubscriptionService service, SubscriptionMapper mapper, SubscriptionKeyMapper subscriptionKeyMapper) {
        this.service = service;
        this.subscriptionMapper = mapper;
        this.subscriptionKeyMapper = subscriptionKeyMapper;
    }

    public void save(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDTO(incomingDto);
        service.save(id);
    }

    public void update(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDTO(incomingDto);
        service.update(id);
    }

    public SubscriptionOutGoingDto findById(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDTO(incomingDto);
        Subscription subscription = service.findById(id);
        return subscriptionMapper.toDTO(subscription);
    }

    public List<SubscriptionOutGoingDto> findAll() {
        List<Subscription> subscriptions = service.findAll();
        return subscriptionMapper.toDTOList(subscriptions);
    }

    public void deleteById(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDTO(incomingDto);
        service.deleteById(id);
    }
}
