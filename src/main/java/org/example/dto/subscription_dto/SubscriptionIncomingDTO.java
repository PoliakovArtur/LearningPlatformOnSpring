package org.example.dto.subscription_dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionIncomingDTO {
    @JsonProperty("course_id")
    private Long courseId;
    @JsonProperty("student_id")
    private Long studentId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
