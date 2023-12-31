package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Column(name = "subscription_date")
    @CreationTimestamp
    private LocalDateTime subscriptionDate;

    @EmbeddedId
    private SubscriptionKey id;

    @Transient
    private Student student;

    @Transient
    private Course course;

    public Subscription() {}

    public Subscription(SubscriptionKey id) {
        this.id = id;
    }

    public Subscription(LocalDateTime subscriptionDate, SubscriptionKey id) {
        this.subscriptionDate = subscriptionDate;
        this.id = id;
    }

    public SubscriptionKey getId() {
        return id;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public void setId(SubscriptionKey id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription that)) return false;
        return Objects.equals(getSubscriptionDate(), that.getSubscriptionDate()) && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubscriptionDate(), getId());
    }
}
