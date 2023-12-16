package org.example.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Column(name = "subscription_date")
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
    public String toString() {
        return "{" +
                "subscription_date=" + subscriptionDate +
                ", id=" + id +
                '}';
    }
}
