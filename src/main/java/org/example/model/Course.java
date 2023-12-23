package org.example.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CourseType type;

    private String description;

    @ManyToOne
    private Teacher teacher;

    private Long price;

    @ManyToMany
    @JoinTable(name = "subscriptions",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public Course() {}

    public Course(Long id, String name, CourseType type, String description, Teacher teacher, Long price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.teacher = teacher;
        this.price = price;
    }

    public Course(String name, CourseType type, String description, Teacher teacher, Long price) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.teacher = teacher;
        this.price = price;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(getId(), course.getId()) && Objects.equals(getName(), course.getName()) && getType() == course.getType() && Objects.equals(getDescription(), course.getDescription()) && Objects.equals(getTeacher(), course.getTeacher()) && Objects.equals(getPrice(), course.getPrice()) && Objects.equals(getStudents(), course.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getDescription(), getTeacher(), getPrice(), getStudents());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", teacher=" + teacher +
                ", price=" + price +
                ", students=" + students +
                '}';
    }
}
