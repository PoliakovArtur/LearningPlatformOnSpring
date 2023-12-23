package org.example.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Column(name = "registration_date")
    @CreationTimestamp
    private LocalDateTime registrationDate;

    public Student(Long id, String name, Integer age, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student() {}

    public Student(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getName(), student.getName()) && Objects.equals(getAge(), student.getAge()) && Objects.equals(getRegistrationDate(), student.getRegistrationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getRegistrationDate());
    }
}
