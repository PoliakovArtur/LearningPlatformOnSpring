package org.example.repositories;

import org.example.model.Subscription;
import org.example.model.SubscriptionKey;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    void save(SubscriptionKey id);

    boolean update(SubscriptionKey id);

    Optional<Subscription> findById(SubscriptionKey id);

    List<Subscription> findAll();

    boolean deleteById(SubscriptionKey id);
}
