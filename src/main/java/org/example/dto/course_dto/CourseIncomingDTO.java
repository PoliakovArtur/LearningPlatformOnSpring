package org.example.dto.course_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.model.CourseType;

import java.util.Objects;

public class CourseIncomingDTO {

    private Long id;

    private String name;

    private CourseType type;

    private String description;

    @JsonProperty("teacher_id")
    private Long teacherId;

    private Long price;

    public CourseIncomingDTO(Long id, String name, CourseType type, String description, Long teacherId, Long price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.teacherId = teacherId;
        this.price = price;
    }

    public CourseIncomingDTO(String name, CourseType type, String description, Long teacherId, Long price) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.teacherId = teacherId;
        this.price = price;
    }

    public CourseIncomingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseIncomingDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && getType() == that.getType() && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getTeacherId(), that.getTeacherId()) && Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getDescription(), getTeacherId(), getPrice());
    }
}
