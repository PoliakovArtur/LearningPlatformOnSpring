package org.example.facade;

import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;

import java.util.List;

public interface SubscriptionServiceAdapterToMapper {

    void save(SubscriptionIncomingDTO incomingDto);

    void update(SubscriptionIncomingDTO incomingDto);

    SubscriptionOutGoingDTO findById(SubscriptionIncomingDTO incomingDto);

    List<SubscriptionOutGoingDTO> findAll();

    void deleteById(SubscriptionIncomingDTO incomingDto);
}
