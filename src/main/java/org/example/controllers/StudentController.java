package org.example.controllers;

import org.example.dto.MessageDTO;
import org.example.dto.StudentDTO;
import org.example.facade.StudentServiceAdapterToMapper;
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
@RequestMapping("/students")
public class StudentController {

    private StudentServiceAdapterToMapper adapter;

    public StudentController(StudentServiceAdapterToMapper adapter) {
        this.adapter = adapter;
    }

    @GetMapping()
    public List<StudentDTO> findAll() {
        return adapter.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO findById(@PathVariable("id") long id) {
        return adapter.findById(id);
    }

    @PostMapping
    public MessageDTO save(@RequestBody StudentDTO StudentDTO) {
        adapter.save(StudentDTO);
        return new MessageDTO("Студент сохранен");
    }

    @PutMapping("/{id}")
    public MessageDTO updateById(@PathVariable("id") long id, @RequestBody StudentDTO StudentDTO) {
        adapter.update(id, StudentDTO);
        return new MessageDTO(String.format("Данные о студенте c id {%d} обновлены", id));
    }

    @DeleteMapping("/{id}")
    public MessageDTO deleteById(@PathVariable("id") long id) {
        adapter.deleteById(id);
        return new MessageDTO(String.format("Студент с id {%d} успешно удален", id));
    }
}
