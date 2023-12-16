package org.example.services;

import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.model.Student;
import org.example.model.Student;
import org.example.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public void save(Student student) {
        if(student.getName() == null
                || student.getAge() == null)
            throw new BadRequestException("Недостаточно данных для создания сущности");
        repository.save(student);
    }

    public void update(Student student) {
        if(student.getName() == null
                && student.getAge() == null)
            throw new BadRequestException("Должно быть установлено хотя бы одно поле для обновления сущности");
        boolean isUpdated = repository.update(student);
        if(!isUpdated)
            throw new NotFoundException(String.format("Студент c id {%s} не найден", student.getId()));
    }

    public Student findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Студент c id {%s} не найден", id)));
    }

    public List<Student> findAll() {
        List<Student> Students = repository.findAll();
        if(Students.isEmpty()) throw new NotFoundException("В данный момент нет ни одного сохраненного студента");
        return Students;
    }

    public void deleteById(Long id) {
        if(!repository.deleteById(id))
            throw new NotFoundException(String.format("Студент c id {%s} не найден", id));
    }
}
