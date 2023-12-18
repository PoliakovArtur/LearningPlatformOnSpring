package org.example.dto.course_dto;

import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.model.CourseType;

import java.util.List;
import java.util.Objects;

public class CourseOutgoingDTO {

    private Long id;

    private String name;

    private CourseType type;

    private String description;

    private TeacherDTO teacher;

    private List<StudentDTO> students;

    private Long price;


    public CourseOutgoingDTO(Long id, String name, CourseType type, String description, TeacherDTO teacher, List<StudentDTO> students, Long price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.teacher = teacher;
        this.students = students;
        this.price = price;
    }

    public CourseOutgoingDTO() {
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

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
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
        if (!(o instanceof CourseOutgoingDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && getType() == that.getType() && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getTeacher(), that.getTeacher()) && Objects.equals(getStudents(), that.getStudents()) && Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getDescription(), getTeacher(), getStudents(), getPrice());
    }
}
