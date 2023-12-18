package org.example.services.impl;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Student;
import org.example.repositories.StudentRepository;
import org.example.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Student student) {
        if(student.getName() == null
                || student.getAge() == null)
            throw new BadRequestException("Недостаточно данных для создания сущности");
        repository.save(student);
    }

    @Override
    public void update(Student student) {
        if(student.getName() == null
                && student.getAge() == null)
            throw new BadRequestException("Должно быть установлено хотя бы одно поле для обновления сущности");
        boolean isUpdated = repository.update(student);
        if(!isUpdated)
            throw new NotFoundException(String.format("Студент c id {%s} не найден", student.getId()));
    }

    @Override
    public Student findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Студент c id {%s} не найден", id)));
    }

    @Override
    public List<Student> findAll() {
        List<Student> Students = repository.findAll();
        if(Students.isEmpty()) throw new NotFoundException("В данный момент нет ни одного сохраненного студента");
        return Students;
    }

    @Override
    public void deleteById(Long id) {
        if(!repository.deleteById(id))
            throw new NotFoundException(String.format("Студент c id {%s} не найден", id));
    }
}
