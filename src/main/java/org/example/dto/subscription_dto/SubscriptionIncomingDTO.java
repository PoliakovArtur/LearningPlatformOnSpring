package org.example.dto.subscription_dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SubscriptionIncomingDTO {

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("student_id")
    private Long studentId;

    public SubscriptionIncomingDTO(Long courseId, Long studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public SubscriptionIncomingDTO() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionIncomingDTO that)) return false;
        return Objects.equals(getCourseId(), that.getCourseId()) && Objects.equals(getStudentId(), that.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getStudentId());
    }
}
