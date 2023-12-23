package org.example.repositories.impl;

import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.repositories.SubscriptionRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final Session session;
    private final CriteriaBuilder criteriaBuilder;

    public SubscriptionRepositoryImpl(Session session) {
        this.session = session;
        this.criteriaBuilder = session.getCriteriaBuilder();
    }

    @Override
    public void save(SubscriptionKey id) {
        Transaction transaction = session.beginTransaction();
        try {
            Subscription subscription = new Subscription(id);
            session.save(subscription);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public boolean update(SubscriptionKey id) {
        Transaction transaction = session.beginTransaction();
        try {
            Subscription subscription = session.get(Subscription.class, id);
            subscription.setSubscriptionDate(LocalDateTime.now());
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return true;
    }

    @Override
    public Optional<Subscription> findById(SubscriptionKey id) {
        Transaction transaction = session.beginTransaction();
        Subscription subscription = null;
        try {
            subscription = session.get(Subscription.class, id);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return Optional.ofNullable(subscription);
    }

    @Override
    public List<Subscription> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Subscription> subscriptions = Collections.emptyList();
        try {
            CriteriaQuery<Subscription> criteriaQuery = criteriaBuilder.createQuery(Subscription.class);
            Root<Subscription> root = criteriaQuery.from(Subscription.class);
            criteriaQuery.select(root);
            subscriptions = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return subscriptions;
    }

    @Override
    public boolean deleteById(SubscriptionKey id) {
        Transaction transaction = session.beginTransaction();
        boolean isDeleted = false;
        try {
            Subscription subscription = session.get(Subscription.class, id);
            if(subscription == null) return false;
            session.delete(subscription);
            transaction.commit();
            isDeleted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isDeleted;
    }
}
