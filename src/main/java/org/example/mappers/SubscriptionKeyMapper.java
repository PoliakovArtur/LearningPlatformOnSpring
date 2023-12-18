package org.example.mappers;

import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.model.SubscriptionKey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionKeyMapper {

    SubscriptionKey fromDto(SubscriptionIncomingDTO incomingDTO);

}
