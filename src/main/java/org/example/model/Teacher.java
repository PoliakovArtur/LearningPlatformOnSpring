package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long salary;

    private Integer age;

    public Teacher() {}

    public Teacher(Long id, String name, Long salary, Integer age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public Teacher(String name, Long salary, Integer age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public Teacher(Long id) {
        this.id = id;
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
        if (!(o instanceof Teacher teacher)) return false;
        return Objects.equals(getId(), teacher.getId()) && Objects.equals(getName(), teacher.getName()) && Objects.equals(getSalary(), teacher.getSalary()) && Objects.equals(getAge(), teacher.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSalary(), getAge());
    }
}
