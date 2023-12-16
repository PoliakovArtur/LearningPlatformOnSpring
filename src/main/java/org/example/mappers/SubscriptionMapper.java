package org.example.mappers;

import org.example.dto.subscription_dto.SubscriptionOutGoingDto;
import org.example.model.Subscription;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionOutGoingDto toDTO(Subscription subscription);
    List<SubscriptionOutGoingDto> toDTOList(List<Subscription> subscriptions);
}
