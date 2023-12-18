package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudentDTO {

    private Long id;

    private String name;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("registration_date")
    private LocalDateTime registrationDate;

    public StudentDTO(Long id, String name, Integer age, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public StudentDTO(String name, Integer age, LocalDateTime registrationDate) {
        this.name = name;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public StudentDTO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public StudentDTO() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDTO dto)) return false;
        return Objects.equals(getId(), dto.getId()) && Objects.equals(getName(), dto.getName()) && Objects.equals(getAge(), dto.getAge()) && Objects.equals(getRegistrationDate(), dto.getRegistrationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getRegistrationDate());
    }
}
