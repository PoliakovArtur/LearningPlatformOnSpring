package org.example.facade.impl;

import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.facade.SubscriptionServiceAdapterToMapper;
import org.example.mappers.SubscriptionKeyMapper;
import org.example.mappers.SubscriptionMapper;
import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.services.SubscriptionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionServiceAdapterToMapperImpl implements SubscriptionServiceAdapterToMapper {

    private final SubscriptionService service;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionKeyMapper subscriptionKeyMapper;

    public SubscriptionServiceAdapterToMapperImpl(SubscriptionService service, SubscriptionMapper mapper, SubscriptionKeyMapper subscriptionKeyMapper) {
        this.service = service;
        this.subscriptionMapper = mapper;
        this.subscriptionKeyMapper = subscriptionKeyMapper;
    }

    @Override
    public void save(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDto(incomingDto);
        service.save(id);
    }

    @Override
    public void update(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDto(incomingDto);
        service.update(id);
    }

    @Override
    public SubscriptionOutGoingDTO findById(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDto(incomingDto);
        Subscription subscription = service.findById(id);
        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public List<SubscriptionOutGoingDTO> findAll() {
        List<Subscription> subscriptions = service.findAll();
        return subscriptionMapper.toDTOList(subscriptions);
    }

    @Override
    public void deleteById(SubscriptionIncomingDTO incomingDto) {
        SubscriptionKey id = subscriptionKeyMapper.fromDto(incomingDto);
        service.deleteById(id);
    }
}
