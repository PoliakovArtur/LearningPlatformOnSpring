package org.example.mappers;

import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.dto.subscription_dto.CourseForSubscriptionDTO;
import org.example.model.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course fromDTO(CourseIncomingDTO courseDTO);

    CourseOutgoingDTO toDTO(Course course);

    List<CourseOutgoingDTO> toDtoList(List<Course> course);

    CourseForSubscriptionDTO toDtoForSubscription(Course course);
}
