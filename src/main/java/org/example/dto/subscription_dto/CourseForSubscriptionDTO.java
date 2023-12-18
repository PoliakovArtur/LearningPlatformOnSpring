package org.example.dto.subscription_dto;

import org.example.model.CourseType;

import java.util.Objects;

public class CourseForSubscriptionDTO {

    public CourseForSubscriptionDTO(Long id, String name, CourseType type, Long price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public CourseForSubscriptionDTO() {
    }

    private Long id;

    private String name;

    private CourseType type;

    private Long price;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseForSubscriptionDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && getType() == that.getType() && Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getPrice());
    }
}
