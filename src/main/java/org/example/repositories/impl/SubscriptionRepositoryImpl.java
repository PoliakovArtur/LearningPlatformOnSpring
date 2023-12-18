package org.example.repositories.impl;

import org.example.model.Subscription;
import org.example.model.SubscriptionKey;
import org.example.repositories.SubscriptionRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
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
            subscription.setSubscriptionDate(LocalDateTime.now());
            session.save(subscription);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
            throw new InternalError();
        }
    }

    @Override
    public boolean update(SubscriptionKey id) {
        Transaction transaction = session.beginTransaction();
        int updateRows = 0;
        try {
            CriteriaUpdate<Subscription> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Subscription.class);
            Root<Subscription> root = criteriaUpdate.from(Subscription.class);
            criteriaUpdate
                    .where(criteriaBuilder.equal(root.get("id"), id))
                    .set("subscriptionDate", LocalDateTime.now());
            updateRows = session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return updateRows > 0;
    }

    @Override
    public Optional<Subscription> findById(SubscriptionKey id) {
        Transaction transaction = session.beginTransaction();
        Subscription subscription = null;
        try {
            CriteriaQuery<Subscription> criteriaQuery = criteriaBuilder.createQuery(Subscription.class);
            Root<Subscription> root = criteriaQuery.from(Subscription.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            subscription = session.createQuery(criteriaQuery).getSingleResult();
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
        int deleteRows = 0;
        try {
            CriteriaDelete<Subscription> criteriaDelete = criteriaBuilder.createCriteriaDelete(Subscription.class);
            Root<Subscription> root = criteriaDelete.from(Subscription.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
            deleteRows = session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return deleteRows > 0;
    }
}
