package org.example.mappers;

import org.example.dto.TeacherDTO;
import org.example.model.Teacher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDTO toDTO(Teacher teacher);

    Teacher fromDTO(TeacherDTO teacherDTO);

    List<TeacherDTO> toDtoList(List<Teacher> teachers);

}
