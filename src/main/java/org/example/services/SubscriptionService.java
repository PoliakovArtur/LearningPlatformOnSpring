package org.example.services;

import org.example.model.Subscription;
import org.example.model.SubscriptionKey;

import java.util.List;

public interface SubscriptionService {

    void save(SubscriptionKey id);

    void update(SubscriptionKey id);

    Subscription findById(SubscriptionKey id);

    List<Subscription> findAll();

    void deleteById(SubscriptionKey id);
}
