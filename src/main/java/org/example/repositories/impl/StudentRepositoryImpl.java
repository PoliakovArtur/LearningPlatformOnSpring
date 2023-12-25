package org.example.repositories.impl;

import org.example.model.Student;
import org.example.repositories.StudentRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final Session session;

    public StudentRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public void save(Student student) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(student);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public boolean update(Student toUpdate) {
        Transaction transaction = session.beginTransaction();
        boolean isUpdate = false;
        try {
            Student student = session.get(Student.class, toUpdate.getId());

            String name = toUpdate.getName();
            Integer age = toUpdate.getAge();

            if(age != null) student.setAge(age);
            if(name != null) student.setName(name);

            transaction.commit();
            isUpdate = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isUpdate;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Transaction transaction = session.beginTransaction();
        Student student = null;
        try {
            student = session.get(Student.class, id);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findAll() {
        Transaction transaction = session.beginTransaction();
        List<Student> students = Collections.emptyList();
        try {
            students = session.createQuery("FROM Student", Student.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return students;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = session.beginTransaction();
        boolean isDeleted = false;
        try {
            Student student = session.get(Student.class, id);
            if(student == null) return false;
            session.remove(student);
            transaction.commit();
            isDeleted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return isDeleted;
    }
}
