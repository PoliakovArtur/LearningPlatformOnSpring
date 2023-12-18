package org.example.mappers;

import org.example.dto.StudentDTO;
import org.example.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student fromDTO(StudentDTO studentDTO);

    StudentDTO toDTO(Student student);

    List<StudentDTO> toDtoList(List<Student> students);
}
