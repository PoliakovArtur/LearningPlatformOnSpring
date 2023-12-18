package org.example.сontrollers;

import org.example.dto.MessageDTO;
import org.example.dto.course_dto.CourseIncomingDTO;
import org.example.dto.course_dto.CourseOutgoingDTO;
import org.example.facade.CourseFacade;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseFacade facade;

    public CourseController(CourseFacade facade) {
        this.facade = facade;
    }

    @GetMapping()
    public List<CourseOutgoingDTO> findAll() {
        return facade.findAll();
    }

    @GetMapping("/{id}")
    public CourseOutgoingDTO findById(@PathVariable("id") long id) {
        return facade.findById(id);
    }

    @PostMapping
    public MessageDTO save(@RequestBody CourseIncomingDTO incomingDTO) {
        facade.save(incomingDTO);
        return new MessageDTO("Курс сохранен");
    }

    @PutMapping("/{id}")
    public MessageDTO updateById(@PathVariable("id") long id, @RequestBody CourseIncomingDTO incomingDTO) {
        facade.update(id, incomingDTO);
        return new MessageDTO(String.format("Данные о курсе c id {%d} обновлены", id));
    }

    @DeleteMapping("/{id}")
    public MessageDTO deleteById(@PathVariable("id") long id) {
        facade.deleteById(id);
        return new MessageDTO(String.format("Курс с id {%d} успешно удален", id));
    }
}
