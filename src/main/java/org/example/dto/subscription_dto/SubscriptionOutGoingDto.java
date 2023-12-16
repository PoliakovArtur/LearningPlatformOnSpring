package org.example.dto.subscription_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.dto.StudentDTO;

import java.time.LocalDateTime;

public class SubscriptionOutGoingDto {
    @JsonProperty("subscription_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime subscriptionDate;
    private StudentDTO student;
    private CourseForSubscriptionDto course;

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

    public CourseForSubscriptionDto getCourse() {
        return course;
    }

    public void setCourse(CourseForSubscriptionDto course) {
        this.course = course;
    }
}
