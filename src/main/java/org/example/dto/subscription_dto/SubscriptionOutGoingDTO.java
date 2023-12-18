package org.example.dto.subscription_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.dto.StudentDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubscriptionOutGoingDTO {

    @JsonProperty("subscription_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime subscriptionDate;

    private StudentDTO student;

    private CourseForSubscriptionDTO course;

    public SubscriptionOutGoingDTO(LocalDateTime subscriptionDate, StudentDTO student, CourseForSubscriptionDTO course) {
        this.subscriptionDate = subscriptionDate;
        this.student = student;
        this.course = course;
    }

    public SubscriptionOutGoingDTO() {
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public CourseForSubscriptionDTO getCourse() {
        return course;
    }

    public void setCourse(CourseForSubscriptionDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionOutGoingDTO that)) return false;
        return Objects.equals(getSubscriptionDate(), that.getSubscriptionDate()) && Objects.equals(getStudent(), that.getStudent()) && Objects.equals(getCourse(), that.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubscriptionDate(), getStudent(), getCourse());
    }
}
