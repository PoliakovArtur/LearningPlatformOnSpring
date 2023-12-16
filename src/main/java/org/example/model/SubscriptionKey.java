package org.example.model;


import javax.persistence.Column;
import java.io.Serializable;

public class SubscriptionKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    public SubscriptionKey() {
    }

    public SubscriptionKey(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
