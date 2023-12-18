package org.example.services.impl;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Teacher;
import org.example.repositories.TeacherRepository;
import org.example.services.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;

    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Teacher teacher) {
        if(teacher.getName() == null
                || teacher.getSalary() == null
                || teacher.getAge() == null)
            throw new BadRequestException("Недостаточно данных для создания сущности");
        repository.save(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        if(teacher.getName() == null
                && teacher.getSalary() == null
                && teacher.getAge() == null)
            throw new BadRequestException("Должно быть установлено хотя бы одно поле для обновления сущности");
        boolean isUpdated = repository.update(teacher);
        if(!isUpdated)
            throw new NotFoundException(String.format("Учитель c id {%s} не найден", teacher.getId()));
    }

    @Override
    public Teacher findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Учитель c id {%s} не найден", id)));
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = repository.findAll();
        if(teachers.isEmpty()) throw new NotFoundException("В данный момент нет ни одного сохраненного учителя");
        return teachers;
    }

    @Override
    public void deleteById(Long id) {
        if(!repository.deleteById(id))
            throw new NotFoundException(String.format("Учитель c id {%s} не найден", id));
    }
}
