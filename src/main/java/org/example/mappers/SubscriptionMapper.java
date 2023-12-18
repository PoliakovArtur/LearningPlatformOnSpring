package org.example.mappers;

import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.model.Subscription;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionOutGoingDTO toDTO(Subscription subscription);

    List<SubscriptionOutGoingDTO> toDTOList(List<Subscription> subscriptions);

}
