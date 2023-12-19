package org.example.controllers;

import org.example.dto.MessageDTO;
import org.example.dto.TeacherDTO;
import org.example.facade.TeacherFacade;
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
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherFacade facade;

    public TeacherController(TeacherFacade facade) {
        this.facade = facade;
    }

    @GetMapping()
    public List<TeacherDTO> findAll() {
        return facade.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDTO findById(@PathVariable("id") long id) {
        return facade.findById(id);
    }

    @PostMapping
    public MessageDTO save(@RequestBody TeacherDTO teacherDTO) {
        facade.save(teacherDTO);
        return new MessageDTO("Учитель сохранен");
    }

    @PutMapping("/{id}")
    public MessageDTO updateById(@PathVariable("id") long id, @RequestBody TeacherDTO teacherDTO) {
        facade.update(id, teacherDTO);
        return new MessageDTO(String.format("Данные об учителе c id {%d} обновлены", id));
    }

    @DeleteMapping("/{id}")
    public MessageDTO deleteById(@PathVariable("id") long id) {
        facade.deleteById(id);
        return new MessageDTO(String.format("Учитель с id {%d} успешно удален", id));
    }
}
