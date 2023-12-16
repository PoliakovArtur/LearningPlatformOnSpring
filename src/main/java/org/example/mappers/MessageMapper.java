package org.example.mappers;

import org.example.dto.MessageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    String fromDTO(MessageDTO messageDTO);
    MessageDTO toDTO(String message);
}
