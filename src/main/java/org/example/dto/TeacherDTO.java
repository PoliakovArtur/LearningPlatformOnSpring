package org.example.dto;

import java.util.Objects;

public class TeacherDTO {
    private Long id;
    private String name;
    private Long salary;
    private Integer age;

    public TeacherDTO(Long id, String name, Long salary, Integer age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public TeacherDTO(String name, Long salary, Integer age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public TeacherDTO() {}

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

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeacherDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getSalary(), that.getSalary()) && Objects.equals(getAge(), that.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSalary(), getAge());
    }
}
