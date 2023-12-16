package org.example.services;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Teacher;
import org.example.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public void save(Teacher teacher) {
        if(teacher.getName() == null
                || teacher.getSalary() == null
                || teacher.getAge() == null)
            throw new BadRequestException("Недостаточно данных для создания сущности");
        repository.save(teacher);
    }

    public void update(Teacher teacher) {
        if(teacher.getName() == null
                && teacher.getSalary() == null
                && teacher.getAge() == null)
            throw new BadRequestException("Должно быть установлено хотя бы одно поле для обновления сущности");
        boolean isUpdated = repository.update(teacher);
        if(!isUpdated)
            throw new NotFoundException(String.format("Учитель c id {%s} не найден", teacher.getId()));
    }

    public Teacher findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Учитель c id {%s} не найден", id)));
    }

    public List<Teacher> findAll() {
        List<Teacher> teachers = repository.findAll();
        if(teachers.isEmpty()) throw new NotFoundException("В данный момент нет ни одного сохраненного учителя");
        return teachers;
    }

    public void deleteById(Long id) {
        if(!repository.deleteById(id))
            throw new NotFoundException(String.format("Учитель c id {%s} не найден", id));
    }
}
